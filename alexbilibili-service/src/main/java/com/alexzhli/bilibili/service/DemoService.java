package com.alexzhli.bilibili.service;

import com.alexzhli.bilibili.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    @Autowired
    private DemoDao demoDao;

    public String query(int id){
        return demoDao.query(id);
    }
}
