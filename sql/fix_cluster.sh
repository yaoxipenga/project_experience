#!/usr/bin/env bash
#此脚本在集群主节点上执行即可
#停止所有节点上的mysql容器
echo "停止当前节点上的mysql容器"
docker stop mysql
echo "停止子节点上的mysql容器"
ssh root@192.168.4.21 docker stop mysql
#删除所有节点信息
echo "删除所有节点信息"
curl -s 'http://192.168.4.10:2379/v2/keys/pxc-cluster/cluster_mect?recursive=true' -XDELETE
#修改主节点上的配置文件,将其设置为主节点
echo "修改主节点上的配置文件,将其设置为主节点"
sed -i "s/safe_to_bootstrap: 0/safe_to_bootstrap: 1/g" /var/lib/docker/volumes/mysql_node/_data/grastate.dat
#启动主节点上的mysql容器
echo "启动主节点上的mysql容器"
docker start mysql
echo "睡眠30秒，等待出现子节点信息"
sleep 30
echo "启动子节点上的mysql容器"
ssh root@192.168.4.21 docker start mysql
exit

