package com.architecture.admin.libraries.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 ******* 오류코드 작성 규칙 ******
 *      - 영문4자 와  숫자4자리로 구성 ex) ELGI-9999
 *      - 앞4자리 영문은 기능이나 페이지를 알 수 있도록 작성
 *      - 뒤4자리 숫자는 아래 규칙에 따라 분류
 *       오류번호   /   설명
 *        1000    =   정상
 *        2xxx    =   필수값 없음
 *        3xxx    =   유효성오류
 *        4xxx    =   sql구문오류
 *        5xxx    =   DB데이터오류
 *        6xxx    =   파일오류
 *        7xxx    =   권한오류
 *        9xxx    =   기타오류
 */
public enum CustomError {
    // EBAD : 유저의 잘못된 요청
    BAD_REQUEST("EBAD-3999", "lang.common.exception.bad.request")                            // 잘못된 요청입니다.(bad request 공통)
    , BAD_REQUEST_PARAMETER_TYPE_MISMATCH("EBAD-3998", "lang.common.exception.bad.request")  // 잘못된 요청입니다.(메소드로 넘어오는 파라미터의 타입 미스매치등)
    , BAD_REQUEST_REQUIRED_VALUE("EBAD-3997", "lang.common.exception.bad.required.value")    // 필수값을 입력해주세요.

    // ESER : 서버 오류(SQL,DB)
    , SERVER_DATABASE_ERROR("ESER-5999", "lang.common.exception.server.database")   // 죄송합니다.서버에 문제가 발생했습니다.잠시후 다시 이용해주세요.
    , SERVER_SQL_ERROR("ESER-5998", "lang.common.exception.server.database")        // 죄송합니다.서버에 문제가 발생했습니다.잠시후 다시 이용해주세요.

    , MEMBER_IDX_ERROR("EADM-9999", "lang.member.exception.idx")            // 로그인 후 이용 가능합니다.
    , IDX_ERROR("EADM-9998", "IDX_ERROR")
    , TYPE_ERROR("EADM-9997", "TYPE_ERROR")

    // EOUT : 회원 탈퇴 관련 오류
    , NAVER_SOCIAL_OUT_FAIL("EOUT-9999", "lang.member.out.exception.naver_fail")     // 네이버 연동해제 실패
    , GOOGLE_SOCIAL_OUT_FAIL("EOUT-9998", "lang.member.out.exception.google_fail");   // 구글 연동해제 실패

    @Autowired
    MessageSource messageSource;
    private String code;
    private String message;

    CustomError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    public CustomError setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    @Component
    public static class EnumValuesInjectionService {

        @Autowired
        private MessageSource messageSource;

        // bean
        @PostConstruct
        public void postConstruct() {
            for (CustomError customError : EnumSet.allOf(CustomError.class)) {
                customError.setMessageSource(messageSource);
            }
        }
    }
}
