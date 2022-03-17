package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    //新增分类
    @Override
    public R<String> add(Category category) {
        categoryMapper.insert(category);
        return R.success("新增分类成功");
    }

    //分页查询
    @Override
    public R<Page> page(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        //分页查询
        categoryMapper.selectPage(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }
    //根据id删除分类
    @Override
    public R<String> remove(Long id) {
            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
            int count1 = dishService.count(dishLambdaQueryWrapper);
            if(count1 > 0){
                throw new CustomException("当前分类下关联了菜品，不能删除");//已经关联菜品，抛出一个业务异常
            }
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
            int count2 = setmealService.count(setmealLambdaQueryWrapper);
            if(count2 > 0){
                throw new CustomException("当前分类下关联了套餐，不能删除");//已经关联套餐，抛出一个业务异常
            }
            super.removeById(id);
        return R.success("删除成功");
    }
    //修改分类信息
    @Override
    public R<String> update(Category category) {
        categoryMapper.updateById(category);
        return R.success("分类信息修改成功");
    }
    //根据条件查询菜品分类信息
    @Override
    public R<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryMapper.selectList(queryWrapper);

        return R.success(list);
    }


}