#!/bin/bash
# This is a shell script to change hostname
export PATH=$PATH
export USER=root
export SNAMEPRE=mq0
export PASSWD=centos  #定义密码
yum -y install expect >> /dev/null 
for i in {1..3};
do /usr/bin/expect << EOF  
spawn ssh-copy-id -i /root/.ssh/id_rsa.pub $USER@$SNAMEPRE$i
expect {
"(yes/no)?" {send "yes\r";exp_continue}
"password:" {send "$PASSWD\r"}
}
interact
expect eof
EOF
ssh $USER@$SNAMEPRE$i "hostnamectl set-hostname $SNAMEPRE$i";
scp /etc/hosts $USER@$SNAMEPRE$i:/etc/hosts;
done;
