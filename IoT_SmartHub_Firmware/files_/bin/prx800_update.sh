#/bin/ash

sleep 10
if [ ! -f /mnt/share/factory.conf ]; then

echo "No file provided."



else
    echo "Setting up the module"
    source /mnt/share/factory.conf
    cp /mnt/share/p2_ship_default.png /www
    cp /mnt/share/jquery.min.js /www
    opkg install /mnt/share/kmod-ipv6_3.10.44-1_ramips_24kec.ipk
    uci set system.@system[0].hostname=$hostname-$systemid
    uci commit system
    uci set wireless.@wifi-iface[0].ssid=$ssid-$systemid
    uci commit wireless
    echo "systemid=wirf"$systemid > /etc/systemid.conf
    result=$(($systemid + 1))
    echo -e systemid=$result"\n"hostname=$hostname"\n"ssid=$ssid"\n"  > /mnt/share/factory.conf   
    echo -e $hostname-$systemid"\n"_http._tcp local"\n"80"\n\n"$hostname-$systemid"\n"_irdroid._tcp local"\n"8765"\n" > /etc/mDNSResponder.conf
    reboot
fi
