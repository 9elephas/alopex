<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../menu/menu.jsp" %>

<%-- DataTables --%>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/dataTables.bootstrap.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/jquery.dataTables.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/dataTables.bootstrap.min.css"
      rel="stylesheet">
<%----%>

<%-- DataTables Editor Button Select--%>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/Editor-1.6.2/js/dataTables.editor.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/Editor-1.6.2/js/editor.bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/Buttons-1.3.1/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/Buttons-1.3.1/js/buttons.bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/lib/DataTables-1.10.15/Select-1.2.2/js/dataTables.select.min.js"></script>

<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/Editor-1.6.2/css/editor.bootstrap.min.css"
      rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/Buttons-1.3.1/css/buttons.dataTables.min.css"
      rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/css/lib/DataTables-1.10.15/Select-1.2.2/css/select.dataTables.min.css"
      rel="stylesheet">

<%----%>


<script src="<%=request.getContextPath()%>/assets/js/config/index.js"></script>
<link href="<%=request.getContextPath()%>/assets/css/config/index.css" rel="stylesheet">


<body>
<table id="config_items_table" class="table table-striped table-bordered hover">
    <thead>
    <tr>
        <th>名称</th>
        <th>值</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>名称</th>
        <th>值</th>
    </tr>
    </tfoot>
</table>
</body>
