package com.jeopardy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class DataLoaderFactoryTest { // tests factory generates the correct loader based on file extension

    @Test
    void testCSVLoader() {
        DataLoaderFactory factory = new DataLoaderFactory();
        GameDataLoader loader = factory.getLoader("sample_game_CSV.csv");
        assertNotNull(loader);
        assertTrue(loader instanceof CSVLoader);

        List<Category> categories = loader.load("sample_game_CSV.csv");
        assertFalse(categories.isEmpty());
        assertEquals("Variables & Data Types", categories.get(0).getName());
    }

    @Test
    void testJSONLoader() {
        DataLoaderFactory factory = new DataLoaderFactory();
        GameDataLoader loader = factory.getLoader("sample_game_JSON.json");
        assertNotNull(loader);
        assertTrue(loader instanceof JSONLoader);

        List<Category> categories = loader.load("sample_game_JSON.json");
        assertFalse(categories.isEmpty());
    }

    @Test
    void testXMLLoader() {
        DataLoaderFactory factory = new DataLoaderFactory();
        GameDataLoader loader = factory.getLoader("sample_game_XML.xml");
        assertNotNull(loader);
        assertTrue(loader instanceof XMLLoader);

        List<Category> categories = loader.load("sample_game_XML.xml");
        assertFalse(categories.isEmpty());
    }

    @Test
    void testUnsupportedLoader() {
        DataLoaderFactory factory = new DataLoaderFactory();
        GameDataLoader loader = factory.getLoader("sample_game_TXT.txt");
        assertNull(loader);
    }
}
