package com.jobproj.jobportal.util;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {
    }

    public static String next(String prefix) {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        return prefix + suffix;
    }
}
