package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    //添加购物车
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据:{}", shoppingCart);
        ShoppingCart cart = shoppingCartService.add(shoppingCart);
        return R.success(cart);
    }

    //查询购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车数据...");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    //清空购物车
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(lqw);
        return R.success("清空购物车成功");
    }

    //购物车菜品或套餐数量>1时可以-1
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        //获取请求的菜品Id
        Long dishId = shoppingCart.getDishId();
        //创建购物车条件构造器
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
//先判断当前登录用户id是否跟下单用户id一致
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
//判断菜品id是否为空
        if (dishId != null) {
            //如果不为空则与购物车菜品id进行菜品匹配
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
            //否则与套餐id进行套餐匹配
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        //根据条件查询出当前购物车对象
        ShoppingCart cart = shoppingCartService.getOne(lqw);
//判断购物车是否为空
        if (cart != null) {
            //如果不为空则获取到购物车中商品数量
            Integer number = cart.getNumber();
            //如果数量大于0
            if (number > 0) {
                //则执行减少数量
                cart.setNumber(number - 1);
                shoppingCartService.updateById(cart);
            }

            //如果等于0则执行删除操作
            if (cart.getNumber() == 0) {
                shoppingCartService.removeById(cart);
            }
        }
        return R.success(cart);
    }
}
