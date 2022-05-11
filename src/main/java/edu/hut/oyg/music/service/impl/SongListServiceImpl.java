package edu.hut.oyg.music.service.impl;

import edu.hut.oyg.music.dao.SongListMapper;
import edu.hut.oyg.music.entity.SongList;
import edu.hut.oyg.music.service.SongListService;
import edu.hut.oyg.music.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    SongListMapper mapper;

    @Override
    public boolean insert(SongList songList) {
        return mapper.insert(songList);
    }

    @Override
    public boolean delete(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public boolean update(SongList songList) {
        return mapper.update(songList);
    }

    @Override
    public List<SongList> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<SongList> selectByTitle(String title) {
        return mapper.selectByTitle(title);
    }

    @Override
    public List<SongList> selectByStyle(String style) {
        return mapper.selectByStyle(style);
    }

    @Override
    public SongList selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public String savePic(MultipartFile uploadFile) {
        String fileName = FileUtil.addTimeMillis(uploadFile.getOriginalFilename());
        String path = FileUtil.userDir + FileUtil.separator + "img" + FileUtil.separator + "songListPic" + FileUtil.separator + fileName;
        String pic = "/img/songListPic/" + fileName;
        boolean success = FileUtil.saveFile(uploadFile, path);
        return success ? pic : null;
    }

}
