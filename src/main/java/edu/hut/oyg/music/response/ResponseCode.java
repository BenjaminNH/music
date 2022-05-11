package edu.hut.oyg.music.response;

public enum ResponseCode {

    SUCCESS(200),
    FAIL(400),
    NOT_FOUND(404);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
