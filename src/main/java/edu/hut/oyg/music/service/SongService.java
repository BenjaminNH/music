package edu.hut.oyg.music.service;

import edu.hut.oyg.music.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService{
    boolean insert(Song song);
    boolean delete(Integer id);
    boolean update(Song song);
    List<Song> selectAll();
    List<Song> selectBySingerId(Integer singerId,String name);
    List<Song> selectByName(String name);
    Song selectById(Integer id);
    String saveSong(MultipartFile uploadFile);
    String saveSongPic(MultipartFile uploadFile);
}
