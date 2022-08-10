# kafka

This module contains several projects with kafka:

* ...


## Installation and running:

You must have docker on your machine

### Env: 
1. Go into the kafka-connect folder and run command "docker build -t kafka-connect .".
It will create container for kafka-connect with driver for mongodb-sink-connector
2. Go into root project folder and run "docker compose -f env.yml up"

UI tools:
1. kafka - localhost:8070
2. postgres - localhost:5050, username test@gmail.com, password - postgres;
connection - host: host.docker.internal, port: 5432, user: postgres, password: postgres
