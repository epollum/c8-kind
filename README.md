# c8-kind
Deployments for C8 using Kind

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
