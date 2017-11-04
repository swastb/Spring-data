## Spring Data Redis


### Build the Project with Tests Running
```
mvn clean install
```

### Run Tests Directly
```
mvn test
```
To Test whether the values got saved in Redis, use the below commands in Redis CLI


This returns all the keys saved ( mainly for product with long)
> keys *

To get the values of product-counts
>GET "product-counts:0"

To get the keys when data is saved as hash data
> hkeys "products:attrs:0"

To get the values use
> hget "products:attrs:0" name

