package com.azka.demo.util;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;

/**
 * Collections of common utility functions.
 */
public final class CommonUtil {

    /**
     * Check if object is array or iterable object
     * will return false if null
     *
     * @param obj object to be check
     * @return true if object is array or iterable
     */
    public static boolean isArrayOrCollection(Object obj) {
        if (null != obj) {
            if (obj.getClass().isArray()) {
                return true;
            } else {
                return ClassUtils.isAssignable(obj.getClass(), Collection.class);
            }
        }
        return false;
    }
    
    public static String randomString() {
    	return RandomStringUtils.randomAlphanumeric(64);
    }
}