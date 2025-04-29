package com.fabioteles.cube.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static class RefCounted<T extends Resource> {
        private T resource;
        private int refCount;

        RefCounted(T resource){
            this.resource = resource;
            this.refCount = 1;
        }
    }

    private static final Map<String, RefCounted<? extends Resource>> resources = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static synchronized <T extends Resource> T load(String path, ResourceLoader<T> loader){
        String normalized = normalizePath(path);
        RefCounted<? extends Resource> entry = resources.get(normalized);
        if (entry != null){
            entry.refCount++;
            return (T) entry.resource;
        }

        T res = loader.load(getInputStream(path), normalized);
        resources.put(normalized, new RefCounted<>(res));
        return res;
    }

    public static synchronized void release(String path) {
        String normalized = normalizePath(path);
        RefCounted<? extends Resource> entry = resources.get(normalized);
        if (entry == null) return;

        entry.refCount--;
        if (entry.refCount <= 0) {
            entry.resource.dispose();
            resources.remove(normalized);
        }
    }

    private static InputStream getInputStream(String path) {
        // Tenta carregar de /resources (classpath)
        InputStream stream = ResourceManager.class.getClassLoader().getResourceAsStream(path);
        if (stream != null) return stream;

        // Se não estiver em resources, tenta da pasta local (onde o jar está)
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + path);
        }
    }

    private static String normalizePath(String path) {
        return path.replace("\\", "/");
    }

    public static synchronized void disposeAll() {
        for (RefCounted<? extends Resource> entry : resources.values()) {
            entry.resource.dispose();
        }
        resources.clear();
    }

    public interface ResourceLoader<T extends Resource> {
        T load(InputStream stream, String path);
    }
}
