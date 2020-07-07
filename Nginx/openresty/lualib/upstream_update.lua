local _M = { _VERSION = '1.0' }  
local function split( str,reps )  
    local resultStrList = {}  
    string.gsub(str,'[^'..reps..']+',function ( w )  
        table.insert(resultStrList,w)  
    end)  
    return resultStrList  
end  
  
local function get_user_define()  
    local user_define=ngx.shared['user_define']  
    if not user_define then  
        return nil, "share memory variable[user_define] not found"  
        else  
      return user_define, ""  
        end  
end  
  
--add server to user_define  
local function common_user_define(func_name)  
     user_define,message=get_user_define()  
     if not user_define then  
           ngx.say("failed to get share memory variable [user_define]: ", message)  
       return  
     end  
     ngx.req.read_body()  
     local args, err = ngx.req.get_post_args()  
   if err == "truncated" then  
       -- one can choose to ignore or reject the current request here  
   end  
   if not args['server'] then  
       ngx.say("failed to get post args: ", err)  
       return  
   else  
          if type(args['server']) ~= 'table' then
			server_list=split(args['server'],',')  
		else  
			server_list=args['server']  
		end  
         func_name(server_list,user_define)  
   end  
end  
  
local function add_user_func(server_list,user_define)  
         for _,value in ipairs(server_list) do  
                    --table.insert(user_define,value)  
                        succ, err, forcible=user_define:set(value, 0)  
                    if not succ then  
                        ngx.say("failed to set share memory variable [user_define]", err)  
                        return nil  
                    end  
        end  
                local temp_list=user_define:get_keys()  
        ngx.say("OK,all server: ", table.concat(temp_list,", "))  
        ngx.say(user_define)
        return user_define  
end  
  
function _M.add_user_define()  
        common_user_define(add_user_func)  
end  
--del server to user_define  
local function del_user_func(server_list,user_define)  
         for _,value in ipairs(server_list) do  
                succ, err, forcible=user_define:delete(value)  
          if not succ then  
                    ngx.say("failed to set share memory variable [user_define]", err)  
                    break  
           end  
       end  
       local temp_list=user_define:get_keys()  
       ngx.say("OK,all server: ", table.concat(temp_list,", "))  
end  
  
function _M.del_user_define()  
        common_user_define(del_user_func)  
end  
  
return _M