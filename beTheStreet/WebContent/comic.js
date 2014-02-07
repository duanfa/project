$(function() {
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 500,
		width : 330,
		modal : true,
		buttons : {
			"submit" : function() {
				$("#dialog-form").dialog("close");
				$("#coreIframe").contents().find("#addComicForm").submit();
				$('#coreIframe').load(function(){  
					try{
						var uploadResult = JSON.parse($('#coreIframe').contents().find('body').html());
						if('success'==uploadResult.result.toLowerCase()){
							addItems();
						}
					}catch(e){
						console.log(e);
					}
				   });
			},
		},
		close : function() {
		}
	});
	$("#create-comic").click(function() {
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="comic_upload.html" frameborder="0" style="height: 350px;"></iframe>');
		$("#dialog-form").dialog("open");
	});
	addItems();
});

function addItems(){
	$.get("api/comic/list/all", function( data ) {}).done(function(data) {
		jsonData = data;
		var result = "";
		$(data).each(function(index, value) {
			var levelhead = '<div class="row-fluid sortable">'+
			'<div class="box span12">'+
				'<div class="box-header well" data-original-title>'+
					'<h2><i class="icon-picture"></i> level '+value[0].level+'</h2>'+
					'<div class="box-icon">'+
						'<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>'+
						'<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>'+
					'</div>'+
				'</div>'+
				'<div class="box-content">'+
					'<br/>'+
					'<ul class="thumbnails gallery">';
			var type = value[0].type;
			var divColor = "";
			if(type=="BONUS"){
				divColor = '<div class="alert alert-success" style="float:left;"><div>BONUS</div>';
			}else if(type=="STREET"){
				divColor = '<div class="alert alert-error" style="float:left;"><div>STREET</div>';
			}else if(type=="CHALLENGE"){
				divColor = '<div class="alert alert-block" style="float:left;padding:8px;"><div>CHALLENGE</div>';
			}
			levelhead = levelhead+divColor;
			result = result+levelhead;
			$(value).each(function(i, v) {
				var comic = '<li style="width: 100px;float:inherit;margin:20px;" id="'+v.id+'" class="thumbnail"><input type="hidden" value="'+v.level+'" name="'+v.orderNum+'" alt="'+v.info+'"/>'+
									'<div>Order:'+v.orderNum+'</div><a style="background:url('+v.thumbnail_path+')" title="'+v.name+'" href="'+v.path+'">'+
									'</a>'+
							'</li>';
				if(v.type!=type){
					type=v.type;
					if(v.type=="BONUS"){
						divColor = '</div><div class="alert alert-success" style="float:left;"><div>BONUS</div>';
					}else if(v.type=="STREET"){
						divColor = '</div><div class="alert alert-error" style="float:left;"><div>STREET</div>';
					}else if(v.type=="CHALLENGE"){
						divColor = '</div><div class="alert alert-block" style="float:left;padding:8px;"><div>CHALLENGE</div>';
					}
					comic = divColor+comic;
				}
				result = result+comic;				
			});
				var leveltail = '</div></ul>'+
				'</div>'+
			'</div>'+
		'</div>';
			result = result+leveltail;	
		});
		$("#level-comic-content").html(result);
		comicAfter();
	});
}
function deleteImg(msgId){
	$.get("api/helloMessage/del/"+msgId, function( data ) {}).done(function(data) {
		addItems();
	});
}

function editComic(imgDom){
	$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="comic_upload.html" frameborder="0" style="height: 350px;"></iframe>');
	$("#dialog-form").dialog("open");
	$('#coreIframe').load(function(){  
		try{
			$("#coreIframe").contents().find("#id").val(imgDom.attr("id"));
			$("#coreIframe").contents().find("#name").val(imgDom.find("a").attr("title"));
			$("#coreIframe").contents().find("#level").val(imgDom.find("input").val());
			$("#coreIframe").contents().find("#order").val(imgDom.find("input").attr("name"));
			$("#coreIframe").contents().find("#type").val(imgDom.parents('.alert').find("div").html());
			$("#coreIframe").contents().find("#info").val(imgDom.find("input").attr("alt"));
		}catch(e){
			console.log(e);
		}
	   });
}
