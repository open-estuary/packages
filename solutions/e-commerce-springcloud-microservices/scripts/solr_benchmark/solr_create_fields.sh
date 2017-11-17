#!/bin/bash

if [ -z "${1}" ] ; then
    echo "Usage: solr_create_fields.sh <solr ip> <solr port> <solr user> <solrcloud_enable>"
    exit 0
fi

host="${1}"
port=${2:-8983}
user="${3:-solr}"
core="e-commerce"

solrcloud_enable=0
solr_cmd="solr"
if [ -z "$(which solr 2>/dev/null)" ] ; then
    solr_cmd="/opt/solr/bin/solr"
fi

if [ ! -z "${4}" ] && [ ${4} -eq 1 ] ; then
    solrcloud_enable=1
    echo "Enable SolrCloud Mode..."
fi

num_shards=3
repl_factor=1
maxshard_pernode=1
config_name="e-commerce"
######### Create core or collections (if solrcloud) ###########################################
if [ ${solrcloud_enable} -eq 0 ] ; then
    sudo -u ${user} ${solr_cmd} create -c "${core}" -p "${port}"
else
      #In case that old collection exist, just delete it before creating new one.
      curl -X POST -H 'Content-type:application/json'  \
      http://${host}:${port}/solr/admin/collections?"action=DELETE&name=${core}&numShards=${num_shards}&replicationFactor=${repl_factor}&maxShardsPerNode=${maxshard_pernode}&collection.configName=${config_name}"

      curl -X POST -H 'Content-type:application/json'  \
      http://${host}:${port}/solr/admin/collections?"action=CREATE&name=${core}&numShards=${num_shards}&replicationFactor=${repl_factor}&maxShardsPerNode=${maxshard_pernode}&collection.configName=${config_name}"
fi

#exit 0

######### update e-commerce cache_size ########################################################
curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.filterCache.class":"solr.FastLRUCache", "query.filterCache.size":512, "query.filterCache.initialSize":1024, "query.filterCache.autowarmCount":256}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.queryResultCache.class":"solr.FastLRUCache", "query.queryResultCache.size":512, "query.queryResultCache.initialSize":1024, "query.queryResultCache.autowarmCount":256}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.documentCache.class":"solr.FastLRUCache", "query.documentCache.size":1024, "query.documentCache.initialSize":1024, "query.documentCache.autowarmCount":256}}' \
      http://${host}:${port}/solr/${core}/config

curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"set-property": { "query.fieldValueCache.class":"solr.FastLRUCache", "query.fieldValueCache.size":1024, "query.fieldValueCache.initialSize":512, "query.fieldValueCache.autowarmCount":256}}' \
      http://${host}:${port}/solr/${core}/config

######### create chinese field type ############################################################
curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"add-field-type": { "name":"text-smartcn", "class": "solr.TextField", "analyzer":{ "tokenizer": {"class":"solr.HMMChineseTokenizerFactory"}, "filter":[{"class":"solr.SmartChineseWordTokenFilterFactory"}, {"class":"solr.CJKWidthFilterFactory", "words":"org/apache/lucene/analysis/cn/smart/stopwords.txt"},{"class":"solr.PorterStemFilterFactory"},{"class":"solr.LowerCaseFilterFactory"}]}}}' \
      http://${host}:${port}/solr/${core}/schema

######### create e-commerce core fields ########################################################
curl -X POST -H 'Content-type:application/json' --data-binary \
      '{"add-field": { "name":"productid", "type":"int", "stored":true,  "indexed":true, "docValues":true}}' \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"cateid", "type":"int", "stored":true, "indexed":true, "docValues":true}}'       \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"title", "type":"text-smartcn", "stored":false, "indexed":true}}'     \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"description", "type":"text-smartcn", "stored":false, "indexed":true}}'  \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"manufacturer","type":"text-smartcn", "stored":false,"indexed":true}}'  \
     http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary  \
     '{"add-field": {"name":"price","type":"int","stored":false, "indexed":true, "docValues":true}}'            \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary  \
     '{"add-field": {"name":"quatity","type":"int","stored":false,"indexed":false}}'          \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"size","type":"text-smartcn","stored":false,"indexed":false}}'           \
      http://${host}:${port}/solr/${core}/schema

curl -X POST -H 'Content-type:application/json' --data-binary \
     '{"add-field": {"name":"url","type":"string","stored":false,"indexed":false}}'           \
      http://${host}:${port}/solr/${core}/schema

echo "\n\n====================================================================================="
curl -X GET http://${host}:${port}/solr/${core}/schema/fields

