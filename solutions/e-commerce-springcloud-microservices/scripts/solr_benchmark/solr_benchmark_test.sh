#!/bin/bash

if [ -z "${2}" ] ; then
    echo "Usage: <solr server ip> <query_file> <solr server port> <number_of_user> <time_in_sec>"
    exit 0
fi

if [ ! -f "${2}" ] ; then
    echo "The file:${2} does not exist"
    exit 0
fi

if [ -z "$(which jmeter 2>/dev/null)" ] ; then
    JMETER="/usr/local/jmeter/apache-jmeter-3.2/bin/jmeter"
else 
    JMETER="jmeter"
fi

CUR_DIR="$(cd `dirname $0`; pwd)"
BENCHMARK_JMX="${CUR_DIR}/solr_benchmark.jmx"

HOST="${1}"
PORT=${3:-8983}

USER_NUM=${4:-100}
DUR_TIME_INSEC=${5:-240}

SERVERPATH="/solr/e-commerce"
QUERYFILE="${2}"

${JMETER} -n -t ${BENCHMARK_JMX} -JserverName=${HOST} -JserverPort=${PORT} -JserverPath=${SERVERPATH} -JqueryFile="${QUERYFILE}" -JnoOfUsers ${USER_NUM} -JdurationInSecs ${DUR_TIME_INSEC}
