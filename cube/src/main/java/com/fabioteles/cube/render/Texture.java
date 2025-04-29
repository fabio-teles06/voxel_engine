package com.fabioteles.cube.render;

import com.fabioteles.cube.resource.Resource;

public class Texture implements Resource {
    private final String path;

    public Texture(String path) {
        this.path = path;

        System.out.println("Loading texture from: " + path);
    }

    @Override
    public void dispose(){
        System.out.println("Disposing texture: " + path);
        // Here you would typically release the texture from GPU memory
    }

    public String getPath() {
        return path;
    }
}
