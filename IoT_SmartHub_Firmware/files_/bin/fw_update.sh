#/bin/ash

while [ 1 ]; do
if [ ! -f /mnt/share/prx800.tar.gz ]; then
echo "No file provided."
else
  if diff /etc/prx800.tar.gz /mnt/share/prx800.tar.gz >/dev/null ; then
  echo "Update not needed, the files are the same!"
else
    echo "Uploading new Webpage!"
    rm /etc/prx800.tar.gz
    cp /mnt/share/prx800.tar.gz /etc
    rm -rf /tmp/prx800/*
    tar -xzf /etc/prx800.tar.gz -C /tmp/prx800
fi
fi
sleep 15
done
