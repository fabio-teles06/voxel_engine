package biog4m3.echoforge.render;

public class RenderManager {
    private static RenderBackend backend;

    public static void use(RenderBackend renderBackend) {
        backend = renderBackend;
        backend.init();
    }

    public static void beginFrame() {
        if (backend != null) {
            backend.beginFrame();
        }
    }

    public static void endFrame() {
        if (backend != null) {
            backend.endFrame();
        }
    }

    public static void clear(float r, float g, float b, float a) {
        if (backend != null) {
            backend.clear(r, g, b, a);
        }
    }

    public static void shutdown() {
        if (backend != null) {
            backend.shutdown();
        }
    }

    public static RenderBackend getBackend() {
        return backend;
    }
}
