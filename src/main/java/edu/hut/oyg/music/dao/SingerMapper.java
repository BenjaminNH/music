package edu.hut.oyg.music.dao;

import edu.hut.oyg.music.dto.admin.SingerDTO;
import edu.hut.oyg.music.entity.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerMapper {
    int insert(Singer singer);
    int delete(Integer id);
    int update(Singer singer);
    Singer selectById(Integer id);
    List<Singer> selectAll();
    List<Singer> selectByName(String name);
    List<SingerDTO> selectDTO();
}
