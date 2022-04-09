package com.atdd.practice.filestore.fixture;

import java.io.File;
import java.nio.file.Paths;

public class TestFilePath {

    private static final String TEST_FILES_DIR_PATH = Paths.get("src", "test", "resources", "file")
            .toFile()
            .getAbsolutePath() + "/";

    public static final String TEST_UPLOAD_FILES_DIR_PATH = Paths.get("src", "test", "resources", "file", "test")
            .toFile()
            .getAbsolutePath() + "/";

    public static final File JPEG_IMAGE_FILE = new File(TEST_FILES_DIR_PATH + "image_test.jpeg");

    public static final File PNG_IMAGE_FILE = new File(TEST_FILES_DIR_PATH + "image_test.png");

    public static final File INVALID_EXTENSION_IMAGE_FILE = new File(TEST_FILES_DIR_PATH + "image_validate_test.mov");
}
