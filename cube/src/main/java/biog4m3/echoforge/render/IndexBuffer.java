package biog4m3.echoforge.render;

public abstract class IndexBuffer {
    public abstract void setData(int[] data);
    public abstract void bind();
    public abstract void unbind();
    public abstract void destroy();
    public abstract int getCount();
}
