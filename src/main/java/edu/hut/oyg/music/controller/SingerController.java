package edu.hut.oyg.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hut.oyg.music.dto.admin.SingerDTO;
import edu.hut.oyg.music.entity.Singer;
import edu.hut.oyg.music.response.ResponseResult;
import edu.hut.oyg.music.response.ResponseResultFactory;
import edu.hut.oyg.music.service.SingerService;
import edu.hut.oyg.music.util.Const;
import edu.hut.oyg.music.util.FileUtil;
import edu.hut.oyg.music.util.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SingerController {

    @Autowired
    SingerService service;

    @PostMapping("/singer")
    public ResponseResult<Singer> addSinger(Singer singer) {
        boolean success = service.insert(singer);
        ResponseResult<Singer> result;
        if (success) {
            result = ResponseResultFactory.genSuccessResult(singer,"add success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"add fail");
        }
        return result;
    }

    @DeleteMapping("/singer/{id}")
    public ResponseResult<Object> deleteSinger(@PathVariable("id") Integer id) {
        boolean success = service.delete(id);
        ResponseResult<Object> result;
        if (success) {
            result = ResponseResultFactory.genSuccessResult(null,"delete success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"delete fail");
        }
        return result;
    }

    @PutMapping("/singer")
    public ResponseResult<Singer> updateSinger(Singer singer) {
        boolean success = service.update(singer);
        ResponseResult<Singer> result;
        if (success) {
            singer = service.selectById(singer.getId());
            result = ResponseResultFactory.genSuccessResult(singer,"update success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"update fail");
        }
        return result;
    }


    @GetMapping(value = {"/singer","/{name}/singer"})
    public ResponseResult<Map<String,Object>> selectList(@PathVariable(value = "name",required = false) String name,
                                                           @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                           @RequestParam(value = "pageSize",defaultValue = Const.DEFAULT_PAGE_SIZE) Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Singer> singers = name == null ? service.selectAll() : service.selectByName(name);
        PageInfo<Singer> info = new PageInfo<>(singers);
        Map<String,Object> data = PageRespUtil.getRes(info,"singers");
        ResponseResult<Map<String,Object>> result;
        if (info.getList() != null) {
            result = ResponseResultFactory.genSuccessResult(data,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @GetMapping("/singer/{id}")
    public ResponseResult<Singer> selectById(@PathVariable("id") Integer id) {
        Singer singer = service.selectById(id);
        ResponseResult<Singer> result;
        if (singer != null) {
            result = ResponseResultFactory.genSuccessResult(singer,"get by id success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @GetMapping("/admin/singer-info")
    public ResponseResult<List<SingerDTO>> selectDTO() {
        ResponseResult<List<SingerDTO>> result;
        List<SingerDTO> singerDTO = service.selectDTO();
        if (singerDTO != null) {
            result = ResponseResultFactory.genSuccessResult(singerDTO,"get success");
        } else {
            result = ResponseResultFactory.genFailResult(null,"get fail");
        }
        return result;
    }

    @PostMapping("/singer/{id}/pic")
    public ResponseResult<String> updateSingerPic(@RequestParam("file") MultipartFile uploadFile,
                                                  @PathVariable("id") Integer id) {
        ResponseResult<String> result;
        if (uploadFile.isEmpty()) {
            result = ResponseResultFactory.genFailResult(null,"empty file");
            return result;
        }
        Singer singer = service.selectById(id);
        boolean deleted = FileUtil.deleteFile(singer.getPic());
        if (!deleted)
            log.warn("delete old singerPic fail : {}",singer.getPic());
        String pic = service.saveSingerPic(uploadFile);
        if (pic == null) {
            result = ResponseResultFactory.genFailResult(null,"save pic fail");
            return result;
        }
        singer.setPic(pic);
        boolean success = service.update(singer);
        if (!success) {
            FileUtil.deleteFile(pic);
            result = ResponseResultFactory.genFailResult(null,"update singerPic fail");
            return result;
        }
        result = ResponseResultFactory.genSuccessResult(singer.getPic(),"update success");
        return result;
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(format,true);
        binder.registerCustomEditor(Date.class,editor);
    }
}
