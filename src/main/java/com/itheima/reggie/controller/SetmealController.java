package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;


    //保存套餐
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);

        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }
    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page page1 = setmealService.page(page, pageSize, name);
        return R.success(page1);
    }
    //删除
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐信息删除成功");
    }
    //修改套餐状态
    @PostMapping("/status/{status}")
    public R<String> dishStatus(Long[] ids, Setmeal setmeal){
        log.info("ids:{}",ids);
        setmealService.setmealStatus(ids,setmeal);
        return R.success("菜品状态修改成功");
    }
    //根据id查询套餐信息
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }
    //修改
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setmealService.updateWithDish(setmealDto);
        return R.success("套餐信息修改成功");
    }
    //移动端根据条件查询套餐
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){

        return setmealService.list(setmeal);
    }
}
