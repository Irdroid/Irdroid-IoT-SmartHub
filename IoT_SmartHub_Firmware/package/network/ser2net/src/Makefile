PROGRAMS = ser2net
CFLAGS += -g -Wall -DVERSION=\"2.9.1.1\" -DUSE_UUCP_LOCKING=1 -DWORDS_BIGENDIAN=1

ser2net_OBJECTS = controller.o dataxfer.o \
	devcfg.o readconfig.o selector.o \
	ser2net.o utils.o telnet.o buffer.o \
	http.o sha1.o

.PHONY: all


all: ${PROGRAMS}

clean:
	-rm *.o ${PROGRAMS}

ser2net: $(ser2net_OBJECTS)
	$(CC) $(ser2net_OBJECTS) -o ser2net

controller.o: controller.c
	$(CC) $(CFLAGS) -c controller.c
dataxfer.o: dataxfer.c
	$(CC) $(CFLAGS) -c dataxfer.c
devcfg.o: devcfg.c
	$(CC) $(CFLAGS) -c devcfg.c
readconfig.o: readconfig.c
	$(CC) $(CFLAGS) -c readconfig.c
selector.o: selector.c
	$(CC) $(CFLAGS) -c selector.c
ser2net.o: ser2net.c
	$(CC) $(CFLAGS) -c ser2net.c
utils.o: utils.c
	$(CC) $(CFLAGS) -c utils.c
telnet.o: telnet.c
	$(CC) $(CFLAGS) -c telnet.c
buffer.o: buffer.c
	$(CC) $(CFLAGS) -c buffer.c
http.o: http.c
	$(CC) $(CFLAGS) -c http.c
sha1.o: sha1.c
	$(CC) $(CFLAGS) -c sha1.c
