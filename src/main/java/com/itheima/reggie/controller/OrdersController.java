package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order){
        log.info("订单数据:{}", order);
        ordersService.submit(order);
        return R.success("下单成功");
    }
    //移动端订单明细
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){
        Page<Orders> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> lqw=new LambdaQueryWrapper<>();
        lqw.orderByDesc(Orders::getOrderTime);
        Page<Orders> ordersPage = ordersService.page(pageInfo, lqw);
        return  R.success(ordersPage);
    }
    //后台端订单明细
    @GetMapping("/page")
    public R<Page> ipage(int page, int pageSize,String number){
        Page<Orders> pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> lqw=new LambdaQueryWrapper<>();
        lqw.like(number!=null,Orders::getNumber,number);
        lqw.orderByDesc(Orders::getOrderTime);
        Page<Orders> ordersPage = ordersService.page(pageInfo, lqw);
        return  R.success(ordersPage);
    }
    //修改派送状态
    @PutMapping
    public R<Orders> updateStatus(@RequestBody Orders order){
        ordersService.updateById(order);
        return R.success(order);
    }
    //再来一单
@PostMapping("/again")
    public R<Orders> again(@RequestBody Orders order){
        ordersService.submit(order);
        return R.success(order);
}
}
