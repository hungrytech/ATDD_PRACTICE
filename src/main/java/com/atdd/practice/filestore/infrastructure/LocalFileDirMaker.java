package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.exception.ApplicationException;
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
        if (createMemberDir(memberDirPath)) {
            return memberDirPath;
        }

        return null;
    }

    private boolean createMemberDir(String memberDirPath) {
        File file = new File(memberDirPath);
        if (!file.exists()) {
            return file.mkdir();
        }

        throw new ApplicationException();
    }

    private String createMemberDirPath(String memberEmail) {
        return dirPath + extractAccountId(memberEmail) + "/";
    }

    private String extractAccountId(String memberEmail) {
        return memberEmail.substring(0, memberEmail.indexOf("@"));
    }
}
