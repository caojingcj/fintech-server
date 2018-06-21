package com.fintech.service;

import java.util.Date;

import com.fintech.enm.OrderStatusEnum;
import com.fintech.util.DateUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println(DateUtils.format(new Date(), "yyyy-MM-dd"));
    }
}
