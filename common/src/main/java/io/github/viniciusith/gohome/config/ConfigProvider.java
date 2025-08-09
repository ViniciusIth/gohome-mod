package io.github.viniciusith.gohome.config;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class ConfigProvider implements SimpleConfig.DefaultConfig {

    private String configContents = "";

    public List<SimpleEntry<String, ?>> getConfigsList() {
        return configsList;
    }

    private final List<SimpleEntry<String, ?>> configsList = new ArrayList<>();

    public void addKeyValuePair(SimpleEntry<String, ?> keyValuePair, String comment) {
        configsList.add(keyValuePair);
        configContents += keyValuePair.getKey() + "=" + keyValuePair.getValue() + " #" + comment + " | default: " + keyValuePair.getValue() + "\n";
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}