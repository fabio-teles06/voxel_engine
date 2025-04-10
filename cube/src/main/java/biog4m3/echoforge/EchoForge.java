package biog4m3.echoforge;

import java.io.IOException;

import biog4m3.echoforge.util.Debug;

public class EchoForge {
    private static final int TICK_RATE = 20;
    private static final long NANOS_PER_TICK = 1_000_000_000L / TICK_RATE;
    private static final int SLEEP_MILLIS = 1;
    private static final long MAX_ELAPSED_TIME = 1_000_000_000L;
    private static final String EngineTAG = "Echoforge";

    private final GameContext context = new GameContext();
    private boolean running = false;
    private Window window;
    public void run() {
        try {
            this.init();
            runGameLoop();
        } catch (IOException e) {
            e.printStackTrace();
            Debug.error(EngineTAG, "Failed to initialize EchoForge: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Debug.error(EngineTAG, "Game loop interrupted: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void init() throws IOException, InterruptedException {
        Debug.setEnabled(true);
        Debug.log(EngineTAG, "Initializing");

        this.window = new Window("EchoForge", 800, 600);

        this.running = true;
    }

    private void runGameLoop() throws IOException, InterruptedException {
        long lastFrameTime = System.nanoTime();
        long startTime = lastFrameTime;
        double tickDelta = 0.0;

        int frames = 0;
        int ticks = 0;
        long lastSecond = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            long elapsed = now - lastFrameTime;
            lastFrameTime = now;

            if (elapsed < 0 || elapsed > MAX_ELAPSED_TIME) {
                elapsed = NANOS_PER_TICK;
            }

            tickDelta += (double) elapsed / NANOS_PER_TICK;

            while (tickDelta >= 1) {
                doTick();
                context.tickCount++;
                tickDelta -= 1;
                ticks++;
            }

            context.lifeTime = now - startTime;

            if (!window.isMinimized()) {
                doFrame();
                frames++;
            }

            if (System.currentTimeMillis() - lastSecond >= 1000) {
                context.currentFPS = frames;
                context.currentTPS = ticks;

                Debug.log(EngineTAG, "FPS: " + context.currentFPS + ", TPS: " + context.currentTPS);

                frames = 0;
                ticks = 0;
                lastSecond += 1000;
            }

            window.pollEvents();

            Thread.sleep(SLEEP_MILLIS);
        }
    }

    private void doTick() {
        if (window.shouldClose()) {
            shutdown();
        }

        //systemManager.update(context);
    }

    private void doFrame() {

        window.swapBuffers();
    }

    private void shutdown() {
        if (!running)
            return;

        running = false;
        if (window != null) {
            window.destroy();
        }
    }
}
