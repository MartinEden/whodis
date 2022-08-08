#!/usr/bin/env bash
set -ex

VERSION=1.0.4
URL=https://github.com/MartinEden/whodis/releases/download/v${VERSION}/whodis-${VERSION}.zip

# Clean up any previous install
rm /usr/bin/whodis || true
rm -r /opt/whodis || true

# Install app
apt-get install -qq -y openjdk-8-jre nmap espeak unzip net-tools
wget -O whodis.zip ${URL}
unzip whodis.zip -d /opt/whodis
ln -s /opt/whodis/whodis-${VERSION}/bin/whodis /usr/bin/whodis
mkdir -p /etc/whodis

# Add as service
mv /opt/whodis/whodis-${VERSION}/whodis.service /etc/systemd/user/whodis.service
systemctl enable --user whodis

set +x
echo "Installed whodis as systemd service"
echo "Control with systemctl [start|stop|status] --user whodis"
echo "Right now run: systemctl start --user whodis"
