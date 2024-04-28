1. Create s3 repository in Elasticsearch
```
curl -X PUT "localhost:9200/_snapshot/backup-repo?pretty" -H 'Content-Type: application/json' -d'
{
  "type": "s3",
  "settings": {
    "bucket": "emma-backup-test"
  }
}
```
2. Add AWS authentication to Elasticsearch/Opensearch

*for opensearch only, you must install the plugin first*
```
sh /usr/share/opensearch/bin/opensearch-plugin install --batch repository-s3
```
```
kubectl exec --stdin --tty elasticsearch-cluster-master-0 -- /bin/bash
```
```
elasticsearch-keystore add --stdin s3.client.default.access_key
elasticsearch-keystore add --stdin s3.client.default.secret_key
```
```
curl -XPOST "http://localhost:9200/_nodes/reload_secure_settings"
```
3. Generate the backups

**Operate**
```
curl --request POST 'https://operate.c8.dev.local/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": 1234}'
```
**Tasklist**
```
curl --request POST 'https://tasklist.c8.dev.local/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": 1234}'
```
**Zeebe**
```
curl --request POST 'https://zeebe.c8.dev.local:9600/actuator/backups' \
-H 'Content-Type: application/json' \
-d '{ "backupId": "1234" }'
```