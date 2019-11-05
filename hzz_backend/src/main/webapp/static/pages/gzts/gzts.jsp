<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>公众投诉2</title>
    <link rel="stylesheet" href="/plug-in/bootstrap3.3.5/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="/plug-in/bootstrap3.3.5/css/default.css" type="text/css"></link>

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script type="text/javascript" src="/plug-in/jquery/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="/plug-in/ace/js/jquery.validate.js"></script>
    <script type="text/javascript" src="/plug-in/ace/js/messages_zh.js"></script>
    <script type="text/javascript" src="/plug-in/jquery-plugs/form/jquery.form.js"></script>

    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script type="text/javascript" src="/plug-in/bootstrap/js/bootstrap.min.js"></script>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        .an {
            text-decoration: none;
            font-size: 14px;
        }

        #divZZ {
            filter: alpha(opacity=50); /*IE滤镜，透明度50%*/
            -moz-opacity: 0.5; /*Firefox私有，透明度50%*/
            opacity: 0.5; /*其他，透明度50%*/
        }
        .error{
            color:red;
        }
    </style>
    <script>
        $.validator.setDefaults({
            submitHandler: function() {
                $('#gztsForm').ajaxSubmit({
                    success: function (res) {
                        var isSure = confirm(res.msg);
                        if(isSure && res.success) {
                            window.location.reload();
                        } else if(isSure && !res.success){
                            var date = new Date();
                            $('#randCodeImage').attr('src', '/randCodeImage?' + date.getTime())
                        }
                    },
                });
            }
        });
        //增加手机号验证规则
        $.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");
        $(function () {

            getEventTypes();
            $('#gztsForm').validate({
                rules: {
                    complainantPhone: {
                        required: true,
                        isMobile:true
                    },
                    messages: {
                        complainantPhone: {
                            required: "请输入手机号"
                        },
                    }
                }
            });
        });

        function btnPhoto() {
            //alert("上传图片");
            $("#chooseImage")[0].click();
        }
    </script>
</head>
<body>
<form id="gztsForm" name="gztsForm" action="/gzts/save.do" method="post" enctype="multipart/form-data">
    <div style="height: 220px; background: #eeeeee url('/images/gzts/bg.png') center center no-repeat;"></div>
    <div style="height: 490px; background: url('/images/gzts/kuang.png') center bottom no-repeat;">
        <div style="height: 490px; width: 800px; margin: 0 auto; overflow: hidden;">
            <div style="font-family: 'Microsoft YaHei'; font-size: 20px; font-weight: bold; height: 30px; line-height: 30px; padding-left: 150px; padding-top: 18px; letter-spacing: 0.1em;">
                我要投诉
            </div>
            <div style="height: 400px; width: 600px; margin: 30px auto 0;">

                <div class="form-group" style="padding-top: 20px;">
                    <label for="name" class="col-md-2 control-label">姓名:</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" id="name" name="complainantName" placeholder="输入姓名" required>
                    </div>
                    <label for="phone" class="col-md-2 control-label">手机号码:</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" id="phone" name="complainantPhone" placeholder="输入手机号码" required>
                    </div>
                    <div style="clear: both;"></div>
                </div>

                <div class="form-group">
                    <label for="eventType" class="col-md-2 control-label">事件类型:</label>
                    <div class="col-md-4">
                        <select id="eventType" name="questType" class="form-control">
                            <option>类型一</option>
                            <option>类型二</option>
                            <option>类型三</option>
                            <option>类型四</option>
                        </select>
                    </div>

                    <label for="site" class="col-md-2 control-label">位置:</label>
                    <div class="col-md-4">
                        <input id="site" name="site" type="text" placeholder="点击选择位置" onclick="OpenMap()" class="form-control" required readonly/>
                        <input id="eventLng" name="eventLng" type="hidden"/>
                        <input id="eventLat" name="eventLat" type="hidden"/>
                    </div>
<!--                    <input id="Button1" type="button" value="选点" onclick="OpenMap()" />-->
                    <div style="clear: both;"></div>
                </div>

                <div class="form-group">
                    <label for="details" class="col-md-2 control-label">详情描述:</label>
                    <div class="col-md-10">
                        <textarea id="details" name="memo" placeholder="请填写描述信息" class="form-control" style="height: 150px;"></textarea>
                    </div>
                    <div style="clear: both;"></div>
                </div>

                <div class="form-group">
                    <label for="randCode" class="col-md-2 control-label">验证码:</label>
                    <div class="col-md-3">
                        <input type="text" id="randCode" placeholder="请输入验证码" name="randCode" class="form-control" required/>
                    </div>
                    <div class="col-md-3">
                        <span width="90" height="30" style="background-color: #eeeeee;">
                            <img id="randCodeImage" src="/randCodeImage"/>
                        </span>
                    </div>
                    <div style="clear: both;"></div>
                </div>

                <div class="form-group">
                    <label for="randCode" class="col-md-2 control-label">现场图片:</label>
                    <div class="col-md-10">
                        <a id="btnPhoto" href="javascript:void(0);" onclick="btnPhoto()">
                            <img src="/images/gzts/photo.png" style="height: 30px;"/></a>
                        <span id="spanImg" style="font-size: 14px;">上传图片信息</span>
                        </span>
                    </div>
                    <div style="clear: both;"></div>
                </div>


            </div>
        </div>
    </div>
    <div style="margin-top: 10px; text-align: center;">
        <input type="image" id="sub" name="sub" src="/images/gzts/tijiao.png" onclick="this.submit()"/>
    </div>
    <div id="upImg" style="display: none">
        <input type="file" name="file" id="chooseImage" multiple="multiple"/>
    </div>
    <div id="divZZ" style="display: none; background-color: black; z-index: 888;"></div>
    <div id="imgPreview" style="height: auto; display: none; background-color: transparent;">
        <div style="height: auto; width: auto; max-width: 600px; border: 1px solid black; padding: 2px; margin: 0 auto; text-align: center; background-color: white">
            <div id="div1" style="height: 30px; line-height: 30px; width: 580px; text-align: right; margin: 0 auto;">
                <a href="javascript:void(0);" onclick="closePic()" class="an" style="color: red;">关闭</a>
            </div>
            <img src="#" id="cropedBigImg" style="background-color: #dddddd; width: auto; height: auto; max-width: 600px; max-height: 450px;"/>
        </div>
    </div>
</form>
<div id="divMap" style="display: none; position: absolute; top: 0; bottom: 0; left: 0; right: 0; overflow: hidden;">
    <iframe name="Iframe1" id="Iframe1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" width="100%" height="100%"></iframe>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('#chooseImage').on('change', function () {//当chooseImage的值改变时，执行此函数
        $("#spanImg").html("请选择文件！");
        var filePath = $(this).val(); //获取到input的value，里面是文件的路径
        if (filePath != "") {
            var fileFormat = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
            if (this.files.length > 0) {
                var src = window.URL.createObjectURL(this.files[0]); //转成可以在本地预览的格式

                // 检查是否是图片
                if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
                    alert('上传错误,文件格式必须为：png/jpg/jpeg');
                    $('#cropedBigImg').attr('src', "");
                    $("#spanImg").html("上传错误,文件格式必须为：png/jpg/jpeg");
                } else {
                    $('#cropedBigImg').attr('src', src);
                    $("#spanImg").html("<a href=\"javascript:void(0)\" onclick=\"showPic()\" class=\"an\" style=\"color:red;\">上传图片预览</a>");
                }
            } else {
                alert('上传错误,无图片！');
                $('#cropedBigImg').attr('src', "");
                $("#spanImg").html("上传错误,无图片！");
            }
        } else {
            alert('上传错误,请选择文件！');
            $('#cropedBigImg').attr('src', "");
            $("#spanImg").html("上传错误,请选择文件！");
        }
    });

    function showPic() {
        var h = $(document).height();
        $("#divZZ").css({
            "position": "absolute",
            "left": "0",
            "top": "0",
            "height": h + "px",
            "right": "0",
            "z-index": "999"
        });
        $("#divZZ").show();
        //position: absolute; left: 0; top: 0; right: 0;
        var top = document.documentElement.scrollTop + 100;
        $("#imgPreview").css({"position": "absolute", "left": "0", "top": top + "px", "right": "0", "z-index": "1000"});
        $("#imgPreview").show();
    }

    function closePic() {
        $("#imgPreview").hide();
        $("#divZZ").hide();
    }

    /**
     * 动态获取事件类型
     */
    function getEventTypes() {
        $('#eventType').empty();
        $.post('/gzts/getListEventType.do', {}, function (res) {
            $.each(res, function (index, obj) {
                $('#eventType').append('<option value="' + obj.typecode + '">' + obj.typename + '</option>')
            });
        }, 'json')
    }

    function OpenMap() {
        document.getElementById("Iframe1").src = "BaiduMap.html?m=" + Math.random();
        $("#divMap").show();
    }

    function CloseMap(lng, lat, val) {
        if (val != null) {
            $("#site").val(val);
        }
        if (lng) {
            $("#eventLng").val(lng);
        }
        if (lat) {
            $("#eventLat").val(lat);
        }
        $("#divMap").hide();
        var frame = $('#Iframe1');
        frame[0].contentWindow.document.write('');
        frame[0].contentWindow.close();
    }

    /**
     * 刷新验证码
     */
    $('#randCodeImage').click(function () {
        var date = new Date();
        var img = document.getElementById("randCodeImage");
        img.src = '/randCodeImage?a=' + date.getTime();
    });

    function openModel() {
        $('#myModal').modal('show');
    }

    function map() {

    }
</script>

</body>
</html>
