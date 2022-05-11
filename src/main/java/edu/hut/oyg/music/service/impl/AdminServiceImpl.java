package edu.hut.oyg.music.service.impl;

import edu.hut.oyg.music.dao.AdminMapper;
import edu.hut.oyg.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public boolean verify(String name, String password) {
        return 1 == adminMapper.verify(name, password);
    }
}
