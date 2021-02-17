# Hotel Coding Challenge

Build a Hotel room occupancy manager for one of our hotel clients! Our customer has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.
 
Please build a small API that provides an interface for hotels to enter the numbers of Premium and Economy rooms that are available for the night and then tells them immediately how many rooms of each category will be occupied and how much money they will make in total.

# Tests
**Click here to see [Test 1](https://49tgjcptha.execute-api.us-east-1.amazonaws.com/Prod/rooms?economy=3&premium=3)**

Free Premium rooms: 3 Free Economy rooms: 3

Usage Premium: 3 (EUR 738) Usage Economy: 3 (EUR 167)

**Click here to see [Test 2](https://49tgjcptha.execute-api.us-east-1.amazonaws.com/Prod/rooms?economy=5&premium=7)**

Free Premium rooms: 7 Free Economy rooms: 5

Usage Premium: 6 (EUR 1054) Usage Economy: 4 (EUR 189)

**Click here to see [Test 3](https://49tgjcptha.execute-api.us-east-1.amazonaws.com/Prod/rooms?economy=7&premium=2)**

Free Premium rooms: 2 Free Economy rooms: 7

Usage Premium: 2 (EUR 583) Usage Economy: 4 (EUR 189)

**Click here to see [Test 4](https://49tgjcptha.execute-api.us-east-1.amazonaws.com/Prod/rooms?economy=1&premium=7)**

Free Premium rooms: 7 Free Economy rooms: 1

Usage Premium: 7 (EUR 1153) Usage Economy: 1 (EUR 45)


# Decisions
## Why /rooms GET?
I wanted to provide a flexible and easy to use api. Also it's GET because the service represents a query.
```$xslt
    /rooms?economy=3&premium=3&anotherRoomType=0
```

## Why so many classes?
To solve the optimization problem I decided to use a rules pattern which is very simple, and in order to apply the rules I needed to represent the state of the whole operation.
The state is then composed of `potentialClients`, `availableRooms`, `occupiedRooms` and `revenue`.

### RoomAvailabilityState
Will have a `RoomType`, `availability`, and a list of assigned guests `occupancy`. We then know in the state of a type of room for the hotel.

### RoomsOccupationState
Will have a list of `RoomAvailabilityState` and a list of `potentialClients`.

### PremiumEconomyPremiumOccupancyOptimizer
Contains the current logic behind the assignment of rooms. It defines 3 `RoomOccupancyHighestPriceFirstOptimizer`s. 
Each optimizer defines a price range and assigns the rooms on a most-paying-customer first fashion. 

## Why double for potentialClients?
Double represents better currencies compared to int

# What's the difference between RoomAvailabilityState and RoomOccupationOptimizationResponse?
The response class was added to have more control over the data that is sent to the client.

# How to deploy
Make sure that you have https://github.com/awslabs/aws-sam-cli installed and an AWS account.

Update the `deploy.sh` file to change the `s3-bucket` and `stack-name` parameters.

Run `deploy.sh`.

Check the output tab for the link to the service. In my case [this is the link](https://49tgjcptha.execute-api.us-east-1.amazonaws.com/Prod/rooms?economy=3&premium=3)