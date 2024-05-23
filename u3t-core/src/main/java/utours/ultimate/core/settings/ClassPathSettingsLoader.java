package utours.ultimate.core.settings;

import utours.ultimate.core.ClassPathResource;

import java.io.IOException;

public class ClassPathSettingsLoader implements SettingsLoader {

    private String settingsNameFile;

    public ClassPathSettingsLoader() {
        this(SettingsLoader.SETTINGS_NAME_FILE);
    }

    public ClassPathSettingsLoader(String settingsNameFile) {
        this.settingsNameFile = settingsNameFile;
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
            var stream = ClassPathResource.getResourceAsStream(settingsNameFile);
            settings.getProperties().load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
