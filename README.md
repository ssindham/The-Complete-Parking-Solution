# The Complete Parking Solution

#### **An android appilication developed to overcome the metropolitan city's parking issues. The modules developed are mentioned below.**

1. Valid Parking Spot
2. Renter/Rentee module
3. Online Payment mode
4. Pay Once Park Anywhere

#### **Explanation of each module**

1. Valid Parking Spot
*Situation*
>You have parked your vehicle outside the bank and after coming from bank, you found that your vehicle has been taken by RTO for parking in non-parking area.
*Solution*
>In this first module, if you have this application installed on your smartphone then at the time of parking it will show you the place you are parking your vehicle is a valid parking area or not. Suppose, you got know the place as non-parking zone in the applcation then as you move the application refreshes itself in every 5 seconds and it notifies you whether it is a parking zone or not.

2. Renter/Rentee module
*Situation*
>You are new to the city or you don't have enough space to park your vehicle at your place. You can find the parking places nearer to you according to your requirements. One can even specify the type of vehicle, the time durations, number of vehicles.
*Solution*
>Suppose you are renter, that you are staying in an apartment or you own a piece of land and you want to utilize that land, you can signup and rent that land for parking and earn. 
>If you are a rentee, you can search for parking places and get rented for particular duration and pay the amount to the owner. The search list shown is based on the distance i.e., the nearer places are shown on top of the list.
* The rentee can speicfy that how many vehicle can be parked, for what duration and days.
* The renter can also specify the type of vehicle and search the parking places nearby.

3. Pay Once Park Anywhere
*Situation*
>On a particular day, you have to go to bank, marketplace, mall etc, wherever you go, you have to pay for parking and collect the receipt.
*Solution*
>Instead on that same day you can use this app and decide the timings and pay for parking and then it will generate a QR code. The QR code is generated using `ZXING` library. You can park at multiple parking places within the selected duration. You can show it to the Parking attendee. The parking attendee will verify the details of your parking and allow you to park. 

4. Online Payment mode
*Situation*
>The above mentioned modules need a payment integration.
*Situation*
Online payment is an integration of **paypal** SDK. The SDK is taken from the internet and integrated into the application.


