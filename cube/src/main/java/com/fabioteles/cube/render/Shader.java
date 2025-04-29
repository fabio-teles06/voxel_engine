package com.fabioteles.cube.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL20;

import com.fabioteles.cube.resource.Resource;

public class Shader implements Resource {
    private final int programId;

    public Shader(InputStream vertexStream, InputStream fragmentStream, String name) {
        String vertexCode = readAll(vertexStream);
        String fragmentCode = readAll(fragmentStream);

        int vertexShader = compileShader(vertexCode, GL20.GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentCode, GL20.GL_FRAGMENT_SHADER);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShader);
        GL20.glAttachShader(programId, fragmentShader);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            String log = GL20.glGetProgramInfoLog(programId);
            throw new RuntimeException("Erro ao linkar shader '" + name + "': " + log);
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        System.out.println("Shader compilado com sucesso: " + name);
    }

    private String readAll(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line).append("\n");
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler shader", e);
        }
    }

    private int compileShader(String source, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
            String log = GL20.glGetShaderInfoLog(shader);
            throw new RuntimeException("Erro ao compilar shader: " + log);
        }

        return shader;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    @Override
    public void dispose() {
        GL20.glDeleteProgram(programId);
        System.out.println("Shader destruído");
    }

    public int getProgramId() {
        return programId;
    }
}
