#!/bin/bash

if [ -z "${1}" ] ; then
    echo "Usage:please input solr server ip"
    exit 0
fi

host="${1}"
port=${2:-8983}
user="${3:-solr}"
core="e-commerce"

sudo -u ${user} solr -h ${host} create -c "${core}"

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

