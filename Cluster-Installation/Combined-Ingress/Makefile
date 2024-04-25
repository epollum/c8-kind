create-cluster:
	kind create cluster --config create-cluster.yaml
	kubectl create secret tls dev-c8-combined-tls \
			--cert dev.c8.combined.crt \
			--key dev.c8.combined.key
	helm install -f ../nginx-ingress/nginx_ingress_values.yaml nginx-ingress oci://ghcr.io/nginxinc/charts/nginx-ingress --version 0.18.0