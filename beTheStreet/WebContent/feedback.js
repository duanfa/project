$(function() {
	addItems();
});

function addItems(){
	$.get("api/feedback/list?pageNo=1&pageSize=10", function( data ) {}).done(function(data) {
		if(data.errorcode=='ok'){
			var result = "";
			$(data.result).each(function(index, value) {
				var content = '<tr>'+
					'<td class="center">'+validate(value.title)+'</td>'+
					'<td class="center">'+validate(value.info)+'</td>'+
					'<td class="center">'+
						/*'<a class="btn btn-info" href="userImage.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image</a>&nbsp;'+
						'<a class="btn  btn-success edite-user" onclick="updateMsg('+index+')" href="#"> <i class="icon-edit icon-white"></i> Edite </a>&nbsp;'+*/
						'<a class="btn btn-danger" onclick="deleteFeedback('+this.id+')" href="#"> <i class="icon-trash icon-white"></i> Delete </a>'+
					'</td>'+
				'</tr>';
				result = result+content;
			});
			$("#user_tbody").html(result);
		}
	});

}
function validate(value){
	console.log(value);
	if(value==null||value==undefined){
		return '';
	}else{
		return value;
	}
}
function deleteFeedback(id){
	$.get("api/feedback/del/"+id, function( data ) {}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems();
		}
	});
}
