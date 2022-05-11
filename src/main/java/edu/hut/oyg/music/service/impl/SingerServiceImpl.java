package edu.hut.oyg.music.service.impl;

import edu.hut.oyg.music.dao.SingerMapper;
import edu.hut.oyg.music.dto.admin.SingerDTO;
import edu.hut.oyg.music.entity.Singer;
import edu.hut.oyg.music.service.SingerService;
import edu.hut.oyg.music.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    SingerMapper mapper;

    @Override
    public boolean insert(Singer singer) {
        return mapper.insert(singer) == 1;
    }

    @Override
    public boolean delete(Integer id) {
        return mapper.delete(id) == 1;
    }

    @Override
    public boolean update(Singer singer) {
        return mapper.update(singer) == 1;
    }

    @Override
    public Singer selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Singer> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<Singer> selectByName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public List<SingerDTO> selectDTO() {
        return mapper.selectDTO();
    }

    /**
     *
     * @param uploadFile 上传的图片
     * @return 返回数据库中需要保存的路径
     */
    @Override
    public String saveSingerPic(MultipartFile uploadFile) {
        String fileName = FileUtil.addTimeMillis(uploadFile.getOriginalFilename());
        String filePath = FileUtil.userDir + FileUtil.separator + "img" + FileUtil.separator + "singerPic" + FileUtil.separator + fileName;
        String pic = "/img/singerPic/" + fileName;
        boolean success = FileUtil.saveFile(uploadFile,filePath);
        return success ? pic : null;
    }

}
