$(function() {
	onReady();
	addItems(1,10);
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
	
	$("#bulk_message").click(function() {
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="message_upload.html" frameborder="0" style="height: 150px;"></iframe>');
		$("#dialog-form").dialog("open");
		var str="";  
		$("[type='checkbox'][checked]").each(function(){  
			str+=$(this).val()+",";  
		});  
		alert(str); 
	});
	
	/*$("#allCheck").click(function(){
	    $("[name='checkbox']").attr("checked",'true');//ȫѡ
	    });*/
	
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
				'<td class="center">'+validate(value.create_time)+'</td>'+
				'<td class="center">'+validate('<span style="background-color:#E2EFD9; padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">1</span><span style="background-color:rgb(255, 243, 203); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">2</span><span style="background-color:rgb(255, 153, 153); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">3</span>'/*value.coins*/)+'</td>'+
				'<td class="center">'+validate("1")+'</td>'+
				'<td class="center">'+validate("4/10")+'</td>'+
				'<td class="center">'+validate("23")+'</td>'+
				'<td class="center">'+validate("46:23:12")+'</td>'+
				'<td class="center">'+validate("Music"/*value.group.type*/)+'</td>'+
				'<td class="center">'+validate("UC Berkeley"/*value.group.org*/)+'</td>'+
				'<td class="center">'+validate("Swim Team"/*value.group.nam*/)+'</td>'+
				'<td class="center">'+validate("true"/*value.group.create*/)+'</td>'+
				'<td class="center">'+
					'<a class="btn btn-info" href="image.html?userid='+value.id+'"><i class="icon-picture icon-white"></i>Image</a>&nbsp;'+
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
		var max = parseInt(count/size)+1;
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
		
		var innerHtml_suffix ;
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

