TARGET = $(notdir $(CURDIR))

ROOT            = ../$(TARGET)


GNU_PREFIX        := aarch64-linux-gnu-
CC                := $(GNU_PREFIX)gcc
LD                := $(GNU_PREFIX)ld
AR                := $(GNU_PREFIX)ar
ECHO              := @echo


SRCDIR := $(BUILD_PATH)

SRCFILES := odp.o hisi.o  


OBJ_FILE        = $(SRCFILES)

CFLAGS  = -O3 -c -pipe $(HRD_ENDIAN_LINUX_CC) -march=armv8-a -mtune=cortex-a57 -mcpu=cortex-a57 -Wall -Wunused -MD -MP -fPIC -DHRD_OS_LINUX -DHRD_ENDNESS_${LINUX_ENDNESS} -DBOARD_TYPE_$(HRD_ENV_TYPE)


                    
#定义当前模块的依赖文件
DEP_FILE        := $(OBJ_FILE:.o=.d)
I_OBJ_FILE      := $(OBJ_FILE:.o=.i)


#定义当前的依赖文件的搜索路径
vpath %.c $(SRCDIR)




#当前模块的.i 的连接规则
#$(OBJ_FILENAME) : $(I_OBJ_FILE)
#	$(ECHO) "LD " $@;\
	$(LD) -fpic -shared -o  $@ $^

#当前模块的.o 的连接规则
$(OBJ_A) : $(OBJ_FILE)
	$(ECHO) "AR " $@;\
	$(AR) rcs $@ $^ 

#当前模块的.so 的连接规则
$(OBJ_SO) : $(OBJ_FILE)
	$(ECHO) "LD " $@;\
	$(CC) -fpic -shared -o $@ $^


clean:
	rm -f $(ROOT)/bin/*.o $(ROOT)/bin/*.a $(ROOT)/bin/*.so





