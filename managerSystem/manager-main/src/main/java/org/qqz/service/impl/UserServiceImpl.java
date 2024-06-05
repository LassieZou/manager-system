package org.qqz.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.qqz.common.Consts;
import org.qqz.common.Exception.MyException;
import org.qqz.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private Map<Integer, Set<String>> resources = new HashMap();
    @Value("${file.endpoints.path}")
    private String endpointsFilePath;
    @Value("${file.endpoints.fileName}")
    private String fileName;
    @PostConstruct
    public void init() throws Exception {

        File dir = new File(endpointsFilePath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(endpointsFilePath+fileName);
        if (!file.exists())
            file.createNewFile();
        refresh(file);

        // 创建文件变化监听器
        FileAlterationListener listener = new FileAlterationListener() {
            @Override
            public void onStart(FileAlterationObserver observer) {
                // 监控开始时的回调
            }

            @Override
            public void onDirectoryCreate(File directory) {
                // 目录创建时的回调
            }

            @Override
            public void onDirectoryChange(File directory) {
                // 目录变化时的回调
            }

            @Override
            public void onDirectoryDelete(File directory) {
                // 目录删除时的回调
            }

            @Override
            public void onFileCreate(File file) {
                // 文件创建时的回调
            }

            @Override
            public void onFileChange(File file) {
                // 文件变化时的回调
                log.info("file changed.");
                refresh(file);
            }

            @Override
            public void onFileDelete(File file) {
                // 文件删除时的回调
            }

            @Override
            public void onStop(FileAlterationObserver observer) {
                // 监控停止时的回调
            }
        };

        // 创建观察者
        FileAlterationObserver observer = new FileAlterationObserver(dir);
        observer.addListener(listener);

        // 创建监控器，并设置监控间隔（单位毫秒）
        FileAlterationMonitor monitor = new FileAlterationMonitor(5000);
        monitor.addObserver(observer);
        // 开始监控
        monitor.start();
    }

    private void refresh(File file){
        try {
            Map<Integer, Set<String>> resources = new HashMap();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] ss = line.split(Consts.SPLIT_SYM);
                Integer userId = Integer.valueOf(ss[0]);
                if (resources.get(userId) == null){
                    resources.put(userId,new HashSet<>());
                }
                resources.get(userId).add(ss[1]);
            }
            this.resources = resources;
        }catch (IOException e){
            log.error("refresh error.{}",e);
            throw new MyException("failed to read.");
        }

    }

    @Override
    public Boolean checkResource(Integer principal, String resource) {
       Set<String> res =  resources.get(principal);
        return res == null ? false : res.contains(resource);
    }
}
