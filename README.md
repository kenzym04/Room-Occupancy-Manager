# Hotel Coding Challenge

Build a room occupancy optimization tool for one of our hotel clients! Our customer has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.


Our hotel clients have two different categories of rooms: Premium and Economy. Our hotels want their customers to be satisfied: they will not book a customer willing to pay EUR 100 or more for the night into an Economy room. But they will book lower paying
customers into Premium rooms if these rooms would be empty and all Economy rooms will be filled by low paying customers. Highest paying customers below EUR 100 will get preference for the “upgrade”. Customers always only have one specific price they are willing to pay for the night.


Please build a small API that provides an interface for hotels to enter the numbers of Premium and Economy rooms that are available for the night and then tells them immediately how many rooms of each category will be occupied and how much money they will make in total. Potential guests are represented by an array of numbers that is their willingness to pay for the night.


Use the following raw JSON file/structure as mock data for potential guests in your tests:
• [23, 45, 155, 374, 22, 99, 100, 101, 115, 209]


# Tests
**[Test 1] amazonaws.com**

Free Premium rooms: 3 Free Economy rooms: 3

Usage Premium: 3 (EUR 738) Usage Economy: 3 (EUR 167)

****[Test 2] amazonaws.com****

Free Premium rooms: 7 Free Economy rooms: 5

Usage Premium: 6 (EUR 1054) Usage Economy: 4 (EUR 189)

****[Test 3] amazonaws.com****

Free Premium rooms: 2 Free Economy rooms: 7

Usage Premium: 2 (EUR 583) Usage Economy: 4 (EUR 189)

****[Test 4] amazonaws.com****

Free Premium rooms: 7 Free Economy rooms: 1

Usage Premium: 7 (EUR 1153) Usage Economy: 1 (EUR 45)

# Deploying
Make sure that you have installed "awslabs/aws-sam-cli" and an AWS account

Update the `deploy.sh` file to change the `s3-bucket` and `stack-name` parameters.

Then run `deploy.sh`.

Check the output tab for the link to the service.
