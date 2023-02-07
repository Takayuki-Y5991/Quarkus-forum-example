postgres:
	docker run --name postgres15 -p 5432:5432 -e POSTGRES_USER=quarkus -e POSTGRES_PASSWORD=password  postgres:15-alpine
createdb:
	docker exec -it postgres15 createdb --username=quarkus --owner=quarkus forum

.PHONY: postgres createdb
