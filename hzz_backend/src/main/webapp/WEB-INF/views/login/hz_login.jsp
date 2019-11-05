<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.saili.hzz.core.util.SysThemesUtil,com.saili.hzz.core.enums.SysThemesEnum" %>
<%@include file="/context/mytags.jsp" %>
<%
    session.setAttribute("lang", "zh-cn");
    SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
    String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <title><t:mutiLang langKey="jeect.platform"/></title>
    <link rel="shortcut icon" href="images/sailiicon.png">
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>

    <link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="plug-in/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css"/>
    <![endif]-->
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->

    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
    <script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
    <script type="text/javascript" src="static/js/modules/login/login-ace.js"></script>
    <script type="text/javascript" src="/static/js/modules/login/hz_login.js"></script>
    <%=lhgdialogTheme %>
    <style type="text/css">
        * {
            padding: 0;
            margin: 0;
        }

        html, body {
            height: 100%;
        }

        input.ico {
            font-size: 16px;
            width: 345px;
            /*height: 30px;*/
            line-height: 30px;
            padding: 10px;
            margin: 3px;
            color: #65c7d0  !important;
            border: none;
            outline: none;
            padding-left: 45px;
        }

        input::-webkit-input-placeholder { /* WebKit, Blink, Edge */
            color: #218390;
        }

        input::-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: #218390;
        }

        input:-ms-input-placeholder { /* Internet Explorer 10-11 */
            color: #218390;
        }
        .top {
            margin-top: 200px;
        }
        .input_span {
            margin-left: 50px; height: 56px; width: 372px; border: 1px solid #65c7d0;
        }
        .user_name_icon {
            background: transparent url('/images/login/1.png') 20px center no-repeat  !important;
        }
        .password_icon {
            background: transparent url('/images/login/2.png') 20px center no-repeat  !important;
        }
        .vlidate_code_icon {
            background: transparent url('/images/login/2.png') 20px center no-repeat  !important;
        }
        .title {
            color: white; width: 435px; font-family: 'Microsoft YaHei'; font-size: 30px; font-weight: bold; letter-spacing: 0.2em;
        }
        .vlidate_code {
            border-left: 100px;
            width: 90px;
            background-color: transparent !important;
            border: 0px;
        }
        .login_btn{
            outline:none;border:0px; width:200px; height:41px; background: transparent url('/images/login/denglu.png') 20px center no-repeat  !important;
        }
    </style>
</head>
<body class="login-layout light-login" style="background: #030d1f url('/images/login/bg.jpg') top right;">
<div class="main-container">
    <div class="main-content">
        <div class="row top">
            <div class="col-md-12 col-md-offset-3">
                <div class="login-container">
                    <div class="center">
                        <div class="title">河湖长制工作管理系统</div>
                    </div>
                    <div class="space-6"></div>
                    <div class="alert alert-warning alert-dismissible col-md-offset-2" style="width: 300px;" role="alert" id="errMsgContiner">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <div id="showErrMsg"></div>
                    </div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border"
                             style="height: 378px; width: 452px; background: url('/images/login/tab1.png') center center no-repeat;">
                                <form id="loinForm" class="form-horizontal" method="post">
                                    <!-- 单点登录参数 -->
                                    <input type="hidden" id="ReturnURL" name="ReturnURL" value="${ReturnURL }"/>
                                    <div>
                                        <div class="space-22"></div>
                                        <label class="block clearfix">
                                            <span class="block input_span">
                                                <input type="text" name="userName" iscookie="true" class="ico user_name_icon"
                                                       placeholder="请输入您的用户名/手机号/邮箱" id="userName" value="admin"/>
                                                <i class="ace-icon fa fa-user"></i>
<%--											</span>--%>
                                        </label>
                                        <label class="block clearfix">
                                            <span class="block input_span">
                                                <input type="password" name="password" class="ico password_icon"
                                                       placeholder="请输入密码" id="password" value="123456"/>
                                                <i class="ace-icon fa fa-lock"></i>
											</span>
                                        </label>
                                        <label class="block clearfix">
                                            <div class="input-group">
                                                <span class="block input_span" style="width: 250px">
                                                    <input type="text" style="width: 250px; background-color: transparent;" name="randCode"
                                                        class="ico vlidate_code_icon" placeholder="请输入验证码" id="randCode"/>
                                                </span>
<%--                                                <div class="input_span" style="margin-left:10px;">--%>
                                                <span class="input-group-addon vlidate_code">
                                                    <img id="randCodeImage" src="randCodeImage"/>
                                                </span>
<%--                                                </div>--%>
                                            </div>
                                        </label>
                                        <div class="space"></div>
                                        <div class="clearfix">
                                            <label class="inline" style="margin-left: 60px;">
                                                <input type="checkbox" class="ace" id="on_off" name="remember"
                                                       value="yes"/>
                                                <span class="lbl" style="color: #65c7d0;">记住用户名</span>
                                            </label>
                                            <a href="loginController.do?goResetPwdMail" class="btn btn-link" style="color: #65c7d0;">忘记密码 ?</a>
                                        </div>
                                        <div class="space-4"></div>
                                        <div style="text-align: center;">
                                            <button type="button" id="but_login" class="login_btn" onclick="checkUser()"></button>
                                        </div>
                                    </div>
                                </form>
                        </div>
                        <div class="center">
                            <h4 class="blue" id="id-company-text">&copy; 江苏赛立版权所有</h4>
                        </div>
                        <div class="navbar-fixed-top align-right">
                            <br/> &nbsp;
                            <a id="btn-login-dark" class="blue" href="#" onclick="darkStyle()">Dark</a> &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-blur" class="blue" href="#" onclick="blurStyle()">Blur</a> &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-light" class="blue" href="#" onclick="lightStyle()">Light</a> &nbsp; &nbsp;
                            &nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
