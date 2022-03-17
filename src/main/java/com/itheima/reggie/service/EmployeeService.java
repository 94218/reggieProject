package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface EmployeeService  extends IService<Employee> {
    //员工登录
   public R<Employee> login(HttpServletRequest request,Employee employee);
    //员工退出
    public R<String> logout(HttpServletRequest request);
    //添加员工
    public R<String> save(HttpServletRequest request,Employee employee);
    //分页查询
    public R<Page>  page(int page,int pageSize,String name);
    //根据id修改员工信息
    public R<String> update(HttpServletRequest request,Employee employee);
    //通过id查询
    public R<Employee> getById(Long id);
}
