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
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_category_upload.html" frameborder="0" style="height: 150px;"></iframe>');
		$("#dialog-form").dialog("open");
	});
	
});
var pageNo,pageSize;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var url = "api/group/category/list?page="+page+"&size="+size;
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var content = '<tr>'+
				'<td class="center">'+validate(value.name)+'</td>'+
				'<td class="center">'+validate(value.info)+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-success" href="group_org.html?categoryid='+value.id+'"><i class="icon-black icon-eye-open"></i>view orgs</a>&nbsp;'+
				'<a class="btn btn-danger" onclick="check('+value.id+',\'FAIL\')" href="#"><i class="icon icon-black icon-edit"></i>edit</a>&nbsp;'+
				'<a class="btn btn-info" onclick="deleteCategory('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>&nbsp;'+
				'<a class="btn btn-success addorg" rel="'+value.id+'" href="#"><i class="icon icon-white icon-add"> </i>add org</a>'+
			'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(page,size,data.count);
		
		$(".addorg").click(function() {
			$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_org_upload.html?categoryid='+$(this).attr("rel")+'" frameborder="0" style="height: 180px;"></iframe>');
			$("#dialog-form").dialog("open");
		});
	});

}
function validate(value){
	if(value==null||value==undefined){
		return '';
	}else{
		return value;
	}
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
function searchUser(){
	var keyWord = $("#userName").val();

	$.get("api/user/search?keyword="+keyWord, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var statu = "";
			if(value.active==true){
				statu = '<span class="label label-success">Active</span>';
			}else{
				statu = '<span class="label label-important">Inactive</span>';
			}
			var content = '<tr>'+
				'<td><img src="'+validateHeadPhoto(value.headPhoto)+'"/></img></td>'+
				'<td>'+validate(value.name)+'</td>'+
				'<td>'+validate(value.nickname)+'</td>'+
				'<td class="center">'+validate(value.email)+'</td>'+
				'<td class="center">'+validate(value.address)+'</td>'+
				'<td class="center">'+validate(value.cellphone)+'</td>'+
				'<td class="center">'+statu+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info" href="user_image.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image</a>&nbsp;'+
					/*'<a class="btn btn-info" href="_userImage.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image_old</a>&nbsp;'+*/
				'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(0,0);
		$("#close_search").click();
	});
}

function deleteCategory(id){
	$.get("api/image/delete/" + imgId, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems(pageNo,pageSize);
		}
	});
}
