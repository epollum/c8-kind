single-node:
	kind create cluster --config create-cluster.yaml
	kubectl create secret tls dev-c8-separated-tls \
			--cert dev.c8.separated.crt \
			--key dev.c8.separated.key
	helm install ingress-nginx ingress-nginx/ingress-nginx -f ../ingress-nginx/values.yaml
	
multi-node:
	kind create cluster --config create-multi-node-cluster.yaml
	kubectl create secret tls dev-c8-separated-tls \
			--cert dev.c8.separated.crt \
			--key dev.c8.separated.key
	helm install ingress-nginx ingress-nginx/ingress-nginx -f ../ingress-nginx/values.yaml