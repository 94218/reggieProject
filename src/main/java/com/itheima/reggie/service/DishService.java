package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.dto.DishDto;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品信息，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);
    //菜品分页查询
   public Page page(int page,int pageSize,String name);
   //通过id查询菜品信息
    public DishDto getByIdWithFlavor(Long id);
    //修改
    public void updateWithFlavor(DishDto dishDto);
    //通过id查询对应的菜品数据
    public R<List<DishDto>> list(Dish dish);
    //修改菜品启售停售的状态
    public void dishStatus(Long[] ids,Dish dish);
    //删除菜品
    public void removeByIds(List<Long> ids);
}
