package com.atdd.practice.common.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @AfterEach
    void cleanUpTestFileDir() throws IOException {
        String testFileDir = System.getProperty("user.dir") + "/src/test/resources/file/test/";

        TestFileDirCleanUp.execute(testFileDir);
    }
}
