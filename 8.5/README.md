# Camunda 8.5 Kind Cluster Install

## Base Installation Steps
You must do this before installing any of the clusters available here.
1. [Install Kind](https://kind.sigs.k8s.io/docs/user/quick-start/)
2. Install the Camunda 8 helm repo

```
helm repo add camunda https://helm.camunda.io
helm repo update
```

3. Add the following to your /etc/hosts file
```
#C8
127.0.0.1   c8.dev.local
127.0.0.1   identity.c8.dev.local
127.0.0.1   keycloak.c8.dev.local
127.0.0.1   modeler.c8.dev.local
127.0.0.1   operate.c8.dev.local
127.0.0.1   optimize.c8.dev.local
127.0.0.1   tasklist.c8.dev.local
127.0.0.1   web-sockets.c8.dev.local
127.0.0.1   zeebe.c8.dev.local
```

4. Start the Kind Cluster
`kind create cluster --config create-cluster.yaml`

3. Add TLS secret for seperated ingress
```
kubectl create secret tls dev-c8-separated \
--cert dev.c8.separated.crt \
--key dev.c8.separated.key
```
4. Add docker registry credentials to have access to Web Modeler
```
kubectl create secret docker-registry dev-c8-registry \
--docker-server=registry.camunda.cloud \
--docker-username=firstname.lastname \
--docker-password='password' \
--docker-email=firstname.lastname@camunda.com
```
5. Install ngnix ingress controller
`helm install -f nginx_ingress_values.yaml nginx-ingress oci://ghcr.io/nginxinc/charts/nginx-ingress --version 0.18.0`