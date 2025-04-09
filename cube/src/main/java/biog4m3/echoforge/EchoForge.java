package biog4m3.echoforge;

import java.io.IOException;

import biog4m3.echoforge.util.Debug;

public class EchoForge {
    private final int TICK_RATE = 20;
    private final long NANOS_PER_TICK = 1_000_000_000L / TICK_RATE;
    private static final int SLEEP_MILLIS = 1;

    private final GameContext context = new GameContext();
    private boolean running = false;
    private Window window;
    private SystemManager systemManager;

    public void run() {
        this.running = true;
        try {
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Debug.error("Game loop interrupted: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void init() throws IOException, InterruptedException {
        Debug.setEnabled(true);
        Debug.log("Initializing EchoForge...");
        this.window = new Window("EchoForge", 800, 600);
        this.systemManager = new SystemManager();
        
        //this.systemManager.init(context);
        
        runGameLoop();
    }

    private void runGameLoop() throws IOException, InterruptedException {
        final long maxElapsedTime = 1_000_000_000L; // 1 segundo
        long startTime = System.nanoTime();
        long lastFrameTime = startTime;
        double tickDelta = 0.0;

        int frames = 0;
        int ticks = 0;
        long lastSecondTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastFrameTime;
            
            if (elapsedTime < 0 || elapsedTime > maxElapsedTime) {
                elapsedTime = NANOS_PER_TICK;
            }

            lastFrameTime = currentTime;
            tickDelta += (double) elapsedTime / NANOS_PER_TICK;

            while (tickDelta >= 1) {
                context.tickCount++;
                doTick(context);
                tickDelta -= 1;
                ticks++;
            }

            context.lifeTime = currentTime - startTime;

            if (!window.isMinimized()) {
                doFrame(context);
                window.swapBuffers();
                frames++;
            }

            if (System.currentTimeMillis() - lastSecondTime >= 1000) {
                context.currentFPS = frames;
                context.currentTPS = ticks;

                frames = 0;
                ticks = 0;
                lastSecondTime += 1000;
            }

            window.pollEvents();

            Thread.sleep(SLEEP_MILLIS);
        }
    }

    private void doTick(GameContext context) {
        Debug.log("fps: " + context.currentFPS);
        if (window.shouldClose()) {
            shutdown();
        }
    }

    private void doFrame(GameContext context) {
    }

    private void shutdown() {
        running = false;
        window.destroy();
    }
}
