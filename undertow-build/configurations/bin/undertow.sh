#!/bin/sh
cd "`dirname $0`/.."

# VARIABLES
LIBDIR="./lib"
JAVA=java
JAVA_OPTS=
MAIN_CLASS=com.texoit.undertow.standalone.Main

if [ -e bin/undertow.conf ]; then
	. bin/undertow.conf
fi

# MAIN
CLASSPATH=`ls ${LIBDIR}/*.jar | tr '\n' ' ' | sed 's/  */ /g' | tr ' ' ':'`
${JAVA} ${JAVA_OPTS} -classpath "${CLASSPATH}" ${MAIN_CLASS}
