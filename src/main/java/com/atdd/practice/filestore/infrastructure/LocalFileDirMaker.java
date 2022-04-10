package com.atdd.practice.filestore.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LocalFileDirMaker {

    private final String dirPath;

    public LocalFileDirMaker(@Value("${file.dir-path}") String dirPath) {
        this.dirPath = dirPath;
    }

    public String createTargetMemberDir(String memberEmail) {
        String memberDirPath = createMemberDirPath(memberEmail);
        createMemberDir(memberDirPath);
        return memberDirPath;
    }

    private void createMemberDir(String memberDirPath) {
        File memberDir = new File(memberDirPath);
        memberDir.mkdir();
    }

    private String createMemberDirPath(String memberEmail) {
        return dirPath + "/" + extractAccountId(memberEmail) + "/";
    }

    private String extractAccountId(String memberEmail) {
        return memberEmail.substring(0, memberEmail.indexOf("@"));
    }
}
