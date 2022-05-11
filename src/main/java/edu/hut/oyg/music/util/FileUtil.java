package edu.hut.oyg.music.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {

    public static final String userDir = System.getProperty("user.dir");
    public static final String separator = System.getProperty("file.separator");

    public static boolean saveFile(MultipartFile uploadFile,String filePath) {
        File saveFile = new File(filePath);
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            log.warn("save file fail");
            return false;
        }
        return true;
    }

    /**
     *
     * @param originalName 原始文件名
     * @return String[0] 文件名，String[1] 文件后缀(包括.)
     */
    public static String[] splitFileSuffix(@NonNull String originalName) {
        int dotIndex = originalName.lastIndexOf(".");
        String[] res = new String[2];
        res[0] = originalName.substring(0,dotIndex);
        res[1] = originalName.substring(dotIndex,originalName.length());
        return res;
    }

    public static String addTimeMillis(String fullName) {
        String[] splitName = splitFileSuffix(fullName);
        return splitName[0] + System.currentTimeMillis() + splitName[1];
    }

    /**
     *
     * @param path 数据库中的文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        String[] split = path.split("/");
        StringBuilder absolutePath = new StringBuilder();
        absolutePath.append(userDir);
        for (int i = 1; i < split.length; i++) {
            absolutePath.append(separator);
            absolutePath.append(split[i]);
        }
        File file = new File(absolutePath.toString());
        boolean delete = false;
        if (file.exists()) {
            delete = file.delete();
        }
        return delete;
    }

}
