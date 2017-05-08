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
        <div class="avatar"></div>
        <div class="form-box">
            <input  id="userInfoJsonStringInput"  type="text" value="{&quot;method&quot;:&quot;fabric-user-register&quot;,&quot;params&quot:{&quot;userName&quot;:&quot;roamer&quot;,&quot;mspID&quot;:&quot;org1msp&quot;,&quot;affiliation&quot;:&quot;org1.department1&quot;}}">
            <button id="registerButton" class="btn btn-info btn-block login" type="submit"> 注册</button>
        </div>
        <div class="result-box">
            <textarea name="result" id="result" cols=100% rows="10"></textarea>
        </div>
    </div>

</div>
