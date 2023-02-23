#!/bin/sh

sudo yum update -y
sudo yum install -y java-17-amazon-corretto
sudo yum install -y httpd.x86_64
sudo systemctl start httpd.service
sudo systemctl enable httpd.service

mkdir mysql
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
wget http://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm -P ~/mysql
sudo yum localinstall -y ~/mysql/mysql57-community-release-el7-8.noarch.rpm 
sudo yum install -y mysql-community-server

sudo systemctl stop mysqld
sudo systemctl set-environment MYSQLD_OPTS="--skip-grant-tables"
sudo systemctl start mysqld
mysql -u root -D mysql -e "UPDATE user SET authentication_string = PASSWORD('root') WHERE User = 'root';"
mysql -u root -D mysql -e "FLUSH PRIVILEGES;"
mysql -u root -e "ALTER USER root@'localhost' IDENTIFIED BY 'root'" -proot --connect-expired-password;
#mysql -u root -e "SET GLOBAL validate_password.length = 4" ;
mysql -u root -e "CREATE DATABASE userwebapp;" -proot --connect-expired-password;
sudo systemctl stop mysqld
sudo systemctl unset-environment MYSQLD_OPTS
sudo systemctl start mysqld
sudo yum install -y jq

mkdir ~/maven/
sudo wget https://dlcdn.apache.org/maven/maven-3/3.9.0/binaries/apache-maven-3.9.0-bin.tar.gz -P ~/maven
sudo tar xf ~/maven/apache-maven-3.9.0-bin.tar.gz -C ~/maven
M2_HOME='/home/ec2-user/maven/apache-maven-3.9.0'
PATH="$M2_HOME/bin:$PATH"
export PATH


#sudo yum install git -y
#git clone https://maheshpoojaryneu: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@github.com/webAppNEU/webapp.git ~/webapp

sudo chmod -R 775 /home/ec2-user/webapp
sudo chown -R ec2-user:ec2-user /home/ec2-user/webapp

sudo cp /home/ec2-user/webapp/webapp.service /etc/systemd/system/webapp.service 
mvn clean install -f /home/ec2-user/webapp/pom.xml
#nohup  java -jar /home/ec2-user/webapp/target/UserWebApp-0.0.1-SNAPSHOT.jar &

sudo systemctl enable webapp
sudo systemctl start webapp
sudo systemctl daemon-reload
sudo systemctl status webapp


#sudo useradd mahesh
#sudo passwd mahesh
#sudo chown mahesh:mahesh /home/ec2-user/webapp/webapp/target/UserWebApp-0.0.1-SNAPSHOT.jar
#sudo chmod 500 /home/ec2-user/webapp/webapp/target/UserWebApp-0.0.1-SNAPSHOT.jar

#sudo chown mahesh:mahesh ~/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#mvn clean install -f /home/ec2-user/webapp/pom.xml
#sudo chown mahesh:mahesh ~/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#sudo chmod -R 775 /home/ec2-user/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#sudo chmod 777 /home/ec2-user/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#sudo chmod 775 /home/ec2-user/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#cat /home/ec2-user/webapp/UserWebApp-0.0.1-SNAPSHOT.jar
#sudo nohup& java -jar /home/ec2-user/webapp/UserWebApp-0.0.1-SNAPSHOT.jar

