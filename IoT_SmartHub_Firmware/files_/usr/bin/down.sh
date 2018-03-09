#!/bin/sh
source /usr/bin/channel.conf
echo $channel
source /usr/bin/id.conf
echo $id
rf-one -p somfy -r $channel -d $id -c off 

