package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;

import java.util.List;


public interface CategoryService extends IService<Category> {
    //新增分类
    public R<String> add(Category category);
    //分页查询
    public R<Page> page(int page,int pageSize);
    //根据id删除分类
    public R<String> remove(Long id);
    //修改分类信息
    public R<String> update(Category category);
    //根据条件查询菜品分类信息
    public R<List<Category>> list(Category category);

}