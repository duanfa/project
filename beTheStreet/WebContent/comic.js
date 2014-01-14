$(function() {
	$("#dialog-form").dialog({
		autoOpen : false,
		height : 420,
		width : 330,
		modal : true,
		buttons : {
			"add" : function() {
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
		$("#dialog-form").html('<iframe id="coreIframe" name="coreIframe" scrolling="no" src="comic_upload.html" frameborder="0" style="height: 310px;"></iframe>');
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
						'<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>'+
						'<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>'+
						'<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>'+
					'</div>'+
				'</div>'+
				'<div class="box-content">'+
					'<br/>'+
					'<ul class="thumbnails gallery">';
			result = result+levelhead;
			$(value).each(function(i, v) {
				 
				var comic = '<li id="'+v.id+'" class="thumbnail">'+
									'<a style="background:url('+v.thumbnail_path+')" title="'+v.name+'" href="'+v.path+'"><img src="'+v.thumbnail_path+'" alt="'+v.info+'"></a>'+
								'</li>';
				result = result+comic;				
			});
			var leveltail = '</ul>'+
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
