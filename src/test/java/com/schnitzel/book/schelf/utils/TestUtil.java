package com.schnitzel.book.schelf.utils;

import java.util.concurrent.Callable;

import org.junit.Assert;
import org.springframework.web.client.HttpClientErrorException;

import liquibase.pro.packaged.T;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {
    public static void doRun(Runnable action) {
        try {
            action.run();
        } catch (HttpClientErrorException e) {
            //Nothing to do
            log.warn("Error in doCall: " + e.getMessage());
        }
    }

    @SuppressWarnings("hiding")
    public static <T> T doCall(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception e) {
            log.warn("Error in doCall: " + e.getMessage());
            return null;
        }
    }

    public static void testError(int expectedStatus, Runnable actionToCheck) {
        HttpClientErrorException exception = null;
        try {
            actionToCheck.run();
        } catch (HttpClientErrorException e) {
            exception = e;
        }

        Assert.assertEquals(expectedStatus, exception.getRawStatusCode());
    }
}
