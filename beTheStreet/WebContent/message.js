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
							addItems(pageNo,pageSize);
				   });
			},
		},
		close : function() {
		}
	});
	
	$("#create-category").click(function() {
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="message_upload.html" frameborder="0" style="height: 150px;"></iframe>');
		$("#dialog-form").dialog("open");
	});
	
});
var pageNo,pageSize;
function addItems(page,size){
	var userid=$.getUrlParam("userid");
	pageNo = page;
	pageSize = size;
	var url = "api/message/pagelist?page="+page+"&size="+size;
	if(userid==null){
		url = url+"&userid=-1";
	}else{
		url = url+"&userid="+userid;
	}
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var content = '<tr>'+
				'<td class="center">'+validateuser(value.user)+'</td>'+
				'<td class="center">'+validate(value.title)+'</td>'+
				'<td class="center">'+validate(value.description)+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-success" href="group_org.html?categoryid='+value.id+'"><i class="icon-black icon-eye-open"></i>view orgs</a>&nbsp;'+
				'<a class="btn btn-danger" onclick="check('+value.id+',\'FAIL\')" href="#"><i class="icon icon-black icon-edit"></i>edit</a>&nbsp;'+
				'<a class="btn btn-info" onclick="deleteCategory('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>&nbsp;'+
				'<a class="btn btn-success addorg" rel="'+value.id+'" href="#"><i class="icon icon-white icon-add"> </i>add org</a>'+
			'</td>'+
			'<td class="center">'+formatDate(new Date(value.create_time))+'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(page,size,data.count);
	});

}
function validate(value){
	try {
		if (value == null || value == undefined) {
			return '';
		} else {
			return value;
		}
	} catch (e) {
		console.log(e);
	}
	return '';
}
function validateuser(value){
	try {
		if (value == null || value == undefined) {
			return 'ALL';
		} else {
			return value.nickname;
		}
	} catch (e) {
		console.log(e);
	}
	return '';
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
		
		var innerHtml_suffix ;
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

function deleteCategory(id){
	$.get("api/image/delete/" + imgId, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems(pageNo,pageSize);
		}
	});
}
