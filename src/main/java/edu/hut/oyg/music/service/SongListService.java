package edu.hut.oyg.music.service;

import edu.hut.oyg.music.entity.SongList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongListService {
    boolean insert(SongList songList);
    boolean delete(Integer id);
    boolean update(SongList songList);
    List<SongList> selectAll();
    List<SongList> selectByTitle(String title);
    List<SongList> selectByStyle(String style);
    SongList selectById(Integer id);
    String savePic(MultipartFile uploadFile);
}

