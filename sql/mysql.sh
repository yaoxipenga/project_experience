#!/usr/bin/env bash
ENTRANCE_IP_ADDR=${1:-"192.168.4.10"}
CLUSTER_NAME=cluster_mect
HOST_PORT=${ENTRANCE_IP_ADDR}:2379
NETWORK_NAME=host
PASSWORD=mect888!
#docker network create -d bridge $NETWORK_NAME
#docker stop $(docker ps -aq |grep percona-xtradb-cluster)
#docker rm $(docker ps -aq |grep percona-xtradb-cluster)
#docker volume rm mysql_conf mysql_backup mysql_node slave_node1_conf slave_node1_data slave_node2_conf slave_node2_data

echo "Starting new node..."
docker run -d --net host --name mysql\
	 -e MYSQL_ROOT_PASSWORD=$PASSWORD \
	 -e DISCOVERY_SERVICE=$HOST_PORT \
	 -e CLUSTER_NAME=$CLUSTER_NAME \
	 -e XTRABACKUP_PASSWORD=$PASSWORD \
	 -v mysql_node:/var/lib/mysql \
	 -v mysql_conf:/etc/mysql \
	 -v mysql_backup:/data \
	 -v /root/sql/localtime:/etc/localtime \
	 --restart=always \
	 percona/percona-xtradb-cluster 
#--general-log=1 --general_log_file=/var/lib/mysql/general.log
echo "Started $(docker ps -l -q)"

# --wsrep_cluster_address="gcomm://$QCOMM"
exit
