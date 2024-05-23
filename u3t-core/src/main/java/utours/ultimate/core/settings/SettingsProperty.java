package utours.ultimate.core.settings;

public interface SettingsProperty {

    static SettingsProperty dataOf(String key, String value) {
        return new SettingsPropertyData(key, value);
    }

    String key();

    String value();

}
