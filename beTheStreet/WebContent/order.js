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
				$("#statu"+editId).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
				$("#coreIframe").contents().find("#addForm").submit();
				$('#coreIframe').load(function(){
							addItems(pageNo,pageSize);
				   });
			},
		},
		close : function() {
		}
	});
});

var pageNo,pageSize,editId;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var url = "api/prize/order/listPage?userid=-1&page="+page+"&size="+size;
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var statu = "";
			if(value.statu=="CANCELLED"){
				statu = '<span id="statu'+value.id+'"><span class="label label-important">CANCELLED</span><span>';
			}else if(value.statu=="PURCHASING"){
				statu = '<span id="statu'+value.id+'"><span class="label label-warning">'+value.statu+'</span><span>';
			}else{
				statu = '<span id="statu'+value.id+'"><span class="label label-success">'+value.statu+'</span><span>';
			}
			var thumbnail_path="";
			var image_path="";
			if(value.prize==null||value.prize==undefined){
			}else{
				thumbnail_path = value.prize.thumbnail_path;
				image_path = value.prize.headPhoto;
			}
			var content = '<tr>'+
				'<td class="center"><span class="thumbnail" style="width: 100px;margin-bottom:0px !important"><a class="cboxElement" href="'+image_path+'"><img src="'+thumbnail_path+'"/></a><span></td>'+
				'<td class="center">'+validatecoins(value)+'</td>'+
				'<td class="center">'+validate(value.user.nickname)+'</td>'+
				'<td class="center">'+validate(value.name)+'</td>'+
				'<td class="center">'+validate(value.email)+'</td>'+
				'<td class="center">'+validate(value.phone)+'</td>'+
				'<td class="center">'+validate(value.address)+'</td>'+
				'<td class="center">'+validate(value.description)+'</td>'+
				'<td class="center" id="statu'+value.id+'">'+statu+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-danger" onclick="update('+value.id+',\''+value.statu+'\')" href="#"><i class="icon icon-black icon-edit"></i>Edit</a>&nbsp;'+
				'<a class="btn btn-info" onclick="deleteOrder('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>'+
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
function validatecoins(value){
	var greenNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	var yellowNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
	var redNum='&nbsp;&nbsp;&nbsp;&nbsp;0';
		if((value.greenNum!=null)&&(value.greenNum!=undefined)){
			greenNum=value.greenNum;
			if(greenNum>10&&greenNum<100){
				greenNum = "&nbsp;&nbsp;"+greenNum;
			}else if(greenNum<10){
				greenNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+greenNum;
			}
		}
		if((value.yellowNum!=null)&&(value.yellowNum!=undefined)){
			yellowNum=value.yellowNum;
			if(yellowNum>10&&yellowNum<100){
				yellowNum = "&nbsp;&nbsp;"+yellowNum;
			}else if(yellowNum<10){
				yellowNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+yellowNum;
			}
		}
		 if((value.redNum!=null)&&(value.redNum!=undefined)){
			 redNum=value.redNum;
			 if(redNum>10&&redNum<100){
				 redNum = "&nbsp;&nbsp;"+redNum;
				}else if(redNum<10){
					redNum = "&nbsp;&nbsp;&nbsp;&nbsp;"+redNum;
				}
		 }
	var spans = '<span style="background-color:#E2EFD9; padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	greenNum+'</span><span style="background-color:rgb(255, 243, 203); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	yellowNum+'</span><span style="background-color:rgb(255, 153, 153); padding-left: 20px; padding-top: 5px; padding-bottom: 15px;">'+
	redNum+'</span>';/*value.coins*/
	return spans;
}

function deleteOrder(id){
	$.get("api/prize/order/delete/" + id, function(data) {
	}).done(function(data) {
		addItems(pageNo,pageSize);
	});
}
function update(id,statu){
	editId = id;
	$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="order_upload.html?id='+id+'&statu='+statu+'" frameborder="0" style="height: 130px;"></iframe>');
	$("#dialog-form").dialog("open");
}
