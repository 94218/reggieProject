package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //新增分类
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
       return categoryService.add(category);
    }
    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
    return categoryService.page(page,pageSize);
    }
    //根据id删除分类信息
  @DeleteMapping
  public R<String> delete(Long id){
        log.info("删除的分类id为:{}",id);
      return    categoryService.remove(id);

  }
    //根据id修改分类信息
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);
         categoryService.update(category);
        return R.success("修改分类信息成功");
    }
    //根据条件查询菜品分类数据
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
      return categoryService.list(category);
    }


}
