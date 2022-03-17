package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.mapper.ShoppingCartMaaper;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMaaper,ShoppingCart> implements ShoppingCartService {
    @Autowired
    private ShoppingCartMaaper shoppingCartMaaper;

    //添加购物车数据
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        //获取当前用户id，
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //获取当前菜品id
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,currentId);

        if(dishId!=null){
            //添加的是菜品
            lqw.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加的是套餐
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        //查询套餐或者套餐是否在购物车中
        ShoppingCart cart = shoppingCartMaaper.selectOne(lqw);
        if(cart!=null){
            //如果存在就在原基础上+1
            Integer number = cart.getNumber();
            cart.setNumber(number+1);
            shoppingCartMaaper.updateById(cart);

        }else {
            //如果不存就添加到购物车
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMaaper.insert(shoppingCart);
            cart=shoppingCart;
        }
        return cart;
    }

}
