package net.mysticcreations.lib.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.config.specification.ConfigField;
import net.mysticcreations.lib.config.specification.ConfigSpecification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Responsible for Parsing ConfigsSpecification Object to json and putting data from json to ConfigSpecifications
public class ConfigSpecParser {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path configPath;
    private final ConfigFileType configType;

    public Map<String, ConfigField<?>> configMap;
    public final ResourceLocation id;

    public ConfigSpecParser(ResourceLocation id, ConfigFileType configType, boolean subfolder) {
        this.id = id;
        this.configMap = new HashMap<>();
        String fileExtension = "";
        switch (configType) {
            case JSON:
                fileExtension = ".json";
            case  TOML:
                fileExtension = ".toml";
        }
        if (subfolder) {
            this.configPath = Paths.get("config", id.getNamespace(), id.getPath() + fileExtension);
        } else {
            this.configPath = Paths.get("config", id.getNamespace() + "_" + id.getPath() + fileExtension);
        }
        this.configType = configType;
    }

    public void parseConfigSpecification(ConfigSpecification spec) {
        Class<? extends ConfigSpecification> configSpecClass = spec.getClass();
        for (Field field : configSpecClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getType().isAssignableFrom(ConfigField.class)) {
                String fieldName = field.getName();
                Object value = null;
                try {
                    value = field.get(spec);
                } catch (IllegalAccessException e) {
                    // ignore it
                }
                if (value != null) {
                    this.configMap.put(fieldName, (ConfigField<?>) value);
                }
            }
        }
    }

    public void writeToConfig() {
        Map<String, Object> encodedMap = this.createEncodedMap();

        String configFileContent = "";

        // get the file contents
        switch(this.configType) {
            case JSON:
                configFileContent = gson.toJson(encodedMap);
            case TOML:

                TomlMapper mapper = new TomlMapper();
                try {
                    configFileContent = mapper.writeValueAsString(this.configMap);
                } catch (JsonProcessingException ignored) {
                    // YEAH WE SHOULD NOT BE IGNORING THIS ERROR PROB...
                }
        }
        File file = this.configPath.toFile();


        try (FileWriter writer = new FileWriter(file)) {
            writer.write(configFileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean loadConfig() throws JsonProcessingException {
        File file = this.configPath.toFile();
        if (!file.exists()) {
            return false;
        }
        // read the file
        String configFileContent;
        try {
            configFileContent = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> encodedMap = switch (this.configType) {
            case JSON -> {
                Type type = new TypeToken<Map<String, Object>>() {
                }.getType();
                yield gson.fromJson(configFileContent, type);
            }
            case TOML -> {
                TomlMapper mapper = new TomlMapper();
                yield mapper.readValue(configFileContent, new TypeReference<>() {
                });
            }
        };

        // put the encoded values back into the configMap
        loadFromEncodedMap(encodedMap);
        return true;
    }

    private void loadFromEncodedMap(Map<String, Object> map) {
        for (String key : map.keySet()) {
            ConfigField<?> field = configMap.get(key);
            if (field != null) {
                field.setFieldValue(map.get(key));
            }
        }
    }

    public Map<String, Object> createEncodedMap() {
        Map<String, Object> map = new HashMap<>();
        for (String key :configMap.keySet()) {
            ConfigField<?> field = configMap.get(key);
            map.put(key, field.getValue());
        }
        return map;
    }

}
