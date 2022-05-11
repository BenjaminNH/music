package edu.hut.oyg.music.service;

import edu.hut.oyg.music.dto.admin.SingerDTO;
import edu.hut.oyg.music.entity.Singer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SingerService {
    boolean insert(Singer singer);
    boolean delete(Integer id);
    boolean update(Singer singer);
    Singer selectById(Integer id);
    List<Singer> selectAll();
    List<Singer> selectByName(String name);
    List<SingerDTO> selectDTO();
    String saveSingerPic(MultipartFile uploadFile);
}
