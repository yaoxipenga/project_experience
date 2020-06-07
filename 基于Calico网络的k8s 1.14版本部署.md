**基于Calico网络的k8s 1.14版本部署**

**基于Calico网络的k8s 1.14版本部署**

**1. 集群部署架构**

参考文档：http://www.debugger.wiki/article/html/1559106039872285

**1.1 软件环境**

| 服务       | 版本       |
| ---------- | ---------- |
| OS System  | CentOS 7.6 |
| Docker     | 18.9.5-ce  |
| Kubernetes | 1.14.0     |
| Etcd       | 3.3.18     |
| Calico     | 3.6.1      |

**1.2 服务器角色**

| 角色       | IP           | 组件                                                         |
| ---------- | ------------ | ------------------------------------------------------------ |
| k8s-master | 172.12.16.28 | kube-apiserver，kube-controller-manager，kube-scheduler，etcd |
| k8s-node1  | 172.12.16.29 | kubelet，kube-proxy，docker，calico                          |
| k8s-node2  | 172.12.16.30 | kubelet，kube-proxy，docker，calico                          |

**1.3 系统初始化**

- 关闭防火墙

systemctl stop firewalld.service && systemctl disable firewalld.service 

- 关闭Selinux

setenforce 0 sed -i "s/SELINUX=enforcing/SELINUX=disabled/" /etc/selinux/config 

- 配置节点配置IP域名映射关系

每台机器都要操作 vim /etc/hosts 172.12.16.28 k8s-master 172.12.16.29 k8s-node1 172.12.16.30 k8s-node2 

- 禁用操作系统swap

swapoff -a sed -i '/^[^#]*swap/s@^@#@' /etc/fstab 

- 配置master到node节点的无密钥登录

在master节点执行，一直回车 ssh-keygen -t rsa  将公钥推送到node节点 ssh-copy-id -i ~/.ssh/id_rsa.pub 172.12.16.29 ssh-copy-id -i ~/.ssh/id_rsa.pub 172.12.16.30 

- 同步系统时间

ntpdate ntp1.aliyun.com  配置crontab定时任务，每5分钟同步一次时间 crontab -e */5 * * * * ntpdate ntp1.aliyun.com >/dev/null 2>&1 重载服务 systemctl restart crond 

**1.4 Etcd集群的搭建**

在k8s-master、k8s-node1、k8s-node2三个节点上部署etcd，并组成集群

**1.4.1 生成etcd使用的证书**

使用cfssl来生成自签证书。

在三个节点初始化工作目录：
mkdir -p /opt/etcd/{bin,ssl,cfg}
mkdir -p /opt/software

在k8s-master节点操作：
1、下载cfssl工具
wget https://pkg.cfssl.org/R1.2/cfssl_linux-amd64 -O /usr/local/bin/cfssl
wget https://pkg.cfssl.org/R1.2/cfssljson_linux-amd64 -O /usr/local/bin/cfssljson
wget https://pkg.cfssl.org/R1.2/cfssl-certinfo_linux-amd64 -O /usr/local/bin/cfssl-certinfo
chmod +x /usr/local/bin/{cfssl,cfssljson,cfssl-certinfo}

2、创建三个文件
cd /opt/etcd/ssl
# 创建Etcd根证书配置文件
cat > ca-config.json <<EOF
{
  "signing": {
    "default": {
      "expiry": "87600h"
    },
    "profiles": {
      "www": {
         "expiry": "87600h",
         "usages": [
            "signing",
            "key encipherment",
            "server auth",
            "client auth"
        ]
      }
    }
  }
}
EOF

# 创建Etcd根证书请求文件
cat > ca-csr.json <<EOF
{
    "CN": "etcd CA",
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "L": "Beijing",
            "ST": "Beijing"
        }
    ]
}
EOF

# 创建Etcd服务证书请求文件
cat > server-csr.json <<EOF
{
    "CN": "etcd",
    "hosts": [
    "172.12.16.28",
    "172.12.16.29",
    "172.12.16.30"
    ],
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "L": "BeiJing",
            "ST": "BeiJing"
        }
    ]
}
EOF

3、生成证书
# 生成Etcd根证书
cfssl gencert -initca ca-csr.json | cfssljson -bare ca -

# 生成Etcd服务证书
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=www server-csr.json | cfssljson -bare server

生成后可以看到4个pem文件
-rw------- 1 root root 1679 12月 13 16:17 ca-key.pem
-rw-r--r-- 1 root root 1265 12月 13 16:17 ca.pem
-rw------- 1 root root 1675 12月 13 16:18 server-key.pem
-rw-r--r-- 1 root root 1338 12月 13 16:18 server.pem

**1.4.2 解压Etcd**

\* 三个节点都要操作 * 下载etcd安装至/opt/software目录下并解压安装包 cd /opt/software && wget https://github.com/etcd-io/etcd/releases/download/v3.3.18/etcd-v3.3.18-linux-amd64.tar.gz tar xf etcd-v3.3.18-linux-amd64.tar.gz cd etcd-v3.3.18-linux-amd64 && mv etcd etcdctl /opt/etcd/bin/ 

**1.4.3 拷贝证书**

\* 在k8s-master操作 * cd /opt/etcd/ssl scp *.pem 172.12.16.29:/opt/etcd/ssl scp *.pem 172.12.16.30:/opt/etcd/ssl 

**1.4.4 创建etcd配置文件**

cat > /opt/etcd/cfg/etcd.conf <<EOF #[Member] ETCD_NAME="etcd01" ETCD_DATA_DIR="/opt/etcd/default.etcd" ETCD_LISTEN_PEER_URLS="https://172.12.16.28:2380" ETCD_LISTEN_CLIENT_URLS="https://172.12.16.28:2379"  #[Clustering] ETCD_INITIAL_ADVERTISE_PEER_URLS="https://172.12.16.28:2380" ETCD_ADVERTISE_CLIENT_URLS="https://172.12.16.28:2379" ETCD_INITIAL_CLUSTER="etcd01=https://172.12.16.28:2380,etcd02=https://172.12.16.29:2380,etcd03=https://172.12.16.30:2380" ETCD_INITIAL_CLUSTER_TOKEN="etcd-cluster" ETCD_INITIAL_CLUSTER_STATE="new" EOF  # 注意：每个节点上根据实际情况需要调整对应的值。 参数说明： ETCD_NAME 节点名称 ETCD_DATA_DIR 数据目录 ETCD_LISTEN_PEER_URLS 集群通信监听地址 ETCD_LISTEN_CLIENT_URLS 客户端访问监听地址 ETCD_INITIAL_ADVERTISE_PEER_URLS 集群通告地址 ETCD_ADVERTISE_CLIENT_URLS 客户端通告地址 ETCD_INITIAL_CLUSTER 集群节点地址 ETCD_INITIAL_CLUSTER_TOKEN 集群Token ETCD_INITIAL_CLUSTER_STATE 加入集群的当前状态，new是新集群，existing表示加入已有集群 

**1.4.5 Systemd管理etcd**

cat /usr/lib/systemd/system/etcd.service [Unit] Description=Etcd Server After=network.target After=network-online.target Wants=network-online.target  [Service] Type=notify EnvironmentFile=/opt/etcd/cfg/etcd.conf ExecStart=/opt/etcd/bin/etcd \ --name=${ETCD_NAME} \ --data-dir=${ETCD_DATA_DIR} \ --listen-peer-urls=${ETCD_LISTEN_PEER_URLS} \ --listen-client-urls=${ETCD_LISTEN_CLIENT_URLS},http://127.0.0.1:2379 \ --advertise-client-urls=${ETCD_ADVERTISE_CLIENT_URLS} \ --initial-advertise-peer-urls=${ETCD_INITIAL_ADVERTISE_PEER_URLS} \ --initial-cluster=${ETCD_INITIAL_CLUSTER} \ --initial-cluster-token=${ETCD_INITIAL_CLUSTER_TOKEN} \ --initial-cluster-state=new \ --cert-file=/opt/etcd/ssl/server.pem \ --key-file=/opt/etcd/ssl/server-key.pem \ --peer-cert-file=/opt/etcd/ssl/server.pem \ --peer-key-file=/opt/etcd/ssl/server-key.pem \ --trusted-ca-file=/opt/etcd/ssl/ca.pem \ --peer-trusted-ca-file=/opt/etcd/ssl/ca.pem Restart=on-failure LimitNOFILE=65536  [Install] WantedBy=multi-user.target 

**1.4.6 启动etcd并设置自启**

systemctl daemon-reload systemctl enable etcd systemctl start etcd systemctl status etcd  # 注意：如果有问题，查看日志：tail -f /var/log/messages 

**1.4.7 验证etcd集群**

/opt/etcd/bin/etcdctl \ --ca-file=/opt/etcd/ssl/ca.pem --cert-file=/opt/etcd/ssl/server.pem --key-file=/opt/etcd/ssl/server-key.pem \ --endpoints="https://172.12.16.28:2379,https://172.12.16.29:2379,https://172.12.16.30:2379" \ cluster-health  # 返回结果如下则说明集群部署成功 member 1a328b89d6d93008 is healthy: got healthy result from https://172.12.16.28:2379 member aef739db951f6fdb is healthy: got healthy result from https://172.12.16.29:2379 member d6de0446126468dd is healthy: got healthy result from https://172.12.16.30:2379 cluster is healthy 

**1.4.8 etcd加入系统环境变量**

cat > /etc/profile.d/etcd.sh <<EOF export PATH=/opt/etcd/bin:\$PATH EOF  # 重新加载环境变量 source /etc/profile  # 查看是否生效 etcdctl --version 

**1.5 Master节点部署组件**

在部署Kubernetes之前一定要确保etcd是正常工作的，无特殊说明则需在master节点上操作。

**1.5.1 生成证书**

# 初始化工作目录
mkdir -p /opt/kubernetes/{ssl,cfg,bin}

1、生成CA证书
cd /opt/kubernetes/ssl
cat > ca-config.json <<EOF
{
  "signing": {
    "default": {
      "expiry": "87600h"
    },
    "profiles": {
      "kubernetes": {
         "expiry": "87600h",
         "usages": [
            "signing",
            "key encipherment",
            "server auth",
            "client auth"
        ]
      }
    }
  }
}
EOF

cat > ca-csr.json <<EOF
{
    "CN": "kubernetes",
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "L": "Beijing",
            "ST": "Beijing",
            "O": "k8s",
            "OU": "System"
        }
    ]
}
EOF

# 执行下面命令生成
cfssl gencert -initca ca-csr.json | cfssljson -bare ca -

2、生成apiserver证书
cat > server-csr.json <<EOF
{
    "CN": "kubernetes",
    "hosts": [
      "10.0.0.1",
      "127.0.0.1",
      "172.12.16.26",
      "172.12.16.27",
      "172.12.16.28",
      "kubernetes",
      "kubernetes.default",
      "kubernetes.default.svc",
      "kubernetes.default.svc.cluster",
      "kubernetes.default.svc.cluster.local"
    ],
    "key": {
        "algo": "rsa",
        "size": 2048
    },
    "names": [
        {
            "C": "CN",
            "L": "BeiJing",
            "ST": "BeiJing",
            "O": "k8s",
            "OU": "System"
        }
    ]
}
EOF

# 执行下面命令生成
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes server-csr.json | cfssljson -bare server

3、生成kube-proxy证书
cat > kube-proxy-csr.json <<EOF
{
  "CN": "system:kube-proxy",
  "hosts": [],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "L": "BeiJing",
      "ST": "BeiJing",
      "O": "k8s",
      "OU": "System"
    }
  ]
}
EOF

# 执行下面命令生成
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes kube-proxy-csr.json | cfssljson -bare kube-proxy

4、生成admin证书
cat > admin-csr.json <<EOF
{
  "CN": "admin",
  "hosts": [],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "BeiJing",
      "L": "BeiJing",
      "O": "system:masters",
      "OU": "System"
    }
  ]
}
EOF

# 执行下面命令生成
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=ca-config.json -profile=kubernetes admin-csr.json | cfssljson -bare admin

5、查看证书
最终生成8个证书文件
-rw------- 1 root root 1675 12月 13 17:19 admin-key.pem
-rw-r--r-- 1 root root 1399 12月 13 17:19 admin.pem
-rw------- 1 root root 1675 12月 13 17:14 ca-key.pem
-rw-r--r-- 1 root root 1359 12月 13 17:14 ca.pem
-rw------- 1 root root 1675 12月 13 17:17 kube-proxy-key.pem
-rw-r--r-- 1 root root 1403 12月 13 17:17 kube-proxy.pem
-rw------- 1 root root 1679 12月 13 17:15 server-key.pem
-rw-r--r-- 1 root root 1627 12月 13 17:15 server.pem

**1.5.2 下载Kubernetes二进制文件**

下载地址：https://github.com/kubernetes/kubernetes/blob/master/CHANGELOG-1.14.md

**1.5.3 部署apiserver组件**

1、解压文件
cd /opt/software && tar xf kubernetes-server-linux-amd64.tar.gz
cd kubernetes/server/bin && cp kube-apiserver kube-controller-manager kubectl kube-scheduler /opt/kubernetes/bin/

2、创建token文件
cat > /opt/kubernetes/cfg/token.csv <<EOF
bd41d77ac7cad4cfaa27f6403b1ccf16,kubelet-bootstrap,10001,"system:kubelet-bootstrap"
EOF

# 说明
第一列：随机32位字符串，可自己命令生成：head -c 16 /dev/urandom | od -An -t x | tr -d ' '
第二列：用户名
第三列：UID
第四列：用户组

3、创建apiserver配置文件
vim /opt/kubernetes/cfg/kube-apiserver.conf
KUBE_APISERVER_OPTS="--logtostderr=true \
--v=4 \
--etcd-servers=https://172.12.16.28:2379,https://172.12.16.29:2379,https://172.12.16.30:2379 \
--bind-address=172.12.16.28 \
--secure-port=6443 \
--advertise-address=172.12.16.28 \
--allow-privileged=true \
--service-cluster-ip-range=10.0.0.0/16 \
--enable-admission-plugins=NamespaceLifecycle,LimitRanger,SecurityContextDeny,ServiceAccount,ResourceQuota,NodeRestriction \
--authorization-mode=RBAC,Node \
--enable-bootstrap-token-auth \
--token-auth-file=/opt/kubernetes/cfg/token.csv \
--service-node-port-range=30000-50000 \
--tls-cert-file=/opt/kubernetes/ssl/server.pem  \
--tls-private-key-file=/opt/kubernetes/ssl/server-key.pem \
--client-ca-file=/opt/kubernetes/ssl/ca.pem \
--service-account-key-file=/opt/kubernetes/ssl/ca-key.pem \
--kubelet-client-certificate=/opt/kubernetes/ssl/admin.pem \
--kubelet-client-key=/opt/kubernetes/ssl/admin-key.pem \
--etcd-cafile=/opt/etcd/ssl/ca.pem \
--etcd-certfile=/opt/etcd/ssl/server.pem \
--etcd-keyfile=/opt/etcd/ssl/server-key.pem"

# 参数说明
--logtostderr 启用日志
--v 日志等级
--etcd-servers etcd集群地址
--bind-address 监听地址
--secure-port https安全端口
--advertise-address 集群通告地址
--allow-privileged 启用授权
--service-cluster-ip-range Service虚拟IP地址段
--enable-admission-plugins 准入控制模块
--authorization-mode 认证授权，启用RBAC授权和节点自管理
--enable-bootstrap-token-auth 启用TLS bootstrap功能，后面会讲到
--token-auth-file token文件
--service-node-port-range Service Node类型默认分配端口范围

4、systemd管理apiserver
cat <<EOF >/usr/lib/systemd/system/kube-apiserver.service
[Unit]
Description=Kubernetes API Server
Documentation=https://github.com/kubernetes/kubernetes

[Service]
EnvironmentFile=-/opt/kubernetes/cfg/kube-apiserver.conf
ExecStart=/opt/kubernetes/bin/kube-apiserver \$KUBE_APISERVER_OPTS
Restart=on-failure

[Install]
WantedBy=multi-user.target
EOF

5、启动apiserver
systemctl daemon-reload
systemctl enable kube-apiserver
systemctl restart kube-apiserver
systemctl status kube-apiserver

**1.5.4 部署schduler组件**

1、创建schduler配置文件
vim /opt/kubernetes/cfg/kube-scheduler.conf
KUBE_SCHEDULER_OPTS="--logtostderr=true \
--v=4 \
--master=127.0.0.1:8080 \
--leader-elect"

# 参数说明
--master 连接本地apiserver
--leader-elect 当该组件启动多个时，自动选举

2、systemd管理kube-scheduler
cat <<EOF >/usr/lib/systemd/system/kube-scheduler.service
[Unit]
Description=Kubernetes Scheduler
Documentation=https://github.com/kubernetes/kubernetes

[Service]
EnvironmentFile=-/opt/kubernetes/cfg/kube-scheduler.conf
ExecStart=/opt/kubernetes/bin/kube-scheduler \$KUBE_SCHEDULER_OPTS
Restart=on-failure

[Install]
WantedBy=multi-user.target
EOF

3、启动scheduler服务
systemctl daemon-reload
systemctl enable kube-scheduler
systemctl restart kube-scheduler
systemctl status kube-scheduler

**1.5.5 部署controller-manager组件**

1、创建controller-manager配置文件 vim /opt/kubernetes/cfg/kube-controller-manager.conf KUBE_CONTROLLER_MANAGER_OPTS="--logtostderr=true \ --v=4 \ --master=127.0.0.1:8080 \ --leader-elect=true \ --address=127.0.0.1 \ --service-cluster-ip-range=10.0.0.0/16 \ --cluster-cidr=192.168.0.0/16 \ --cluster-name=kubernetes \ --cluster-signing-cert-file=/opt/kubernetes/ssl/ca.pem \ --cluster-signing-key-file=/opt/kubernetes/ssl/ca-key.pem \ --root-ca-file=/opt/kubernetes/ssl/ca.pem \ --service-account-private-key-file=/opt/kubernetes/ssl/ca-key.pem"  2、systemd管理controller-manager组件 cat <<EOF >/usr/lib/systemd/system/kube-controller-manager.service [Unit] Description=Kubernetes Controller Manager Documentation=https://github.com/kubernetes/kubernetes  [Service] EnvironmentFile=-/opt/kubernetes/cfg/kube-controller-manager.conf ExecStart=/opt/kubernetes/bin/kube-controller-manager \$KUBE_CONTROLLER_MANAGER_OPTS Restart=on-failure  [Install] WantedBy=multi-user.target EOF  3、启动controller-manager服务 systemctl daemon-reload systemctl enable kube-controller-manager systemctl restart kube-controller-manager systemctl status kube-controller-manager 

**1.5.6 查看集群组件状态**

/opt/kubernetes/bin/kubectl get cs NAME                 STATUS    MESSAGE             ERROR controller-manager   Healthy   ok                   scheduler            Healthy   ok                   etcd-0               Healthy   {"health":"true"}    etcd-2               Healthy   {"health":"true"}    etcd-1               Healthy   {"health":"true"}  如上输出则说明组件都正常。 

**1.5.7 将kubectl加入环境变量**

cat > /etc/profile.d/k8s.sh <<EOF export PATH=/opt/kubernetes/bin:\$PATH EOF  # 重新加载环境变量 source /etc/profile  # 查看是否生效 kubectl version 

**1.6 Node节点部署组件**

Master apiserver启用TLS认证后，Node节点kubelet组件想要加入集群，必须使用CA签发的有效证书才能与apiserver通信，当Node节点很多时，签署证书是一件很繁琐的事情，因此有了TLS Bootstrapping机制，kubelet会以一个低权限用户自动向apiserver申请证书，kubelet的证书由apiserver动态签署。

**1.6.1 认证流程**

认证大致工作流程如图所示：

![img](https://app.yinxiang.com/FileSharing.action?hash=1/86917d25177f83823968892e27005909-42814)

**1.6.2 用户绑定系统集群角色**

在master上面执行，将kubelet-bootstrap用户绑定到系统集群角色：

/opt/kubernetes/bin/kubectl create clusterrolebinding kubelet-bootstrap \ --clusterrole=system:node-bootstrapper \ --user=kubelet-bootstrap 

**1.6.3 创建kubeconfig文件**

在master节点生成生成kubeconfig文件，其他节点拷贝过去即可。

- 创建bootstrap.kubeconfig文件

cd /opt/kubernetes/cfg 1) 设置集群参数 kubectl config set-cluster kubernetes \ --certificate-authority=/opt/kubernetes/ssl/ca.pem \ --embed-certs=true \ --server=https://172.12.16.28:6443 \ --kubeconfig=bootstrap.kubeconfig 2）设置客户端认证参数   kubectl config set-credentials kubelet-bootstrap \ --token=bd41d77ac7cad4cfaa27f6403b1ccf16 \ --kubeconfig=bootstrap.kubeconfig 3) 设置上下文参数 kubectl config set-context default \ --cluster=kubernetes \ --user=kubelet-bootstrap \ --kubeconfig=bootstrap.kubeconfig 4) 设置默认上下文 kubectl config use-context default \ --kubeconfig=bootstrap.kubeconfig 

- 创建kube-proxy.kubeconfig文件

1) 设置集群参数 kubectl config set-cluster kubernetes \ --certificate-authority=/opt/kubernetes/ssl/ca.pem \ --embed-certs=true \ --server=https://172.12.16.28:6443 \ --kubeconfig=kube-proxy.kubeconfig 2) 设置客户端认证参数 /opt/kubernetes/bin/kubectl config set-credentials kube-proxy \ --client-certificate=/opt/kubernetes/ssl/kube-proxy.pem \ --client-key=/opt/kubernetes/ssl/kube-proxy-key.pem \ --embed-certs=true \ --kubeconfig=kube-proxy.kubeconfig 3）设置上下文参数 kubectl config set-context default \ --cluster=kubernetes \ --user=kube-proxy \ --kubeconfig=kube-proxy.kubeconfig 4）设置默认上下文 kubectl config use-context default \ --kubeconfig=kube-proxy.kubeconfig 

**1.6.4 拷贝kubeconfig文件**

将两个kubeconfig文件拷贝至每台Node节点的/opt/kubernetes/cfg目录下。

\# 首先在每台Node节点上初始化工作目录 mkdir -p /opt/kubernetes/{bin,cfg,ssl}  scp *.kubeconfig 172.12.16.29:/opt/kubernetes/cfg/ scp *.kubeconfig 172.12.16.30:/opt/kubernetes/cfg/ 

**1.6.5 Node节点安装Docker**

在所有Node节点上安装Docker。

1） 卸载旧版本的docker yum remove docker \                   docker-client \                   docker-client-latest \                   docker-common \                   docker-latest \                   docker-latest-logrotate \                   docker-logrotate \                   docker-engine                    2) 安装docker社区版 yum install -y yum-utils device-mapper-persistent-data lvm2  yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo  yum list docker-ce --showduplicates | sort -r  yum install docker-ce-18.09.5-3.el7 docker-ce-cli-18.09.5-3.el7 containerd.io -y  3) 创建docker配置文件存放目录 mkdir /etc/docker  4) 设置daemon.json cat > /etc/docker/daemon.json <<EOF {   "log-driver": "json-file",   "log-opts": {     "max-size": "100m"   },   "storage-driver": "overlay2",   "storage-opts": [     "overlay2.override_kernel_check=true"   ] } EOF  5) 系统优化 modprobe overlay modprobe br_netfilter  cat > /etc/sysctl.d/99-kubernetes-cri.conf <<EOF net.bridge.bridge-nf-call-iptables  = 1 net.ipv4.ip_forward                 = 1 net.bridge.bridge-nf-call-ip6tables = 1 EOF  sysctl --system  6) 启动docker服务 systemctl daemon-reload systemctl enable docker systemctl restart docker  7) 查看docker是否启动 docker info 

**1.6.6 部署kubelet组件**

1、将master节点上/opt/software/kubernetes/server/bin目录下的kubelet和kube-proxy文件拷贝到node节点。

在master上面执行 cd /opt/software/kubernetes/server/bin scp kubelet kube-proxy 172.12.16.29:/opt/kubernetes/bin/ scp kubelet kube-proxy 172.12.16.30:/opt/kubernetes/bin/ 

2、将master节点上/opt/kubernetes/ssl目录下生成的pem文件拷贝到node节点。

在master上面执行 cd /opt/kubernetes/ssl scp *.pem 172.12.16.29:/opt/kubernetes/ssl/ scp *.pem 172.12.16.30:/opt/kubernetes/ssl/ 

3、创建kubelet配置文件

在node节点上执行 vim /opt/kubernetes/cfg/kubelet.conf KUBELET_OPTS="--logtostderr=true \ --v=4 \ --hostname-override=K8S-NODE \ --kubeconfig=/opt/kubernetes/cfg/kubelet.kubeconfig \ --bootstrap-kubeconfig=/opt/kubernetes/cfg/bootstrap.kubeconfig \ --config=/opt/kubernetes/cfg/kubelet.config \ --cert-dir=/opt/kubernetes/ssl \ --client-ca-file=/opt/kubernetes/ssl/ca.pem \ --pod-infra-container-image=registry.cn-hangzhou.aliyuncs.com/google-containers/pause-amd64:3.0" 

参数说明：

--hostname-override 在集群中显示的主机名

--kubeconfig 指定kubeconfig文件位置，会自动生成

--bootstrap-kubeconfig 指定刚才生成的bootstrap.kubeconfig文件

--cert-dir 颁发证书存放位置

--pod-infra-container-image 管理Pod网络的镜像

注意：K8S-NODE替换成对应机器的IP地址

4、创建kubelet.config配置文件

vim /opt/kubernetes/cfg/kubelet.config

kind: KubeletConfiguration apiVersion: kubelet.config.k8s.io/v1beta1 address: K8S-NODE port: 10250 readOnlyPort: 10255 cgroupDriver: cgroupfs clusterDNS: ["10.0.0.2"] clusterDomain: cluster.local. failSwapOn: false authentication:   anonymous:     enabled: true 

注意：K8S-NODE替换成对应机器的IP地址

5、systemd管理kubelet组件

vim /usr/lib/systemd/system/kubelet.service

[Unit] Description=Kubernetes Kubelet After=docker.service Requires=docker.service  [Service] EnvironmentFile=/opt/kubernetes/cfg/kubelet.conf ExecStart=/opt/kubernetes/bin/kubelet $KUBELET_OPTS Restart=on-failure KillMode=process  [Install] WantedBy=multi-user.target 

6、启动kubelet组件

systemctl daemon-reload systemctl enable kubelet systemctl restart kubelet systemctl status kubelet 

7、在Master审批Node加入集群

启动后还没加入到集群中，需要手动允许该节点才可以。

1）在Master节点查看请求签名的Node：

\# kubectl get csr NAME                                                   AGE     REQUESTOR           CONDITION node-csr-nDuJ2jq0m1mHihXjJyr0TxLvTejew8b59Z_U8MSfAKE   4m10s   kubelet-bootstrap   Pending node-csr-rvIqUQmrQI7CX3gLvzIy8Pmhg-6rjku3EEKXP1piCq4   6s      kubelet-bootstrap   Pending 

2）在Master节点批准签名

kubectl certificate approve node-csr-nDuJ2jq0m1mHihXjJyr0TxLvTejew8b59Z_U8MSfAKE kubectl certificate approve node-csr-rvIqUQmrQI7CX3gLvzIy8Pmhg-6rjku3EEKXP1piCq4 

1. 查看签名状态

\# kubectl get node NAME           STATUS   ROLES    AGE     VERSION 172.12.16.29   Ready    <none>   7m36s   v1.14.0 172.12.16.30   Ready    <none>   44s     v1.14.0 

**1.6.7 部署kube-proxy组件**

1、创建kube-proxy配置文件

\# vim /opt/kubernetes/cfg/kube-proxy.conf KUBE_PROXY_OPTS="--logtostderr=true \ --v=4 \ --hostname-override=K8S-NODE \ --cluster-cidr=192.168.0.0/16 \ --kubeconfig=/opt/kubernetes/cfg/kube-proxy.kubeconfig" 

2、systemd管理kube-proxy组件

\# vim /usr/lib/systemd/system/kube-proxy.service [Unit] Description=Kubernetes Proxy After=network.target  [Service] EnvironmentFile=-/opt/kubernetes/cfg/kube-proxy.conf ExecStart=/opt/kubernetes/bin/kube-proxy $KUBE_PROXY_OPTS Restart=on-failure  [Install] WantedBy=multi-user.target 

3、启动kube-proxy组件

systemctl daemon-reload systemctl enable kube-proxy systemctl restart kube-proxy systemctl status kube-proxy 

**1.7 部署Calico网络**

**1.7.1 工作原理**

1、特点优势

- 纯三层的SDN 实现，它基于BPG 协议和Linux自身的路由转发机制，不依赖特殊硬件，容器通信也不依赖iptables NAT（使用直接路由方式实现通行）或Tunnel 等技术，带宽性能接近主机。
- 能够方便的部署在物理服务器、虚拟机或者容器环境下。
- 同时calico自带的基于iptables的ACL管理组件非常灵活，能够满足比较复杂的安全隔离需求。支持Kubernetes networkpolicy概念。
- 容器间网络三层隔离，无需要担心arp风暴。
- 自由控制的policy规则。
- 通过iptables和kernel包转发，效率高，损耗低。

2、calico组件

- 通过iptables和kernel包转发，效率高，损耗低；
- etcd：分布式键值存储，主要负责网络元数据一致性，确保Calico网络状态的准确性，可以与kubernetes共用；
- BGPClient(BIRD), 主要负责把 Felix写入 kernel的路由信息分发到当前 Calico网络，确保 workload间的通信的有效性；
- BGPRoute Reflector(BIRD), 大规模部署时使用，摒弃所有节点互联的mesh模式，通过一个或者多个 BGPRoute Reflector 来完成集中式的路由分发；

**1.7.2 配置calico**

需要在master节点执行以下操作。

1、下载yaml文件

mkdir -p /opt/calico/{bin,deploy} cd /opt/calico/deploy wget https://docs.projectcalico.org/v3.6/getting-started/kubernetes/installation/hosted/calico.yaml 

2、修改calico配置文件

1） 配置ConfigMap中etcd集群etcd_endpoints

etcd_endpoints: "https://172.12.16.28:2379,https://172.12.16.29:2379,https://172.12.16.30:2379" 

2）配置Calico访问etcd集群的TLS证书，默认已经给出了访问etcd的TLS文件所在容器路径了，填进去即可。

etcd_ca: "/calico-secrets/etcd-ca" etcd_cert: "/calico-secrets/etcd-cert" etcd_key: "/calico-secrets/etcd-key" 

3）在Secret部分中，是采用创建secret的方式来配置TLS文件，在这里我们改用hostPath挂载TLS文件方式。

\#设置calico-node以hostpath的方式来挂载TLS文件，找到DaemonSet部分，设置为如下：

在在381行下面增加         - name: etcd-certs           hostPath:             path: /opt/etcd/ssl               注释387-390行            

\#设置calico-kube-controllers以hostpath挂载TLS文件，找到Deployment部分，设置为如下：

在478行下面增加         - name: etcd-certs           hostPath:             path: /opt/etcd/ssl              注释482-485行             

4）修改deamonset部分CALICO_IPV4POOL_CIDR变量

value: "192.168.0.0/16" 

**1.7.3 修改kubelet**

在每个node节点上操作

\#kubelet启动服务中增加--network-plugin、--cni-conf-dir、--cni-bin-dir三个配置参数

--network-plugin=cni \ --cni-conf-dir=/etc/cni/net.d \ --cni-bin-dir=/opt/cni/bin \ 

\#重启kubelet

systemctl restart kubelet && systemctl status kubelet 

**1.7.4 拷贝etcd的TLS授权文件**

把master上的etcd证书全部复制到所有node的目录下(步骤略)，然后重命名一下(这里复制一份，因为后面安装calicoctl工具需要原来的证书名字)。

以下步骤是在node节点操作

cd /opt/etcd/ssl cp ca.pem etcd-ca cp server-key.pem etcd-key cp server.pem etcd-cert 

**1.7.5 部署calico组件**

\#在master节点执行

cd /opt/calico/deploy kubectl apply -f calico.yaml 

\#查看calico服务

calico-node用的是daemonset，会在每个node上启动； calico-kube-controllers用的是Deployment，会在某个node启动。

kubectl get deployment,pod -n kube-system

![img](https://app.yinxiang.com/FileSharing.action?hash=1/137fa10160cc796c8cd43043cd396711-16812)

\#同时在node节点上可以看到已经生成了tunl0隧道网卡

![img](https://app.yinxiang.com/FileSharing.action?hash=1/bf4df7f7ce6a8bc79936940864b45e79-46363)

**1.7.6 安装管理工具calicoctl**

在所有master和node节点上操作

1、下载calicoctl

cd /opt/calico/bin wget https://github.com/projectcalico/calicoctl/releases/download/v3.6.0/calicoctl chmod +x calicoctl 

2、添加calicoctl 命令加入到环境变量

echo "export PATH=/opt/calico/bin:\$PATH" >>/etc/profile.d/calico.sh source /etc/profile 

3、编写calicoctl的配置文件

\#默认为下面的路径，不要改动

mkdir /etc/calico

vim /etc/calico/calicoctl.cfg

apiVersion: projectcalico.org/v3 kind: CalicoAPIConfig metadata: spec:   datastoreType: "etcdv3"   etcdEndpoints: "https://172.12.16.28:2379,https://172.12.16.29:2379,https://172.12.16.30:2379"   etcdKeyFile: "/opt/etcd/ssl/server-key.pem"   etcdCertFile: "/opt/etcd/ssl/server.pem"   etcdCACertFile: "/opt/etcd/ssl/ca.pem" 

4、查看calico状态

\#查看已注册的节点列表

calicoctl get node

![img](https://app.yinxiang.com/FileSharing.action?hash=1/3fb830e69e75c9c832f794d27f83b5c2-5403)

\#查看默认IP池

calicoctl get ippool -o wide

![img](https://app.yinxiang.com/FileSharing.action?hash=1/d5e8a1db6ca9ecdfca38fad7de5dcc5e-7266)

\#查看节点状态为Established（这个必须要在每个node节点上安装calicoctl，在node上执行，并且只能看到非本节点的）。

calicoctl node status

![img](https://app.yinxiang.com/FileSharing.action?hash=1/d65b057d26c937638d3a66248a55098c-11786)



到此部署calico完成。

**1.8 部署CoreDNS**

\#在master节点操作

**1.8.1 下载配置文件**

从官网下载配置文件（https://github.com/coredns/deployment/tree/master/kubernetes），找到 coredns.yam.sed 和 deploy.sh 两个文件，下载后存放在下面的新建目录下

mkdir /opt/coredns && cd /opt/coredns wget https://raw.githubusercontent.com/coredns/deployment/master/kubernetes/deploy.sh wget https://raw.githubusercontent.com/coredns/deployment/master/kubernetes/coredns.yaml.sed chmod +x deploy.sh 

1、先查看集群的CLUSTER-IP网段

kubectl get svc

![img](https://app.yinxiang.com/FileSharing.action?hash=1/27b701667fc1d41e9a50b9300c5791dd-6855)

2、修改部署文件

修改DNS_DOMAIN、DNS_SERVER_IP变量为实际值，并修改image后面的镜像。

这里直接用deploy.sh脚本进行修改：

./deploy.sh -s -r 10.0.0.0/16 -i 10.0.0.2 -d cluster.local > coredns.yaml 

3、修改前后的文件对比

diff coredns.yaml coredns.yaml.sed

![img](https://app.yinxiang.com/FileSharing.action?hash=1/27fc5444c7feecbf16ff9f2aec908423-15823)

**1.8.2 部署CoreDNS**

kubectl apply -f coredns.yaml

查看

kubectl get svc,pod -n kube-system

![img](https://app.yinxiang.com/FileSharing.action?hash=1/c7c6fcfd34947d68be26f424f729269c-14822)

**1.8.3 修改kubelet的dns服务参数**

在所有node节点上操作，添加以下参数

vim /opt/kubernetes/cfg/kubelet

--cluster-dns=10.0.0.2 \ --cluster-domain=cluster.local. \ --resolv-conf=/etc/resolv.conf \ 

重启kubelet 并查看状态

systemctl restart kubelet && systemctl status kubelet

**1.8.4 验证CoreDNS服务解析**

\#在master上操作，注意：busybox不能用高版本，要用低版本测试。

kubectl run busybox --image busybox:1.28 --restart=Never --rm -it busybox -- sh nslookup kubernetes.default nslookup www.baidu.com 

![img](https://app.yinxiang.com/FileSharing.action?hash=1/5de3101920431cd04a7d4e39707f4934-19963)



输出结果如上，说明coredns可以解析成功。

cat /etc/resolv.conf

![img](https://app.yinxiang.com/FileSharing.action?hash=1/a045a0a8ad5e384840f903cac704b4f3-6560)



如上，每个pod都会在resolv.conf文件中声明dns地址。

**1.9 部署高可用ingress-nginx**

**1.9.1 需求**

CoreDNS是实现pods之间通过域名访问，如果外部需要访问service服务，需访问对应的NodeIP:Port。但是由于NodePort需要指定宿主机端口，一旦服务多起来，多个端口就难以管理。那么，这种情况下，使用Ingress暴露服务更加合适。

**1.9.2 Ingress组件说明**

使用Ingress时一般会有三个组件：反向代理负载均衡器、Ingress Controller、Ingress

1、反向代理负载均衡器

反向代理负载均衡器很简单，说白了就是 nginx、apache 等中间件，新版k8s已经将Nginx与Ingress Controller合并为一个组件，所以Nginx无需单独部署，只需要部署Ingress Controller即可。在集群中反向代理负载均衡器可以自由部署，可以使用 Replication Controller、Deployment、DaemonSet 等等方式

2、Ingress Controller

Ingress Controller 实质上可以理解为是个监视器，Ingress Controller 通过不断地跟 kubernetes API 打交道，实时的感知后端 service、pod 等变化，比如新增和减少 pod，service 增加与减少等；当得到这些变化信息后，Ingress Controller 再结合下文的 Ingress 生成配置，然后更新反向代理负载均衡器，并刷新其配置，达到服务发现的作用

3、Ingress

Ingress 简单理解就是个规则定义；比如说某个域名对应某个 service，即当某个域名的请求进来时转发给某个 service;这个规则将与 Ingress Controller 结合，然后 Ingress Controller 将其动态写入到负载均衡器配置中，从而实现整体的服务发现和负载均衡

整体关系如下图所示：

![img](https://app.yinxiang.com/FileSharing.action?hash=1/4ff266a4765daf28c5f52542368b2a62-185527)



从上图中可以很清晰的看到，实际上请求进来还是被负载均衡器拦截，比如 nginx，然后 Ingress Controller 通过跟 Ingress 交互得知某个域名对应哪个 service，再通过跟 kubernetes API 交互得知 service 地址等信息；综合以后生成配置文件实时写入负载均衡器，然后负载均衡器 reload 该规则便可实现服务发现，即动态映射。

**1.9.3 Nginx-Ingress工作原理**

ingress controller通过和kubernetes api交互，动态的去感知集群中ingress规则变化；然后读取它，按照自定义的规则，规则就是写明了哪个域名对应哪个service，生成一段nginx配置；再写到nginx-ingress-control的pod里，这个Ingress controller的pod里运行着一个Nginx服务，控制器会把生成的nginx配置写入/etc/nginx.conf文件中；然后reload一下使配置生效。以此达到域名分配置和动态更新的问题。

说明：基于nginx服务的ingress controller根据不同的开发公司，又分为：

k8s社区的ingres-nginx（https://github.com/kubernetes/ingress-nginx）

nginx公司的nginx-ingress（https://github.com/nginxinc/kubernetes-ingress）

**1.9.4 Ingress Controller高可用架构**

作为集群流量接入层，Ingress Controller的高可用性显得尤为重要，高可用性首先要解决的就是单点故障问题，一般常用的是采用多副本部署的方式，我们在Kubernetes集群中部署高可用Ingress Controller接入层同样采用多节点部署架构，同时由于Ingress作为集群流量接入口，建议采用独占Ingress节点的方式，以避免业务应用与Ingress服务发生资源争抢。

![img](https://app.yinxiang.com/FileSharing.action?hash=1/e5c1f58836fb6c4fe6bcdb2ae892bf84-77510)



如上述部署架构图，由多个独占Ingress实例组成统一接入层承载集群入口流量，同时可依据后端业务流量水平扩缩容Ingress节点。当然如果您前期的集群规模并不大，也可以采用将Ingress服务与业务应用混部的方式，但建议进行资源限制和隔离。

**1.9.5 部署高可用Ingress**

ingress的高可用的话，要可以通过把nginx-ingress-controller运行到指定添加标签的几个node节点上，然后再把这几个node节点加入到LB中，然后对应的域名解析到该LB即可实现ingress的高可用。（注意：添加标签的节点数量要大于等于集群Pod副本数，从而避免多个Pod运行在同一个节点上。不建议将Ingress服务部署到master节点上，尽量选择worker节点添加标签。）

以下无特殊说明，都在master上操作，参考：https://www.cnblogs.com/terrycy/p/10048165.html

1、给node添加标签

kubectl label node 172.12.16.29 ingresscontroller=true kubectl label node 172.12.16.30 ingresscontroller=true 

\#查看标签

kubectl get nodes --show-labels

![img](https://app.yinxiang.com/FileSharing.action?hash=1/d282b6df1dd5a0d71fb23cbcbade53ba-18216)

\#删除标签

kubectl label node k8snode1 ingresscontroller-

\#更新标签

kubectl label node k8snode1 ingresscontroller=false --overwrite

**1.9.6 下载配置文件**

下载地址：https://github.com/kubernetes/ingress-nginx/tree/master/deploy，有如下几个yaml文件，我们只需要下载mandatory.yaml文件即可，它包含了其余4个文件的内容。

mkdir /opt/ingress && cd /opt/ingress/ wget https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/mandatory.yaml 

**1.9.7 修改配置文件**

1、修改Deployment为DaemonSet，并注释掉副本数

kind: DaemonSet    #replicas: 1 

2、启用hostNetwork网络，并指定运行节点

hostNetwork暴露ingress-nginx controller的相关业务端口到主机，这样node节点主机所在网络的其他主机，都可以通过该端口访问到此应用程序。

nodeSelector指定之前添加ingresscontroller=true标签的node

​      hostNetwork: true       nodeSelector:         ingresscontroller: 'true' 

3、修改镜像地址

registry.cn-hangzhou.aliyuncs.com/google_containers/nginx-ingress-controller:0.24.1 

![img](https://app.yinxiang.com/FileSharing.action?hash=1/9fad4790b0cf73a5710ab3d879f76301-8901)

**1.9.8 部署Ingress**

\#部署

kubectl apply -f mandatory.yaml

\#查看ingress-controller

kubectl get ds -n ingress-nginx kubectl get pods -n ingress-nginx -o wide 

![img](https://app.yinxiang.com/FileSharing.action?hash=1/35a327b9d8902732163e2d9e8f7f41e9-16981)

踩坑：pod无法创建，并且create的时候也没有任何错误。

参考网上文章：https://www.liuyalei.top/1455.html 后，有提示报错：Error creating: pods "nginx-ingress-controller-565dfd6dff-g977n" is forbidden: SecurityContext.RunAsUser is forbidden

解决：需要对准入控制器进行修改，取消SecurityContextDeny 的enable就行，然后重启apiserver：

vim /opt/kubernetes/cfg/kube-apiserver

--enable-admission-plugins=NamespaceLifecycle,LimitRanger,ServiceAccount,ResourceQuota,NodeRestriction \ 

\#重启 kube-apiserver

systemctl restart kube-apiserver && systemctl status kube-apiserver