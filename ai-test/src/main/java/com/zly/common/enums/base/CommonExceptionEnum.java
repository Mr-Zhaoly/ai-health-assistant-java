package com.zly.common.enums.base;

import com.zly.common.exception.base.ApiExceptionEnum;
import lombok.Getter;

@Getter
public enum CommonExceptionEnum implements ApiExceptionEnum {
    SUCCESS(200, "success"),
    NOTEXISTS(404, "Not Found"),
    NOAUTHORITY(401, "Unauthorized"),
    SERVERERROR(500, "System error, please try again later!"),
    SERVER_BUSY(503, "Server is busy, please try again later!"),
    BAD_REQUEST(400, "Wrong request"),
    ACCOUNT_ERROR(407, "Account exception, please contact the official customer service"),
    ;

    private final int responseCode;

    private final String reasonPhrase;

    CommonExceptionEnum(int responseCode, String reasonPhrase) {
        this.responseCode = responseCode;
        this.reasonPhrase = reasonPhrase;
    }


    @Override
    public String toString() {
        return Integer.toString(responseCode);
    }

    public static CommonExceptionEnum getStatusByReasonPhrase(String reasonPhrase) {
        for (CommonExceptionEnum statusEnum : CommonExceptionEnum.values()) {
            if (statusEnum.reasonPhrase.equals(reasonPhrase)) {
                return statusEnum;
            }
        }
        return null;
    }
}
