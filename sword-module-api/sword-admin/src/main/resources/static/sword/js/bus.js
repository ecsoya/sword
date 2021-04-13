
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

	var _EXPIRE_TIME = 5*60*1000;//5分钟没有操作，则关闭
    var _interval_handler=-1;
    
    function enablePermissionFlag(title, key, callback) {
    	if (getPermissionFlag(key) === '') {
    		return;
    	}
    	if (notifyEnabled){
	    	var label = title + '此功能需输入验证码验证后使用！(<small>验证码会在点击确认后发出，5分钟之内有效</small>)'
	    	$.modal.confirm(label, function(){
	    		verifyCode(function(){
	    			doEnablePermissionFlag(key, callback);
	    		});
	    	})
    	} else {
			doEnablePermissionFlag(key, callback);
		}
    }
function doEnablePermissionFlag(key, callback){
	if (window.sessionStorage) {
	    				$('#' + key).hide();
						window.sessionStorage.setItem(key, true);
						window.sessionStorage.setItem(key + "_expireTime", new Date().getTime());
						_interval_handler=setInterval(function(){
							checkExpired(key, callback);
						}, 10*1000);
					  	if (callback) {
							callback();
						}
					}
}
function getPermissionFlag(key, value) {
	if (window.sessionStorage) {
		var storage = window.sessionStorage.getItem(key);
	    var storeLastTime= window.sessionStorage.getItem(key+"_expireTime")?window.sessionStorage.getItem(key + "_expireTime"):-1;
	    if (storeLastTime == -1 || (new Date()).getTime()-storeLastTime>_EXPIRE_TIME){
   		    return 'hidden'; //过期
		}
		if ('true' == storage) {
			return value;
		}
	}
   	return 'hidden';
}

function checkExpired(key, callback) {
    	if (!key || !window.sessionStorage) {
    		return;
   		}
   	    var storeLastTime= window.sessionStorage.getItem(key + "_expireTime")?window.sessionStorage.getItem(key + "_expireTime"):-1;
    	if (storeLastTime==-1) {
    		clearInterval(_interval_handler);
    	} else if ((new Date()).getTime()-storeLastTime>_EXPIRE_TIME) {  //过期了
    		$('#' + key).show();
    	    window.sessionStorage.setItem(key, false);
    	    window.sessionStorage.removeItem(key + "_expireTime");
    	    clearInterval(_interval_handler);
    	    if (callback) {
				callback();
			}
    	}
	}