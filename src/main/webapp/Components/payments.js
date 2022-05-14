$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});



// SAVE ============================================
$(document).on("click", "#btnSave", function() {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation------------------- 
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	$("#formPayment").submit();
});



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function() {
	$("#hidpaymentIdSave").val($(this).closest("tr").find('#hidpaymentIdUpdate').val());
	/*$("#paymentId").val($(this).closest("tr").find('td:eq(0)').text());*/
	$("#amount").val($(this).closest("tr").find('td:eq(1)').text());
	$("#cardNo").val($(this).closest("tr").find('td:eq(2)').text());
	$("#cardHolder").val($(this).closest("tr").find('td:eq(3)').text());
	$("#cvv").val($(this).closest("tr").find('td:eq(4)').text());
	$("#expDate").val($(this).closest("tr").find('td:eq(5)').text());
});


// Delete============================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "paymentId=" + $(this).data("paymentId"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});



// CLIENT-MODEL==============================================================
function validatePaymentForm() {
	// amount
	if ($("#amount").val().trim() == "") {
		return "Insert amount.";
	}
	//card no
	if ($("#cardNo").val().trim() == "") {
		return "Insert cardNo.";
	}
	
	if ($("#cardHolder").val().trim() == "") {
		return "Insert cardHolder.";
	}
	
	if ($("#cvv").val().trim() == "") {
		return "Insert cvv.";
	}
	
	if ($("#expDate").val().trim() == "") {
		return "Insert expDate.";
	}
	
	return true;
}

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidpaymentIdSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "PaymentsAPI",
			type: type,
			data: $("#formPayment").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onPaymentSaveComplete(response.responseText, status);
			}
		});
});


function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidpaymentIdSave").val("");
	$("#formPayment")[0].reset();
}



function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}