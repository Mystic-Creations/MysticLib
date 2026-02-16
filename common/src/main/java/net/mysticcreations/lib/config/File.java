package net.mysticcreations.lib.config;

public interface File<V> {
    void readFile();
    void writeFile();
    void startTrackingFile();

    V getValue(String[] path);
    void setValue(String[] path, V value);
    void clearValues();
    void setDesc(String[] path, String comment);
    void availableValuesNote(String[] path, String comment);
}
