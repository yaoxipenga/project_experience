#!/usr/bin/env bash 
NODE1_IP_ADDR=${1:-"http://192.168.4.11"}
NODE2_IP_ADDR=${2:-"http://192.168.4.21"}
docker stop etcd
docker rm etcd
docker volume rm etcd-data
docker run -d --name etcd \
    -p 2379:2379 \
    -p 2380:2380 \
    --restart=always \
    -v /root/sql/localtime:/etcd/localtime \
    --volume=etcd-data:/etcd-data \
    quay.io/coreos/etcd \
    /usr/local/bin/etcd \
    --data-dir=/etcd-data --name node1 \
    --initial-advertise-peer-urls ${NODE1_IP_ADDR}:2380 \
    --listen-peer-urls http://0.0.0.0:2380 \
    --advertise-client-urls ${NODE1_IP_ADDR}:2379 \
    --listen-client-urls http://0.0.0.0:2379 \
    --initial-cluster-state new \
    --initial-cluster-token docker-etcd \
    --initial-cluster node1=${NODE1_IP_ADDR}:2380,node2=${NODE2_IP_ADDR}:2380
exit
