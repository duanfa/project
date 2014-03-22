$(function() {
	addItems(1,30);
});
var pageNo,pageSize;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var userid=$.getUrlParam("userid");
	var url = "api/feedback/list?pageNo="+page+"&pageSize="+size;
	if(userid==null){
		url = url+"&userid=-1";
	}else{
		url = url+"&userid="+userid;
	}
	$.get(url, function( data ) {}).done(function(data) {
		if(data.errorcode=='ok'){
			var result = "";
			$(data.result).each(function(index, value) {
				var content = '<tr>'+
					'<td class="center">'+validateUser(value)+'</td>'+
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
			pagination(pageNo,pageSize,data.count);
		}
	});

}
function validateUser(value){
	if(value.user==null||value.user==undefined){
		return '';
	}else{
		return value.user.nickname;
	}
}
function validate(value){
	if(value==null||value==undefined){
		return '';
	}else{
		return value;
	}
}
function deleteFeedback(id){
	$.get("api/feedback/del/"+id, function( data ) {}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems(pageNo,pageSize);
		}
	});
}

function pagination(page,size,count){
	if(page==0){
		$(".pagination").html("");
		return;
	}
		var max ;
		if(count%size==0){
			max= parseInt(count/size);
		}else{
			max= parseInt(count/size)+1;
		}
		var innerHtml_pre;
		if(page>=3){
			innerHtml_pre = '<ul>'+
							'<li><a onclick="addItems(1,'+size+')" href="#" title="1">|<</a></li>'+
							'<li><a onclick="addItems('+(page-2)+','+size+')" href="#">'+(page-2)+'</a></li>'+
							'<li><a onclick="addItems('+(page-1)+','+size+')" href="#">'+(page-1)+'</a></li>';
		}else{
			if(page==1){
				innerHtml_pre = '<ul>';
			}
			if(page==2){
				innerHtml_pre = '<ul>'+
				'<li><a onclick="addItems(1,'+size+')" href="#">1</a></li>';
				
			}
		}
		var innerHtml_active = '<li class="active"><a onclick="addItems('+page+','+size+')" href="#">'+page+'</a></li>';
		
		var innerHtml_suffix = '</ul>';;
		if(max-page>=3){
			innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>'+
								'<li><a onclick="addItems('+(page+2)+','+size+')" href="#">'+(page+2)+'</a></li>'+
								'<li><a title="'+max+'" href="#" onclick="addItems('+max+','+size+')">>|</a></li>'+
						'</ul>';
		}else{
			if(max-page==0){
				innerHtml_suffix = '</ul>';
			}
			if(max-page==1){
				innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>'+
						'</ul>';
			}
			if(max-page==2){
				innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>'+
									'<li><a onclick="addItems('+(page+2)+','+size+')" href="#">'+(page+2)+'</a></li>'+
				'</ul>';
			}
		}
		$(".pagination").html(innerHtml_pre+innerHtml_active+innerHtml_suffix);
}
