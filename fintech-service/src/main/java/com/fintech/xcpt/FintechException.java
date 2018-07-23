package com.fintech.xcpt;

/**
 * Fintech业务异常
 * @author caojing
 */
public class FintechException extends Exception {

    private static final long serialVersionUID = 4339189403322326179L;

    public FintechException(String message) {
        super(message);
    }

}
