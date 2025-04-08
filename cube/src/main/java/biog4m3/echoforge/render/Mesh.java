package biog4m3.echoforge.render;

public class Mesh {
    private final VertexArray vao;
    private final VertexBuffer vbo;
    private final IndexBuffer ebo;

    public Mesh(VertexArray vao, VertexBuffer vbo, IndexBuffer ebo) {
        this.vao = vao;
        this.vbo = vbo;
        this.ebo = ebo;
    }

    public void bind() {
        vao.bind();
        vbo.bind();
        ebo.bind();
    }

    public void unbind() {
        ebo.unbind();
        vbo.unbind();
        vao.unbind();
    }

    public void draw() {
        vao.bind();
        ebo.bind();
        // Assuming the index buffer is bound, we can draw the elements
        // The draw call would typically be something like:
        // glDrawElements(GL_TRIANGLES, ebo.getCount(), GL_UNSIGNED_INT, 0);
    }

    public void destroy() {
        vao.destroy();
        vbo.destroy();
        ebo.destroy();
    }

    public void updateVertex(int index, float[] vertexData) {
        vbo.updateVertex(index, vertexData);
    }
}
