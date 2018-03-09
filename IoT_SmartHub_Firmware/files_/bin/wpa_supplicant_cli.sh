uci set wireless.@wifi-iface[0].mode=sta; uci commit wireless;
uci set network.wifi.proto=dhcp;uci commit network;
wifi down
sleep 1
wifi up 
sleep 2
wpa_cli wps_pbc
sleep 60
/bin/wpa_supplicant_update_config.sh

