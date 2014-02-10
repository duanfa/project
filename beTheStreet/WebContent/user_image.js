$(document).ready(function(){
	var userid = $.getUrlParam('userid');
	addItems(userid);
	//$('.thumbnail a').colorbox({rel:'thumbnail a', transition:"elastic", maxWidth:"95%", maxHeight:"95%"});
});

function addItems(userid){
	$.get("api/image/list/user/"+userid, function( data ) {}).done(function(data) {
		if(data.result.length>0){
			$("#username").html(data.result[0][0].user.name+"'s images");
		}
		jsonData = data;
		var result = "";
		$(data.result).each(function(index, value) {
			var head = '<div class="row-fluid sortable">'+
			'<div class="box span12">'+
				'<div class="box-header well" data-original-title>'+
					'<h2> level '+value[0].level+'</h2>'+
					'<div class="box-icon">'+
						'<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>'+
						'<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>'+
						'<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>'+
					'</div>'+
				'</div>'+
				'<div class="box-content">'+
					'<table class="table table-bordered table-striped table-condensed">'+
						'<thead>'+
							'<tr>'+
								'<th>Preview</th>'+
								'<th>Statu</th>'+
								'<th>Type</th>'+
								'<th>IP</th>'+
								'<th>GPS</th>'+
								'<th>Upload time</th>'+
								'<th>Desc</th>'+
								'<th>Actions</th>'+
							'</tr>'+
						'</thead>'+
						'<tbody id="user_tbody">';
						result = result + head;
							$(value).each(function(i, v) {
								var statu = "";
								if(v.statu=='PASS'){
									statu = '<span class="label label-success">Pass</span>';
								}else if(v.statu=='FAIL'){
									statu = '<span class="label label-important">Deny</span>';
								}else if(v.statu=='UNREAD'){
									statu = '<span class="label label-warning">Unread</span>';
								}
								var type = "";
								if(v.type=='BONUS'){
									type = '<span class="label label-info">BONUS</span>';
								}else if(v.type=='STREET'){
									type = '<span class="label label-inverse">STREET</span>';
								}else if(v.type=='CHALLENGE'){
									type = '<span class="label label-primary">CHALLENGE</span>';
								}
								result = result+'<tr>'+
									'<td><span class="thumbnail" style="width: 100px;margin-bottom:0px !important"><a title="'+v.desc+'" href='+v.path+'><img src="'+v.thumbnail_path+'"/></a><span></td>'+
									'<td class="center">'+statu+'</td>'+
									'<td class="center">'+type+'</td>'+
									'<td class="center">'+validate(v.address)+'</td>'+
									'<td class="center">'+validate(v.gps)+'</td>'+
									'<td class="center">'+formatDate(new Date(v.create_time))+'</td>'+
									'<td class="center">'+validate(v.desc)+'</td>'+
									'<td class="center">'+
										'<a class="btn btn-success" onclick="check('+v.id+',\'PASS\')" href="#"><i class="icon icon-black icon-check"></i>Pass</a>&nbsp;'+
										'<a class="btn btn-danger" onclick="check('+v.id+',\'FAIL\')" href="#"><i class="icon icon-black icon-close"></i>Deny</a>&nbsp;'+
										'<a class="btn btn-info" onclick="deleteImg('+v.id+')" href="#"><i class="icon icon-black icon-trash"></i>Delete</a>'+
									'</td>'+
								'</tr>';
							});
							var foot = '</tbody>'+
									'</table>'+
									'</div>'+
								'</div>'+
							'</div>';
							result = result + foot;
		});
		$("#image_content").html(result);
		ready();
	});

}
function validate(value){
	if(value==null||value==undefined){
		return '';
	}else{
		return value;
	}
}
function formatDate(date) {
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
} 
function deleteImg(imgId){
	$.get("api/image/delete/" + imgId, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			var userid = $.getUrlParam('userid');
			addItems(userid);
		}
	});
}
function check(imgId,statu){
	img/ajax-loaders/ajax-loader-1.gif
	$.get("api/image/check/" + imgId+'?statu='+statu, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			var userid = $.getUrlParam('userid');
			addItems(userid);
		}
	});
}