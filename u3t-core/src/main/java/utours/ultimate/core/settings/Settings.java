package utours.ultimate.core.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Settings {

    private final Properties properties;
    private final List<SettingsProperty> settingsProperties;

    public Settings() {
        this.properties = new Properties();
        this.settingsProperties = new ArrayList<>();
    }

    public String getValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public List<SettingsProperty> getSettingsProperties() {
        return settingsProperties;
    }

    public Properties getProperties() {
        return properties;
    }

}
