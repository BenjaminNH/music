package edu.hut.oyg.music;

import edu.hut.oyg.music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
//@Component
public class Preload implements ApplicationRunner {

    @Autowired
    AdminService adminService;

    @Autowired
    JdbcTemplate template;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("starting preload");
        RestTemplate restTemplate = new RestTemplate();
//        adminService.verify("preload","preload");
        restTemplate.execute("http://127.0.0.1:8080/preload",HttpMethod.GET,null,null);
        template.execute("select `name` from singer where id = 1");
        log.info("completed preload");
    }
}


