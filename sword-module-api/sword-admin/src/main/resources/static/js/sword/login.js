$(function() {
	validateKickout();
    validateRule();
	$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});
});

var wait=60; 
function notifyTimer() {  
	var btn = $('#notifyCodeButton');
   if (wait == 0) {  
      btn.attr('disabled',false);       
      btn.text("发送验证码");  
      wait = 60;  
   } else {  
      btn.attr('disabled',true);         
      btn.text('重新发送(' + wait + ')'); 
      wait--;  
      setTimeout(function() {  
          notifyTimer()  
      },  1000)  
   }  
}  
var subject='后台登录验证码';
var template = encodeURI('<html><body><h3>亲爱的管理员，</h3><p>你正在尝试登录后台，你的验证码是： <strong>{}</strong>，请在5分钟之内使用！</p> <small>温馨提示：请保护好自己的账号和密码，验证码切记不要告诉他人！</small></body></html>');

function sendNotifyCode() {
	
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    
    if (username==null || username=='') {
    	$.modal.msg("请输入用户名");
    	return;
    }
    if (password==null || password=='') {
    	$.modal.msg("请输入密码");
    	return;
    }
    
    $.ajax({
        type: "post",
        url: ctx + "open/code/deliveryByUsername?subject="+subject+ "&template=" +template +"&username=" + username,
        success: function(r) {
            if (r.code == 0) {
                $.modal.msg('发送成功');
            } else {
            	$.modal.msg(r.msg);
            }
        }
    });
    notifyTimer()  
}

$.validator.setDefaults({
    submitHandler: function() {
		login();
    }
});

function login() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    var notifyCode = $.common.trim($("input[name='notifyCode']").val());
    var rememberMe = $("input[name='rememberme']").is(':checked');
    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password,
            "validateCode": validateCode,
            "notifyCode": notifyCode,
            "rememberMe": rememberMe
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'index';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

function validateKickout() {
	if (getParam("kickout") == 1) {
	    layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
	        icon: 0,
	        title: "系统提示"
	    },
	    function(index) {
	        //关闭弹窗
	        layer.close(index);
	        if (top != self) {
	            top.location = self.location;
	        } else {
	            var url  =  location.search;
	            if (url) {
	                var oldUrl  = window.location.href;
	                var newUrl  = oldUrl.substring(0,  oldUrl.indexOf('?'));
	                self.location  = newUrl;
	            }
	        }
	    });
	}
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}