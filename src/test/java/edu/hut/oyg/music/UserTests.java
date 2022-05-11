package edu.hut.oyg.music;

import edu.hut.oyg.music.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserTests {

    @Autowired
    UserMapper mapper;

    @Test
    void verifyPwdTest() {
        int success = mapper.verifyPassword("Yin", "123");
        log.info("verifyPwd :{}",success);
    }
}
