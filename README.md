# Parking-garage-simulation
 
## System Requirements

 -The garage should have multiple levels.
 
-Each level should have multiple spots.

-The garage should support different types of vehicles:

•	Cars
•	Motorbikes

-Vehicles should be able to enter and exit the garage 

-The garage should assign a free space or reject the vehicle if there are no more free parking lots. 

apis with 3 endpoints:

•	One used when a vehicle enter

•	One used when a vehicle exists

•	One for status report that generates the status of the garage

-A status is a basic level by level info of how many vacant places and how many occupied places

## Use cases for Garage

- To add a parking level to the system. 
- To add, modify a parking spot on a parking level.
- Assign a spot to vehicle.
- Unassign a vehicle from a parking spot.
- Show garage is full.
- Show available spot count.
- Show occupied spot count.

## Class diagram
- Level: The garage will have many parking levels.
- Spot: Each parking floor will have many parking spots. Our system will support different parking spots: 1) Car, 2) Motorbike
- Vehicle: Vehicles will be parked in the parking spots. Our system will support different types of vehicles: 1) Car, 2) Motorbike
- Parking Record: The parking information is saved for each vehicle with level and spots information.

## Prerequisites
•	H2 in-memory DB
•	Java 1.8
•	Maven 3

## Rest Apis:
It is builded with Docker.

To be able to use the apis, create level and spots. There is no api support this but *TestDataSetupService* injects mock data.

You can use Swagger to control each api via http://localhost:8090/swagger-ui.html

- Create level:

![image](https://user-images.githubusercontent.com/6909124/155438122-a85ba5a5-052e-417a-b19c-c7200420dd48.png)

- Create spot according to level:

![image](https://user-images.githubusercontent.com/6909124/155438160-c0b3bce9-fd7b-4168-8782-a83304e6fcd3.png)


- Get occupied/free spot count:

![image](https://user-images.githubusercontent.com/6909124/155438187-12e3c145-480a-4f17-b12e-c8496b404d67.png)

- Parking a vehicle:

![image](https://user-images.githubusercontent.com/6909124/155438204-8cee3298-a8e4-4d0c-8e5a-a518bf11072b.png)

- Unparking:

![image](https://user-images.githubusercontent.com/6909124/155438216-1a99c4bb-41f9-4741-b2b8-a9850349c781.png)

- Check whether garage is full:

![image](https://user-images.githubusercontent.com/6909124/155438228-83219486-bdfb-4a33-b77b-9067f3d4aa7f.png)

