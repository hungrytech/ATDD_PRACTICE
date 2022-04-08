package com.atdd.practice.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestFileDirCleanUp {

    public static void execute(String fileDir) throws IOException {
        Files.walk(Paths.get(fileDir))
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
