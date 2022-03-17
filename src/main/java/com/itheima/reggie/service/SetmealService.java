package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    //保存套餐信息
    public void saveWithDish(SetmealDto setmealDto);
    //分页查询
    public Page page(int page,int pageSize,String name);
    //删除
    public void removeWithDish(List<Long> ids);
    //修改套餐启售停售状态
    public void setmealStatus(Long[] ids,Setmeal setmeal);
    //通过id查询套餐信息
    public SetmealDto getByIdWithDish(Long id);
    //修改套餐信息
    public void updateWithDish(SetmealDto setmealDto);
    //根据条件查询套餐
    public R<List<Setmeal>> list(Setmeal setmeal);
}
