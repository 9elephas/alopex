<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../menu/menu.jsp" %>

<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/dataTables.bootstrap.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/jquery.dataTables.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/dataTables.bootstrap.min.css"
      rel="stylesheet">


<script src="<%=request.getContextPath()%>/assets/js/config/index.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/config/index.css" rel="stylesheet">


<body>
<table id="config_items_table" class="table table-striped table-bordered hover">
    <thead>
    <tr>
        <th>名称</th>
        <th>值</th>
        <th width="200px">操作</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>名称</th>
        <th>值</th>
        <th>操作</th>
    </tr>
    </tfoot>
</table>
</body>
