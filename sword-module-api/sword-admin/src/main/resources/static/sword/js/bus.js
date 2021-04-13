
function uploadImg(obj){
    var formData = new FormData();
    var files = obj.files[0];
    formData.append("code", $("#rid").val());
    formData.append("file", files);
    formData.append("type", "img");
    $.ajax({
        url: ctx + 'common/uploadCloud',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false, 
        dataType: 'json',
        success:function (res) {
        	$($(obj).data("id")).val(res.url);
        	$($(obj).data("id") + "-preview").attr('src', res.url);
        },error:function (res) {
        	console.log(res)
        }
    });
}


function verifyCode(callback) {
	$.post(ctx + 'code/delivery', {}, function(res){
    			if (res.code == 0) {
    				layer.prompt({
    		    		title: '请输入验证码',
    		    		content: '<input type="number" length="6" class="layui-layer-input">'
    		    	},function(val, index){
    		    		if (val.length != 6) {
    		    			return;
    		    		}
    		    		$.post(ctx + 'code/verify?code=' + val, {}, function(res){
    		 			   if (res.code == 0) {
    		 			   	  layer.close(index);
    		 				  if (callback) {
    		 				  	callback();
    		 				  }
    		 				  
    		 			   } else {
    		 				  $.modal.msgError('验证码错误');    	
    		 			   }
    		 		   })
    		    	});
    			} else {
					$.modal.msgError('系统繁忙，请稍后再试');    				
    			}
    		});
}