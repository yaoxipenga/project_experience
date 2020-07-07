local function close_redis(red)  
    if not red then  
        return  
    end  
    local ok, err = red:close()  
    if not ok then  
        ngx.say("close redis error : ", err)  
    end  
end  
  
local redis = require("resty.redis")  
  
--创建实例  
local red = redis:new()  
--设置超时（毫秒）  
red:set_timeout(1000)  
--建立连接  

local ip = "192.168.81.235"  
local port = 6379  
local ok, err = red:connect(ip, port)  
if not ok then  
    ngx.say("connect to redis error : ", err)  
    return close_redis(red)  
end 
--local res, err = red:auth("shiyuesoft")
--if not res then
    --ngx.say("failed to authenticate: ", err)
    --return
--end

ok, err = red:select(0)
if not ok then
    ngx.say("failed to select db: ", err)
    return 
end
--调用API进行处理  
--ok, err = red:set("token2", "ppp")  
--if not ok then  
    --ngx.say("set msg error : ", err)  
    --return close_redis(red)  
--end  

--调用API获取数据
--ngx.req.read_body()
local args = ngx.req.get_uri_args()

--local id = ngx.req.get_body_data("id")
--local url = ngx.req.get_body_data("url")
--ngx.req.get_uri_args();
local id = args["id"];
local url = args["url"]  
if not id then  
    close_redis(red)
    ngx.exec("@error")
end  
if not url then  
    close_redis(red)
    ngx.exec("@error")
end  

local resp, err = red:hset("servers_id", id, url)  
if not resp then  
	close_redis(red)
    ngx.exec("@error")  
end  
local resp, err = red:sadd("servers", url)
if not resp then  
	close_redis(red)
    ngx.exec("@error")  
end  
close_redis(red)
ngx.exec("/insert")
  
