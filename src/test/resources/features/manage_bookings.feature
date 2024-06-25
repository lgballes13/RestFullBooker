#Author: Luis Ballesteros

  Feature: Automate the different methods for creation, get and update of API RESTFUL BOOKER


    Background:  access to baseURL
      Given the user wants to use the base URL



  Scenario Outline: Create Booking success
    When create booking with information of client
    |firstname|lastname|totalprice|depositpaid|checkin|checkout|additionalneeds|
    |<firstname>     |<lastname>    |<totalprice>      |<depositpaid>       |<checkin>|<checkout>|<additionalneeds>   |
    Then validate status code 200
    And  validate response with information the user

    Examples:
      |firstname|lastname|totalprice|depositpaid|checkin|checkout|additionalneeds|
      |luis     |ball    |10        |true       |2018-01-01|2019-01-01|nothinh   |
      |andres   |prueba  |555       |true       |2018-01-01|2019-01-01|breakfast |



  Scenario: Get Booking of client
    When create booking with information of client
      |firstname|lastname|totalprice|depositpaid|checkin|checkout|additionalneeds|
      |andres   |prueba  |555       |true       |2018-01-01|2019-01-01|breakfast |
    And consult the information of booking
    Then validate status code 200
    And  validate response with information saved of user


   Scenario: Update Booking Existing
     When create booking with information of client
       |firstname|lastname|totalprice|depositpaid|checkin|checkout|additionalneeds|
       |miguel     |manga    |15898       |false       |2018-01-01|2019-01-01|nothinh   |
     And create token for update
    When update booking
     |firstname|lastname|totalprice|depositpaid|checkin|checkout|additionalneeds|
      |miguel2     |manga    |15898       |false       |2018-01-01|2019-01-01|nothinh   |
    Then validate response with information saved of user


