#!/bin/bash

#
# Warning : Please make sure Jmeter client and Jmeter-server belong to the same subnetwork !
#

if [ -z "${2}" ] ; then
    echo "Usage: <solr server ip> <solr server port> <number_of_user> <time_in_sec> <remote_agent_hosts>"
    exit 0
fi

echo "                                  ********                                                      "
echo "Please make sure LOCAL_HOST and REMOTE_HOST have been set properly during distributed test!"
echo "                                  ********                                                      "

#Jmeter client which triggers Jmeter servers to start tests and manage test results. 
LOCAL_HOST="192.168.11.247"
#Jmeter server (or agents) which perform real test works
REMOTE_HOST=${5:-"192.168.11.247,192.168.11.246"}

if [ -z "$(which jmeter 2>/dev/null)" ] ; then
    JMETER="/opt/jmeter/bin/jmeter"
else 
    JMETER="jmeter"
fi

CUR_DIR="$(cd `dirname $0`; pwd)"
BENCHMARK_JMX="${CUR_DIR}/solr_benchmark.jmx"

HOST="${1}"
PORT=${2:-8983}

USER_NUM=${3:-100}
DUR_TIME_INSEC=${4:-240}

SERVERPATH="/solr/e-commerce"
#By default, the solr ansible scripts will install solr_query file under /home/estuaryapp/solr_benchmark directory
#Please also make sure this file exists on all remote_hosts servers.
QUERYFILE="/home/estuaryapp/solr_benchmark/solr_query"

CUR_DIR="$(cd `dirname $0`; pwd)"
if [ -f "${CUR_DIR}/solr_benchmark_result.jtl" ] ; then
    echo "Delete old test logs ..."
    rm ${CUR_DIR}/solr_benchmark_result.jtl
    rm ${CUR_DIR}/jmeter.log
fi

echo "Perform New Solr Test(LOCAL_HOST:${LOCAL_HOST}, REMOTE_HOST:${REMOTE_HOST}, Target Server:${HOST}, Target Port:${PORT}, NumberofUser:${USER_NUM}, TestTimeInSecs:${DUR_TIME_INSEC}, QUERYFILE:${QUERYFILE}"
taskset -c 2-63 ${JMETER} -n -t ${BENCHMARK_JMX} -Djava.rmi.server.hostname=${LOCAL_HOST} -GserverName=${HOST} -GserverPort=${PORT} -GserverPath=${SERVERPATH} -GqueryFile="${QUERYFILE}" -GnoOfUsers ${USER_NUM} -GdurationInSecs ${DUR_TIME_INSEC} -l ${CUR_DIR}/solr_benchmark_result.jtl -o ${CUR_DIR}/solr_benchmark_report -e -R"${REMOTE_HOST}"

