#! /bin/shell

#======================================================================
# 项目重启shell脚本
# 先调用shutdown.sh停服
# 然后调用startup.sh启动服务
#
# author: geekidea
# date: 2018-12-2
#======================================================================

# 项目名称
APPLICATION="spring-boot-assembly"

# 停服
echo stop ${APPLICATION} Application...
sh shutdown.sh

# 启动服务
echo start ${APPLICATION} Application...
sh startup.sh