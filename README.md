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

To run tests:
    sbt "clean" "compile" "test"

To run in developer mode
    sbt "project webpaymentapi" "clean" "clean-files" "compile" "run 9100"