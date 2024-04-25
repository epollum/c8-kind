create-cluster:
	kind create cluster --config create-cluster.yaml
	kubectl create secret tls dev-c8-separated-tls \
			--cert dev.c8.separated.crt \
			--key dev.c8.separated.key
	helm install -f ../nginx-ingress/nginx_ingress_values.yaml nginx-ingress oci://ghcr.io/nginxinc/charts/nginx-ingress --version 0.18.0