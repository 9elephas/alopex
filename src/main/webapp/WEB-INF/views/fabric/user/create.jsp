<%--
  ~ Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
  ~ @author 徐泽宇
  ~
  ~
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../menu/menu.jsp" %>

<script src="<%=request.getContextPath()%>/assets/js/fabric/user/create.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/fabric/user/create.css" rel="stylesheet">

<div class="container">
    <div class="user-create">
        <div id="output"></div>
        <div class="avatar"></div>
        <div class="form-box">
            <input id="user" name="user" type="text" placeholder=" 用户名">
            <input id="passwd" name="passwd" type="input" placeholder="密码">
            <button id="registerButton" class="btn btn-info btn-block login" type="submit"> 注册</button>
        </div>
    </div>

</div>
