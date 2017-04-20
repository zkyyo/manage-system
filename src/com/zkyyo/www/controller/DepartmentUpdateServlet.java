package com.zkyyo.www.controller;

import com.zkyyo.www.po.DepartmentPo;
import com.zkyyo.www.service.DepartmentService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/department_update.do")
public class DepartmentUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        String name = request.getParameter("name").trim();
        String departmentId = request.getParameter("deptId").trim();
        String buildDate = request.getParameter("buildDate").trim();
        String desc = request.getParameter("description").trim();

        List<String> errors = new ArrayList<>();
        DepartmentService departmentService = DepartmentService.getInstance();
        DepartmentPo dept = new DepartmentPo();
        dept.setDeptId(Integer.valueOf(departmentId));

        //部门名
        if (name.length() > 0) {
            if (!departmentService.isValidName(name)) {
                errors.add("部门名输入有误或已被注册");
            } else {
                dept.setDeptName(name);
            }
        }
        //创建日期
        if (buildDate.length() > 0) {
            if (!departmentService.isValidDate(buildDate)) {
                errors.add("创建日期输入有误");
            } else {
                dept.setBuildDate(java.sql.Date.valueOf(buildDate));
            }
        }
        //描述
        if (desc.length() > 0) {
            dept.setDeptDesc(desc);
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            DepartmentPo initialDept = departmentService.findDepartment(Integer.valueOf(departmentId));
            DepartmentPo updatedDept = departmentService.updateDepartment(dept);
            if (updatedDept == null) {
                errors.add("数据库更新错误");
                request.setAttribute("errors", errors);
            } else {
                request.setAttribute("status", "ok");
                LogUtil.update(loginId, initialDept, updatedDept);
            }
        }

        request.getRequestDispatcher("department_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
