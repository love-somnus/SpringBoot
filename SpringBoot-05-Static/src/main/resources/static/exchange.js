// 设置 STOMP 客户端
var stompClient = null;
// 设置 WebSocket 进入端点
var SOCKET_ENDPOINT = "http://192.168.97.101:10105/ws";

var possible = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9'];
var sessionId = _.sample(possible, 8).join('');

/* 进行连接 */
function connect() {

    var callback = {
        sessionId : function sessionid(){
            return sessionId;
        }
    }
    // 设置 SOCKET
    var socket = new SockJS(SOCKET_ENDPOINT, null , callback);
    console.log(socket);
    // 配置 STOMP 客户端
    stompClient = Stomp.over(socket);
    /**heart-beating是利用window.setInterval()去规律地发送heart-beats或者检查服务端的heart-beats。*/
    /** client will send heartbeats*/
    stompClient.heartbeat.outgoing = 10000;
    /** client does not want to receive heartbeats*/
    stompClient.heartbeat.incoming = 10000;
    // STOMP 客户端连接
    var headers = {appId:"60", userId:$("#userId").val(), ticket:"U2559292a4cf4bc299b1aa3f440d4ac0"}
    stompClient.connect(headers, function (frame) {
        // 设置订阅地址(订阅发送给自己的消息)
        var subscribe = "/exchange/stomp.exchange/p2p.online." + sessionId;
        console.log('Connected:' + frame);
        $('button[value=connect]').prop('disabled', true).addClass("disabled");
        $('button[value=disconnect]').prop('disabled', false).removeClass("disabled");
        /**
         * 1.{ack: 'client'} ck具有累积效应，譬如接收了10条消息，如果你ack了第8条消息，那么1-7条消息都会被ack，只有9-10两条消息还保持未ack状态
         * 2.{ack: 'client-individual'} ack为独立确认模式，只确认当前调用ack的消息不会影响其他消息，在订阅回调接口中大量接收单条消息使用
         * 3.auto(默认) 自动确认
         */
        var sub_headers = {ack:'client-individual', appId:"60", userId:$("#userId").val()}
        // 执行订阅消息
        stompClient.subscribe(subscribe, function (message) {
            var body = JSON.parse(message.body)
            console.log(body);
            message.ack({"id" : body.id});
            $("#information").append("<tr><td>收到的信息：</td><td>" + message.body + "</td></tr>");
        }, sub_headers);
        $('button[value=subscribe]').prop('disabled', true).addClass("disabled");
        alert("连接成功，设置订阅地址为：" + subscribe);
    });
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
    stompClient.send("/mq/p2p", {}, message);
    $("#information").append("<tr><td>发出去的信息：</td><td>" + message + "</td></tr>");
}

//强制关闭浏览器  调用websocket.close（）,进行正常关闭
window.onunload = function() {
    disconnect()
}
