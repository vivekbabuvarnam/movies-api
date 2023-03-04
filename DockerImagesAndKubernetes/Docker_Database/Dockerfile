FROM postgres

ENV POSTGRES_USER user
ENV POSTGRES_PASSWORD admin
COPY create_tables.sql /docker-entrypoint-initdb.d/
