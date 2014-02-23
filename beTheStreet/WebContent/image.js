$(function() {
	addItems(1,30);
});

$("#allCheck").click(function(){
	if($("#allCheck").attr("checked")=='checked'){
		$("[type='checkbox']").each(function(){
			$(this).attr("checked",'true');
		});
	}else{
		$("[type='checkbox']").each(function(){
			$(this).removeAttr("checked");
		});
	}
});

$("#bulk_verify").click(function() {
	var imageids="";  
	$("[type='checkbox']").each(function(){
		if($(this).attr("checked")=="checked"){
			imageids+=$(this).val()+",";  
			$("#statu"+$(this).val()).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
		}
	}); 
	console.log("verify:"+imageids);
	$.get("api/image/check/" + imageids+'?statu=PASS', function(data) {
	}).done(function(data) {
			addItems(pageNo,pageSize);
	});
});
$("#bulk_deny").click(function() {
	var imageids="";  
	$("[type='checkbox']").each(function(){
		if($(this).attr("checked")=="checked"){
			imageids+=$(this).val()+",";  
			$("#statu"+$(this).val()).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
		}
	}); 
	console.log("deny:"+imageids);
	$.get("api/image/check/" + imageids+'?statu=FAIL', function(data) {
	}).done(function(data) {
			addItems(pageNo,pageSize);
	});
});
$("#bulk_delete").click(function() {
	var userids="";  
	$("[type='checkbox']").each(function(){
		if($(this).attr("checked")=="checked"){
			userids+=$(this).val()+",";  
		}
	});  
	$.get("api/image/delete/" + userids, function(data) {
	}).done(function(data) {
			addItems(pageNo,pageSize);
	});
});

var pageNo,pageSize;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var url = "api/image/list?page="+page+"&size="+size;
	var userid = $.getUrlParam('userid');
	if(userid==null){
	}else{
		url = "api/image/list/"+userid+"?page="+page+"&size="+size;
		console.log(userid);
	}
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var statu = "";
			if(value.statu=='PASS'){
				statu = '<span id="statu'+value.id+'"><span class="label label-success">Pass</span><span>';
			}else if(value.statu=='FAIL'){
				statu = '<span id="statu'+value.id+'"><span class="label label-important">Deny</span><span>';
			}else if(value.statu=='UNREAD'){
				statu = '<span id="statu'+value.id+'"><span class="label label-warning">Unread</span><span>';
			}
			var content = '<tr>'+
				'<td><input type="checkbox" id="inlineCheckbox1" value="'+value.id+'"></td>'+
				'<td>'+validate(value.level)+'</td>'+
				'<td class="center"><span class="thumbnail" style="width: 100px;margin-bottom:0px !important"><a title="'+value.desc+'" href='+value.path+'><img src="'+value.thumbnail_path+'"/></a><span></td>'+
				'<td class="center">'+formatDate(new Date(value.create_time))+'</td>'+
				'<td class="center">'+validate(value.gps)+'</td>'+
				'<td class="center">'+validate(value.address)+'</td>'+
				'<td class="center">'+validate(value.user.nickname)+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-success" onclick="check('+value.id+',\'PASS\')" href="#"><i class="icon icon-black icon-check"></i>Pass</a>&nbsp;'+
				'<a class="btn btn-danger" onclick="check('+value.id+',\'FAIL\')" href="#"><i class="icon icon-black icon-close"></i>Deny</a>&nbsp;'+
				'<a class="btn btn-info" onclick="deleteImg('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>'+
			'</td>'+
				'<td class="center">'+validate(statu)+'</td>'+
				'<td class="center">'+formatDate(new Date(value.update_time))+'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(page,size,data.count);
	});

}
function validate(value){
	if(value==null||value==undefined){
		return '';
	}else{
		return value;
	}
}
function validateHeadPhoto(value){
	if(value==null||value==undefined){
		return 'comicDir/user_portrait.jpg';
	}else{
		return value;
	}
}
function pagination(page,size,count){
	if(page==0){
		$(".pagination_ul").html("");
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
			innerHtml_pre = '<li><a onclick="addItems(1,'+size+')" href="#" title="1">|<</a></li>'+
							'<li><a onclick="addItems('+(page-2)+','+size+')" href="#">'+(page-2)+'</a></li>'+
							'<li><a onclick="addItems('+(page-1)+','+size+')" href="#">'+(page-1)+'</a></li>';
		}else{
			if(page==1){
				innerHtml_pre = '';
			}
			if(page==2){
				innerHtml_pre = '<li><a onclick="addItems(1,'+size+')" href="#">1</a></li>';
				
			}
		}
		var innerHtml_active = '<li class="active"><a onclick="addItems('+page+','+size+')" href="#">'+page+'</a></li>';
		
		var innerHtml_suffix = '';
		if(max-page>=3){
			innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>'+
								'<li><a onclick="addItems('+(page+2)+','+size+')" href="#">'+(page+2)+'</a></li>'+
								'<li><a title="'+max+'" href="#" onclick="addItems('+max+','+size+')">>|</a></li>';
		}else{
			if(max-page==0){
				innerHtml_suffix = '';
			}
			if(max-page==1){
				innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>';
			}
			if(max-page==2){
				innerHtml_suffix = 	'<li><a onclick="addItems('+(page+1)+','+size+')" href="#">'+(page+1)+'</a></li>'+
									'<li><a onclick="addItems('+(page+2)+','+size+')" href="#">'+(page+2)+'</a></li>';
			}
		}
		$(".pagination_ul").html(innerHtml_pre+innerHtml_active+innerHtml_suffix);
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

function deleteImg(imgId){
	$.get("api/image/delete/" + imgId, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems(pageNo,pageSize);
		}
	});
}
function check(imgId,statu){
	$("#statu"+imgId).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
	$.get("api/image/check/" + imgId+'?statu='+statu, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			addItems(pageNo,pageSize);
		}
	});
}