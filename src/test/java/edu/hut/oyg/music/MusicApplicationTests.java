package edu.hut.oyg.music;

import edu.hut.oyg.music.controller.AdminController;
import edu.hut.oyg.music.response.ResponseCode;
import edu.hut.oyg.music.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        String s = "/img/singerPic/test.png";
        boolean b = FileUtil.deleteFile(s);
        log.info("delete:{}",b);
    }

}
