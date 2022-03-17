package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {
    //新增地址
    public R<AddressBook> add(AddressBook addressBook);
    //查询所有地址
    public List<AddressBook> getALL(AddressBook addressBook);
    //通过id查询地址
    public AddressBook getById(Long id);
    //设置默认地址
    public R<AddressBook> setDefault(AddressBook addressBook);
    //查询默认地址
     public R<AddressBook> getDefault();
    //修改地址
    public void update(AddressBook addressBook);
}
