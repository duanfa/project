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
					/*startTime : $("#from").val(),
					endTime : $("#to").val(),*/
					title : $("#title").val()
				}, function() {
					// alert("success");
				}).done(function() {
					addItems();
					 $("#message").val("");
					 $("#title").val("");
					/* $("#from").val("");
					 $("#to").val("");*/
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
});

function addItems(){
	$.get("api/helloMessage/list", function( data ) {}).done(function(data) {
		jsonData = data;
		var result = "";
		$(data).each(function(index, value) {
			var content = '<tr>'+
				'<td>'+this.title+'</td>'+
				'<td class="center">'+this.info+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info edite-user" onclick="updateMsg('+index+')" href="#"> <i class="icon-edit icon-white"></i> Edite </a>&nbsp;'+
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
				title : $("#title").val()
			}, function() {
			}).done(function() {
				 addItems();
				 $("#message").val("");
				 $("#title").val("");
			}).fail(function() {
				alert("server error");
			}).always(function() {
				$("#dialog-form").dialog("close");
			});
		}
	}] );
	$("#message").val(jsonData[index].info);
	$("#title").val(jsonData[index].title);
	$("#dialog-form").dialog("open");
}
