package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;

    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
    //菜品分类查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page page1 = dishService.page(page, pageSize, name);
        return R.success(page1);
    }
    //通过id查询菜品信息
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }
    //修改菜品信息
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");

    }
    //根据条件查询对应的菜品
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//    return dishService.list(dish);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        return dishService.list(dish);
    }


    //修改菜品启售停售状态
    @PostMapping("/status/{status}")
        public R<String> dishStatus(Long[] ids,Dish dish){
        log.info("ids:{}",ids);
         dishService.dishStatus(ids,dish);
        return R.success("菜品状态修改成功");
    }
    //菜品删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        dishService.removeByIds(ids);
        return R.success("菜品删除成功");
    }

}
