# Rabobank Customer Statement Processor 
## Build With
* [Java 11](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  - Programming language
* [Maven 3.6.3](https://maven.apache.org/download.cgi) - Build tool
* [Spring boot 2.4.5](https://projects.spring.io/spring-boot/) - Spring Boot 
### Pull from git 
```
$ git clone https://github.com/seyidyagmur/robobank-assignment
$ cd robobank-assignment
```
### Build & run 

* Run test
```
$ mvn test
```

* Run the web server on dev mode
```
$ mvn spring-boot:run
```

### Reference Documentation
Rabobank receives monthly deliveries of customer statement records. This information is delivered in JSON Format.
These records need to be validated.
* Request parameters and explanations should be as follows
```  
     reference -> A numeric value (Long)
     accountNumber -> An IBAN (String)
     startBalance -> The starting balance in Euros (BigDecimal)
     mutation -> addition or a deduction.Must be +/- values (BigDecimal)
     description -> Free text (String)
     endBalance -> The end balance in Euros (BigDecimal)
```     
Request Endpoint must be; 
```  
http://localhost:8080/api/v1/validate
```  
Sample success request should be array json as below;
``` 
[
  {
    "reference":"123",
    "accountNumber":"123",
    "startBalance":"150",
    "mutation":"-50",
    "description":"Description",
    "endBalance":"100"
  }
]
```


## Authors

* **Seyid Yagmur**