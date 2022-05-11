package edu.hut.oyg.music.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    int verify(String name, String password);
}
