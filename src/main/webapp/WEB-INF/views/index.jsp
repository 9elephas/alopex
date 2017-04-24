<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<style>
    #header {
        width: 100%;
        height: 90px;
        margin: 0 auto;
        text-align: center;
    }

    #body {
        width: 980px;
        height: 500px;
        margin: 0 auto;
    }

    #aside {
        float: left;
        width: 140px;
        height: 500px;
        text-align: left;
    }

    #main {
        float: left;
        width: 740px;
        height: 500px;
        text-align: center;
    }

    #footer {
        width: 100%;
        height: 90px;
        margin: 0 auto;
        text-align: right;
    }
</style>

<body>


<div id="wrapper">
    <div id="header"><h1>欢迎使用</h1></div>
    <div id="body" class="clearfix">
        <div id="aside"><img src="<c:url value="/assets/imgs/alopex_log.jpg" />" width="100%"/>
            <div class="inner"></div>
        </div>
        <div id="main">
            <div class="inner"><h2>Fabric Client REST 服务平台</h2></div>
        </div>
    </div>
    <div id="footer">九象网络科技（上海）有限公司</div>
</div>

</body>
</html>
