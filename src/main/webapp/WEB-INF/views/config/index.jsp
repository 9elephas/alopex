<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../menu/menu.jsp"%>

<script src="<%=request.getContextPath()%>/assets/js/config/index.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/config/index.css" rel="stylesheet">

<body>
<c:forEach items="${configItems}" var="node">
    <c:out value="${node.key}"></c:out>
    <c:out value="${node.value}"></c:out>
    <br/>
</c:forEach>
</body>
