# plugflow

This is the server component of Reactively a user behavior analytics platform. It designed to be a bare minimal layer that collects user events from frontends or backends using a simple HTTP interface with string schemas and Avro.

## Design

### Tech

Depending on what is the most expensive operation this can be the path for any message:

```
http post to /messages/$type -> validation -> ok -> channel-inbound-$type -> convert to clj? -> channel-outbound-type -> write orc
```

Questions:

- do we need to convert Avro byte[] to Clojure type or we could have a recursive function that directly translates the message to ORC?
- if we need to convert it, do we need to have multiple convert to clj threads per message type?
- is shring the writer among threds a good idea?


## Usage

### Building a single JAR

```
lein uberjar
```

### Running in dev mode

```
lein run
```

Generating test load:

```bash
while true; do 
  curl -XPOST -H "Content-type: application/json" -v \
-d '{"name":"Public Platform","subdomain":"cloud","awsRegion":"dev-external"}' http://localhost:8080/messages/test;
  sleep 10;
done
```

```fish
while true
  curl -XPOST -H "Content-type: application/json" -v -d '{"name":"Public Platform","subdomain":"cloud","awsRegion":"dev-external"}' http://localhost:8080/messages/test
  sleep 10
end
```


## License

Copyright StreamBright LLC © 2017 AGPL

