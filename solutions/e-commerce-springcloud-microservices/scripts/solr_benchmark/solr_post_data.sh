#!/bin/bash

if [ -z "${2}" ] ; then
    echo "Usage: <solr server ip> <data file or data directory> [ <solr server port> ] "
    exit 0
fi

host="${1}"
port=${3:-8983}
data="${2}"

if [ -d "${data}" ] ; then
    data="${data}/*"
fi

core="e-commerce"
post_cmd="post"

if [ -z "$(which post 2>/dev/null)" ] ; then
    post_cmd="/opt/solr/bin/post"
fi

#${post_cmd} -c "${core}" -p "${port}" -host "${host}" ${data} -filetypes csv -params "fieldnames=productid,cateid,title,description,manufacturer,price,quatity,size,url&header=true" -params "separator=%09"
${post_cmd} -c "${core}" -p "${port}" -host "${host}" ${data} -filetypes csv -params "separator=%09"
