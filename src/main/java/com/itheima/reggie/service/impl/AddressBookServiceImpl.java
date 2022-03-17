package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    //新增地址
    @Override
    public R<AddressBook> add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.insert(addressBook);
        return R.success(addressBook);
    }

    //查询所有地址
    @Override
    public List<AddressBook> getALL(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        //条件构造器
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();

        lqw.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
        lqw.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressBookMapper.selectList(lqw);
        return list;
    }

    //通过id查询地址
    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.selectById(id);
        return addressBook;
    }

    //设置默认地址
    @Override
    public R<AddressBook> setDefault(AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();
        luw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        luw.set(AddressBook::getIsDefault, 0);
        addressBookMapper.update(addressBook,luw);

        addressBook.setIsDefault(1);
        addressBookMapper.updateById(addressBook);
        return R.success(addressBook);
    }
    //查询默认地址
    @Override
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> lqw=new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lqw.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookMapper.selectOne(lqw);
        if(addressBook==null){
            return R.error("没有找到该对象");
        }else {
            return R.success(addressBook);
        }
    }
    //修改地址
    @Override
    public void update(AddressBook addressBook) {
//
//        LambdaQueryWrapper<AddressBook> lqw=new LambdaQueryWrapper<>();
//        lqw.eq(AddressBook::getId,addressBook.getId());
        addressBookMapper.updateById(addressBook);
    }

}
