CC = gcc
BC = mipsel-openwrt-linux-uclibc-gcc
TARGET = unit_test_client
LIBS = -lmodbus
OBJECTS = $(patsubst %.c,%.o, $(wildcard *.c))

$(TARGET): $(OBJECTS)
	@echo "** Linking Executable"
	$(BC) $(OBJECTS) -o $(TARGET) $(LIBS) 

clean:
	@rm -f *.o *~

veryclean: clean
	@rm -f $(TARGET)

%.o: %.cpp
	@echo "** Compiling C Source" 
	$(CC) -c $<
