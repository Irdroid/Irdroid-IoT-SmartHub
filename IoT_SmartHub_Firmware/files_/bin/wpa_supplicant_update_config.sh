awk  '/^network=/,/^}/{if(++m==1)n++;if(n==2)print;if(/^}/)m=0}'  /var/run/wpa_supplicant-wlan0.conf > /tmp/wpa_supplicant-wlan0.conf
SSID=$(awk '/ssid=/ ' /tmp/wpa_supplicant-wlan0.conf | cut -c 7-50)
ssid1=$(echo $SSID | awk '{print substr($0, 2, length($0) - 2)}')
KEY=$(awk '/psk=/ ' /tmp/wpa_supplicant-wlan0.conf | cut -c 6-50)
key1=$(echo $KEY | awk '{print substr($0, 2, length($0) - 2)}') 
uci set wireless.@wifi-iface[0].ssid=$ssid1; uci commit wireless; 
uci set wireless.@wifi-iface[0].key=$key1; uci commit wireless; 
