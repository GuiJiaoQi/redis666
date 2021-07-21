<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <title>测试bs_datetimepicker插件</title>
    <script type="text/javascript">
        $(function () {
           $("#birthday").datetimepicker({
               //中文格式
                language:'zh-CN',
                //日期格式
               format:'yyyy-mm-dd',
                //准确时间,取消时分秒
               minView:'month',
               //选择日期后自动确认
               autoclose:true,
               //今天的日期
               todayBtn:true,
               //清空日期
               clearBtn:true
           })
        });
    </script>
</head>
<body>
<input type="text" id="birthday" readonly>
</body>
</html>
