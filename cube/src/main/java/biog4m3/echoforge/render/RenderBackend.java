package biog4m3.echoforge.render;

public interface RenderBackend {
    void init();

    void beginFrame();

    void endFrame();

    void clear(float r, float g, float b, float a);

    void shutdown();
}
