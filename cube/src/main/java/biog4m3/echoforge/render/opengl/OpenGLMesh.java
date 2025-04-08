package biog4m3.echoforge.render.opengl;

import biog4m3.echoforge.render.Mesh;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class OpenGLMesh extends Mesh {
    private int vao, vbo, ebo;
    private int vertexCount;
    private int vertexSize; // Em floats (ex: 2 = x, y)

    private float[] vertexData; // Armazena os dados dos vértices

    @Override
    public void upload(float[] vertices, int[] indices, int vertexSize) {
        this.vertexCount = indices.length;
        this.vertexSize = vertexSize;
        this.vertexData = vertices.clone();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // VBO
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);
        updateVertices(vertices); // preenche dados

        // EBO
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(indexBuffer);

        // Layout: 0 = posição (x, y) ou (x, y, z)
        glVertexAttribPointer(0, vertexSize, GL_FLOAT, false, vertexSize * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    @Override
    public void updateVertices(float[] vertices) {
        this.vertexData = vertices.clone(); // Atualiza armazenamento local
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        MemoryUtil.memFree(buffer);
    }

    @Override
    public void updateVertex(int index, float[] data) {
        if (data.length != vertexSize)
            throw new IllegalArgumentException("Tamanho errado");

        for (int i = 0; i < vertexSize; i++) {
            vertexData[index * vertexSize + i] = data[i];
        }

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertexSize);
        buffer.put(data).flip();
        glBufferSubData(GL_ARRAY_BUFFER, (long) index * vertexSize * Float.BYTES, buffer);
        MemoryUtil.memFree(buffer);
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    @Override
    public void destroy() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }
}
