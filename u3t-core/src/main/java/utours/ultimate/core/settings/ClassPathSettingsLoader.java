package utours.ultimate.core.settings;

import utours.ultimate.core.ClassPathResource;

import java.io.IOException;
import java.net.URL;

public class ClassPathSettingsLoader implements SettingsLoader {

    private String settingsNameFile;
    private Class<?> contextClass;

    public ClassPathSettingsLoader(Class<?> contextClass) {
        this(SettingsLoader.SETTINGS_NAME_FILE, contextClass);
    }

    public ClassPathSettingsLoader(String settingsNameFile, Class<?> contextClass) {
        this.settingsNameFile = settingsNameFile;
        this.contextClass = contextClass;
    }

    @Override
    public String getSettingsNameFile() {
        return settingsNameFile;
    }

    @Override
    public void setSettingsNameFile(String settingsNameFile) {
        this.settingsNameFile = settingsNameFile;
    }

    public void load(Settings settings) {
        try {
            URL url = contextClass.getResource(settingsNameFile);
            assert url != null;
            settings.getProperties().load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
