#!/bin/ash

source hidchannel.conf
echo "changing channel" > /dev/console
echo $channel
temp=$(($channel + 1))
echo $temp
if [ $temp == 11 ]; then
temp=0
echo "channel=$temp" > /usr/bin/hidchannel.conf

else
case "$channel" in
"0")
    /usr/bin/set_chan1.sh 
    ;;
"1")
    /usr/bin/set_chan2.sh
    ;;
"2")
    /usr/bin/set_chan3.sh
    ;;
"3")                                           
    /usr/bin/set_chan4.sh                      
    ;;                   
"4")                     
    /usr/bin/set_chan5.sh
    ;;                   
"5")                     
    /usr/bin/set_chan6.sh
    ;;  
"6")                                           
    /usr/bin/set_chan7.sh                      
    ;;                   
"7")                     
    /usr/bin/set_chan8.sh
    ;;                   
"8")                     
    /usr/bin/set_chan9.sh
    ;;  
"9")                                           
    /usr/bin/set_chan10.sh                      
    ;;  
                                       
               
   
esac
echo "channel=$temp" > /usr/bin/hidchannel.conf
fi

