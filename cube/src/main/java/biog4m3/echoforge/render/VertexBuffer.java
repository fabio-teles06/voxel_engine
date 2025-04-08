package biog4m3.echoforge.render;

public abstract class VertexBuffer {
    public abstract void setData(float[] data);
    public abstract void updateVertex(int index, float[] vertexData);
    public abstract void bind();
    public abstract void unbind();
    public abstract void destroy();
}
