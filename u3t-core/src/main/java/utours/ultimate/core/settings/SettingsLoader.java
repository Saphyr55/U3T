package utours.ultimate.core.settings;

public interface SettingsLoader {

    String SETTINGS_NAME_FILE = "settings.properties";

    void load(Settings settings);

    String getSettingsNameFile();

    void setSettingsNameFile(String nameFile);

}
