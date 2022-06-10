# Demo application for Free.fi

This is demo application for Free.fi. Application is build with Java 11 and Spring boot 2.7.0. 
Application uses H2 in-memory database.

> !Note! Application integrates to ApiLayer and uses existing credentials. If daily usage limit is passed, use exactly the requests in examples to get seed data result.

### Running application

> !You need to have at least Java 11, maven and git cmd installed. Application can be also run in Docker

### Run Spring boot app locally
1. Open command line
2. Go to folder you want to fetch the repo content
3. Enter "git clone https://github.com/aojaranta/freefi-demo.git"
4. Enter "mvn clean package"

** Run java app **
5. Enter "java -jar target/demo-[version].jar". Builded jar file name can be located under ./target folder 


** Run on docker **
5. Enter "docker pull openjdk"
6. Enter "docker build -t demo:latest ."
7. Enter "docker run -p8080:8080 demo:latest"


### API endpoints

application base URL is "http://localhost:8080"

ResponseCodes:
- 2XX -> Success
- 4XX -> Client error
- 5XX -> Server error

** POST /api/ssn  ** - Validate social security number. 

Request
```
curl --location --request POST 'http://localhost:8080/api/ssn' --header 'Content-Type: application/json' --data-raw '{"country_code": "FI","ssn": "040873-155V"}'
```

Response 200
```
{
    "ssn_valid": true/false
}
```

** GET /api/currency  ** - Calculate currency exchange.

Request
```
curl --location --request GET 'http://localhost:8080/api/currency?from=EUR&from_amount=10.00&to=SEK'
```

Response 200
```
{
    "from": "EUR",
    "to": "SEK",
    "to_amount": 105.0000,
    "exchange_rate": 10.00
}
```