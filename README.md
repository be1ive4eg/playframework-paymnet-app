# playframework-paymnet-app
Example web application using playframework, ebean-orm, guice

This example app has two modules
 - hiddensign-core-payment-api (dao, service, entities layer)
 - hiddensign-web-payment-api (web controller layer)

Web app has standard for playframework application's structure

 - app
    - controllers
    - models
    - views
 - conf
    - application.conf
    - routes
 - project
    - build.properties
    - plugins.sbt

Web rest api usage

 - account
    - add account
        - curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{ "balance": 100, "ccy": "RUB" }' http://localhost:9100/accounts
    - get account by "accId"
        - curl -H "Accept: application/json" -H "Content-type: application/json" -X GET http://localhost:9100/accounts/{accId}
    - transfer between "fromAcc" and "toAcc"
        - curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{ "fromAcc": 1, "toAcc": 2, "amount": 50 }' http://localhost:9100/accounts/transfer



To run tests:
    - sbt "clean" "compile" "test"

To build distribution with sh and bat
    - sbt "clean" "compile" "dist"

To run in developer mode:
    - sbt "project webpaymentapi" "clean" "clean-files" "compile" "run 9100"
