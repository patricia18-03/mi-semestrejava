package org.example.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    private String host;
    private int port;
    private String name;
    private String username;
    private String password;

    public ConfigLoader() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("config/database.json");
            JsonNode root = mapper.readTree(jsonFile);

            this.host = root.get("host").asText();
            this.port = root.get("port").asInt();
            this.name = root.get("name").asText();
            this.username = root.get("username").asText();
            this.password = root.get("password").asText();

        } catch (IOException e) {
            System.out.println("Erreur: fichier config/database.json introuvable");
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC";
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}