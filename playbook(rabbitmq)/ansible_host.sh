echo "[mq01]" >> /etc/ansible/hosts
echo "192.168.1.11" >> /etc/ansible/hosts

echo "[group2]" >> /etc/ansible/hosts
for i in {2..3};do echo "192.168.1.1$i hostname=mq0$i" >> /etc/ansible/hosts;done;

echo "[group1]" >> /etc/ansible/hosts
for i in {1..3};do echo "192.168.1.1$i hostname=mq0$i" >> /etc/ansible/hosts;done;

