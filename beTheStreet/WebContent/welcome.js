var jsonData ;
$(function() {
	addItems();
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			"add" : function() {
				$.post("api/helloMessage/add", {
					msg : $("#message").val(),
					title : $("#title").val(),
					startTime : $("#from").val(),
					endTime : $("#to").val()
				}, function() {
					// alert("success");
				}).done(function() {
					addItems();
					 $("#message").val("");
					 $("#title").val("");
					 $("#from").val("");
					 $("#to").val("");
				}).fail(function() {
					alert("server error");
				}).always(function() {
					$("#dialog-form").dialog("close");
				});
			},
		},
		close : function() {
		}
	});
	$("#create-user").click(function() {
		$("#dialog-form").dialog("open");
	});

	$("#from").datepicker({
		defaultDate : "+1w",
		changeMonth : true,
		changeYear : true,
		numberOfMonths : 1,
		dateFormat : "yy-mm-dd",
		onClose : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#to").datepicker({
		defaultDate : "+1w",
		changeMonth : true,
		changeYear : true,
		numberOfMonths : 1,
		dateFormat : "yy-mm-dd",
		onClose : function(selectedDate) {
			$("#from").datepicker("option", "maxDate", selectedDate);
		}
	});
});

function addItems(){
	$.get("api/helloMessage/list", function( data ) {}).done(function(data) {
		jsonData = data;
		var result = "";
		$(data).each(function(index, value) {
			var content = '<tr>'+
				'<td>'+this.title+'</td>'+
				'<td class="center">'+this.info+'</td>'+
				'<td class="center">'+formatDate(new Date(this.startTime))+'</td>'+
				'<td class="center">'+formatDate(new Date(this.endTime))+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info edite-user" onclick="updateMsg('+index+')" href="#"> <i class="icon-edit icon-white"></i> Edite </a>&nbsp;'+
					'<a class="btn btn-danger" onclick="deleteMsg('+this.id+')" href="#"> <i class="icon-trash icon-white"></i> Delete </a>'+
				'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#tbody").html(result);
	});

}
function deleteMsg(msgId){
	$.get("api/helloMessage/del/"+msgId, function( data ) {}).done(function(data) {
		addItems();
	});
}
function updateMsg(index){
	$( "#dialog-form" ).dialog( "option", "buttons", [{
		text: "Update", click: function() {
			$.post("api/helloMessage/update", {
				id : jsonData[index].id,
				msg : $("#message").val(),
				title : $("#title").val(),
				startTime : $("#from").val(),
				endTime : $("#to").val()
			}, function() {
				// alert("success");
			}).done(function() {
				 addItems();
				 $("#message").val("");
				 $("#title").val("");
				 $("#from").val("");
				 $("#to").val("");
				 $( "#dialog-form" ).dialog( "option", "buttons", [{
					 text: "Add", click: function() {
							$.post("api/helloMessage/add", {
								msg : $("#message").val(),
								title : $("#title").val(),
								startTime : $("#from").val(),
								endTime : $("#to").val()
							}, function() {
								// alert("success");
							}).done(function() {
								addItems();
								 $("#message").val("");
								 $("#title").val("");
								 $("#from").val("");
								 $("#to").val("");
							}).fail(function() {
								alert("server error");
							}).always(function() {
								$("#dialog-form").dialog("close");
							});
						}
					}] );
			}).fail(function() {
				alert("server error");
			}).always(function() {
				$("#dialog-form").dialog("close");
			});
		}
	}] );
	$("#message").val(jsonData[index].info);
	$("#title").val(jsonData[index].title);
	$("#from").val(formatDate(new Date(jsonData[index].startTime)));
	$("#to").val(formatDate(new Date(jsonData[index].endTime)));
	$("#dialog-form").dialog("open");
}
function formatDate(date) {
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
} 