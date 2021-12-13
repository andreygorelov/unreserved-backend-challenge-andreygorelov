# Unreserved Backend Code Challenge

## Introduction
Welcome to the Unreserved Backend Code Challenge! Please carefully read the instructions and complete the assigned tasks.
You can modify any file in the project unless otherwise stated.
Please commit your changes into a new branch and create a pull request when the challenge is complete.

## Quickstart
1. To launch the application run `mvn spring-boot:run` in the project root.
2. Open <http://localhost:8080/now> to check the application is up 
3. Open <http://localhost:8080/h2-console> connect to the H2 database with the following config:
   ````
   JDBC URL: jdbc:h2:mem:unreserved
   User Name: sa
   Password: 
   ````
    
## Challenge
Note: there are some TODOs in the project pointing to missing logic. 
Please address those and remove the TODOs after. 
Completed challenge should not have any TODOs.

### Evaluation criteria
- correctness and completeness of assigned tasks
- solutions simplicity
- code style and consistency with the existing codebase
- use of git, following conventions

### Tasks:
1. Listings are one of 2 types - `Condo` or `House`. Extend the `Listing` data model to be one of types `Condo` / `House` containing type-specific fields:
```
Condo:
- monthly condo fees
- reference to Building

House:
- lot square footage
```

   - use `V02` to implement the data model migration. 
   - use `V03` to bootstrap the listings mentioned in `V03__bootstrap_listings.sql`, feel free to add any additional entries to make a diverse dataset for task 5. 

2. Implement `POST /listings` API creating a new listing from the input and returning the created entity. Implement input validation
   - Use random UUID for new entity id
   - Use `DRAFT` for new entity status
   - Input:
      - type - required, `HOUSE | CONDO`
      - reservePrice - optional, must be a positive number if provided
      - address1 - optional
      - city - optional
      - regionCode - optional, must be 2 uppercase letters if provided
      - bedNumber - optional, should be a positive number if provided
      - bathNumber - optional, positive, can be either integer or integer and a half if provided
      - propertySqft - optional, should be a positive number if provided
      - monthlyCondoFees - optional, positive, can only be for `CONDO` listing type
      - buildingId - optional, foreign key to Building, can only be for `CONDO` listing type
      - lotSqft - optional, positive, can only be for `HOUSE` listing type
   
3. Implement `GET /listings` API returning a list of listings with the following requirements:
   - should support pagination
   - should support filters; any filter is optional, should be case-insensitive and work together as `AND`:
     - listing type `HOUSE|CONDO`
     - city(should support multiple values)
     - reserve price LTE, reserve price GTE
   - response format:
   ```
   {
        "listings": [
             {
                 "id": "id",
                 "type": "CONDO",
                 "status": "DRAFT",
                 "reserve_price": 1200000,
                 "address1": "address 1",
                 "city": "City",
                 "region_code": "RC",
                 "country": "Country",
                 "bed_number": 2,
                 "bath_number": 2.5,
                 "property_sqft": 800,
                 "monthly_condo_fees": 1230.4,
                 "building_name": "Neptune 1"
             }
        ]
    }
   ```

4. Add a new entity `Customer`:
   ```
   Customer { 
     id: UUID, nonnull
     createdAt: Instant, nonnull
     email: String, nonnull, unique
     # Collection of listings that the customer is interested in
     listingsInterested: Collection<Listing>
   }
   ``` 
   - use `V04` migration to add a new `customer` table and `V05` to bootstrap sample customers data
   - implement `GET /customers` and `POST /customers` APIs. 
   - `GET` should return all customers sorted by `createdAt` DESC, response format: 
   ```
   GET /customers
   {
      customers: [
         {
            "id": "id1",
            "createdAt": "2021-01-01T01:01:01Z",
            "email": "test@test.com",
            "listingsInterested": [
               {
                    "id": "id",
                    "type": "CONDO",
                    "status": "DRAFT",
                    "reserve_price": 1200000,
                    "address1": "address 1",
                    "city": "City",
                    "region_code": "RC",
                    "country": "Country",
                    "bed_number": 2,
                    "bath_number": 2.5,
                    "property_sqft": 800,
                    "monthly_condo_fees": 1230.4,
                    "building_name": "Neptune 1"
               }
            ]
         }
      ]
   }
   ```
   - `POST` should return the created customer entity and accept the following input:
      - email - required, should be unique
      - listingIdsInterested - optional, should contain valid listing ids
    
5. Add a new service which will log(level INFO) a report every 60 seconds with the following data:
    - Average reserve price by listings city. 
      Should be ordered by the number DESC and show only cities where the number is higher than 1,000,000(threshold value may be changed in the future).
    - Count of interested customers aggregated by listing building name(condos only), sorted by the count DESC.
    
    Result example:
    ```
    Average listing reserve price by city:
        Toronto : 1,210,000
        Vancouver : 1,150,000
        Ottawa : 1,030,000
    
    Number of interested customers by listing building name:
        Neptune 2 : 12
        Atlantis : 7
        Neptune 1 : 5
    ```
   

### Bonus task
Implement tests covering `GET /listings` and `POST /listings`

