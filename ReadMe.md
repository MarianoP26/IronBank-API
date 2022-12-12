# IronBank-API
My Ironhack BackEnd bootcamp final project using Java and SpringBoot



## Users  
There are going to be three different kind of users  

**AccountHolder**: Has access to /profile and /send routes.  
**ThirdParty**: Has access to /tp/send-in and /tp/send-out routes.  
**Admin**: Has access to everything except /tp and /send routes.  

Pre-created account holders:
- **username: "Pepe", password: "Pepito"**, account: StudentChecking, balance: 2000.
- **username: "Roboro", password: "Bogdoso"**, account: Checking, balance: 2000.
- **username: "Mirella", password: "Traquea"**, account: CreditCard, balance: 800.
- **username: "Bogdan", password: "Megador"**, account: Savings, balance: 1200.

Pre-created Third parties:
- **username: "TPV1", password: "123456"**.
- **username: "ING", password: "abcde"**.

Pre-created admin:
- **username: "Mariano", password:"Javatizador"**.

Note: you will be able to create your own users using the API.

# Rest API

## Routes

### User related routes
##### GET  
**/agents** - Retrieves a list with all users registered in the bank (Admin only).  
**/agents/all @RequestParam "name": String** - Retrieves a list with all users matching the input name or all users if no input is given (Admin only).  
**/agents/{id} @PathVariable "id": Long** - Retrieves the user matching given Id (Admin only).  
**/profile** - Shows info about the logged in user (Users).  
##### POST (Admin only)  
**/agents/add-third-party @RequestBody "name": String, "password": String** - Creates a new ThirdParty user.  
**/agents/add-admin @RequestBody "name": String, "password": String** - Creates a new ThirdParty user.  
**/agents/add-account-holder @RequestBody {  
    @NonNull "name": String,  
    @NonNull "password": String,  
    @NonNull "dateOfBirth": "yyyy-MM-dd",  
    @NonNull "primaryAddress": String,  
             "mailingAddress": String  
    }** - Creates a new account holder user with the specified properties.  
### Account related routes
##### GET  (Admin only)
**/accounts** - Retrieves a list with all bank accounts registered.  
**/accounts/all @RequestParam "id": Long** - Retrieves a bank account belonging to the matching user Id.  
**/accounts/{id} @PathVariable "id": Long** - Retrieves the bank account matching given Id.   
##### PATCH  (Admin only)
**/accounts/{id} @PathVariable "id": Long @RequestParam "newBalance": Double** - Replaces the current balance of the matching Id bank account with the new balance specified.   
**/accounts/set-secondary-owner @RequestBody "holderId": Double, "accountId": Double** - Sets the matching id holder as a secondary owner of the matching id account.  
##### POST  (Admin only)  
**/accounts/add @RequestBody  {  
    @NonNull "type": String ("Checking", "Savings", CreditCard"),  
    @NonNull "accountHolderId": Long,  
    @NonNull "balance": Double,  
    "secretKey": String,  
    "creditLimit": Double,  
    "interestRate": Double,  
    "minimumbalance": Double  
    }** - Adds a new bank account with the specified properties.  
##### DELETE  (Admin only)  
**/accounts/{id} @PathVariable "id": Long** - Deletes matching id bank account (also deletes related holders).  
### Transaction related routes  
##### GET  (Admin only)  
**/transfer** - Retrieves a list with all transactions made in the app.  
**/transfer/{id} @PathVariable "id": Long** - Retrieves the matching id transaction.  
**/transfer/user/{id} @PathVariable "id": Long** - Retrieves all transaction from the matching account holder Id.  
**/transfer/all @RequestParam "amount": Double** - Retrieves all transactions with an amount higher than the specified.  
##### Patch  
**/send @RequestBody "id": Long, "name": String, "amount": Double** - Sends money from the logged in account holder the specified amount to the matching id account holder (AccountHolders only).  
**/tp/send-in @RequestBody "id": Long, "secretKey": String, "amount": Double** - Sends money from the logged in ThirdParty account the specified amount to the matching id account holder (ThirdParty only).  
**/tp/send-out @RequestBody "id": Long, "secretKey": String, "amount": Double** - Sends money from the matching id account holder to a third party account (ThirdParty only).  
**/emit @RequestBody "fromId": Long, "toId": Long, "secretKey": String, "amount": Double** - Forcefully sends money from matching id account holder to matching id account holder bypassing any limitations like enough balance, frozen account. It also wont apply any interest or fraud checks (Admin only).  

