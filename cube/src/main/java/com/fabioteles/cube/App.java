package com.fabioteles.cube;

import com.fabioteles.cube.core.Engine;

public class App {
    public static void main(String[] args) {
        Engine engine = new Engine("Cube", 800, 600);
        engine.start();
    }
}
