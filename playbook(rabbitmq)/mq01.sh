#!/usr/bin/bash 
rabbitmqctl set_policy ha-all '^(?!amq\.).*' '{"ha-mode": "all"}'
rabbitmqctl add_user  admin 123456
rabbitmqctl  set_permissions -p / admin '.*' '.*' '.*'
rabbitmqctl set_user_tags admin administrator
curl 192.168.1.11:15672 >> /dev/null 
if [ $? -eq 0 ];then 
	echo "deploy success"
else
	echo "deploy failure"
fi 


