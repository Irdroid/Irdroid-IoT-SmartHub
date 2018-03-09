#!/bin/sh
source /usr/bin/channel.conf
echo $channel
source /usr/bin/id.conf
echo $id

rf-one -p somfy -r $channel -d $id -c off
sleep 0.1
rf-one -p somfy -r $channel -d $id -c f1


