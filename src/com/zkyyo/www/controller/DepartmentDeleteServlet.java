package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "DepartmentDeleteServlet",
        urlPatterns = {"/department_delete.do"},
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW", value = "operation_message.jsp"),
                @WebInitParam(name = "ERROR_VIEW", value = "operation_message.jsp")
        }
)
public class DepartmentDeleteServlet extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;

    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        int deptId = Integer.valueOf(request.getParameter("deptId"));

        DepartmentService employeeService = DepartmentService.getInstance();
        DepartmentPo deletedDept = employeeService.deleteDepartment(deptId);
        String page = ERROR_VIEW;
        if (deletedDept == null) {
            request.setAttribute("message", "部门解散失败");
        } else {
            request.setAttribute("message", "部门解散成功");
            LogUtil.delete(loginId, deletedDept);
            page = SUCCESS_VIEW;
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
