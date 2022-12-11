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

### Account related routes
##### GET  (Admin only)
**/accounts** - Retrieves a list with all bank accounts registered.  
**/accounts/all @RequestParam "id": Long** - Retrieves a bank account belonging to the matching user Id.  
**/accounts/{id} @PathVariable "id": Long** - Retrieves the bank account matching given Id.   
##### PATCH  (Admin only)
**/accounts/{id} @PathVariable "id": Long @RequestParam "newBalance": Double** - Replaces the current balance of the matching Id bank account with the new balance specified.   
**/accounts/set-secondary-owner @RequestBody "holderId": Double, "accountId": Double** - Sets the matching id holder as a secondary owner of the matching id account.  
##### POST  (Admin only)
