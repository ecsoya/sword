
var websocket = null;
var callback = null;

function connectWebSocket(callback) {
	this.callback = callback;
	if (!uuid){
		if (callback) {
        	callback({code: 2, msg: 'Parameter Error'});
        }
		reutrn;
	}
	//判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        websocket = new WebSocket(wsurl + '/message/' + uuid);
    } else{
        if (callback) {
        	callback({code: 2, msg: 'WebSocket Not Supportted'});
        }
        return;
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
    	if (callback) {
        	callback({code: 2, msg: 'WebSocket Error'});
        }
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        if (callback) {
        	callback({code: 0, msg: 'WebSocket Opened'});
        }
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        if (callback) {
        	callback({code: 1, msg: event});
        }
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        if (callback) {
        	callback({code: 0, msg: 'WebSocket Closed'});
        }
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        if (websocket) {
			websocket.close();
		}
    }
}

function closeWebSocket() {
	if (websocket) {
		websocket.close();
	}
}

function sendMessage(message) {
	
}