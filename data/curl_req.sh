# Sample 01
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/authorized_buyers --data ' {
    "id": "dsfdsklfsknfkns",
    "site": {
      "id": 1,
      "domain": "test.co"
    },
    "imp": [
      {
        "id": "imp1",
        "tagid": "tagid1"
      }
    ],
    "user": {
      "id": "ewewew",
      "geo": {
        "country": "Sri Lanka"
      }
    }
  }'

# Sample 02
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/authorized_buyers --data '{
    "id": "dsfdsklfsknfkns",
    "site": {
      "id": 1,
      "domain": "test.co"
    },
    "imp": [
      {
        "id": "imp1",
        "tagid": "tagid1",
        "w": 500,
        "h": 400
      }
    ],
    "user": {
      "id": "ewewew",
      "geo": {
        "country": "Sri Lanka"
      }
    }
  }'

# Sample 03
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/authorized_buyers --data ' {
    "id": "dsfdsklfsknfkns",
    "site": {
      "id": 1,
      "domain": "test.co"
    },
    "imp": [
      {
        "id": "imp1",
        "tagid": "tagid1",
        "w": 500,
        "h": 400,
        "bidFloor": 0.01
      }
    ],
    "user": {
      "id": "ewewew",
      "geo": {
        "country": "Sri Lanka"
      }
    }
  }'

# Sample 04
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
