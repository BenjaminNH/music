package edu.hut.oyg.music;

import edu.hut.oyg.music.dao.SongListMapper;
import edu.hut.oyg.music.entity.SongList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class SongListTests {

    @Autowired
    SongListMapper mapper;

    @Test
    void selectTest() {
        List<SongList> all = mapper.selectAll();
        log.info("selectALl : {}",all.size());
        SongList id = mapper.selectById(1);
        log.info("selectById : {}",id);
        List<SongList> name = mapper.selectByTitle("我");
        log.info("selectByName : {}",name.size());
        List<SongList> style = mapper.selectByStyle("华语");
        log.info("selectByStyle : {}",style.size());
    }

    @Test
    void deleteTest() {
        log.info("delete : {}",mapper.delete(85));
    }
}
