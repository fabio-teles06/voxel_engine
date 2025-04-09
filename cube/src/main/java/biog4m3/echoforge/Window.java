package biog4m3.echoforge;

import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class Window {
    private static boolean glfwInitialized = false;
    private static final Set<Window> windows = new java.util.HashSet<>();

    private final long windowHandle;

    public Window(String title, int width, int height) {
        initGLFW();

        this.windowHandle = createWindow(title, width, height);
        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwShowWindow(windowHandle);

        windows.add(this);
    }

    private static void initGLFW() {
        if (glfwInitialized)
            return;
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwInitialized = true;
    }

    private long createWindow(String title, int width, int height) {
        long window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0)
            throw new RuntimeException("Failed to create the GLFW window");

        GL.createCapabilities();
        return window;
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public boolean isMinimized() {
        return GLFW.glfwGetWindowAttrib(windowHandle, GLFW.GLFW_ICONIFIED) == 1;
    }

    public void makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(windowHandle);
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public void destroy() {
        GLFW.glfwDestroyWindow(windowHandle);
        windows.remove(this);

        if (windows.isEmpty()) {
            GLFW.glfwTerminate();
            glfwInitialized = false;
        }
    }
}
