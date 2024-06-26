# Kind Cluster Installation
Deployments for C8 using Kind

## Installation Steps

1. Decide if you want a combined or seperated ingress. In the respective folders for each ingress setup, you will find a create-cluster.yaml file. Use this to create your Kind cluster. This creates a single node kubernetes cluster.
```
kind create cluster --config create-cluster.yaml
```
You will also find a config file for launching a 4 node cluster. This creates one control plane, and 3 worker nodes. This is useful for running multiple broker scenarios.
```
kind create cluster --config create-multi-node-cluster.yaml
```

2. Add the TLS certificates to the cluster. Be sure to use the correct certificate name:
```
kubectl create secret tls dev-c8-separated-tls \
--cert dev.c8.separated.crt \
--key dev.c8.separated.key
```
or
```
kubectl create secret tls dev-c8-combined-tls \
--cert dev.c8.combined.crt \
--key dev.c8.combined.key
```

3. Add your docker registry information so you can download web-modeler. Replace the placeholders in the below declaration:
```
kubectl create secret docker-registry dev-c8-registry \
--docker-server=registry.camunda.cloud \
--docker-username=firstname.lastname \
--docker-password='password' \
--docker-email=firstname.lastname@camunda.com
```

4. Install the ingress controller of your choice. Open the controller's folder to find the relevant values.yaml to install. Below find the instructions to install the Camunda preferred ingress, [ingress-nginx](ingress-nginx)

```
helm install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace
  -f values.yaml
```

5. You have all the components needed to install Camunda 8. Navigate to the verison you would like to install and select the options you'd like to install. For example, if you would like to install a basic setup with combined ingress, you can naviagte to the version you want to install and run:
```
helm install camunda camunda/camunda-platform -f combined.yaml -f base-values.yaml
```


## (Optional) Install Opensearch

1. Add the Opensearch repo to helm.
```
helm repo add opensearch https://opensearch-project.github.io/helm-charts/
helm repo update
```

2. Install Opensearch
```
helm install opensearch opensearch/opensearch -f os-values.yaml
helm install osd opensearch/opensearch-dashboards -f osd-values.yaml
```

3. If you want to connect Opensearch to AWS you will have to add your secrets as environment variables in os-values.yaml.