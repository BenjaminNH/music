package edu.hut.oyg.music.service.impl;

import edu.hut.oyg.music.dao.SongMapper;
import edu.hut.oyg.music.entity.Song;
import edu.hut.oyg.music.service.SongService;
import edu.hut.oyg.music.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongMapper mapper;

    @Override
    public boolean insert(Song song) {
        return mapper.insert(song) == 1;
    }

    @Override
    public boolean delete(Integer id) {
        return mapper.delete(id) == 1;
    }

    @Override
    public boolean update(Song song) {
        return mapper.update(song) == 1;
    }

    @Override
    public List<Song> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<Song> selectBySingerId(Integer singerId,String name) {
        return mapper.selectBySingerId(singerId,name);
    }

    @Override
    public List<Song> selectByName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public Song selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public String saveSong(MultipartFile uploadFile) {
        String fileName = FileUtil.addTimeMillis(uploadFile.getOriginalFilename());
        String filePath = FileUtil.userDir + FileUtil.separator + "song" + FileUtil.separator + fileName;
        String url = "/song/" + fileName;
        boolean success = FileUtil.saveFile(uploadFile,filePath);
        return success ? url : null;
    }


    public String saveSongPic(MultipartFile uploadFile) {
        String fileName = FileUtil.addTimeMillis(uploadFile.getOriginalFilename());
        String filePath = FileUtil.userDir + FileUtil.separator + "img" + FileUtil.separator + "songPic" + FileUtil.separator + fileName;
        String pic = "/img/songPic/" + fileName;
        boolean success = FileUtil.saveFile(uploadFile,filePath);
        return success ? pic : null;
    }
}
