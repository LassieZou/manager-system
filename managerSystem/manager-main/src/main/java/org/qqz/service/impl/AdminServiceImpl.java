package org.qqz.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.qqz.common.Consts;
import org.qqz.common.Exception.MyException;
import org.qqz.common.vo.ResourceInfo;
import org.qqz.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Value("${file.endpoints.path}")
    private String endpointsFilePath;
    @Value("${file.endpoints.fileName}")
    private String fileName;

    private volatile FileWriter fileWriter = null;

    private FileWriter getWriter(){
        try {
            synchronized (AdminServiceImpl.class){
                if (fileWriter == null){
                    File file = new File(endpointsFilePath+fileName);
                      fileWriter = new FileWriter(file);
                }
                return fileWriter;
            }
        } catch (IOException e) {
            log.error("file writer init error,{}",e);
            throw new MyException("no writer got.");
        }
    }

    @Override
    public void addUser(ResourceInfo resourceInfo) {
        FileWriter writer = getWriter();
        synchronized (writer){
            String buffer = resourceInfo.getEndpoint().stream().map(x-> resourceInfo.getUserId()+ Consts.SPLIT_SYM +x).collect(Collectors.joining("\n"));
            try {
                writer.write(buffer);
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                log.error("error while write files,",e);
                throw new MyException("failed to write");
            }
        }
    }
}
