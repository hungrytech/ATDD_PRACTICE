package com.atdd.practice.common.config;

import com.atdd.practice.filestore.infrastructure.LocalFileDirMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@TestConfiguration
public class FileAcceptanceTestConfig {

    @Bean
    @Primary
    public LocalFileDirMaker testLocalFileDirMaker(@Value("${file.dir-path}") String testDirPath) throws FileNotFoundException {
        return new LocalFileDirMaker(ResourceUtils.getFile(testDirPath).getAbsolutePath());
    }
}
