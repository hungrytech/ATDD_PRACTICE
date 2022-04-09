package com.atdd.practice.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestFileDirSetting {

    public static void executeDirCleanUp(String fileDir) throws IOException {
        Files.walk(Paths.get(fileDir))
                .map(Path::toFile)
                .forEach(File::delete);

        createDir(fileDir);
    }

    public static void createDir(String fileDir) {
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
