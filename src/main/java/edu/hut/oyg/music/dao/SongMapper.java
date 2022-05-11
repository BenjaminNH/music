package edu.hut.oyg.music.dao;

import edu.hut.oyg.music.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {
    int insert(Song song);
    int delete(Integer id);
    int update(Song song);
    List<Song> selectAll();
    List<Song> selectBySingerId(@Param("singerId") Integer singerId,@Param("name") String name);
    List<Song> selectByName(String name);
    Song selectById(Integer id);
}
