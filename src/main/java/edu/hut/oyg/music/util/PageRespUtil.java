package edu.hut.oyg.music.util;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class PageRespUtil {
    public static Map<String,Object> getRes(PageInfo info,String dataName) {
        Map<String,Object> res = new HashMap<>();
        res.put("totalPage",info.getPages());
        res.put("totalRecord",info.getTotal());
        res.put("pageNo",info.getPageNum());
        res.put("pageSize",info.getPageSize());
        res.put(dataName,info.getList());
        return res;
    }
}
