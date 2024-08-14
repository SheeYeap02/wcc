package com.intern.wcc.utils;

import java.lang.reflect.Field;

public class BeanCompare {

    public static boolean hasDifferences(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null || !obj1.getClass().equals(obj2.getClass())) {
            throw new IllegalArgumentException("Objects must be of the same class and not null.");
        }

        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            boolean hasDifferences = false;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);
                if (value1 != null ? !value1.equals(value2) : value2 != null) {
                    hasDifferences = true;
                    break;
                }
            }
            return hasDifferences;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
