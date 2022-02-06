# REST API - Customers

This documentation describes aspects and procedures to run this REST API

## Overview

This API was developed using Springboot 2.1.6.  It uses paging to reduce the overall weight over networking and increase the overall performance.

## Running the Example

1. Clone this repository.
2. Open a terminal in the project directory.
3. Run the command:

    ```bash
    ./mvnw spring-boot:run
    ```

4. Visit: <http://127.0.0.1:8080/v1/customers> (should display a JSON with the customers data)


## Concepts

Pagination - It uses 3 query string parameters, namely: 'page', 'size' and 'sort' to implement the pagination


## License

[MIT](https://opensource.org/licenses/MIT)
