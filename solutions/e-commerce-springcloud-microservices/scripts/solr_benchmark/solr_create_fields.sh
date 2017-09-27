#!/bin/bash

if [ -z "${1}" ] ; then
    echo "Usage:please input solr server ip"
    exit 0
fi

host="${1}"
port=${2:-8983}
user="${3:-solr}"
core="e-commerce"

solr_cmd="solr"
if [ -z "$(which solr 2>/dev/null)" ] ; then
    solr_cmd="/opt/solr/bin/solr"
fi

sudo -u ${user} ${solr_cmd} create -c "${core}" -p "${port}"

######### update e-commerce cache_size ########################################################
curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.filterCache.size":10240000, "query.filterCache.initialSize":10240000, "query.filterCache.autowarmCount":4096}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.queryResultCache.size":10240000, "query.queryResultCache.initialSize":10240000, "query.queryResultCache.autowarmCount":4096}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.documentCache.size":10240000, "query.documentCache.initialSize":10240000, "query.documentCache.autowarmCount":4096}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.fieldValueCache.size":10240000, "query.fieldValueCache.initialSize":10240000, "query.fieldValueCache.autowarmCount":4096}}' \
      http://${host}:${port}/solr/${core}/config

######### create e-commerce core fields ########################################################
curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"add-field": { "name":"productid", "type":"int", "stored":true,  "indexed":true}}' \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"cateid", "type":"int", "stored":true, "indexed":true}}'       \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"title", "type":"string", "stored":true, "indexed":true}}'     \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"description", "type":"string", "stored":true, "indexed":true}}'  \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"manufacturer","type":"string", "stored":true,"indexed":true}}'  \
     http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary  \
     '{"add-field": {"name":"price","type":"int","stored":true, "indexed":true}}'            \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary  \
     '{"add-field": {"name":"quatity","type":"int","stored":true,"indexed":false}}'          \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"size","type":"string","stored":true,"indexed":false}}'           \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"url","type":"string","stored":true,"indexed":false}}'           \
      http://${host}:${port}/solr/${core}/schema

echo "\n\n====================================================================================="
curl -X GET http://${host}:${port}/solr/${core}/schema/fields

