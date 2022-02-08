# REST API - Customers

This documentation describes aspects and procedures to run this REST API

## Overview

This API was developed using Springboot 2.1.6.  It uses paging to reduce the overall weight over networking and increase the overall performance.

## Running the Example

1. Using the bash or command prompt navigate to the project directory
2. Run the command:
	```docker build -t jumia-api .```
3. Run the command
	```docker run -p 8080:8080 jumia-api```
4. Visit: <http://127.0.0.1:8080/v1/customers> (should display a JSON with the customers data)
5. Visit <http://localhost:8080/swagger-ui/index.html> (should display the Swagger Interface)

## Concepts

### Features
- Pagination - It uses 2 query string parameters, namely: 'page', 'size' 
- Sorting - It uses the parameter 'sort' to sort the results

#### Database Alterations
To make simpler the filtering of Valid and Not Valid Scenario and the Country filtering I asked to Pedro Rego and Ricardo Taboada if it would be possible to execute an ALTER table so the filtering would become more efficient and neat, filtering everything directly from the Database and avoid traffic of non usable payload.  Pedro authorized and I did the changes.

The Valid and Not Valid numbers were updated inside the Database using the REGEEX supplied in the documentation.  The Country code was replicated inside the table so the queries to filter would run faster.  Instead of doing text comparison (the phone number is TEXT on DB and String on Java) it compares Integers, a better and faster way.
 
### Testing Strategy
- In order to keep the test as close as possible to the real case use I decided to run the tests using SpringBoot and not Mock any interfaces or object, rather than this I run the Springboot application and fire requests as it should be fired from the Frontend.

## License

[MIT](https://opensource.org/licenses/MIT)