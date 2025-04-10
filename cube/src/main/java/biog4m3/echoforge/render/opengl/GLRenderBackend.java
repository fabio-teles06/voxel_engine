package biog4m3.echoforge.render.opengl;

import biog4m3.echoforge.render.RenderBackend;
import biog4m3.echoforge.util.Debug;

public class GLRenderBackend extends RenderBackend {
    private static final String TAG = "GLRenderBackend";

    public GLRenderBackend() {
        Debug.log(TAG, "Using OpenGL render backend");
    }

    @Override
    public void init() {
        // Initialize OpenGL context and settings here
        Debug.log(TAG, "OpenGL context initialized");
    }

    @Override
    public void clear(float r, float g, float b, float a) {
        // Clear the screen with the specified color
        Debug.log(TAG, String.format("Clearing screen with color: (%f, %f, %f, %f)", r, g, b, a));
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        // Set the viewport for rendering
        Debug.log(TAG, String.format("Setting viewport: (%d, %d, %d, %d)", x, y, width, height));
    }

    @Override
    public void shutdown() {
        // Clean up OpenGL resources here
        Debug.log(TAG, "Shutting down OpenGL render backend");
    }
}
