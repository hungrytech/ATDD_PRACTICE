package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.config.ServiceTestForFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.atdd.practice.filestore.fixture.TestFilePath.TEST_UPLOAD_FILES_DIR_PATH;
import static org.assertj.core.api.Assertions.assertThat;

class LocalFileDirMakerTest extends ServiceTestForFile {

    private LocalFileDirMaker localFileDirMaker;

    @BeforeEach
    void setUp() {
        localFileDirMaker = new LocalFileDirMaker(TEST_UPLOAD_FILES_DIR_PATH);
    }

    @DisplayName("회원아이디의 고유 로컬 디렉토리 생성 후 경로를 반환한다.")
    @Test
    void 디렉토리_생성_성공_회원_아이디() {
        // given
        String memberEmail = "xorals9448@gmail.com";

        // when
        String memberLocalDir = localFileDirMaker.createTargetMemberDir(memberEmail);

        // then
        assertThat(memberLocalDir).isEqualTo(TEST_UPLOAD_FILES_DIR_PATH + extractAccountId(memberEmail) + "/");
    }

    private String extractAccountId(String memberEmail) {
        return memberEmail.substring(0, memberEmail.indexOf("@"));
    }

}