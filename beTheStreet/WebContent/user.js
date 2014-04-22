var pageVal,sizeVal;
$(function() {
	onReady();
	addItems(1,30);
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 350,
		width : 330,
		modal : true,
		buttons : {
			"submit" : function() {
				$("#dialog-form").dialog("close");
				$("#coreIframe").contents().find("#addForm").submit();
				$('#coreIframe').load(function(){
					alert("send success");
				 });
			},
		},
		close : function() {
		}
	});
	
	$("#xls").click(function() {
		window.location.href = "api/user/xls?page="+pageVal+"&size="+sizeVal;
	});
	$("#bulk_message").click(function() {
		var userids="";  
		$("[type='checkbox']").each(function(){
			if($(this).attr("checked")=="checked"){
				userids+=$(this).val()+",";  
			}
		});  
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="user_message_upload.html?userids='+userids+'" frameborder="0" style="height: 250px;"></iframe>');
		$("#dialog-form").dialog("open");
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
});

function addItems(page,size){
	 pageVal=page;
	 sizeVal=size;
	var url ="api/user/list/page?page="+page+"&size="+size;
	var groupid = $.getUrlParam("groupid");
	if(groupid==null){
		url = url +"&groupid=-1";
	}else{
		url = url + "&groupid="+groupid;
	}
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var content = '<tr>'+
				'<td><input type="checkbox" id="inlineCheckbox1" value="'+value.id+'"></td>'+
				'<td>'+validate(value.nickname)+'</td>'+
				'<td class="center">'+formatDate(new Date(value.create_time))+'</td>'+
				'<td class="center">'+validatecoins(value.coins)+'</td>'+
				'<td class="center">'+validate(value.high_level)+'</td>'+
				'<td class="center">'+validate(value.high_level_stage+"/"+value.high_level_all)+'</td>'+
				'<td class="center">'+validate(value.logins)+'</td>'+
				'<td class="center">'+validate(value.totaltime)+'</td>'+
				'<td class="center">'+validateGrouCategory(value)+'</td>'+
				'<td class="center">'+validateGrouOrg(value)+'</td>'+
				'<td class="center">'+validateGroup(value)+'</td>'+
				'<td class="center">'+validateGroupCreater(value)+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info" href="image.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image</a>&nbsp;'+
					'<a class="btn btn-warning" href="message.html?userid='+value.id+'"><i class="icon-envelope icon-white"></i>message</a>&nbsp;'+
					'<a class="btn btn-primary" href="feedback.html?userid='+value.id+'"><i class="icon-bullhorn icon-white"></i>feedback</a>'+
				'</td>'+
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
function validatecoins(coins){
	var greenNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	var yellowNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	var redNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	if(coins==null||coins==undefined){
		 greenNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
		 yellowNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
		 redNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	}else{
		if((coins.greenNum!=null)&&(coins.greenNum!=undefined)){
			greenNum=coins.greenNum;
			if(greenNum>10&&greenNum<100){
				greenNum = "&nbsp;&nbsp;"+greenNum;
			}else if(greenNum<10){
				greenNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+greenNum;
			}
		}
		if((coins.yellowNum!=null)&&(coins.yellowNum!=undefined)){
			yellowNum=coins.yellowNum;
			if(yellowNum>10&&yellowNum<100){
				yellowNum = "&nbsp;&nbsp;"+yellowNum;
			}else if(yellowNum<10){
				yellowNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+yellowNum;
			}
		}
		 if((coins.redNum!=null)&&(coins.redNum!=undefined)){
			 redNum=coins.redNum;
			 if(redNum>10&&redNum<100){
				 redNum = "&nbsp;&nbsp;"+redNum;
				}else if(redNum<10){
					redNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+redNum;
				}
		 }
	}
	var spans = '<span style="background-color:#E2EFD9; padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	greenNum+'</span><span style="background-color:rgb(255, 243, 203); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	yellowNum+'</span><span style="background-color:rgb(255, 153, 153); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	redNum+'</span>';/*value.coins*/
	return spans;
}
function validateGrouCategory(value){
		try {
			if (value.ingroup) {
				return value.group.category.name;
			}
		} catch (e) {
			console.log(e);
		}
		return "";
}
function validateGrouOrg(value){
	try {
		if (value.ingroup) {
			return value.group.groupOrg.name;
		}
	} catch (e) {
		console.log(e);
	}
	return "";
}
function validateGroup(value){
	try {
		if (value.ingroup) {
			return value.group.name;
		}
	} catch (e) {
		console.log(e);
	}
	return "";
}
function validateGroupCreater(value){
	try {
		if (value.ingroup) {
			if(value.group.createrid==value.id){
				return "true";
			}else{
				return "false";
			}
		}
	} catch (e) {
		console.log(e);
	}
	return "false";
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
								'<li><a title="'+max+'" href="#" onclick="addItems('+max+','+size+')">>|</a></li>'+
						'</ul>';
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
			'<td><input type="checkbox" id="inlineCheckbox1" value="'+value.id+'"></td>'+
			'<td>'+validate(value.nickname)+'</td>'+
			'<td class="center">'+formatDate(new Date(value.create_time))+'</td>'+
			'<td class="center">'+validatecoins(value.coins)+'</td>'+
			'<td class="center">'+validate(value.high_level)+'</td>'+
			'<td class="center">'+validate(value.high_level_stage+"/"+value.high_level_all)+'</td>'+
			'<td class="center">'+validate("23")+'</td>'+
			'<td class="center">'+validate("46:23:12")+'</td>'+
			'<td class="center">'+validateGrouCategory(value)+'</td>'+
			'<td class="center">'+validateGrouOrg(value)+'</td>'+
			'<td class="center">'+validateGroup(value)+'</td>'+
			'<td class="center">'+validateGroupCreater(value)+'</td>'+
			'<td class="center">'+
				'<a class="btn btn-info" href="image.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image</a>&nbsp;'+
				'<a class="btn btn-warning" href="message.html?userid='+value.id+'"><i class="icon-envelope icon-white"></i>message</a>&nbsp;'+
				'<a class="btn btn-primary" href="feedback.html?userid='+value.id+'"><i class="icon-bullhorn icon-white"></i>feedback</a>'+
			'</td>'+
		'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(0,0);
		$("#close_search").click();
	});
}

