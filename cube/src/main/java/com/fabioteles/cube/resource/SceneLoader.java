package com.fabioteles.cube.resource;

import java.io.IOException;
import java.io.InputStream;
//import org.json

public class SceneLoader {
    public static void loadSceneResources(String scenePath) {
        try (InputStream is = ResourceManager.getInputStream(scenePath)) {
            System.out.println("Carregando cena: " + scenePath);

            //JSON

            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar cena: " + scenePath, e);
        }
    }
}
