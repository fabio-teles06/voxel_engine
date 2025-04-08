package biog4m3.cube;

import java.io.IOException;

public class Cube {
    private final int TICK_RATE = 20;
    private final long NANOS_PER_TICK = 1_000_000_000L / TICK_RATE;
    private static final int SLEEP_MILLIS = 1;

    private final GameContext context = new GameContext();
    private boolean running = false;

    public void run() {
        this.running = true;
        try {
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Game interrupted: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void init() throws IOException, InterruptedException {
        // Initialize game resources here
        System.out.println("Game initialized.");
        runGameLoop();
    }

    private void runGameLoop() throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        long lastFrameTime = startTime;
        double delta = 0;

        int frames = 0;
        int ticks = 0;
        long lastSecondTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            delta += elapsedTime;

            while (delta >= NANOS_PER_TICK) {
                context.tickCount++;
                doTick(context);
                delta -= NANOS_PER_TICK;
                ticks++;
            }

            context.lifeTime = currentTime - startTime;

            doFrame(context);
            frames++;

            if (System.currentTimeMillis() - lastSecondTime >= 1000) {
                context.currentFPS = frames;
                context.currentTPS = ticks;

                frames = 0;
                ticks = 0;
                lastSecondTime += 1000;
            }

            Thread.sleep(SLEEP_MILLIS);
        }
    }

    private void doTick(GameContext context) {
        // Update game state here
        System.out.println("Tick: " + context.currentFPS);
        // Example: context.player.update();
        // Example: context.world.update();
        // Example: context.network.update();
    }

    private void doFrame(GameContext context) {

    }

    private void shutdown() {
        running = false;
    }
}
