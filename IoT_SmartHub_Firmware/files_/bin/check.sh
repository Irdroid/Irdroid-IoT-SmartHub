#!/bin/ash

wget -q --spider http://google.com

if [ $? -eq 0 ]; then
    echo "Online"
    
    killall mqtt_subscribe.lua
    source /etc/systemid.conf
    /bin/mqtt_subscribe.lua -i local_$systemid -t $systemid@/# > /dev/null &



else
    killall mqtt_subscribe
    echo "Offline"
fi
