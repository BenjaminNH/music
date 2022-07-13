package edu.hut.oyg.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hut.oyg.music.entity.Song;
import edu.hut.oyg.music.response.ResponseResult;
import edu.hut.oyg.music.response.ResponseResultFactory;
import edu.hut.oyg.music.service.SongService;
import edu.hut.oyg.music.constant.PageConstants;
import edu.hut.oyg.music.util.FileUtil;
import edu.hut.oyg.music.util.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SongController {

    //TODO: 修改为默认图片
    private static final String DEFAULT_PIC = "/img/songPic/2.png";

    @Autowired
    SongService service;

    @PostMapping("/song")
    public ResponseResult<Song> addSong(@RequestParam("file") MultipartFile uploadFile,
                                        Song song) {
        ResponseResult<Song> result;
        if (uploadFile.isEmpty()) {
            result = ResponseResultFactory.genFailResult(null,"empty file");
            return result;
        }
        String url = service.saveSong(uploadFile);
        if (url != null) {
            song.setUrl(url);
            song.setCreateTime(new Date());
            song.setUpdateTime(new Date());
            song.setPic(DEFAULT_PIC);
            boolean success = service.insert(song);
            if (success) {
                result = ResponseResultFactory.genSuccessResult(song,"add success");
            } else {
                FileUtil.deleteFile(url);
                result = ResponseResultFactory.genFailResult(null,"save fail");
            }
        } else {
            result = ResponseResultFactory.genFailResult(null,"uploadFile save fail");
        }
        return result;
    }

    @DeleteMapping("/{id}/song")
    public ResponseResult<Object> deleteSong(@PathVariable("id") Integer id) {
        Song song = service.selectById(id);
        if (!DEFAULT_PIC.equals(song.getPic())) {
            boolean deletedPic = FileUtil.deleteFile(song.getPic());
            if (!deletedPic)
                log.warn("delete songPic fail : {}",song.getPic());
        }
        boolean deletedSong = FileUtil.deleteFile(song.getUrl());
        if (!deletedSong)
            log.warn("delete songFile fail : {}",song.getUrl());
        boolean success = service.delete(id);
        ResponseResult<Object> result;
        if (success) {
            result = ResponseResultFactory.genSuccessResult(null,"delete success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"delete fail");
        }
        return result;
    }

    @PutMapping("/song")
    public ResponseResult<Song> updateSong(Song song) {
        song.setUpdateTime(new Date());
        boolean success = service.update(song);
        ResponseResult<Song> result;
        if (success) {
            song = service.selectById(song.getId());
            result = ResponseResultFactory.genSuccessResult(song,"update success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"update fail");
        }
        return result;
    }

    @PostMapping("/song/{id}/pic")
    public ResponseResult<String> updateSongPic(@PathVariable("id") Integer id,
                                                @RequestParam("file") MultipartFile uploadFile) {
        ResponseResult<String> result;
        Song song = service.selectById(id);
        String oldPath = song.getPic();
        if (!DEFAULT_PIC.equals(oldPath)) {
            boolean deleted = FileUtil.deleteFile(oldPath);
            if (!deleted) {
                log.warn("delete old songPic fail : {}",oldPath);
            }
        }
        String pic = service.saveSongPic(uploadFile);
        if (pic != null) {
            song.setPic(pic);
            song.setUpdateTime(new Date());
            boolean updated = service.update(song);
            if (updated) {
                result = ResponseResultFactory.genSuccessResult(pic, "update success");
            } else {
                result = ResponseResultFactory.genFailResult(null,"update fail");
            }
        } else {
            result = ResponseResultFactory.genFailResult(null,"save file fail");
        }
        return result;
    }

    @PostMapping("/{id}/song")
    public ResponseResult<String> updateSong(@PathVariable("id") Integer id,
                                             @RequestParam("file") MultipartFile uploadFile) {
        ResponseResult<String> result;
        Song song = service.selectById(id);
        String oldUrl = song.getUrl();
        boolean deleted = FileUtil.deleteFile(oldUrl);
        if (!deleted) {
            log.warn("delete old songPic fail : {}",oldUrl);
        }
        String url = service.saveSong(uploadFile);
        if (url != null) {
            song.setUpdateTime(new Date());
            song.setUrl(url);
            boolean updated = service.update(song);
            if (updated) {
                result = ResponseResultFactory.genSuccessResult(url,"update success");
            } else {
                result = ResponseResultFactory.genFailResult(null,"update fail");
            }
        } else {
            result = ResponseResultFactory.genFailResult(null,"save file sail");
        }
        return result;
    }

    @GetMapping(value = {"/song","/song/singer/{id}","/song/singer/{id}/{name}"})
    public ResponseResult<Map<String,Object>> selectList(@PathVariable(value = "id",required = false) Integer singerId,
                                                         @PathVariable(value = "name",required = false) String name,
                                                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                         @RequestParam(value = "pageSize",defaultValue = PageConstants.DEFAULT_PAGE_SIZE) Integer pageSize) {
        ResponseResult<Map<String,Object>> result;
        PageHelper.startPage(pageNo,pageSize);
        List<Song> songs = singerId == null ? service.selectAll() : service.selectBySingerId(singerId,name);
        if (songs != null) {
            PageInfo<Song> info = new PageInfo<>(songs);
            Map<String, Object> res = PageRespUtil.getRes(info,"songs");
            result = ResponseResultFactory.genSuccessResult(res,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @GetMapping("/{id}/song")
    public ResponseResult<Song> selectById(@PathVariable("id") Integer id) {
        ResponseResult<Song> result;
        Song song = service.selectById(id);
        if (song != null) {
            result = ResponseResultFactory.genSuccessResult(song,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }
}
