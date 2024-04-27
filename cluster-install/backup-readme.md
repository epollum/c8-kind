
curl -X PUT "localhost:9200/_snapshot/backup-repo?pretty" -H 'Content-Type: application/json' -d'
{
  "type": "s3",
  "settings": {
    "bucket": "emma-backup-test"
  }
}

kubectl exec --stdin --tty opensearch-cluster-master-0 -- /bin/bash

Elasticsearch:
elasticsearch-keystore add --stdin s3.client.default.access_key elasticsearch-keystore add --stdin s3.client.default.secret_key

elasticsearch-plugin install --batch repository-s3


Opensearch:
sh /usr/share/opensearch/bin/opensearch-plugin install --batch repository-s3

echo $AWS_ACCESS_KEY_ID | /usr/share/opensearch/bin/opensearch-keystore add --stdin s3.client.default.access_key &&
echo $AWS_SECRET_ACCESS_KEY | /usr/share/opensearch/bin/opensearch-keystore add --stdin s3.client.default.secret_key

curl -XPOST "http://localhost:9200/_nodes/reload_secure_settings"



curl --request POST 'https://operate.c8.dev.local/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": 1234}'

curl --request POST 'https://tasklist.c8.dev.local/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": 1234}'

curl --request POST 'http://localhost:9600/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": "1234" }'



tar -xzf zeebe-distribution-8.4.5.tar.gz -C zeebe /
./bin/restore --backupId=1234