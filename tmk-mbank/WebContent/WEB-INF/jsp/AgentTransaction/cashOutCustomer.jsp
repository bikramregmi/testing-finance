<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

    <form action="cashOutCustomer" method="POST">
        <label for="walletId">Customer ID:</label>
        <input type="text" name="walletId"/><br><br>
        <label for="amount">Amount :</label>
        <input type="text" name="amount"/><br><br>
        <input type="Submit" value="Cash Out">
    </form>


</body>
</html>