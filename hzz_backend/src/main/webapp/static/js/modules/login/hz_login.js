//# sourceURL=hz_login.js
/**
 * @Title: 登录 js
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-09 11:57:11:57
 * @version: V1.0
 */

$(document).ready(function() {
    optErrMsg();
    /**
     * 刷新验证码
     */
    $('#randCodeImage').click(function(){
        reloadRandCodeImage();
    });
});
$("#errMsgContiner").hide();

//输入验证码，回车登录
$(document).bind('keyup', function(event) {
    if (event.keyCode == "13") {
        $('#but_login').click();
    }
});

//验证用户信息
function checkUser(){
    if(!validForm()){
        //这个先不用
        //return false;
    }
    newLogin();
}



/*
$(function () {
    $("#userName").empty();
    $("#password").empty();
    $("#randCode").empty();

    GetVCode();
});

function LoginInfo() {
    var userName = $("#userName").val();
    var password = $("#password").val();
    var code = $("#randCode").val();

    alert("用户名：" + userName + "\n\r密码：" + password + "\n\r验证码：" + code + "," + VCode);

    return false;
}



/!**
 * 刷新验证码
 *!/
$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});

function draw2(vcode) {
    var canvas_width = $('#canvas').width();
    var canvas_height = $('#canvas').height();
    var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
    var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    for (var i = 0; i <= vcode.length; i++) {
        //var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
        var deg = RandomNumBoth(-30, 30) * Math.PI / 180;
        var txt = vcode[i];//得到随机的一个内容
        var x = 20 + i * 40;//文字在canvas上的x坐标
        //var y = 30 + Math.random() * 8;//文字在canvas上的y坐标
        var y = 30 + RandomNumBoth(-8, 8);
        context.font = "bold 25px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
    }
    for (var i = 0; i <= 30; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}

function RandomNumBoth(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    var num = Min + Math.round(Rand * Range); //四舍五入
    return num;
}

function randomColor() {//得到随机的颜色值
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

var VCode;

function GetVCode() {
    VCode = "1234";
    draw2(VCode);
}

$("#canvas").on('click', function () {
    GetVCode();
});*/
