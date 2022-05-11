package edu.hut.oyg.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hut.oyg.music.entity.Song;
import edu.hut.oyg.music.entity.SongList;
import edu.hut.oyg.music.response.ResponseResult;
import edu.hut.oyg.music.response.ResponseResultFactory;
import edu.hut.oyg.music.service.SongListService;
import edu.hut.oyg.music.util.Const;
import edu.hut.oyg.music.util.FileUtil;
import edu.hut.oyg.music.util.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SongListController {

    @Autowired
    SongListService service;

    //TODO: 修改为默认图片
    private static final String DEFAULT_PIC = "/img/singerPic/2.png";

    @PostMapping("/songList")
    public ResponseResult<SongList> addSongList(SongList songList) {
        ResponseResult<SongList> result;
        songList.setPic(DEFAULT_PIC);
        boolean success = service.insert(songList);
        if (success) {
            result = ResponseResultFactory.genSuccessResult(songList,"add success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"add fail");
        }
        return result;
    }

    @DeleteMapping("/songList/{id}")
    public ResponseResult<Object> deleteSongList(@PathVariable("id") Integer id) {
        ResponseResult<Object> result;
        boolean delete = service.delete(id);
        if (delete) {
            result = ResponseResultFactory.genSuccessResult(null,"delete success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"delete fail");
        }
        return result;
    }

    @PutMapping("/songList")
    public ResponseResult<SongList> updateSongList(SongList songList) {
        ResponseResult<SongList> result;
        boolean update = service.update(songList);
        if (update) {
            songList = service.selectById(songList.getId());
            result = ResponseResultFactory.genSuccessResult(songList,"update success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"update fail");
        }
        return result;
    }

    @GetMapping(value = {"/songList","/songList/title/{title}","/songList/style/{style}"})
    public ResponseResult<Map<String,Object>> selectList(@PathVariable(value = "title",required = false) String title,
                                                         @PathVariable(value = "style",required = false) String style,
                                                         @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                         @RequestParam(value = "pageSize",defaultValue = Const.DEFAULT_PAGE_SIZE) Integer pageSize) {
        ResponseResult<Map<String,Object>> result;
        List<SongList> list;
        PageHelper.startPage(pageNo,pageSize);
        if (title == null && style == null) {
            list = service.selectAll();
        } else if (title != null && style == null) {
            list = service.selectByTitle(title);
        } else {
            list = service.selectByStyle(style);
        }
        PageInfo<SongList> info = new PageInfo<>(list);
        if (info.getList() != null) {
            result = ResponseResultFactory.genSuccessResult(PageRespUtil.getRes(info,"songList"),"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @GetMapping("/songList/{id}")
    public ResponseResult<SongList> selectById(@PathVariable("id") Integer id) {
        ResponseResult<SongList> result;
        SongList songList = service.selectById(id);
        if (songList != null) {
            result = ResponseResultFactory.genSuccessResult(songList,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @PostMapping("/songList/{id}/pic")
    public ResponseResult<SongList> updateSongListPic(@RequestParam("file") MultipartFile uploadFile,
                                                      @PathVariable("id") Integer id) {
        ResponseResult<SongList> result;
        SongList list = service.selectById(id);
        if (!DEFAULT_PIC.equals(list.getPic())) {
            boolean delete = FileUtil.deleteFile(list.getPic());
            if (!delete) {
                log.warn("delete file fail : {}",list.getPic());
            }
        }
        String pic = service.savePic(uploadFile);
        if (pic != null) {
            list.setPic(pic);
            boolean update = service.update(list);
            if (update) {
                result = ResponseResultFactory.genSuccessResult(list,"update success");
            } else {
                result = ResponseResultFactory.genFailResult(null,"update fail");
            }
        } else {
            result = ResponseResultFactory.genFailResult(null,"save file fail");
        }
        return result;
    }
}
