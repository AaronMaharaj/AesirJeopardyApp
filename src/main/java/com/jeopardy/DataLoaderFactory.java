package com.jeopardy;

/**
 * Factory class for creating appropriate GameDataLoader instances.
 * Uses file extension to determine which loader implementation to return.
 */
public class DataLoaderFactory {
    /**
     * Returns a GameDataLoader implementation based on the file extension.
     * Supports CSV, JSON, and XML file formats.
     * @param fileName the name of the file to load
     * @return a GameDataLoader appropriate for the file type, or null if unsupported
     */
    public GameDataLoader getLoader(String fileName) {
        if (fileName.toLowerCase().endsWith(".csv")) {
            return new CSVLoader();
        } else if (fileName.toLowerCase().endsWith(".json")) {
            return new JSONLoader();
        } else if (fileName.toLowerCase().endsWith(".xml")) {
            return new XMLLoader();
        }
        return null;
    }
}
