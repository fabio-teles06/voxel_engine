package biog4m3.cube;

import java.io.IOException;

public class Cube {
    private final int tickRate = 20;
    private final long timePerTick = 1_000_000_000L / tickRate;

    private boolean running = false;

    public void run() {
        this.running = true;
        try {
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    private void init() throws IOException {
        // Initialize game resources here
        System.out.println("Game initialized.");
        runGameLoop();
    }

    private void runGameLoop() throws IOException {
        long previousTime = System.nanoTime();
        double delta = 0;

        long timer = System.currentTimeMillis();
        int frames = 0;
        int ticks;

        int totalTicks = 0;

        while (running) {
            
        }
    }

    private void doTick() {

    }

    private void renderFrame() {
        
    }

    private void shutdown() {
        running = false;
    }
}
