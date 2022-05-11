package edu.hut.oyg.music;

import edu.hut.oyg.music.dao.SongMapper;
import edu.hut.oyg.music.entity.Song;
import edu.hut.oyg.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class SongTests {

    @Autowired
    SongMapper mapper;

    @Autowired
    SongService service;

    @Test
    void selectTest() {
        List<Song> all = mapper.selectAll();
        log.info("selectAll:{}",all.size());
        Song id = mapper.selectById(1);
        log.info("selectById:{}",id.getName());
        List<Song> name = mapper.selectByName("夜");
        log.info("selectByName:{}",name.size());
        List<Song> singerId = mapper.selectBySingerId(2,null);
        log.info("selectBySingerId:{}",singerId.size());
    }
}
