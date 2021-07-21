<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <base href="<%=basePath%>">
    <title>演示bs_typeahead插件</title>
    <script type="text/javascript">
        $(function () {
            var name2id = {};
           /* alert($("#customerName"));*/
            $("#customerName").typeahead({
                /*source:['字节跳动','动力节点','国庆节','中国人民']*/
                source:function(query,process){
                    $.ajax({
                        url:'workbench/transaction/typeahead.do',
                        data:{
                            customerName:query,
                          /*process:process*/
                        },
                        type:'post',
                        dataType:'json',
                        success:function (data){
                            /*alert(data.length);*/
                            var customerNameArr = [];
                            $.each(data,function (index,obj){
                                /*alert(obj.name);*/
                                customerNameArr.push(obj.name);
                                name2id[obj.name] = obj.id;
                            });
                            process(customerNameArr);
                        }
                    })
                },
                //选择之后触发该方法
                afterSelect:function (item){
                   /* alert(item.id)*/
                    alert(name2id[item]);
                }
            });
        });
    </script>
</head>
<body>
<input type="text" id="customerName">
</body>
</html>
