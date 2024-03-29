# whodis
Monitor local network for devices and announce them creepily.

## What is this?
This is a silly little service, designed to run on Ubuntu. You give it a list of MAC addresses and human readable 
names (like John Smith). When it detects a device joining your WiFi network it announces "John Smith has arrived" 
using text to speech. 

The idea is that you can leave this running and (if you live on a hill like I do) when your 
friends are approaching your house their devices will automatically pick up your WiFi and whodis will tell you that 
they are approaching, so you can get the kettle on.

If a device has been disconnected for 10 minutes it will announce them again next time they reconnect (you can 
override this default in the settings file; see below).

## Install it
Clone the repository and then run `./install.sh`. You should run it using your ordinary user account. (i.e. do not 
run as root.)

It uses a JSON file at `/etc/whodis/hosts` to match up MAC addresses with names. The format is:

    [
        {
            "address": "00:00:00:00:00:00",
            "name": "John Smith",
            "announce": true
        },
        {
            "address": "11:11:11:11:11:11",
            "name": "My printer",
            "announce": false
        }
    ]

For devices that you are not interested in announcing (such as your printer), you can create an entry and set 
`announce` to false. 

Devices that do not have an entry in the hosts file will be announced as "Unknown device" to 
remind you to add them to the hosts file. You can see the associated MAC addresses by viewing the service log using 
`journalctl --user-unit whodis -e`.  

You will need to restart the service (`systemctl restart whodis`) for 
changes to this file to take effect.

## Settings
Create a JSON file at `/etc/whodis/settings` to override the default settings.

| Setting key               |   Default value   | Meaning                                                                                                                 |
|---------------------------|:-----------------:|-------------------------------------------------------------------------------------------------------------------------|
| `timeoutInSeconds`        | 600s (10 minutes) | If a device has not been seen on the network for this long it will be forgotten, and re-announced next time it connects |
| `checkFrequencyInSeconds` |        20s        | How often the service should poll for ARP data to discover hosts                                                        |

Here is a sample settings file showing the default values. All settings are optional (as is the settings file itself).

    {
        "timeoutInSeconds": 600,
        "checkFrequencyInSeconds": 20
    }

## Run it from source
Clone the repo and then run `./gradlew :run`. You will need a JDK already installed.

## Troubleshooting
* Make sure sound is enabled (see below)
* Make sure that the host is connected to the WiFi if you want to announce WiFi devices
* Check `journalctl -user-unit whodis -e`

### Enabling sound on Ubuntu server
Instructions from [here](http://howto.blbosti.com/2010/03/ubuntu-server-install-alsa-sound-and-moc-music-on-console/):

```
sudo apt install alsa alsa-tools
sudo adduser $USER audio
```

Then reboot and run `alsamixer` to interactively set the volume.

## Creating a new release
1. Update the version number in `build.gradle`
2. Run `./release.sh`.
3. Create a new release in GitHub and upload the zip file from `build/distributions`.
