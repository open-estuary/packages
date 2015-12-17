#TARGET = $(notdir $(CURDIR)) 当前工作路径
THIS_MAKEFILE=$(abspath $(firstword $(subst $(MAKEFILES),,$(MAKEFILE_LIST)))) 
DIR = $(dir $(THIS_MAKEFILE))
DIR_NAME = $(shell basename $(DIR))
ROOT            = $(DIR)../..

#32位应用程序
ifeq ("$(BUILD_DIR)", "build_32") 
	GNU_PREFIX=arm-linux-gnueabi-
	LIB_ODP=odp32
	APP_OUT=$(DIR_NAME)_app32
	CFLAGS=-O3 -D_GNU_SOURCE -mlittle-endian -lpthread -march=armv7-a  -D__arm32__
else    
	GNU_PREFIX = aarch64-linux-gnu-
	LIB_ODP=odp
	APP_OUT=$(DIR_NAME)_app
	CFLAGS=-O3 -D_GNU_SOURCE -mlittle-endian -lpthread -march=armv8-a -mtune=cortex-a57 -mcpu=cortex-a57
endif

CC                := $(GNU_PREFIX)gcc
LD                := $(GNU_PREFIX)ld
OBJDUMP           := $(GNU_PREFIX)objdump
ECHO              := @echo

SRCS          := $(wildcard $(DIR)*.c)

OBJ_FILE      := $(SRCS:.c=.o)  
I_OBJ_FILE    := $(SRCS:.c=.i)

LIBS := -L$(ROOT)/$(BUILD_DIR)/bin/ -l$(LIB_ODP) -lpthread -ldl -lrt -lm



INCLUDE_FILES      := -I$(DIR) \
                     -I$(ROOT)/include \
		     -I$(ROOT)/platform/linux-generic \
                     -I$(ROOT)/platform/linux-generic/include \
                     -I$(ROOT)/platform/linux-generic/include/odp/plat \
                     -I$(ROOT)/platform/linux-hisilicon/include/odp \
                     -I$(ROOT)/platform/linux-hisilicon/include \
                     -I$(ROOT)/helper/include/odp/helper \
                     -I$(ROOT)/helper/include \
                     -I$(ROOT)/example \
                     -I$(ROOT)/$(BUILD_DIR) 



$(APP_OUT) : $(OBJ_FILE)
	$(ECHO) "LD " $@;\
	$(CC) $(LIBS) $(CFLAGS) -o $(ROOT)/$(BUILD_DIR)/app/$@ $^  $(ROOT)/$(BUILD_DIR)/lib/libcrypto.a

clean:
	rm -f $(DIR)*.o $(DIR)*.d $(DIR)*.so $(DIR)$(APP_OUT)


#当前模块的.o 的编译规则, 把 %.c 改成为 .i , 则为在代码目录生成 .i文件。
$(OBJ_FILE) : %.o : %.c
	$(ECHO) "CC " $(notdir $@);
	$(CC)  $(CFLAGS) $(INCLUDE_FILES) -c -o $@ $<

#加入 -C ，则增加注释
$(I_OBJ_FILE) :%.i :%.c
	$(ECHO) "CC " $@;
	$(CC) $(CFLAGS) $(INCLUDE_FILES) $< -E -P -o $@
