#!/usr/bin/env bash
set -ex

VERSION=1.0.4
URL=https://github.com/MartinEden/whodis/releases/download/v${VERSION}/whodis-${VERSION}.zip

# Stop and unlink any previous install
systemctl stop --user whodis || true
sudo rm /usr/bin/whodis || true

# Install app
sudo apt-get install -qq -y openjdk-8-jre nmap espeak unzip net-tools
wget -O whodis.zip ${URL}
sudo unzip whodis.zip -d /opt/whodis
sudo ln -s /opt/whodis/whodis-${VERSION}/bin/whodis /usr/bin/whodis
sudo mkdir -p /etc/whodis

# Add as service
sudo mv /opt/whodis/whodis-${VERSION}/whodis.service /etc/systemd/user/whodis.service
systemctl enable --user whodis
systemctl start --user whodis

set +x
echo "Installed and started whodis as a systemd service"
echo "Control with systemctl [start|stop|status] --user whodis"
