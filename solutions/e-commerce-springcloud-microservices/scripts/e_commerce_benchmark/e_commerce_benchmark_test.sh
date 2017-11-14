#!/bin/bash

#
# Warning : Please make sure Jmeter client and Jmeter-server belong to the same subnetwork !
#

if [ -z "${2}" ] ; then
    echo "Usage: <e-commerce server ip> <e-commerce server port> <number_of_user> <time_in_sec> <remote_agent_hosts> <result_save_dir>"
    exit 0
fi

echo "                                  ********                                                      "
echo "Please make sure LOCAL_HOST and REMOTE_HOST have been set properly during distributed test!"
echo "                                  ********                                                      "

#Jmeter client which triggers Jmeter servers to start tests and manage test results. 
LOCAL_HOST="192.168.11.247"
#Jmeter server (or agents) which perform real test works
REMOTE_HOST="192.168.11.247"

if [ -z "$(which jmeter 2>/dev/null)" ] ; then
    JMETER="/opt/jmeter/bin/jmeter"
else 
    JMETER="jmeter"
fi

CUR_DIR="$(cd `dirname $0`; pwd)"
BENCHMARK_JMX="${CUR_DIR}/api_benchmark.jmx"

HOST="${1}"
PORT=${2:-9000}
USER_NUM=${3:-1000}
DUR_TIME_INSEC=${4:-240}

ORDER_CREATE_GET_PERCENT=${5:-30}
CART_PERCENT=${6:-30}
SEARCH_PERCENT=${7:-30}
ORDER_DEL_PERCENT=${8:-10}

QUERY_FILE=${9:-"/home/estuaryapp/solr_benchmark/solr_query"}

#By default, the solr ansible scripts will install solr_query file under /home/estuaryapp/solr_benchmark directory
#Please also make sure this file exists on all remote_hosts servers.

CUR_DIR="$(cd `dirname $0`; pwd)"
if [ -f "${CUR_DIR}/e_commerce_benchmark_result.jtl" ] ; then
    echo "Delete old test logs ..."
    rm ${CUR_DIR}/e_commerce_benchmark_result.jtl
    rm ${CUR_DIR}/jmeter.log
fi

echo "Perform New E-commerce Test(LOCAL_HOST:${LOCAL_HOST}, REMOTE_HOST:${REMOTE_HOST}, Target Server:${HOST}, Target Port:${PORT}, NumberofUser:${USER_NUM}, TestTimeInSecs:${DUR_TIME_INSEC}, QUERYFILE:${QUERYFILE}"
taskset -c 2-60 ${JMETER} -n -t ${BENCHMARK_JMX} -Djava.rmi.server.hostname=${LOCAL_HOST} -Ghost=${HOST} -Gport=${PORT} -Gquery_filename="${QUERYFILE}" -Gusers ${USER_NUM} -Gduration_in_secs ${DUR_TIME_INSEC} -Gsearch_percent ${SEARCH_PERCENT} -Gcart_percent ${CART_PERCENT} -Gorder_del_percent ${ORDER_DEL_PERCENT} -Gorder_create_get_percent ${ORDER_CREATE_GET_PERCENT} -l ${CUR_DIR}/e_commerce_benchmark_result.jtl -o ${CUR_DIR}/e_commerce_benchmark_report -e -R"${REMOTE_HOST}"

OUT_DIR="${10:-/estuarytest/e_commerce_testresults/}"
if [ ! -d ${OUT_DIR} ] ; then
    mkdir -p ${OUT_DIR}
fi

if [ -d ./e_commerce_benchmark_report ] ; then
    OUT_FILE="e_commerce_benchmark_report_${USER_NUM}users"
    mv ./e_commerce_benchmark_report ${OUT_DIR}/${OUT_FILE}
    echo "Please check test report under ${OUT_DIR}/${OUT_FILE}"
fi
