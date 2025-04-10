package biog4m3.echoforge.render;

public abstract class RenderBackend {
    public static final int RENDER_BACKEND_OPENGL = 0;

    // The render backend to use. This is set by the user in the config file.
    public abstract void init();
    public abstract void clear(float r, float g, float b, float a);
    public abstract void setViewport(int x, int y, int width, int height);
    public abstract void shutdown();
}
