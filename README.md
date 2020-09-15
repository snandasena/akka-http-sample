# Prerequisites 
* Java 11

# Run the application
```bash
chmod u+x ./sbt
chmod u+x ./sbt-dist/bin/sbt
./sbt reStart
```

# How to test this application
The following curl command can be used to test this application and more sample requests can be found from [data](./data) folder.
```bash
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/authorized_buyers --data '{
    "id": "dsfdsklfsknfkns",
    "site": {
      "id": 10,
      "domain": "test.co"
    },
    "imp": [
      {
        "id": "imp1",
        "tagid": "tagid1",
        "w": 1000,
        "h": 200,
        "bidFloor": 1.0
      }
    ],
    "user": {
      "id": "ewewew",
      "geo": {
        "country": "Sri Lanka"
      }
    }
  }'
```

#References
* [Akka Http](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)