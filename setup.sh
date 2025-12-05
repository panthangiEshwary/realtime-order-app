#!/bin/bash

echo "================ Updating System ================"
sudo apt update -y
sudo apt upgrade -y

echo "================ Installing Docker ================"
sudo apt install -y docker.io docker-compose-plugin
sudo systemctl enable docker
sudo usermod -aG docker ubuntu

echo "================ Installing Java & Maven ================"
sudo apt install -y openjdk-17-jdk maven

echo "================ Building Java Application ================"
cd /home/ubuntu/realtime-order-app
mvn clean package -DskipTests

echo "================ Building Docker Image for App ================"
docker build -t realtime-order-app .

echo "================ Running Java App Container ================"
docker stop order-app 2>/dev/null
docker rm order-app 2>/dev/null
docker run -d -p 8080:8080 --name order-app realtime-order-app

echo "================ Setting Up Monitoring Stack ================"
cd monitoring
docker compose up -d

echo "================ Setup Finished Successfully ================"
echo "App URL: http://$(curl -s ifconfig.me):8080/orders"
echo "Prometheus: http://$(curl -s ifconfig.me):9090"
echo "Grafana: http://$(curl -s ifconfig.me):3000"
echo "Alertmanager: http://$(curl -s ifconfig.me):9093"
echo "n8n: http://$(curl -s ifconfig.me):5678"
