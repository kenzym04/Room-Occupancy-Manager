# Hotel Coding Challenge

Build a Hotel room occupancy manager for one of our hotel clients! Our customer has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.
 
Please build a small API that provides an interface for hotels to enter the numbers of Premium and Economy rooms that are available for the night and then tells them immediately how many rooms of each category will be occupied and how much money they will make in total.

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
