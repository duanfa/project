$(function() {
	addItems();
});

function addItems(){
	$.get("http://localhost:8080/BeTheStreet/api/user/list/page?page=1&size=10", function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var content = '<tr>'+
				'<td>'+value.name+'</td>'+
				'<td class="center">'+value.email+'</td>'+
				'<td class="center">'+value.address+'</td>'+
				'<td class="center">'+value.cellphone+'</td>'+
				'<td class="center">'+value.statu+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info edite-user" onclick="updateMsg('+index+')" href="#"> <i class="icon-edit icon-white"></i> Edite </a>&nbsp;'+
					'<a class="btn btn-danger" onclick="deleteMsg('+this.id+')" href="#"> <i class="icon-trash icon-white"></i> Delete </a>'+
				'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
	});

}
function deleteUser(userId){
	$.get("api/user/del/"+msgId, function( data ) {}).done(function(data) {
		addItems();
	});
}
