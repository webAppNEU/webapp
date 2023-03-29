#!/bin/sh

sudo yum upgrade -y
sudo yum update -y
sudo yum install -y java-17-amazon-corretto
sudo yum install -y httpd.x86_64
sudo systemctl start httpd.service
sudo systemctl enable httpd.service

mkdir mysql
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm -P ~/mysql
sudo yum localinstall -y ~/mysql/mysql57-community-release-el7-8.noarch.rpm 
sudo yum install -y mysql-community-client

sudo chmod -R 775 /home/ec2-user/webapp
sudo chown -R ec2-user:ec2-user /home/ec2-user/webapp

sudo cp /home/ec2-user/webapp/webapp.service /etc/systemd/system/webapp.service 
sudo systemctl daemon-reload
sudo systemctl enable webapp
sudo systemctl start webapp
# sudo systemctl daemon-reload
# sudo systemctl status webapp

sudo yum install amazon-cloudwatch-agent -y


sudo systemctl enable amazon-cloudwatch-agent.service
sudo systemctl start amazon-cloudwatch-agent.service
