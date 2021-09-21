1> Create Account
===========================================================
URL : http://localhost:8080/API/Account/Create
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	country (ISO3)
	
Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Account Created"
		details: {
			accountNo: "TM1444022193891"
			balance: 0
			country: "NPL"
			currency: "NPR"
		}
	}
-----------------------------------------------------------
2> Check Balance
-----------------------------------------------------------
URL :  http://localhost:8080/API/Account/Balance
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	accountNo
	
Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Account Balance"
		details: {
			accountNo: "TM1444022193891"
			balance: 0
			country: "NPL"
			currency: "NPR"
		}
	}
-----------------------------------------------------------
3> Create Transaction
-----------------------------------------------------------
URL :  http://localhost:8080/API/Transaction/Create
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	(1) Fund Transfer
	
	{
		"transactionId" : "123456",
		"fromAccount": "TM1444018312640",      
		"toAccount": "TM1444019583816",
		"sentAmount": 10.00,                  
		"fromCurrency": "NPR",                 
		"receivedAmount": 10.00,
		"toCurrency": "NPR",
		"exchangeRate": 1.00,                  
		"transactionStatus": "Pending",
		"transactionType": "Transfer",
		"remarks" : "Fund Transfer"
	}
	
	(2) Load Fund
	
	{
		"transactionId" : "1234535637",
		"toAccount": "TM1444019583816",
		"receivedAmount": 10.00,
		"toCurrency": "NPR",
		"transactionStatus": "Complete",
		"transactionType": "Load",
		"remarks" : "Load Fund"
	}

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Transaction Created"
		details: null
	}

-----------------------------------------------------------
4> Process Transaction 
  (Optional if Transaction are set to pending while
   creating transaction)
-----------------------------------------------------------
URL :  http://localhost:8080/API/Transaction/Process
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	transactionId

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Transaction Processed"
		details: null
	}
	
-----------------------------------------------------------
5> Cancel Transaction 
  (Optional if Transaction are set to pending while
   creating transaction)
-----------------------------------------------------------
URL :  http://localhost:8080/API/Transaction/Cancel
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	transactionId

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Transaction Cancelled"
		details: null
	}

-----------------------------------------------------------
6> Reverse Transaction 
   (Only available for fund transfer)
-----------------------------------------------------------
URL :  http://localhost:8080/API/Transaction/Reverse
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	transactionId

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Transaction Reversed"
		details: null
	}

-----------------------------------------------------------
7> Statement
-----------------------------------------------------------
URL :  http://localhost:8080/API/Statement
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	accountNo

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Account Created"
		details: {
			statement: {
				12345: "NPR.10.0 Has Been Transfered To Your Account."
				123456: "NPR.10.0 Has Been Transfered To Your Account."
				1234544: "NPR.10.0 Has Been Transfered To Your Account."
				1234556: "NPR.10.0 Amount Has Been Loaded To Your Account."
				12345356: "NPR.10.0 Amount Has Been Loaded To Your Account."
				12345678: "NPR.10.0 Has Been Transfered To Your Account."
				123453563: "NPR.10.0 Amount Has Been Loaded To Your Account."
				1234535637: "NPR.10.0 Amount Has Been Loaded To Your Account."
			}
		}
	}
	
-----------------------------------------------------------
8> Transaction Detail
-----------------------------------------------------------
URL :  http://localhost:8080/API/Transaction/Detail
Method : POST
Content-Type : Application/Json
Header:
	username , password
	
Request Payload:
	transactionId

Success Response:
	{
		status: "Success"
		code: "S00"
		message: "Transaction Detail"
		details: {
			transactionId: "1234535637"
			fromAccount: null
			toAccount: "TM1444019583816"
			sentAmount: 0
			fromCurrency: null
			receivedAmount: 10
			toCurrency: "NPR"
			exchangeRate: 0
			transactionStatus: "Complete"
			transactionType: "Load"
			remarks: null
		}
	}
	
===========================================================
Failure Response
===========================================================
	{
		status: "Internal Server Error"
		code: "F01"
		message: "Internal Server Error"
	}

===========================================================
INDEX
===========================================================
1> Transaction Status
	
	Pending
	Complete
	Cancel
	Reversed
----------------------------------------------------------	
2> Transaction Type
	
	Transfer
	Load
===========================================================
Response Code
===========================================================
S00 - Success
F00 - Failure
F01 - Internal Server Error
F02 - Invalid Session
F03 - Invalid Request
===========================================================