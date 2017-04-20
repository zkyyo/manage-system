package com.zkyyo.www.controller;

import com.zkyyo.www.po.EmployeePo;
import com.zkyyo.www.service.EmployeeService;
import com.zkyyo.www.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employee_update.do")
public class EmployeeUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer loginId = (Integer) request.getSession().getAttribute("login");
        String userId = request.getParameter("userId");
        String name = request.getParameter("name").trim();
        String mobile = request.getParameter("mobile").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmedPaswword = request.getParameter("confirmedPassword");
        String departmendId = request.getParameter("departmentId").trim();
        String salary = request.getParameter("salary").trim();
        String date = request.getParameter("date").trim();

        List<String> errors = new ArrayList<>();
        EmployeeService employeeService = EmployeeService.getInstance();
        EmployeePo ep = new EmployeePo();
        ep.setUserId(Integer.valueOf(userId));
        if (name.length() > 0) {
            if (!employeeService.isValidName(name)) {
                errors.add("姓名输入有误");
            } else {
                ep.setUserName(name);
            }
        }
        if (mobile.length() > 0) {
            if (!employeeService.isValidMobile(mobile)) {
                errors.add("手机号输入有误");
            } else {
                ep.setMobile(mobile);
            }
        }
        if (email.length() > 0) {
            if (!employeeService.isValidEmail(email)) {
                errors.add("邮箱输入有误");
            } else {
                ep.setEmail(email);
            }
        }
        if (password.length() > 0) {
            if (!employeeService.isValidPassword(password, confirmedPaswword)) {
                errors.add("第二次密码输入有误");
            } else {
                ep.setPassword(password);
            }
        }
        if (departmendId.length() > 0) {
            ep.setDeptId(Integer.valueOf(departmendId));
        }
        if (salary.length() > 0) {
            if (!employeeService.isValidSalary(salary)) {
                errors.add("薪水输入有误");
            } else {
                ep.setSalary(Double.valueOf(salary));
            }
        }
        if (date.length() > 0) {
            if (!employeeService.isValidDate(date)) {
                errors.add("就职日期输入有误");
            } else {
                ep.setEmployDate(java.sql.Date.valueOf(date));
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            EmployeePo initialEp = employeeService.findEmployee(Integer.valueOf(userId));
            EmployeePo updatedEp = employeeService.updateEmployee(ep);
            if (updatedEp == null) {
                errors.add("数据库更新错误");
                request.setAttribute("errors", errors);
            } else {
                request.setAttribute("status", "ok");
                LogUtil.update(loginId, initialEp, updatedEp);
            }
        }

        request.getRequestDispatcher("employee_update.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
