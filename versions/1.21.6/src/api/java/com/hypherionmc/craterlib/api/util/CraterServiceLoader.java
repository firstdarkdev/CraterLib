package com.hypherionmc.craterlib.api.util;

import java.util.List;
import java.util.ServiceLoader;

public final class CraterServiceLoader {

    public static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public static <T> T load(Class<T> tClass) {
        return ServiceLoader.load(tClass, loader)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + tClass.getName()));
    }

    public static <T> List<T> loadAll(Class<T> tClass) {
        return ServiceLoader.load(tClass, loader).stream().map(ServiceLoader.Provider::get).toList();
    }
}
