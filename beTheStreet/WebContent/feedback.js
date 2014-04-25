$(function() {
	addItems(1,30);
	
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 300,
		width : 330,
		modal : true,
		buttons : {
			"submit" : function() {
				$("#dialog-form").dialog("close");
				$("#coreIframe").contents().find("#addForm").submit();
				$('#coreIframe').load(function(){
					alert("send success!!!");
				 });
			},
		},
		close : function() {
		}
	});
	
});

function reply(userids){
	$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="user_message_upload.html?userids='+userids+'" frameborder="0" style="height: 150px;"></iframe>');
	$("#dialog-form").dialog("open");
}
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
				var read = '<span class="label label-success">read</span>&nbsp;&nbsp;';
				if(!this.read){
					read = '<span id="mark_'+this.id+'"><a class="btn btn-info"  onclick="read('+this.id+')"  href="#"><i class=" icon-eye-open icon-white"></i>mark as read</a></span>&nbsp;'
				}
				var content = '<tr>'+
					'<td class="center">'+validateUser(value)+'</td>'+
					'<td class="center">'+validate(value.info)+'</td>'+
					'<td class="center">'+formatDate(new Date(value.date))+'</td>'+
					'<td class="center">'+
						read+
						'<a class="btn  btn-success edite-user" onclick="reply('+this.id+')" href="#"> <i class="icon-envelope icon-white"></i> reply </a>&nbsp;'+
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

function read(id){
	$("#mark_"+id).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
	$.get("api/feedback/read/"+id, function( data ) {}).done(function(data) {
			addItems(pageNo,pageSize);
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
