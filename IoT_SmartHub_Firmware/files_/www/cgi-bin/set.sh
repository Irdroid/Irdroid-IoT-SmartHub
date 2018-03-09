#!/bin/ash

echo "Content-type: text/html"
echo ""

echo '<html>'
echo '<head>'
echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">'
echo '<title>Environment Variables</title>'
echo '<link rel="stylesheet" type="text/css" href="./styles.css" />'
echo '</head>'
echo '<body>'
# Save the old internal field separator.
OIFS="$IFS"

# Set the field separator to & and parse the QUERY_STRING at the ampersand.
IFS="${IFS}&"
set $QUERY_STRING
Args="$*"
IFS="$OIFS"

# Next parse the individual "name=value" tokens.

ARGX=""
ARGY=""
ARGZ=""

for i in $Args ;do

# Set the field separator to =
IFS="${OIFS}="
set $i
IFS="${OIFS}"

case $1 in
# Don't allow "/" changed to " ". Prevent hacker problems.
namex) ARGX="`echo $2 | sed 's|[\]||g' | sed 's|%20| |g'`"
;;
# Filter for "/" not applied here
namey) ARGY="`echo $2 | sed 's|%20| |g'`"
;;
namez) ARGZ="${2/\// /}"
;;
*) echo "<hr>Warning:"\
"<br>Unrecognized variable \'$1\' passed by FORM in QUERY_STRING.<hr>"
;;

esac
done
first=$(echo $ARGX | cut -c1)
second=$(echo $ARGX | cut -c2)




if [ "$ARGX" -eq 1 ];then
 /usr/bin/set_chan1.sh 
echo 1 > /www/channel.conf
fi 


if [ "$ARGX" -eq 2 ];then
 /usr/bin/set_chan2.sh
echo 2 > /www/channel.conf
fi

if [ "$ARGX" -eq 3 ];then
 /usr/bin/set_chan3.sh
echo 3 > /www/channel.conf
fi

if [ "$ARGX" -eq 4 ];then
 /usr/bin/set_chan4.sh
echo 4 > /www/channel.conf
fi

if [ "$ARGX" -eq 5 ];then
 /usr/bin/set_chan5.sh
echo 5 > /www/channel.conf
fi

if [ "$ARGX" -eq 6 ];then
 /usr/bin/set_chan6.sh
echo 6 > /www/channel.conf
fi

if [ "$ARGX" -eq 7 ];then
 /usr/bin/set_chan7.sh
echo 7 > /www/channel.conf
fi

if [ "$ARGX" -eq 8 ];then
 /usr/bin/set_chan8.sh
echo 8 > /www/channel.conf
fi

if [ "$ARGX" -eq 9 ];then
 /usr/bin/set_chan9.sh
echo 9 > /www/channel.conf
fi

if [ "$ARGX" -eq 10 ];then
 /usr/bin/set_chan10.sh
echo 10 > /www/channel.conf
fi

if [ "$ARGX" -eq 11 ];then
 /usr/bin/set_chan11.sh
echo 11 > /www/channel.conf
fi

if [ "$ARGX" -eq 12 ];then
 /usr/bin/set_chan12.sh
echo 12 > /www/channel.conf
fi

if [ "$ARGX" -eq 13 ];then
 /usr/bin/set_chan13.sh
echo 13 > /www/channel.conf
fi

if [ "$ARGX" -eq 14 ];then
 /usr/bin/set_chan14.sh
echo 14 > /www/channel.conf
fi

if [ "$ARGX" -eq 15 ];then
 /usr/bin/set_chan15.sh
echo 15 > /www/channel.conf
fi

if [ "$ARGX" -eq 16 ];then
 /usr/bin/set_chan16.sh
echo 16 > /www/channel.conf
fi

if [ "$ARGX" -eq 17 ];then
 /usr/bin/set_chan17.sh
echo 17 > /www/channel.conf
fi

if [ "$ARGX" -eq 18 ];then
 /usr/bin/set_chan18.sh
echo 18 > /www/channel.conf
fi

if [ "$ARGX" -eq 19 ];then
 /usr/bin/set_chan19.sh
echo 19 > /www/channel.conf
fi

if [ "$ARGX" -eq 20 ];then
 /usr/bin/set_chan20.sh
echo 20 > /www/channel.conf
fi


echo '</div>'
echo '</div>'


 echo ""
 echo "<html><head><title>Demo</title>"
 echo "</head><body>"
 echo "<script type="text/javascript"><!--"
 echo "setTimeout('Redirect()',0);"
 echo " function Redirect(){  location.href = './index.cgi';}"
 echo " --></script></body></html>"
