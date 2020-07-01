# mobileAPI
REST API with OAuth2 using Springboot 2.2.X written in Kotlin

This repository is part of a series of tutorials:
- [Part 1. First Controller](https://proandroiddev.com/how-to-create-a-rest-api-for-your-app-with-spring-boot-kotlin-gradle-part-1-first-controller-c19fe075e968)
- [Part 2. Securing with OAuth2](https://proandroiddev.com/how-to-create-a-rest-api-for-your-app-with-spring-boot-kotlin-gradle-part-2-security-with-32f944918fe1)
- [Part 3. Adding a H2 database](https://proandroiddev.com/how-to-create-a-rest-api-for-your-app-with-spring-boot-kotlin-gradle-part-3-adding-a-h2-7f9e6219b367)
- [Part 4. Testing the API](https://proandroiddev.com/how-to-create-a-rest-api-for-your-app-with-spring-boot-kotlin-gradle-part-4-testing-a66ab6846e8f)
- [Part 5. Deploy on Heroku](https://proandroiddev.com/how-to-create-a-rest-api-for-your-app-with-spring-boot-kotlin-gradle-part-5-deploy-on-heroku-ff21e77ea5f3)

### Instructions

1 - Run the server
```
./gradlew bootRun
```

2 - Test the API

There is a [postman configuration](./doc/postman/MobileAPI.postman_collection.json)
 that you can import and start testing the web services.
 
 ### Gradle tasks
```
./gradlew detektAll //Code analysis.
./gradlew checkDependencyUpdates //Check dependency updates.
```

