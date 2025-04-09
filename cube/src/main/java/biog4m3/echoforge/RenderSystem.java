package biog4m3.echoforge;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import biog4m3.echoforge.util.Debug;

public class RenderSystem implements ISystem {
    @Override
    public void init(GameContext context) {
        Debug.log("RenderSystem initialized.");

        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.1f, 1.0f);
    }

    @Override
    public void tick(GameContext context) {
        // Nenhuma lógica por tick neste exemplo
    }

    @Override
    public void render(GameContext context) {
        // Limpa tela e z-buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Exemplo: desenhar um triângulo simples em modo imediato
        glBegin(GL_TRIANGLES);
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex2f(-0.5f, -0.5f);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex2f(0.5f, -0.5f);
        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex2f(0.0f, 0.5f);
        glEnd();
    }

    @Override
    public void shutdown(GameContext context) {
        Debug.log("RenderSystem shutting down.");
    }
}
