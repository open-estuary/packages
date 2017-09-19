#!/bin/bash

if [ -z "${2}" ] ; then
    echo "Usage: <solr server ip> <query_file> [<solr server port>]"
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

SERVERPATH="/solr/e-commerce"
QUERYFILE="${2}"

${JMETER} -n -t ${BENCHMARK_JMX} -JserverName=${HOST} -JserverPort=${PORT} -JserverPath=${SERVERPATH} -JqueryFile="${QUERYFILE}"
