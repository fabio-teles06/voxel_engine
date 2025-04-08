package biog4m3.echoforge.render.opengl;

import org.lwjgl.opengl.GL;

import biog4m3.echoforge.render.RenderBackend;
import biog4m3.echoforge.util.Debug;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLRenderer implements RenderBackend {
    @Override
    public void init() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        Debug.log("[OpenGL] Renderer initialized");
    }

    @Override
    public void beginFrame() {
        // Pode configurar viewport ou outros estados
    }

    @Override
    public void endFrame() {
        // Pode usar isso no futuro pra profiling ou pós-processamento
    }

    @Override
    public void clear(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void shutdown() {
        Debug.log("[OpenGL] Renderer shutting down");
    }
}
