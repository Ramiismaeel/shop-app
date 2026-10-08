# Shop Service

![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=java&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)


## Programming: ShopService


### Programming: Order Status
Add an order status to the order (Order) (PROCESSING, IN_DELIVERY, COMPLETED) to determine the status of the order.

To do this, create a new branch, create and push the commits, create a pull request, review the PR, and merge it into the main branch.

### Programming: Order Status
Write a method in the ShopService that returns a list of all orders with a specific order status (parameter) using streams.

### Programming: Optional Product
Modify the ‘getProductById’ method in your ProductRepo so that it returns an Optional if the product exists, otherwise an empty Optional.

### Programming: Exceptions
Modify the ‘addOrder’ method in the ShopService so that an exception is thrown if the product does not exist.

### Programming: Lombok
Add a method ‘updateOrder’ in the ShopService that updates the order based on an orderId and a new order status. Use the Lombok annotation @With for this.

### Programming: Order Date
Extend the Order object with a field that stores the order timestamp. Fill this field in the ‘addOrder’ method with the current timestamp.

This timestamp should be usable as evidence in court if customers claim they did not place the order. Consider which data type is most suitable, even if customers order from abroad.

## Bonus: 

### Setup in the Main Repo
Create a Main class with a Main method. In this method, create an instance of the ShopService.

The concrete instances for OrderRepo and ShopRepo should also be created here in the Main method. Pass them to the ShopService constructor. Use the @RequiredArgsConstructor annotation in the ShopService to generate an appropriate constructor.

Define three concrete orders and add them all to the ShopService.

### ID Generation
Create an IdService for generating an ID, which returns a new UUID in the generateId method (using java.util.UUID). Create a concrete implementation of the IdService in the Main method and pass it to the ShopService constructor.

### Pending Orders
Write a method getOldestOrderPerStatus that returns a map with the oldest Order object per status.
