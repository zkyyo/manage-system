<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评价员工</title>
</head>
<body>
<%
    Integer userId = (Integer) request.getSession().getAttribute("login");
    if (userId == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String beEvaluatedId = request.getParameter("beEvaluatedId");
%>

<h1>评价员工</h1>
<form method="get" action="employee_evaluate.do">
    <table border="1">
        <tr>
            <td>评价内容(300字以内):</td>
            <td><textarea name="evaluation" rows="10" cols="30" maxlength="300"></textarea></td>
        </tr>
    </table>
    评价等级(1-10):
    <input type="hidden" name="evaluatorId" value=<%= userId %>>
    <input type="hidden" name="beEvaluatedId" value=<%= beEvaluatedId %>>
    <input type="number" name="stars" min="1" max="10"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
