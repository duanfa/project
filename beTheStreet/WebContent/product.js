$(document).ready(function(){
	var userid = $.getUrlParam('userid');
	addItems(userid);
	//$('.thumbnail a').colorbox({rel:'thumbnail a', transition:"elastic", maxWidth:"95%", maxHeight:"95%"});
});

function addItems(userid){
	$.get("api/product/list", function( data ) {}).done(function(data) {
		var result = "";
		$(data).each(function(index, value) {
			var head = '<div class="row-fluid sortable">'+
			'<div class="box span12">'+
				'<div class="box-header well" data-original-title>'+
					'<h2>'+value[0].category.name+'</h2>'+
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
								'<th>Type</th>'+
								'<th>Name</th>'+
								'<th>Price</th>'+
								'<th>Num</th>'+
								'<th>Info</th>'+
								'<th>Action</th>'+
							'</tr>'+
						'</thead>'+
						'<tbody id="user_tbody">';
						result = result + head;
							$(value).each(function(i, v) {
								var type = "";
								if(v.type=='ONSALE'){
									type = '<span id="statu'+v.id+'"><span class="label label-success">ONSALE</span><span>';
								}else if(v.type=='COMING'){
									type = '<span id="statu'+v.id+'"><span class="label label-important">COMING</span><span>';
								}else if(v.type=='SALEOUT'){
									type = '<span id="statu'+v.id+'"><span class="label label-warning">SALEOUT</span><span>';
								}
								result = result+'<tr>'+
									'<td><span class="thumbnail" style="width: 100px;margin-bottom:0px !important"><a title="'+v.desc+'" href='+v.path+'><img src="'+v.thumbnail_path+'"/></a><span></td>'+
									'<td class="center">'+type+'</td>'+
									'<td class="center">'+validate(v.name)+'</td>'+
									'<td class="center">'+validate(v.price)+'</td>'+
									'<td class="center">'+validate(v.num)+'</td>'+
									'<td class="center">'+validate(v.info)+'</td>'+
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
	$("#statu"+imgId).html('<img src="img/ajax-loaders/ajax-loader-1.gif"/>');
	$.get("api/image/check/" + imgId+'?statu='+statu, function(data) {
	}).done(function(data) {
		if(data.errorcode=='ok'){
			var userid = $.getUrlParam('userid');
			addItems(userid);
		}
	});
}