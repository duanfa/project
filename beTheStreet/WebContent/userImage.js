var userid;
$(function() {
	userid = $.getUrlParam('userid');
	addItems();
});

function addItems(){
	$.get("api/image/list/user/"+userid, function( data ) {}).done(function(data) {
		jsonData = data;
		var result = "";
		$(data.result).each(function(index, value) {
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
				var statuIcon = "";
				try {
					if (v.statu == "PASS") {
						statuIcon = '<span class="icon32 icon-green icon-check"></span>';
					} else if (v.statu == "UNREAD") {
						statuIcon = '<span class="icon32 icon-color icon-pin" style="margin-top: -20px;"></span>';
					} else if (v.statu == "FAIL") {
						statuIcon = '<span class="icon32 icon-red icon-cross"></span>';
					}
				} catch (e) {
					alert(e);
				}
				var comic = '<li id="'+v.id+'" class="thumbnail">'+
									'<a style="background:url('+v.thumbnail_path+')" title="'+v.name+'" href="'+v.path+'">'+
									statuIcon+
									'</a>'+
								'</li>';
				result = result+comic;				
			});
				var leveltail = '</ul>'+
				'</div>'+
			'</div>'+
		'</div>';
			result = result+leveltail;	
		});
		$("#user-image-content").html(result);
		userImageAfter();
	});
}
