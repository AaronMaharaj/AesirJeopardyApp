package com.jeopardy;

import java.util.List;

/**
 * Interface for loading game data from files.
 * Implementing classes support different file formats (CSV, JSON, XML, etc.).
 */
public interface GameDataLoader {
    /**
     * Loads game categories and questions from a file.
     * @param fileName the path to the file to load
     * @return a List of Category objects loaded from the file
     */
    List<Category> load(String fileName);
}
