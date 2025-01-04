#!/bin/bash

set -e # 에러 발생 시 스크립트 중단

# 필요한 패키지 업데이트 및 설치
echo "[-] Updating package list and installing required packages..."
apt-get update && apt-get install -y \
  mysql-client \
  redis-tools \
  curl \
  tar \
  iproute2 iputils-ping net-tools iftop \
  && apt-get clean

# Node Exporter 다운로드 및 설치
NODE_EXPORTER_VERSION="1.6.0"
NODE_EXPORTER_URL="https://github.com/prometheus/node_exporter/releases/download/v${NODE_EXPORTER_VERSION}/node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64.tar.gz"
INSTALL_DIR="/usr/local/bin"

echo "[-] Downloading Node Exporter version ${NODE_EXPORTER_VERSION}..."
curl -L ${NODE_EXPORTER_URL} | tar xz

echo "[-] Installing Node Exporter to ${INSTALL_DIR}..."
mv node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64/node_exporter ${INSTALL_DIR}/
chmod +x ${INSTALL_DIR}/node_exporter
rm -rf node_exporter-${NODE_EXPORTER_VERSION}.linux-amd64

# 설치 확인
echo "[-] Verifying Node Exporter installation..."
if [ -f "${INSTALL_DIR}/node_exporter" ]; then
    echo "Node Exporter installed successfully!"
else
    echo "Node Exporter installation failed!"
    exit 1
fi

# Node Exporter 실행
echo "[-] Starting Node Exporter with --no-collector.nfsd option..."
${INSTALL_DIR}/node_exporter --no-collector.nfsd &
echo "[-] Node Exporter is now running on port 9100."

# 실행 상태 확인
sleep 2
if pgrep -f node_exporter > /dev/null; then
    echo "Node Exporter is running successfully!"
else
    echo "Failed to start Node Exporter."
    exit 1
fi

# 포트 정보 출력
echo "[-] Exposed ports: 9100 (Node Exporter), others as configured."
