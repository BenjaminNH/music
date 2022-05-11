package edu.hut.oyg.music.dao;

import edu.hut.oyg.music.entity.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListMapper {
    boolean insert(SongList songList);
    boolean delete(Integer id);
    boolean update(SongList songList);
    List<SongList> selectAll();
    List<SongList> selectByTitle(String title);
    List<SongList> selectByStyle(String style);
    SongList selectById(Integer id);
}
