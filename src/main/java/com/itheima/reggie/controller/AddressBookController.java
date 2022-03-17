package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    //新增地址
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        log.info("addressBook:{}",addressBook);
        return addressBookService.add(addressBook);
    }
//查询所有地址
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        List<AddressBook> list = addressBookService.getALL(addressBook);
        return  R.success(list);
    }
    //通过id查询地址
    @GetMapping("/{id}")
    public R<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该地址");
        }
    }
    //设为默认地址
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        R<AddressBook> bookR = addressBookService.setDefault(addressBook);
        return bookR;
    }
    //查询默认地址
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        R<AddressBook> addressBook = addressBookService.getDefault();
        return addressBook;
    }

    //修改地址
    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        log.info(addressBook.toString());
        addressBookService.update(addressBook);
       return R.success("success");
    }
    //删除地址
    @DeleteMapping
    public R<String> delete(@RequestParam Long ids){
        log.info("ids:{}",ids);
        addressBookService.removeById(ids);
        return R.success("删除成功");
    }
}
