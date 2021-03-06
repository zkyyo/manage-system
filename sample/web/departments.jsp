<%@ page import="java.util.List" %>
<%@ page import="com.zkyyo.www.po.DepartmentPo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询部门</title>
    <style type="text/css">
        body {
            margin: auto;
            text-align: center
        }
    </style>
</head>
<body>
<div style="text-align: right">
    <a href="${pageContext.request.contextPath}/functions.jsp">首页</a>
    <a href="${pageContext.request.contextPath}/logout.do">注销&nbsp;</a>
</div>
<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<h2>查询部门</h2>
<hr/>

<form method="get" action="${pageContext.request.contextPath}/department_find.do">
    <select name="way">
        <option value="all">查询所有</option>
        <option value="by_dept_id">通过部门号</option>
        <option value="by_dept_name">通过部门名</option>
    </select>
    <input type="text" name="info" size="50" maxlength="80">
    <input type="submit" value="搜索一下"><br/>
    排序依据
    <input type="radio" name="order" value="default" checked>默认
    <input type="radio" name="order" value="id">部门号
    <input type="radio" name="order" value="pop">部门人数
    <input type="radio" name="order" value="date">建立时间<br/>
    排序方式
    <input type="radio" name="reverse" value="false" checked>升序
    <input type="radio" name="reverse" value="true">倒序
</form>

<table border="1" align="center">
    <%
        List<DepartmentPo> result = (List<DepartmentPo>) request.getAttribute("result");
        if (result != null) {
    %>
    <tr>
        <td>部门号</td>
        <td>部门名</td>
        <td>人数</td>
        <td>建立日期</td>
        <td>描述</td>
    </tr>
    <%
        for (DepartmentPo d : result) {
    %>
    <tr>
        <td><%= d.getDeptId() %>
        </td>
        <td><%= d.getName() %>
        </td>
        <td><%= d.getPopulation() %>
        </td>
        <td><%= d.getBuildDate() %>
        </td>
        <td><%= d.getDescription() %>
        </td>
        <td><a href="/department_detail.do?&deptId=<%= d.getDeptId() %>" target="_blank">详细</a></td>
        <td><a href="/department_update.jsp?&deptId=<%= d.getDeptId() %>" target="_blank">修改</a></td>
        <td><a href="/department_delete.do?&deptId=<%= d.getDeptId() %>" target="_blank">删除</a></td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>
