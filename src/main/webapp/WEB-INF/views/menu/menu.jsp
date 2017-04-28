<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../layouts/head.jsp" %>

<script src="<%=request.getContextPath()%>/assets/js/menu/menu.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/menu/menu.css" rel="stylesheet">

<div class="navbar-content">
    <nav id="navbar-example" class="navbar navbar-default navbar-static">
        <div class="logo_img_div">
            <%--<img class="logo_img" src="<%=request.getContextPath()%>/assets/imgs/alopex_logo.jpg">--%>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="navbar-header col-md-2">
                    <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                            data-target=".bs-example-js-navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<%=request.getContextPath()%>/">Alopex 控制平台</a>
                </div>
                <div class="col-md-7">
                    <ul class="nav navbar-nav" id="s-menu">
                        <li class="active">
                            <a href="<%=request.getContextPath()%>/main" data-ref="rf1">首页</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a href="#" data-ref="rf2">产品</a>
                        </li>
                        <li>
                            <a href="#" data-ref="rf3">内容</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-haspopup="true" aria-expanded="false">系统 <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<%=request.getContextPath()%>/config/index">参数设置</a></li>
                                </ul>
                            </li>
                        </ul>
                    </ul>
                </div>
                <div class="col-md-2">
                    <ul class="nav navbar-nav navbar-right">
                        <li id="fat-menu" class="dropdown">
                            <a id="drop3" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-haspopup="true" aria-expanded="false">
                                你好：<%=session.getAttribute("current_user") %>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="drop3">
                                <li>
                                    <a href="#">个人中心</a>
                                </li>
                                <li>
                                    <a href="#">系统设置</a>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="<%=request.getContextPath()%>/admins/login_out">退出登录</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>

