package biog4m3.echoforge.render;

public abstract class VertexArray {
    public abstract void addVertexBuffer(VertexBuffer buffer, int vertexSize);
    public abstract void setIndexBuffer(IndexBuffer buffer);
    public abstract void bind();
    public abstract void unbind();
    public abstract void destroy();
}
