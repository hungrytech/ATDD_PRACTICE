package com.atdd.practice.filestore.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import com.atdd.practice.common.config.TestFileDirSetting;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

public class FileAcceptanceTest extends AcceptanceTest {

    @Value("${file.dir-path}")
    String testFileDir;

    @AfterEach
    void cleanUpTestFileDir() throws IOException {
        TestFileDirSetting.executeDirCleanUp(ResourceUtils.getFile(testFileDir).getAbsolutePath());
    }
}
