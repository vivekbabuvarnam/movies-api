## Kubernetes
************
1. Currrently the Api and postgres database can be run as seperate component in Kubernetes.
2. If the load is increased then scale up the API pods to handle the load(just by increasing the number of replicas in pod definition!)
3. If required convert the JWT token generation and Authentication/Authorization as seperate deployable component so that API pod load can be reduced
4. In future introduce the service type as LoadBalancer so that load can be routed amoung all available API pods
5. In future introduce HAproxy or other relavant softwares which can route the traffice to all the API pods based on the ingress

## Database
*********
1. Fine tune the hikari connection pool for increased performance
2. Analyze and Introduces indexing for better database performance
