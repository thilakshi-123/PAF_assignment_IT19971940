<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" 
	rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" 
	crossorigin="anonymous">
<script src="Components/jquery-3.6.0.js" type="text/javascript"></script>
<script src="Components/payments.js" type="text/javascript"></script>
<style type="text/css">
#divEmployersGrid{   padding-top:20px; padding-left: 50px; background-color:#b2b9d1;}
.frm{ padding-top:20px; padding-right: 50px; background-color:#b7c4c4; border-right: 1px solid grey; }
</style>
<title>Payment Management</title>
</head>
<body style="background-color:#9fd1cf;">
	<div class="container" style="box-shadow: 1px 15px 30px #404037; background-color:#aebfbe;" >
	<h1 style="margin:20px 0px 20px 400px; padding-top: 10px;">Payment Management</h1>
	<hr>
	<div class="row">
			<div class="col-4 frm">				
				<form id="formPayment" name="formPayment" action="">
					Amount: <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br> 
						
					CardNo: <input id="cardNo" name="cardNo" type="text"
						class="form-control form-control-sm"> <br> 
						
					Card Holder: <input id="cardHolder" name="cardHolder" type="text"
						class="form-control form-control-sm"> <br> 
						
					cvv: <input id="cvv" name="cvv" type="text"
						class="form-control form-control-sm"> <br>
						
					Expire Date: <input id="expDate" name="expDate" type="text"
						class="form-control form-control-sm"> <br> 
						
						<input style="width: 50%;" id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
						
						<input type="hidden" id="hidpaymentIdSave" name="hidpaymentIdSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				</div>
				<div class="col-8"  id="divPaymentsGrid">
					<%
					Payment payObj = new Payment();
					out.print(payObj.readPayments());
					%>
				</div>
		</div>
		</div>
</body>
</html>