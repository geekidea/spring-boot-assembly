#! /bin/shell

#======================================================================
# mvn package script
# default local profile
#
# author: geekidea
# date: 2018-12-22
#======================================================================

PROFILE=$1
if [[ -z "$PROFILE" ]]; then
    PROFILE=local
fi
echo "profile:${PROFILE}"
mvn clean package -P${PROFILE} -DskipTests
echo "profile:${PROFILE}"