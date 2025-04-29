package com.fabioteles.cube.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static class RefCounted<T extends Resource> {
        private final T resource;
        private int refCount;

        RefCounted(T resource) {
            this.resource = resource;
            this.refCount = 1;
        }

        void increment() {
            refCount++;
        }

        void decrement() {
            refCount--;
        }

        int getRefCount() {
            return refCount;
        }

        T getResource() {
            return resource;
        }
    }

    private static final Map<String, RefCounted<? extends Resource>> resources = new HashMap<>();
    private static final Map<String, ResourceLoader<? extends Resource>> loaders = new HashMap<>();

    public static <T extends Resource> void registerLoader(String type, ResourceLoader<T> loader) {
        if (loaders.containsKey(type)) {
            throw new IllegalArgumentException("Loader already registered for type: " + type);
        }
        loaders.put(type, loader);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T extends Resource> T load(String id, String type, String path) {
        String key = normalizePath(path);
        RefCounted<T> cached = (RefCounted<T>) resources.get(key);

        if (cached != null) {
            cached.increment();
            return cached.getResource();
        }

        ResourceLoader<T> loader = (ResourceLoader<T>) loaders.get(type);
        if (loader == null) {
            throw new RuntimeException("Loader não registrado para o tipo: " + type);
        }

        T resource = loader.load(path);
        resources.put(key, new RefCounted<>(resource));
        return resource;
    }

    public static synchronized void release(String path) {
        String key = normalizePath(path);
        RefCounted<? extends Resource> entry = resources.get(key);
        if (entry == null) return;

        entry.decrement();
        if (entry.getRefCount() <= 0) {
            entry.getResource().dispose();
            resources.remove(key);
        }
    }

    public static InputStream getInputStream(String path) {
        // Tenta carregar da pasta /resources (classpath)
        InputStream stream = ResourceManager.class.getClassLoader().getResourceAsStream(path);
        if (stream != null)
            return stream;

        // Tenta da pasta onde o .jar está
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + path, e);
        }
    }

    private static String normalizePath(String path) {
        return path.replace("\\", "/");
    }

    public static synchronized void disposeAll() {
        for (RefCounted<? extends Resource> entry : resources.values()) {
            entry.getResource().dispose();
        }
        resources.clear();
    }

    @FunctionalInterface
    public interface ResourceLoader<T extends Resource> {
        T load(String path);
    }
}