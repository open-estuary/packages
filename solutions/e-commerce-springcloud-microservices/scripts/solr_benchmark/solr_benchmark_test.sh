#!/bin/bash

if [ -z "$(which jmeter 2>/dev/null)" ] ; then
    JMETER="/usr/local/jmeter/apache-jmeter-3.2/bin/jmeter"
else 
    JMETER="jmeter"
fi

BENCHMARK_JMX="./SolrQueryTest.jmx"
HOST="192.168.1.86"
PORT="8983"
SERVERPATH="/solr/e-commerce"
QUERYFILE="./product_query"

${JMETER} -n -t ${BENCHMARK_JMX} -JserverName=${HOST} -JserverPort=${PORT} -JserverPath=${SERVERPATH} -JqueryFile="${QUERYFILE}"
