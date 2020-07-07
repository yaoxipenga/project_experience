local balancer = require "ngx.balancer"
local now_server = ngx.shared.now_server;
local servers = ngx.shared.servers;
local server_len = ngx.shared.lens;
local redis = require "resty.redis"
 
local _M = { _VERSION = '1.0' }
 
local function con_redis()
	local red = redis:new()
	red:set_timeout(1000)
	local ok, err = red:connect("192.168.81.235", 6379)--注意此处
    if not ok then
        ngx.log(ngx.ERR,"failed to connect: ", err)
        return ngx.exit(500)
    else
    	return red
    end
end
 
local function set_peer(host,port)
	if not host and not port then
	    ngx.log(ngx.ERR, " host and port can not be nil")
        return ngx.exit(500)
	end
 
	local ok, err = balancer.set_current_peer(host, port)
    if not ok then
        ngx.log(ngx.ERR, "failed to set the current peer: ", err)
        return ngx.exit(500)
    end
end
 
local function split( str,reps)
    local resultStrList = {}
    string.gsub(str,'[^'..reps..']+',function ( w )
        table.insert(resultStrList,w)
    end)
    return resultStrList
end
 
local function test_server()
	 local value, err = servers:get(1)
	 if  not value then
	 	ngx.log(ngx.ERR, "no server found: ", err)
	 	ngx.status=ngx.HTTP_INTERNAL_SERVER_ERROR 
	 	return ngx.say("no server found")
	 else
	 	return true
	 end
end
 
local function fail_say( fail_str )
	-- body
	ngx.log(ngx.ERR, fail_str)
	ngx.status = ngx.HTTP_INTERNAL_SERVER_ERROR 
	return ngx.say(fail_str)
end
 
local function get_lens()
	-- body
	local len, flags = server_len:get("len")
	if not len then
		local fail_str = "failed to get len "..flags
		fail_say(fail_str)
	end
	return len
end
 
local function get_nums()
	local value = now_server:get("nums")
	if not value then
		local fail_str = 'reset  nums  to 1'
		ngx.log(ngx.ERR, fail_str)
		local ok, err = now_server:set("nums",1)
		if not ok then
			fail_str = 'failed to set nums '..err
			fail_say(fail_str)
		else
			return -1
		end
	end
	return value
end
 
local function round_server()
	-- body
	local value = get_nums()
	if not value then
		return
	end
	--ngx.log(ngx.ERR, '---->','ago',value)
	local len = get_lens()
	if not len then
		return
	end
	if value+1 >= len then
		local ok, err = now_server:set("nums",1)
		if not ok then
			fail_str = 'failed to set nums '..err
			fail_say(fail_str)
		else
			return 1
		end
	else
		local ok, err = now_server:set( "nums", 2+value )
		if not ok then
			fail_str = 'failed to set nums '..err
			fail_say(fail_str)
		else
			return 2+value
		end
	end 
end
 
local function  handle_redis(red)
	if not red then
		return 
	end
    local res, err = red:smembers("servers")
    if not res then
        ngx.log(ngx.ERR,"failed to smembers servers: ", err)
        return ngx.exit(500)
    else
--    	res = {"192.168.81.243:80","192.168.81.235:80"}
    	return res
    end
 
end
 
function _M.insert_server()
	-- body
	local red = con_redis()
	if not red then
		return
	end
	local red_result = handle_redis(red)
	--ngx.log(ngx.ERR,'-->',red_result[1])
	if not red_result then
		red:close()
		return
	end
	local nums = 0
	for  k,v in pairs(red_result) do
		local resultStrList = split(v,':')
		--ngx.log(ngx.ERR,'-->', resultStrList[1], resultStrList[2])
		if  resultStrList[1] and  resultStrList[2] then
			local res, err = servers:set(2*k-1, resultStrList[1])
			if not res then
        		ngx.log(ngx.ERR,"failed to set servers first: ", err)
        		red:close()
        		return ngx.exit(500)
        	end
        	--ngx.log(ngx.ERR,'-->',2*k-1,'--->',resultStrList[1])
        	--ngx.log(ngx.ERR,'-->',2*k,'--->',resultStrList[2])
        	local res1, err1 = servers:set(2*k, resultStrList[2])
        	if not res1 then
        		ngx.log(ngx.ERR,"failed to set servers second: ", err1)
        		local res2, err2 = servers:rpop(k)
        		if not res2 then
        			ngx.log(ngx.ERR,"failed to rpop servers first: ", err2)
        		end
        		red:close()
        		return ngx.exit(500)
        	else
        		nums = nums+1
        		--ngx.log(ngx.ERR,'-->', nums)
        	end
		end
	end
	local res, err = server_len:set('len', nums*2)
	if not res then
        ngx.log(ngx.ERR,"failed to set servers first: ", err)
        return ngx.exit(500)
    else
    	ngx.say('ok,all is ', nums)
    	return true
    end
end
 
function _M.run_peer()
	local ok = test_server()
	if not ok then
		return		
	end
	local len = round_server()
	if not len then
		return		
	end
	--ngx.log(ngx.ERR,'--->', len)--可以去掉，调试时使用
	local host ,err = servers:get(len)
	if not host then
		ngx.log(ngx.ERR,"failed to get server-host: ", err)
        return ngx.exit(500)
	end
	--ngx.log(ngx.ERR,"get server: ", host, '----', len+1)
	local port ,err = servers:get(len+1)
	if not port then
		ngx.log(ngx.ERR,"failed to get server-port: ", err)
        return ngx.exit(500)
	end
	--ngx.log(ngx.ERR, "set peer ", host, ':', port) --可以去掉，调试时使用
	set_peer(host, port)
end
 
return _M