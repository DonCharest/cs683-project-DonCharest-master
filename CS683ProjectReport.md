# Loan Payment Calculator Application

## Overview
For my term project, I will be creating a loan payment calculator application. 

I like the idea of creating an application that performs a simple task, requires little overhead, and has the potential for a large userbase. I feel that if I can successfully get an application like this into the play store, it will allow me to work on several other, more complex, utility type applications in the future.

This application will be useful to anyone looking open a new loan, whether to purchase a home, car, education, or refinance existing debt.

## Related Work
There are several similar applications available in the Google play store, as well as web-based applications that allow a user to calculate a loan payment using an annuity formula, with fixed interest rates. However, I would like to add some features to my app to provide differentiation, including: the ability to make additional principle payments, display an amortization schedule, and provide a graphical output of the loan details. 


## Requirement Analysis and Testing 
*Testing has been completed using the __Nexus 5X API 25__*

### Iteration 1
#### Calculate Loan Payment - *(Scheduled and Completed in Iteration 1)*

1. The plan for Iteration 1, was to implement the Main Activity (Calculate Loan Payment), which allows a user to enter in the terms of the loan including: principle, interest rate, and the number of monthly payments. Upon completing the form, the user clicks the submit button and the monthly payment amount is displayed in the view.

2. While creating the layout, I deviated slightly from the initial markups, in that I eliminated the TextEdit field for the returned monthly payment amount, and instead used a TextView field, which is only displayed after the user completes the form and clicks the submit button.

3. Additionally, I added two new TextView fields, which provide the user with more details of loan:
    1. Total cost - this TextView field returns the total amount that will be paid when the loan has been satisfied.
    2. Total interest - his TextView field returns the total amount of interest that will be paid when the loan has been satisfied.

#### The following screenshot shows the Calculate Loan Screen, upon loading or refreshing:
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/68616406-f9bef880-0492-11ea-907b-8267c2039680.PNG">   

#### The following screenshot shows the Calculate Loan Screen, upon form completion and submitting:
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/68616513-2bd05a80-0493-11ea-8a24-03ac828b3947.PNG">  

### Iteration 2
#### Display Amortization Schedule - *(Scheduled and Completed in Iteration 2)*
1. The plan for Iteration 2, was to implement the Amortization Schedule, which allows a user to view the complete loan schedule, line-by-line.
2. This new Activity is viewable by swiping left in the Main Activity.
3. In creating the schedule table layout, I added following data point:
    1. __Balance-end__ - which displays the balance after the current installment due is paid. 

#### The following screenshot shows the Amortization Schedule Screen, upon loading *(by swiping left in Main Activity)*:
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69119872-d9c89f80-0a65-11ea-857c-54484acf4f2f.PNG">   

### Iteration 3       
    
#### Complete data sharing between Main Activity and Amortization Schedule Activity - *(Scheduled and Completed in Iteration 3)*
1. In the previous Iteration, the Amortization Schedule was implemented; however, the loan detail parameters were hard-coded and not passed along on the creation of the new Activity.
    1. To resolve this issue, I updated the MainActivity Class to pass the user defined parameters over to the Amortization Schedule Activity upon swiping the display, which essentially creates a new view using Intent, and passes the bundle of data at the same time. Once received in the new activity, the data can now be extracted and used to dynamically populate the amortization table.

2. Upon swiping on the screen to switch views, the previously entered data would be gone after returning to that view. While this didn't impact the overall functionality, I thought it was not a good user experience. This is especially true if you want to do quick comparisons, without having to re-enter all of the parameters, or simply switch back-and-forth between the views. Moreover, this was not my design intent.
    1. To resolve this issue, the activity parameters for __.MainActivity__ were modified to enable only one instance of the activity to be created, allowing the user to switch back-and-forth between the two activities, while preserving the application session and data. To facilitate this, changes to the following parameters were set:
        1. android:alwaysRetainTaskState="true"
        2. android:launchMode="singleTask"

#### Apply additional principle payment - *(Scheduled and Completed in Iteration 3)*
1. The desired requirement to apply an optional, additional recurring monthly principle payment has been implemented. While this change does not affect the minimum monthly payment due, it does have a great impact on the length of the loan and the total amount of interest paid, which is more dramatic with larger extra payment amounts and/or longer loan payback periods. 
    1. In order to apply an additional payment, I updated the __activity_main.xml__ layout to include a new field, where the user can enter an optional, extra principle payment along with each monthly installment. 
    2. Additionally, this new datapoint was added to the bundled data and sent over to the new view table view upon creation. 
    3. In the Main Activity these changes are reflected in the Total Cost and the Total Interest paid on the loan. 
    4. In the Amortization Schedule, these changes are reflected in each row of the table, with a final payment that is less than the recurring monthly payment, and typically fewer monthly payments are required to pay back the loan.
    5. To prevent bad data from being collected and sent over to the AmortizationSheduleActivity Class, I have the __extraPayment__ field pre-populated to contain an amount of $0. 
        1. If the user chooses to add an extra payment, they can simply rewrite this value. 
        2. If the user deletes this pre-populated value, I set up a form validation using Toast to prompt the user that the field cannot be left empty.

#### The following screenshot displays the revised MainActivity View: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69576283-f438d680-0f99-11ea-841b-3db062f812e5.PNG">

#### The following screenshot displays the revised table schedule:  
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69576284-f438d680-0f99-11ea-9002-b4b089c4fcd4.PNG"> 

#### The following screenshot displays the form validation prompt for the extraPayment field: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69592470-f9ab1680-0fc3-11ea-9752-09acfa5c9bf1.PNG">


#### Update Main Activity - *(Scheduled and Completed in Iteration 3)*
1. The Main Activity has been updated to reflect the correct _Total Cost & Total Interest_ paid on a loan, when an additional monthly principle payment is applied, which differs than when no additioanl payment is applied.

#### The following screenshot displays the _Total Cost & Total Interest_ paid on a loan with _no additional monthly principle payment_: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69600097-6aaaf800-0fdd-11ea-97ea-84892123b5e6.PNG">

#### The following screenshot displays the _Total Cost & Total Interest_ paid on a loan with a _$50 additional monthly payment_: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69599842-86fa6500-0fdc-11ea-9e28-1276abfd291e.PNG">

### Iteration 4     

#### Display Loan Details View - *(Scheduled and Completed in Iteration 4)*
1. The optional requirement to include a __Loan Details View has been implemented__. 
    1. This new view __provides a graphical representation__ of the loan details, in the form of a _Donut Graph_, by displaying the __Total Cost__ of the loan and breaks out the amounts paid to __Principle__ and __Interest__ respectively. 
    2. This new view is displayed by swiping right in the main activity view.
    3. The user may return to the MainActivity by swiping left.
    #### The following screenshot displays the new Loan Details View: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70115382-85a8e800-162d-11ea-8fc9-542393ffa383.PNG">
    
#### Update Main Activity - *(Scheduled and Completed in Iteration 4)*
1. The Main Activity Java file has been updated to restrict the main view from being changed until a loan payment has been calculated. 
    1. This was implemented by adding a control statement to the onTouchEvent.

### Iteration 5

#### Export the amortization schedule - *(Scheduled and Completed in Iteration 5)*
1. The Amortization Schedule Activity has been updated and now includes the optional requirement to provide the user with the ability to export or print the amortization schedule table. Since generating a loan payment schedule is relatively quick and easy, in that the user only needs to enter in three parameters, I did not find much value in saving the all the data for later recall. 
2. However, I felt that exporting the table would provide value in allowing the table can be printed, saved, or shared more easily.

#### The following screenshot displays the PDF export of the Amortization Schedule: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448543-82b75880-1a6e-11ea-8f55-5a35fb3db258.PNG">

#### The following screenshot displays the PDF export of the Amortization Schedule, with user options: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448542-82b75880-1a6e-11ea-97f7-30ce785d3a96.PNG">

#### Update 2nd Activity - *(Scheduled and Completed in Iteration 5)*
1. The Amortization Schedule layout view has been updated to include a button to allow the user to print the table to pdf.

#### The following screenshot displays the updated Amortization Schedule View, which now includes a print button: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448539-82b75880-1a6e-11ea-9a37-106a5d014ba5.PNG">

#### Update User Interface - *(Scheduled and Completed in Iteration 5)*
1. The UI has been updated to now display comma separators for all currency input and output fields including:
    1. Main Activity - the EditText fields include a watcher and update the text as the user enters it in to include or remove commas.
    2. Loan Details Activity - The TextView fields now display comma delimited currency values.
    3. Amortization Schedule - The table output fields now include comma delimited currency values.

#### The following screenshot displays the updated Main Activity View, which now includes comma delimited currency values: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448547-834fef00-1a6e-11ea-83fc-c460425ed213.PNG"> 

#### The following screenshot displays the updated Loan Details View, which now includes comma delimited currency values: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448545-834fef00-1a6e-11ea-8a2a-8b0c099ac4d3.PNG"> 

#### The following screenshot displays the updated Amortization Schedule View, which now includes comma delimited currency values: 
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70448544-82b75880-1a6e-11ea-8cf0-ad3dff2cc125.PNG">    

    
## Design and Implementation

### Iteration 1
#### Calculate Loan Payment
This component uses a simple form to collect user input for three required parameters - *form validation, with Toast is used on these parameters, to ensure valid, error-free data is processed and returned*.
1. **Principle**: this is the amount of money to be borrowed
    1. input type: numberDecimal
    2. form validation:
        1. no empty field -> *text: "You did not enter the principle"*
        2. (value must be > 0) -> *text: "The principle must be greater than zero"*
2. **Interest Rate**: this is the annual percentage rate of the loan
    1. input type: numberDecimal
    2. form validation:
        1. no empty field -> *text: "You did not enter the interest rate"*
3. **Time Period**: this is the number of loan payments (due monthly)
    1. input type: number
    2. form validation:
        1. no empty field -> *text: "You did not enter the time period"*
        2. (value must be > 0) -> *text: "The time period must be greater than zero"*
        
Upon completing the form and clicking the submit button, three data points are returned and displayed on the view
1. **Monthly Payment**: this is the recurring monthly installment payment calculated
    1. returns a $USD formatted 2-place decimal number.
2. **Total Cost**: this is the total amount that will be paid back after the terms of the loan have been satisfied.
    1. returns a $USD formatted 2-place decimal number.
3. **Total Interest**: This is total amount of interest that will be paid after the terms of the loan have been satisfied (total cost - principle).
    1. returns a $USD formatted 2-place decimal number.
    
#### The following screenshot shows the Calculate Loan Screen - *form validation for an empty field*:
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/68621796-a05cc680-049e-11ea-8ab9-a3de404d56a4.PNG">   

#### The following screenshot shows the Calculate Loan Screen - *form validation for a value not greater than zero*:
<img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/68621800-a2bf2080-049e-11ea-8467-c8b3e2dabf40.PNG">   

### Iteration 2
#### Display Amortization Schedule
This activity is started by swiping left from the Main Activity. To implement this functionality I created an onTouchEvent inside the Main Activity. Conversely, I created a second onTouchEvent inside the Amortization Schedule Activity to return to the Main Activity by swiping right.

The Amortization schedule is displayed within a TableLayout, nested inside a ScrollView, in order to view the table data which exceeds the boundaries of the view.

The plan is to pass the user-entered form data from the Main Activity to the Amortization Schedule Activity. I am trying to implement this data transfer by creating a Bundle, which contains the required parameters including:
1. double amount
2. double interestRate
3. double payment
4. int timePeriod

The Bundle is then to be shared by creating a new Intent, within the Main Activity and passing the Bundle using the putExtras method. The bundle is then received in the Amortization Schedule Activity, extracted from the Bundle, and finally passed from onCreate() into init(), through the method signature.

However, I am currently trying to resolve this error *.getExtras()' on a null object reference*. In the meantime, I am using hard-coded loan detail parameters to populate the Amortization Schedule table.

#### The following screenshot shows the onTouchEvent() in the Main Activity:
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69121779-1cd94180-0a6b-11ea-87a4-91833e57fdd7.PNG">   

#### The following screenshot shows the onTouchEvent() in the Amortization Schedule Activity:
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69121785-21055f00-0a6b-11ea-80e9-a93db494633f.PNG">   

#### The following screenshot shows the Bundle being created and passed from the Main Activity to the Amortization Schedule Activity using Intent:
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69121790-26fb4000-0a6b-11ea-928c-8e3b12b1b3ba.PNG"> 

#### The following screenshot shows the Bundle being received in the Amortization Schedule Activity from the Main Activity through Intent; the Bundle parameters are being extracted and passed through the init() function:
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69121799-2bbff400-0a6b-11ea-8b2d-c72b38260f2c.PNG">

### Iteration 3
#### Complete data sharing between Main Activity and Amortization Schedule Activity
1. Updated the __MainActivity Class__ to create the new class variable __Bundle loanData__, this Bundle contains the following user defined loan parameters:
    1. [A] - amount of loan
    2. [i] - interest rate
    3. [P] - monthly payment
    4. [n] - number of monthly payments
    5. [eP] - extra principle payment
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69575443-3eb95380-0f98-11ea-935d-36f1396c4967.PNG"> 
        2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69575444-3eb95380-0f98-11ea-9510-133b2f077975.PNG">
2. This loanData bundle is then passed to the __AmortizationSheduleActivity Class__ upon an onTouchEvent, by swiping left on the display. When this event is triggered, a new Intent is instantiated and the loanData bundle is passed using the __putExtra() method__, and the __AmortizationScheduleActivity is started__.
    1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69567551-956a6180-0f87-11ea-8ebf-a28543110afc.PNG">  
3. In the AmortizationScheduleActivity, I updated the __onCreate() method__ to unpack the bundle, and then send that data as parameters through the __init() method__. When the page loads, the view is updated with the parameters passed through init().
    1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69575723-d1f28900-0f98-11ea-83ba-f6d5e23e0e85.PNG">
4. In order to be able to change between the two views, without losing the user entered data, I updated the __AndroidManifest__ to change the launchMode of the MainActivity to __singleTask__; I also have __alwaysRetainTaskState set to "true"__. These modifications prevent a new instance of each view to be created every time the user swipes on the screen to change between activities; instead, only one instance is running, and the state is preserved.
    1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69567103-a2d31c00-0f86-11ea-9413-9076ca0e4f35.PNG">   

#### Apply additional principle payment
1. The __activity_main.xml__ layout has been updated to include a new field to include an __additional principle payment__
2. This new datapoint was added to the bundle and sent to the __AmortizationSheduleActivity Class__.
3. The __AmortizationSheduleActivity Class__ was modified to provide a responsive table than can process an additional extra principle payment, which will result in lower total interest, and in most cases, fewer total monthly payments to satisfy the loan repayment.
    1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69592629-88b82e80-0fc4-11ea-8b31-1b912d78a40b.PNG"> 
    2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69592630-88b82e80-0fc4-11ea-8903-67a5b46cab40.PNG">

#### Update Main Activity
1. To implement the __modified total cost & total interest of the loan__, when an optional additional principle payment is applied, the MainActivity Class has been updated to include the __extraPaymentFormula()__ method.
    1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69599183-29651900-0fda-11ea-9209-039fd64acfe5.PNG">
     2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/69599182-29651900-0fda-11ea-8366-0b50170959a6.PNG">
         
### Iteration 4
#### Display Loan Details View
1. Updated the __MainActivity Class__ to create the new class variable __Bundle chartData__, this Bundle contains the following calculated loan parameters:
    1. [tI] - __total interest__ to be paid
    2. [tC] - __total cost__ of the loan
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70147324-fa057a80-1671-11ea-9b71-f15df173bebe.PNG"> 
        2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70147323-fa057a80-1671-11ea-9023-794d5d50c75c.PNG">        
2. This chartData bundle is then passed to the __LoanDetailActivity Class__ upon an onTouchEvent, by swiping right on the display. 
    1. When this event is triggered, a new Intent is instantiated and the chartData bundle is passed using the __putExtra() method__, and the __LoanDetailsActivity is started__.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70150435-01c81d80-1678-11ea-9fad-5aadbe6eb81d.PNG">     
3. In the LoanDetailsActivity, the __onCreate() method__ unpacks the bundle, and then send that data as parameters through the __updateChart() method__. 
    1. When the page loads, the view is updated with the parameters passed through __updateChart()__.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70150434-01c81d80-1678-11ea-82e7-d3c69c305de9.PNG">
    2. The __updateChart()__ method then takes these parameters and draws the pie chart by calling __pieChart.setProgress(progress)__, which draws rings representing the corresponding loan parameters, and displays the text loan detail parameters.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70160886-3690a080-1689-11ea-85b6-4a2fab5d78e6.PNG">           
4. The __activity_loan_details.xml__ layout was defined, which contains the following elements:
    1. Default appliaction name: Loan Payment Calculator Application
    2. View title: Loan Details
    3. Dount chart graphic, which represents:
        1. Total cost of the loan - diplayed in the center of the chart.
        2. Principle - as a percentage of the total cost of the loan, also displayed as text under the chart.
        3. Interest - as a percentage of the total cost of the loan, also displayed as text under the chart.          
5. The graphics are drawn in the activity_loan_detals layout, using the following res_drawable xml files:
    1. stats_principle - chart ring represting the total amount of principle paid as a percentage of the toal cost of of the loan. 
    2. stats_interest - chart ring represting the total amount of interest paid as a percentage of the toal cost of of the loan.
    3. circle_principle - legend detail highlighting the color of the ring representing the total principle.
    3. circle_interest - legend detail highlighting the color of the ring representing the total interest.
    
#### Update Main Activity
1. In order to prevent the user from accessing either the Amortization Schedule View or Loan Details View before a loan is calculated, I updated the onTouchEvent() method in the MainActivity to include a control statement to check if the calculated monthly payment variable __(mP)__ contains an empty string. 
    1. This prevents the user from viewing an empty Amortization Schedule Table or Pie Chart. Once the value of this __mP__ has been changed to contain a calculated monthly payment, swiping either left or right on the screen will change the activity view.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70151458-a565fd80-1679-11ea-98e5-3a3224b364e3.PNG">  

### Iteration 5

#### Export the Amortization Schedule
1. __Android Manifest__ - Before data may be exported by accessing __External Storage__, the following __uses-permission__ are required:
    1. READ_EXTERNAL_STORAGE
    2. WRITE_EXTERNAL_STORAGE
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70457749-b4382000-1a7e-11ea-8fb2-7a50870e2e75.PNG">
        
2. __Permission__ - Starting with Android 6.0, in addition to the permissions on the manifest, permission must also be granted by the user. 
    1. The following code snippet from the MainActivity onCreate() method checks to see which permissions are included in the manifest and then checks to see if the user has granted access- if access has not previously been granted, the user will be prompted for permission
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451407-518d5700-1a73-11ea-80c2-1fd59170e035.PNG">  
        2. <img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70454395-5d2f4c80-1a78-11ea-970d-f8eb8ce75dc6.PNG">
        3. <img width="178" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70454394-5d2f4c80-1a78-11ea-8d2a-33d07bafbfce.PNG">
    2. After permission has been granted, __onRequestPerssionResult()__ is called and the the array value will be updated accordingly.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451414-52be8400-1a73-11ea-87da-27a3cc572e63.PNG">
        
3. When the user wants to print the Amortization Schedule to PDF, the __printPDF()__ fuction is called.
    1. A new __Canvas__ is then created, and a __Bitmap__ of the relative view layout is drawn to it. 
    2. A PDF is then created, which includes the following page info:
        1. width
        2. height
        3. page number
    3. The __Bitmap__ is then drawn to the __Canvas__.
    4. A new __File__ is created, with the path and name specified.
    5. After the file is created, __openPDF()__ is called, and the new PDF file is opened in a new __Activity__
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451419-53571a80-1a73-11ea-98b0-37565e3863e3.PNG">  
        2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451417-52be8400-1a73-11ea-8945-6303ad2fcf4c.PNG">  

#### Update 2nd Activity
1. To allow the user with the option to print the Amortization Schedule to PDF
    1. The __activity_amortization_schedule__ was modified to include a button
    2. When selected, an onClick() function that calls __printPDF__
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70456256-c8c6e900-1a7b-11ea-968f-9665403a0dc1.PNG">

#### Update UI
1. The UI was updated to provide better clarity for the user by adding a comma-separated delimiter after every three, non-decimal place digits - this provides better user readability and will help to eliminate user errors. 
    1. The __TextView__ fields were easily modified to meet this requirement by modifying the __String.format__ to include _Locale.US "%,.2f",_ where the comma was not previously included.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451408-5225ed80-1a73-11ea-9e66-8cd8c90165b0.PNG">
    2. The __EditText__ fields were more complicated, since a listener had to be used to change the values as the user types and makes changes. To incorporate these changes, I found a useful class __(NumberTextWatcherForThousand)__ online and made some adjustments to suit my purposes. The class contains methods to watch for changes, reformat the string to include the commas, and then remove the commas so that the values can be process.
        1. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70457472-3bd15f00-1a7e-11ea-89d6-f5683f83fcc8.PNG">
        2. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451409-52be8400-1a73-11ea-8c1f-3c926d37e6f5.PNG">
        3. <img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70451421-53efb100-1a73-11ea-9563-12c728cbefe1.PNG">


## Project Structure
### Iteration 5
The following nine files were created/modified during this iteration:
1. MainActivity.java
    1. Added functionality to check/request use permissions
    2. Enhananced UI by added comma delimiter to input & output text fields
2. LoanDetailsActivity.java
    1. Enhananced UI by added comma delimiter to output text fields
3. AmortizationScheduleActivity.java
    1. Added functionality to print the schedule to .pdf
    2. Enhananced UI by added comma delimiter to output text fields
4. NumberTextWatcherForThousands.java
    1. New Class with the following funtions:
        1. Watch for changes to the TextEdit fields
        2. Modify text to include commas
        3. Modify value to strip commas
5. activity_amortization_schedule.xml
    1. Added new button for the user to print the schedule
6. strings.xml
    Added resource values
7. AndroidManifest.xml
    1. Added uses-preferences
8. CS683ProjectReadMe.md
    1. Reorganized the outline of the report
    2. Removed the following sections:
        1. Proposed High Level Requirements
        2. Android Components and Features To Be Used
    3. Added the following Sections:
        1. Future Work
        2. Project Links
    4. Updated the following sections to include all Iteration details (including Iteration 5):
        1. Project Timeline
        2. Requirement Analysis and Testing
        3. Design and Implementation
        4. Project Structure
9. README.md
    1. Updated README to include the following features:
        1. Add extra payment
        2. View loan details
        3. Print PDF

#### The following screenshot displays the current project structure:
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70462287-97a0e580-1a88-11ea-8e3b-77537928a51a.PNG">  
<img width="356" alt="Screen Shot 2019-09-02 at 5 14 27 PM" src="https://user-images.githubusercontent.com/20328070/70462860-c4a1c800-1a89-11ea-8a5c-43d85c7c6ce5.PNG">


## Timeline
|Iteration | Application Requirements (E/D/O) | Android Components and Features| 
|---|---|---|
|1|[x] Calculate loan payment (E)|[x] Implement Main Activity|
|2|[x] Display Amortization Schedule (E)|[x] Implement 2nd Activity|
|3|[X] Complete data sharing between Main Activity and Amortization Schedule Activity; [X] Apply additional principle payment (D)|[X] Update Main Activity; [X] Update 2nd Activity|
|4|[X] Display loan details view (O)|[X] Implement 3rd Activity; [X] Update Main Activity|
|5|[X] Export the amortization schedule (O)|[X] Update 2nd Activity; [X] Update UI|


## Future Work
1. Get the application registered and into the Google Play store.
2. Enhancements:
    1. Add an option for the user to include a _down payment amount_, either as a dollar amount or percent of the total.
    2. Add an option to calculate _interest only_ loans.


## References
**1. Formulas for Amortizing Loan Payment:** *Loan Payment = Amount / Discount Factor or P = A / D*

    where:

    Number of Periodic Payments (n) = Payments per year times number of years
    Periodic Interest Rate (i) = Annual rate divided by number of payment periods
    Discount Factor (D) = {[(1 + i) ^n] - 1} / [i(1 + i)^n]
    Loan amount (A)
    
    https://www.thebalance.com/loan-payment-calculations-315564
