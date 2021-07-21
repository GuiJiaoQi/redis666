<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <!--  JQUERY -->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <!--  BOOTSTRAP -->
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <title>演示分页插件</title>
    <script type="text/javascript">
        $(function() {
            $("#demo_pag1").bs_pagination({
                //当前页
                currentPage:1,
                //每页显示条数
                rowsPerPage:10,
                //总条数
                totalRows:100,
                //总页数
                totalPages:10,
                //显示翻页的卡片数
                visiblePageLinks:5,
                //为true时显示跳转层,为false时不显示跳转页面层
                showGoToPage:true,
                //为true时显示每页的条数,为false时隐藏
                showRowsPerPage:false,
                //为true时显示页面详情,为false时隐藏
                showRowsInfo:false,
                //只要触发页面跳转增都会启动该方法
                onChangePage:function (e,pageObj){
                    //触发该方法时会显示当前页面页数和条数
                    alert(pageObj.currentPage);
                    alert(pageObj.rowsPerPage);
                    //调到后台进行处理
                    window.location.href="";
                }
            })
        });
    </script>
</head>
<body>
<!--  Just create a div and give it an ID -->
<div id="demo_pag1"></div>
</body>
</html>
