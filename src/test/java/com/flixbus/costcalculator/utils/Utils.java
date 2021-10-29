package com.flixbus.costcalculator.utils;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {
    public static String asString(Resource resource) {
        try (final var reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
