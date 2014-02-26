$(function() {
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
	addItems(1,30);
});
var pageNo,pageSize;
function addItems(page,size){
	pageNo = page;
	pageSize = size;
	var categoryid = $.getUrlParam('categoryid');
	if(categoryid==null){
		categoryid=0;
	}
	var url = "api/group/org/list?page="+page+"&size="+size+"&categoryid="+categoryid;
	$.get(url, function( data ) {}).done(function(data) {
		var result = "";
		$(data.result).each(function(index, value) {
			var content = '<tr>'+
				'<td class="center">'+validate(value.name)+'</td>'+
				'<td class="center">'+validate(value.info)+'</td>'+
				'<td class="center">'+validate(value.category.name)+'</td>'+
				'<td class="center">'+
				'<a class="btn btn-success" href="group.html?orgid='+value.id+'"><i class="icon-black icon-eye-open"></i>view groups</a>&nbsp;'+
				'<a class="btn btn-info" onclick="update(\''+value.id+'\',\''+value.name+'\',\''+value.info+'\',\''+value.category.id+'\')" href="#"><i class="icon icon-black icon-edit"></i>Edit</a>&nbsp;'+
				'<a class="btn btn-danger" onclick="deleteGroupOrg('+value.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>&nbsp;'+
				'<a class="btn btn-success addgroup" rel="'+value.id+'" href="#"><i class="icon icon-white icon-add"> </i>add group</a>'+
			'</td>'+
			'</tr>';
			result = result+content;
		});
		$("#user_tbody").html(result);
		pagination(page,size,data.count);
		$(".addgroup").click(function() {
			$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_upload.html?orgid='+$(this).attr("rel")+'" frameborder="0" style="height: 180px;"></iframe>');
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

function deleteGroupOrg(id){
	$.get("api/group/org/delete/" + id, function(data) {
	}).done(function(data) {
		addItems(pageNo,pageSize);
	});
}
function update(id,name,info,categoryid){
	$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="group_org_upload.html?id='+id+'&name='+name+'&info='+info+'&categoryid='+categoryid+'" frameborder="0" style="height: 180px;"></iframe>');
	$("#dialog-form").dialog("open");
}