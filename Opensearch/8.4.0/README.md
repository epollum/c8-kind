# C8 on Opensearch
This repository will create a C8 deployment with all web applications on a Kind cluster you can run locally. It uses Opensearch as a backend for the web applications.

This readme assumes:
1. You have followed the steps in the base readme to set up your /etc/hosts file to direct traffic to your kubernetes cluster.
2. [You have added the Camunda repo to helm](https://docs.camunda.io/docs/next/self-managed/platform-deployment/helm-kubernetes/deploy/#helm-repository)
3. [You have added the Opensearch repo to helm](https://opensearch.org/docs/latest/install-and-configure/install-opensearch/helm/#install-opensearch-using-helm)

## Basic Installation
1. Create the Kind cluster 

`kind create cluster --config create-cluster.yaml`

2. Create your TLS secrets
```
kubectl create secret docker-registry dev-c8-registry \
    	    --docker-server=registry.camunda.cloud \
    	    --docker-username=<your-username> \
    	    --docker-password='<your-password>' \
    	    --docker-email=<your-email>
```
3. Install the nginx-ingress controller

`helm install -f nginx_ingress_values.yaml nginx-ingress oci://ghcr.io/nginxinc/charts/nginx-ingress --version 0.18.0`

4. Install Opensearch

`helm install opensearch opensearch/opensearch -f os-values.yaml`

5. Install C8

`helm install camunda camunda/camunda-platform`