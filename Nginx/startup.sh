#!/usr/bin/env bash
echo -e "\033[1;31m  此脚本自动配置nginx \033[0m"
work_path="/usr/local/src"
echo -e "\033[1;31m  1.复制文件 \033[0m"
echo -e "\033[1;32m  复制nginx到${work_path}中 \033[0m"
cp -r openresty/ ${work_path}/
echo -e "\033[1;32m  复制检测脚本到${work_path}中 \033[0m"
cp check_nginx.sh ${work_path}/
echo -e "\033[1;32m  查看文件结构 \033[0m"
ls -l ${work_path}/

echo -e "\033[1;31m  2.配置keepalived服务 \033[0m"
echo -e "\033[1;32m  安装keepalived服务 \033[0m"
yum -y install keepalived
echo -e "\033[1;32m  配置keepalived服务 \033[0m"
/usr/bin/bash keepalived.sh
exit
