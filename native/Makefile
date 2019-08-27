JDK    := $(HOME)/space/openj9/bootjdk11
CFLAGS += -I$(JDK)/include -I$(JDK)/include/linux

all : libtest.so

clean :
	rm -f jnitest.o libtest.so

libtest.so : jnitest.o
	$(CC) -o $@ -shared jnitest.o

jintest.o : jnitest.c
