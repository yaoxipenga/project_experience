#!/usr/bin/bash 
for i in {1..3};do echo "192.168.1.1$i mq0$i" >> /etc/hosts;done;
