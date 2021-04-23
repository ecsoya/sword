
function uploadImg(obj, width){
    var formData = new FormData();
    var files = obj.files[0];
    formData.append("code", $("#rid").val());
    formData.append("file", files);
    formData.append("type", "img");
    if (width) {
    	formData.append("width", width);
    }
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
	$.post(ctx + 'code/delivery?subject=后台验证码', {}, function(res){
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
    
    function enablePermissionFlag(title, key, value, successCallback, expireCallback) {
    	if ('hidden' == value) {
    		$.modal.msgError('你无此权限，请联系管理员');
    		return;
    	}
    	if (getPermissionFlag(key, value) === '') {
	    	var storage = window.sessionStorage.getItem(key);
			$('#' + key).hide();
			if (_interval_handler == -1) {
				_interval_handler = setInterval(function(){
					checkExpired(key, expireCallback);
				}, 10*1000);
			}
    		return;
    	}
    	if (notifyEnabled){
	    	var label = title + '此功能需输入<strong>验证码</strong>后使用！<br><small>验证码会在点击<strong>确认</strong>后发出，5分钟之内有效！</small></br>'
	    	$.modal.confirm(label, function(){
	    		verifyCode(function(){
	    			doEnablePermissionFlag(key, successCallback, expireCallback);
	    		});
	    	})
    	} else {
			doEnablePermissionFlag(key, successCallback, expireCallback);
		}
    }
function doEnablePermissionFlag(key, successCallback, expireCallback){
	if (window.sessionStorage) {
	    				$('#' + key).hide();
						window.sessionStorage.setItem(key, true);
						window.sessionStorage.setItem(key + "_expireTime", new Date().getTime());
						_interval_handler=setInterval(function(){
							checkExpired(key, expireCallback);
						}, 10*1000);
					  	if (successCallback) {
							successCallback();
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

function checkExpired(key, expireCallback) {
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
    	    if (expireCallback) {
				expireCallback();
			}
    	}
	}