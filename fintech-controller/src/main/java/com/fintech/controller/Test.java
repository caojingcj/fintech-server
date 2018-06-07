package com.fintech.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@PropertySource("classpath:jdbc.properties")
@Component
public class Test {
	@Value("${test.msg}")
    private String ss;

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }
}
