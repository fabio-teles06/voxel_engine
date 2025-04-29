package com.fabioteles.cube.core;

import java.io.InputStream;

import com.fabioteles.cube.render.Shader;
import com.fabioteles.cube.resource.ResourceManager;
import com.fabioteles.cube.resource.SceneLoader;

public class Engine implements Runnable {
    public static final int TARGET_FPS = 60; // 0 to disable
    public static final int TARGET_UPS = 20;

    private final Window window;
    private final Thread gameLoopThread;
    private final Timer timer;

    public Engine(String title, int width, int height) {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(title, width, height, true);
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    protected void init() throws Exception {
        window.init();
        timer.init();

        ResourceManager.registerLoader("shader", (name) -> {
            try {
                InputStream vs = ResourceManager.getInputStream(name + ".vs");
                InputStream fs = ResourceManager.getInputStream(name + ".fs");
                return new Shader(vs, fs, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        SceneLoader.loadSceneResources("scene1.json");
    }

    protected void gameLoop() {
        float ellapsedTime;
        float deltaTime = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            ellapsedTime = timer.getEllapsedTime();
            deltaTime += ellapsedTime + deltaTime;

            while (deltaTime >= interval) {
                update(interval);
                deltaTime -= interval;
            }

            render();

            if (window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    protected void update(float interval) {
        // Update game logic here
    }

    Shader test;
    protected void render() {
        if (test == null) {
            test = (Shader) ResourceManager.load("test", "shader", "shaders/test");
        }
        test.bind();

        test.unbind();
        window.update();
    }

    public void cleanup() {
        ResourceManager.disposeAll();
        window.cleanup();
    }
}
