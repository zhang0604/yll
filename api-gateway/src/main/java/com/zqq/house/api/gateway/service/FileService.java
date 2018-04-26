package com.zqq.house.api.gateway.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 *
 * 文件上传
 *
 * Created By 张庆庆
 * DATA: 2018/4/10
 * TIME: 18:37
 */
@Service
public class FileService {

    @Value("${spring.filepath}")
    private String filePath;


    public List<String> getImgPath(List<MultipartFile> files){
        List<String> paths = Lists.newArrayList();
        //遍历上传的文件
        files.forEach(file->{
            //声明本地文件对象
            File localFile = null;
            if (!file.isEmpty()){
                try {
                    localFile = saveToLocal(file,filePath);
                    //上传的文件不为空
                    String last = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
                    paths.add(last);
                }catch (Exception e){
                    throw new IllegalArgumentException();
                }
            }
        });
        return paths;
    }

    /**
     * 保存文件至本地
     * @param file
     * @param filePath
     * @return
     * @throws IOException
     */
    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        //创建目标文件对象
        File newFile = new File(filePath+"/"+ Instant.now().getEpochSecond()+"/"+file.getOriginalFilename());
        if (!newFile.exists()){
            newFile.getParentFile().mkdir();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }
}
