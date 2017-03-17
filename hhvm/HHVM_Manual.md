* [Introduction](#1)
* [Installation](#2)

## <a name="1">Introduction</a>
This hhvm is a virtual machine designed for executing programs written in Hack and PHP. HHVM uses a just-in-time (JIT) compilation approach to achieve superior performance while maintaining the development flexibility that PHP provides.

HHVM supports Hack, PHP 5 and the major features of PHP 7. We are aware of minor incompatibilities, so please open issues when you find them. HHVM also supports many extensions as well.

HHVM should be used together with a webserver like the built in, easy to deploy Proxygen, or a FastCGI-based webserver on top of nginx or Apache.

Currently, we use nginx to worked as a webserver in Estuary version, and it will be installed at the same time with hhvm package, the detail information please refer to the next section.

## <a name="3">Installation</a>
This HHVM could be installed autoamtically if the hhvm package is set to "enabled" and build command is "install" in [EstuaryCfg.json](https://github.com/open-estuary/estuary/blob/master/estuarycfg.json). 

## <a name="3">Start HHVM Service</a>
You can call the start.sh script to start hhvm service
