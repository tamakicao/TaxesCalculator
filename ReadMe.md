#Sales Taxes Calculator  
##Background  
>Sales Taxes Calculator is a program used to generate receipts which list the name of all the inputted items and their price (including tax), finishing with the total cost of the items,
and the total amounts of sales taxes paid.  
##Assumptions  
>**Tax Rate**  
>* Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt.   
>* Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.  
>***  
> **Rounding Rules**  
>* The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.  
>* Tax and Price should be rounded to 2 decimal places.  
##Requirements  
* ###Non-Functional Requirements  
>Sales Taxes Calculator is written in **Java** with an associated GUI and is intended to run on a PC running Windows or macOS.  
* ###Functional Requirements  
>* The program must provide a usage guide.  
>* The program must allow the user to enter product information including product name, quantity, unit shelf price.
>* The program must be able to determine the applicable tax rate.  
>* The program must round up the tax to the nearest 0.05.  
>* The program must round up to two decimal places for all calculations.
>* The program must allow the user to add more items into the shopping cart.
>* The program must allow the user to edit the shopping cart.
>* The program must prevent the user from completing the shopping when the cart is empty.
>* When there are empty fields in the shopping cart, the program must prevent the user from completing the shopping and show a prompt message.
>* The program must only allow the user to enter item quantities as positive integers.
>* The program must only allow the user to enter unit prices as non-negative numbers with up to two decimal places.  
>* The program must provide the user with prompt text about the proper format of inputs.
>* After the user completes the shopping, the program must display a receipt which list the name of all the inputted items and their price (including tax), finishing with the total cost of the items,
  and the total amounts of sales taxes paid.  
>* The program must allow the user to start over after showing the receipt.  
>* The program must process the product name entered by the user into a suitable format in the receipt, that is, the beginning and end of the product name cannot be blank characters, and for products with more than one quantity, an 's' must be added at the end of the name.
