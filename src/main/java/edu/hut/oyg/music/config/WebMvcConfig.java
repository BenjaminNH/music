package edu.hut.oyg.music.config;

import edu.hut.oyg.music.util.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //映射路径
                .allowedOriginPatterns("*") //放行的原始域
                .allowedMethods("*") //放行的请求方法
                .allowCredentials(true); //允许Cookie
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String separator = FileUtil.separator;
        String dir = FileUtil.userDir;
        //歌手图片映射
        String singerPic = "file:" + dir + separator + "img" + separator + "singerPic" + separator;
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(singerPic);
        //歌曲映射
        String songUrl = "file:" + dir + separator + "song" + separator;
        registry.addResourceHandler("/song/**")
                .addResourceLocations(songUrl);
        //歌曲图片映射
        String songPic = "file:" + dir + separator + "img" + separator + "songPic" + separator;
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(songPic);
        //歌单图片映射
        String songListPic = "file:" + dir + separator + "img" + separator + "songListPic" + separator;
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(songListPic);
        //用户头像映射
        String userPic = "file:" + dir +separator + "img" + separator + "userPic" + separator;
        registry.addResourceHandler("/img/userPic/**")
                .addResourceLocations(userPic);
    }
}
