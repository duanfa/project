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
	$("#create-group").click(function() {
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_upload.html?orgid='+$(this).attr("rel")+'" frameborder="0" style="height: 180px;"></iframe>');
		$("#dialog-form").dialog("open");
	});
});
var pageNo,pageSize;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var url = "api/group/list?&orgid=-1&categoryid=-1&page="+page+"&size="+size;
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var groupOrgid = "-1";
			try {
				groupOrgid = value.groupOrg.id;
			} catch (e) {
			}
			var content = '<tr>'+
				'<td class="center">'+validate(value.name)+'</td>'+
				'<td class="center">'+validate(value.info)+'</td>'+
				'<td class="center">'+validateCategory(value)+'</td>'+
				'<td class="center">'+validateGroup(value)+'</td>'+
				'<td class="center">'+validate(value.menberNum)+'</td>'+
				'<td class="center">'+validatecoins(value.coins)+'</td>'+
				'<td class="center">'+formatDate(new Date(value.create_time))+'</td>'+
				'<td class="center">'+validate(value.creatername)+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-info" onclick="update(\''+value.id+'\',\''+value.name+'\',\''+value.info+'\',\''+groupOrgid+'\')" href="#"><i class="icon icon-black icon-edit"></i>Edit</a>&nbsp;'+
				'<a class="btn btn-danger" onclick="deleteGroup('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>'+
			'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(page,size,data.count);
	});

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

function validateCategory(value){
	var categoryName = "";
	try {
		categoryName = value.category.name;
	} catch (e) {
		console.log(e);
		return 'others';
	}
	return categoryName;
}
function validateGroup(value){
	var orgName = "";
	try {
		orgName = value.groupOrg.name;
	} catch (e) {
		console.log(e);
		return 'others';
	}
	return orgName;
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
		
		var innerHtml_suffix = '</ul>';
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

function deleteGroup(id){
	$.get("api/group/delete/" + id, function(data) {
	}).done(function(data) {
		addItems(pageNo,pageSize);
	});
}
function update(id,name,info,groupOrgid){
	$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_upload.html?id='+id+'&name='+name+'&info='+info+'&orgid='+groupOrgid+'" frameborder="0" style="height: 180px;"></iframe>');
	$("#dialog-form").dialog("open");
}