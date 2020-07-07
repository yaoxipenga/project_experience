#!/usr/bin/env bash
sed -i "/3306/s/$/ \nfederated \nskip-name-resolve/" /var/lib/docker/volumes/mysql_conf/_data/my.cnf
exit