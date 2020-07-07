#!/usr/bin/env bash
echo -e "\033[1;31m  此脚本自动生成keepalived的配置文件 \033[0m"
SCRIPT_PATH="/usr/local/src/check_nginx.sh"
echo -e "\033[1;32m  查看IP状态 \033[0m"
ip a
echo -n -e "\033[1;32m  请输入节点ID(此ID与其他节点的ID不能重复,如nginx_master): \033[0m"
read ROUTER_ID
echo -n -e "\033[1;32m  请输入keepalived角色(主节点为MASTER，备份节点为BACKUP，且必须全部大写): \033[0m"
read STATE
echo -n -e "\033[1;32m  请输入节点的执行优先级(0-255,主节点的优先级必须大于从节点，并且从节点的优先级不能一样): \033[0m"
read PRIORITY
echo -n -e "\033[1;32m  请输入用于数据传输的网卡设备号: \033[0m"
read INTERFACE
echo -n -e "\033[1;32m  请输入虚拟虚拟路由编号(主从必须保持一致): \033[0m"
read ROUTE_ID
echo -n -e "\033[1;32m  请输入虚拟IP地址: \033[0m"
read VIP
cat <<EOF >/etc/keepalived/keepalived.conf
global_defs {
    notification_email {
        example@email.com
    }
    notification_email_from sns-lvs@gmail.com
    smtp_server smtp.hysec.com
    #smtp_connection_timeout 30
    router_id ${ROUTER_ID}          # 设置nginx master的id，在一个网络应该是唯一的
}
vrrp_script chk_http_port {
    script ${SCRIPT_PATH}           #最后手动执行下此脚本，以确保此脚本能够正常执行
    interval 2                      #（检测脚本执行的间隔，单位是秒）
    weight 2
}
vrrp_instance VI_1 {
    state ${STATE}                  # 指定keepalived的角色，MASTER为主，BACKUP为备
    interface ${INTERFACE}          # 当前进行vrrp通讯的网络接口卡(当前centos的网卡)
    virtual_router_id ${ROUTE_ID}   # 虚拟路由编号，主从要一致
    priority ${PRIORITY}            # 优先级，数值越大，获取处理请求的优先级越高
    advert_int 1                    # 检查间隔，默认为1s(vrrp组播周期秒数)
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    track_script {
        chk_http_port               #（调用检测脚本）
    }
    virtual_ipaddress {
        ${VIP}                      # 定义虚拟ip(VIP)，可多设，每行一个
    }
}
EOF
echo -e "\033[1;32m  配置文件生成成功，查看配置文件 \033[0m"
cat /etc/keepalived/keepalived.conf
echo -e "\033[1;32m  赋予配置文件644权限 \033[0m"
chmod 644  /etc/keepalived/keepalived.conf
echo -e "\033[1;32m  设置keepalived服务开机启动 \033[0m"
systemctl enable keepalived
echo -e "\033[1;32m  查看当前keepalived服务状态 \033[0m"
systemctl status keepalived
echo -n -e "\033[1;32m  是否启动keepalived服务(y/n) \033[0m"
read CHOICE
if [[ "${CHOICE}" == "y" ]];then
    echo -e "\033[1;32m  启动keepalived服务 \033[0m"
    systemctl restart keepalived
    echo -e "\033[1;32m  查看keepalived服务状态 \033[0m"
    systemctl status keepalived
else
    echo -e "\033[1;32m  暂不操作 \033[0m"
fi
echo -e "\033[1;32m  keepalived服务部署成功 \033[0m"
exit
