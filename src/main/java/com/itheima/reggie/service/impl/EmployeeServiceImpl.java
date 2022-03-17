package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;
    //登录
    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        //将密码进行md5加密
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeMapper.selectOne(queryWrapper);
        //判断是否查询到结果
        if(emp==null){
            return R.error("登录失败");
        }
        //密码比对，不一致返回失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //判断员工状态是否是禁用
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @Override
    public R<String> logout(HttpServletRequest request) {
        //清理session中保存的当前员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
        //添加员工
    @Override
    public R<String> save(HttpServletRequest request, Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //获得当前登录用户的id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeMapper.insert(employee);
        return R.success("添加成功");
    }
    //分页查询
    @Override
    public R<Page> page(int page, int pageSize, String name) {
        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeMapper.selectPage(pageInfo,queryWrapper);
        return R.success(pageInfo);

    }
        //根据id修改员工信息
    @Override
    public R<String> update(HttpServletRequest request, Employee employee) {
     //  long id = Thread.currentThread().getId();
        //log.info("线程id为：{}",id);
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        employeeMapper.updateById(employee);
        return R.success("员工信息修改成功");
    }
    //通过id来查询员工信息
    @Override
    public R<Employee> getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

}
