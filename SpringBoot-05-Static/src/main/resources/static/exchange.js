// 设置 STOMP 客户端
var stompClient = null;
// 设置 WebSocket 进入端点
/*var SOCKET_ENDPOINT = "http://192.168.97.101:8000/flower/ws";*/
/*var SOCKET_ENDPOINT = "https://api2.test.nesh/flower/ws";*/
/*var SOCKET_ENDPOINT = "https://apitest2.baochuangames.com/flower/ws";*/
var SOCKET_ENDPOINT = "https://wtgame-api-live.baochuangame.com:10443/flower/ws";

/* 进行连接 */
function connect() {
    // 设置 SOCKET
    var socket = new SockJS(SOCKET_ENDPOINT);
    console.log("socket", socket);
    // 配置 STOMP 客户端
    stompClient = Stomp.over(socket);
    /** websocket客户端和rabbitmq stomp中继服务会定期发送和响应ping,pong指令保活（红色向下箭头代表服务器响应的消息，绿色向上箭头代表客户端发送的消息）*/
    /** heart-beating也就是频率，incoming是接收频率，outgoing是发送频率。通过改变incoming和outgoing是以更改客户端的heart-beating(默认为10000ms)：*/
    /** heart-beating是利用window.setInterval()去规律地发送heart-beats或者检查服务端的heart-beats。*/
    /** 客户端每10000ms发送一次心跳检测(绿色↑)*/
    stompClient.heartbeat.outgoing = 30000;
    /** 客户端每10000ms接收服务端的一次心跳检测(红色↓)*/
    stompClient.heartbeat.incoming = 0;
    // STOMP 客户端连接
    var headers = {Authorization: 'Bearer ' + $("#Authorization").val()}
    stompClient.connect(headers, function (frame) {
        console.log("stompClient", stompClient);
        nextDate = new Date();
        countConnection = 0;
        console.log(JSON.stringify(frame));
        $('button[value=connect]').prop('disabled', true).addClass("disabled");
        $('button[value=disconnect]').prop('disabled', false).removeClass("disabled");
        /**
         * 1.{ack: 'client'} ck具有累积效应，譬如接收了10条消息，如果你ack了第8条消息，那么1-7条消息都会被ack，只有9-10两条消息还保持未ack状态
         * 2.{ack: 'client-individual'} ack为独立确认模式，只确认当前调用ack的消息不会影响其他消息，在订阅回调接口中大量接收单条消息使用
         * 3.auto(默认) 自动确认
         */
        var sub_headers = {ack:'client-individual',  Authorization: 'Bearer ' + $("#Authorization").val(), id: 'notify'}
        // 设置订阅地址(订阅发送给自己的消息)
        var subscribe = "/exchange/topic.online.exchange/ws.notify." + frame.headers['user-name'];
        // 执行订阅消息
        stompClient.subscribe(subscribe, function (message) {
            console.log(message.body);
            var body = JSON.parse(message.body)
            console.log(body);
            message.ack();
            $("#information").append("<tr><td>收到的信息：</td><td>" + message.body + "</td></tr>");
        }, sub_headers);
        /**
         * 1.{ack: 'client'} ck具有累积效应，譬如接收了10条消息，如果你ack了第8条消息，那么1-7条消息都会被ack，只有9-10两条消息还保持未ack状态
         * 2.{ack: 'client-individual'} ack为独立确认模式，只确认当前调用ack的消息不会影响其他消息，在订阅回调接口中大量接收单条消息使用
         * 3.auto(默认) 自动确认
         */
        var sub_headers2 = {ack:'client-individual',  Authorization: 'Bearer ' + $("#Authorization").val(), id: 'banner'}
        var subscribe2 = "/exchange/fanout.banner.exchange/ws.banner";
        // 执行订阅消息
        stompClient.subscribe(subscribe2, function (message) {
            console.log(message);
            console.log(message.body);
            var body = JSON.parse(message.body)
            console.log(body);
            message.ack({"id" : body.season});
            $("#information").append("<tr><td>收到的信息：</td><td>" + message.body + "</td></tr>");
        }, sub_headers2);
        /**
         * 1.{ack: 'client'} ck具有累积效应，譬如接收了10条消息，如果你ack了第8条消息，那么1-7条消息都会被ack，只有9-10两条消息还保持未ack状态
         * 2.{ack: 'client-individual'} ack为独立确认模式，只确认当前调用ack的消息不会影响其他消息，在订阅回调接口中大量接收单条消息使用
         * 3.auto(默认) 自动确认
         */
        var sub_headers3 = {ack:'client-individual',  Authorization: 'Bearer ' + $("#Authorization").val(), id: 'play.song'}
        var subscribe3 = "/exchange/topic.online.exchange/ws.play.song." + frame.headers['user-name'];
        // 执行订阅消息
        stompClient.subscribe(subscribe3, function (message) {
            console.log(message);
            console.log(message.body);
            var body = JSON.parse(message.body)
            console.log(body);
            message.ack({"id" : body.season});
            $("#information").append("<tr><td>收到的信息：</td><td>" + message.body + "</td></tr>");
        }, sub_headers3);
        /*alert("连接成功，设置订阅地址为：" + subscribe);*/
    }, errorCallback);

}
var nowDate, nextDate, countConnection, timer;
//断开连接时，重新连接
function errorCallback(frame){
    console.error('errorCallback.....', frame)
    nowDate = new Date();
    countConnection += 1;
    var diffSecond = parseInt(parseInt(nowDate - nextDate) / 1000)
    console.log("countConnection>>", stompClient.connected, "countConnection>>", countConnection, "|diffSecond>>", diffSecond);
    /* 每隔1s进行重连，重连超过10次并且间隔时间超过10s，则放弃重连，直接断开 */
    if(stompClient.connected && countConnection > 10 && diffSecond < 10){
        console.log("对话连接已经关闭");
        stompClient.disconnect(function() {
            $('button[value=connect]').prop('disabled', false).removeClass("disabled");
            $('button[value=disconnect]').prop('disabled', true).addClass("disabled");
        });
    }else{
        timer && clearTimeout(timer);
        timer = setTimeout(function(){
            stompClient.disconnect();
            connect();
        }, 1000);
        nextDate = new Date();
    }
}

/* 断开连接 */
function disconnect() {
    stompClient.disconnect(function() {
        alert("断开连接");
        $('button[value=connect]').prop('disabled', false).removeClass("disabled");
        $('button[value=disconnect]').prop('disabled', true).addClass("disabled");
    });
}

/* 发送消息并指定目标地址 */
function sendMessageNoParameter() {
    var sender = $("#userId").val();
    var receiver = $("#receiver").val();
    // 设置发送的内容
    var sendContent = $("#content").val();
    // 设置待发送的消息内容
    var message = '{"sender":"' + sender + '", "receiver": "' + receiver + '", "content": "' + sendContent + '", "appId": "' + 60 + '"}';
    // 发送消息
    stompClient.send("/ws/p2p", {}, message);
    $("#information").append("<tr><td>发出去的信息：</td><td>" + message + "</td></tr>");
}

//强制关闭浏览器  调用websocket.close（）,进行正常关闭
window.onunload = function() {
    disconnect()
}
