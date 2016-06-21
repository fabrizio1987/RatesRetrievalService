# EntroPay Rates Retrieval Assessment
## Candidate release

### Candidate Name: Pietro Cascio
#### Public Repo: https://github.com/consulbit/RatesRetrievalService

The assessment has been developed based on two branches:
* **master** branch
* **SpringMVC** branch

As required and advised in the specification, the technologies and tools that has been used are the following:
* _Java JDK 1.8+_
* Web container _WildFly 9+_
* Relational Database _H2_ (in-memory instance)
* JPA with Hibernate as persistence layer
* _JSR-352 - Batch Processing_
* _Java EE 7.x_ (for the **master** branch)
* _Spring MVC Framework_ on top of the _Java EE 7.x_ infrastructure (for the **SpringMVC** branch)
* _JAX-RS_ specification using the _RestEasy_ engine implementation (shipped with _WildFly 9+_)

## Master branch
### Batch Processing Layer  
This part of the application is based on the _JSR-352 Specification_ and works in the following way:
in the **META-INF/batch-jobs** there is the bacth job configuration file named **ratesProcessJob.xml**.
In this file, what needs to be configured is the properties section composed of three _property_ tag. 
Next, the explanation of their meaning: 
* **scan_directory** attribute name: _the path folder from which the files will be loaded from the job in order to 
reads, parses and stores their content into a in-memory H2 database_;
* **archive_directory** attribute name: _the path folder where the files will be store after being processed from the job_;
* **failed_directory** attribute name: _the path folder from files that throw errors during the batch process. For each input file 
processed, it'll be created a file in this directory if any record from the input file will throw an error during the parsing process_;

Each processed record, if no errors are occurred, will create a record into the database as per assessment specification.

### REST API Layer
To developed this layer has been used the RestEasy engine implementation (shipped with _WildFly 9+_).
Next, the endpoints to call the application rest api with a REST path root of _**/api**_.

#### Start the job
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/job_ 
* **Accept Content-Type**: none
* **Method**: _POST_

#### Retrieve Rate List
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates_ 
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _GET_

#### Retrieve Rate List filtered by date
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates/param?date=2016-01-03_ 
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _GET_

#### Retrieve Single Rate Item
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates/{id}_ where _**{id}**_ is the rate id. 
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _GET_

#### Add Rate Item
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates_  
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _POST_
* **Body**:
  * **Content-Type**: _application/xml_ or _application/json_
  * **Request Payload**: 
    * **json format**
                           {
                             "Filename": "rates-2016-01-01.DAT.test",
                             "ConversionRate": 0.8655800,
                             "BuyCurrencyCode": "USD",
                             "SellCurrencyCode": "EUR",
                             "ValidDate": "2016-01-01"
                         }
    * **xml format**
                         &lt;rate&gt;
                             &lt;BuyCurrencyCode&gt;USD&lt;/BuyCurrencyCode&gt;
                             &lt;ConversionRate&gt;0.8655800&lt;/ConversionRate&gt;
                             &lt;Filename&gt;rates-2016-01-01.DAT.test&lt;/Filename&gt;
                             &lt;SellCurrencyCode&gt;EUR&lt;/SellCurrencyCode&gt;
                             &lt;ValidDate&gt;2016-01-01T00:00:00+01:00&lt;/ValidDate&gt;
                         &lt;/rate&gt;

#### Update Rate Item
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates_  
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _PUT_
* **Body**:
  * **Content-Type**: _application/xml_ or _application/json_
  * **Request Payload**: 
    * **json format**
                           {
                             "Id": 1,
                             "Filename": "rates-2016-01-01.DAT.test",
                             "ConversionRate": 0.8655800,
                             "BuyCurrencyCode": "USD",
                             "SellCurrencyCode": "EUR",
                             "ValidDate": "2016-01-01"
                         }
    * **xml format**
                         &lt;rate&gt;
                             &lt;BuyCurrencyCode&gt;USD&lt;/BuyCurrencyCode&gt;
                             &lt;ConversionRate&gt;0.8655800&lt;/ConversionRate&gt;
                             &lt;Filename&gt;rates-2016-01-01.DAT.test&lt;/Filename&gt;
                             &lt;Id&gt;1&lt;/Id&gt;
                             &lt;SellCurrencyCode&gt;EUR&lt;/SellCurrencyCode&gt;
                             &lt;ValidDate&gt;2016-01-01T00:00:00+01:00&lt;/ValidDate&gt;
                         &lt;/rate&gt;

#### Delete Rate Item
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates/{id}_ where _**{id}**_ is the rate id. 
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _DELETE_

#### Delete All Rate Items
* **Endpoint Example**: _http://localhost:8080/rates-retrieval-service/api/rates 
* **Accept Content-Type**: _application/xml_ or _application/json_
* **Method**: _DELETE_
