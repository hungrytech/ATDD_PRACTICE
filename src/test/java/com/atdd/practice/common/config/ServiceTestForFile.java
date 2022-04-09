package com.atdd.practice.common.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static com.atdd.practice.filestore.fixture.TestFilePath.TEST_UPLOAD_FILES_DIR_PATH;

public class ServiceTestForFile extends ServiceTest {

    @BeforeEach
    void createTestFileDir() {
        TestFileDirSetting.createDir(TEST_UPLOAD_FILES_DIR_PATH);
    }

    @AfterEach
    void cleanUpTestFileDir() throws IOException {
        TestFileDirSetting.executeDirCleanUp(TEST_UPLOAD_FILES_DIR_PATH);
    }
}
