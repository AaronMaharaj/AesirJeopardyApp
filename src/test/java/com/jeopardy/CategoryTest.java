package com.jeopardy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTest {//tests that check for question logic withing the category class

    @Test
    public void testAddAndRetrieveQuestion() {
        Category category = new Category("History");

        Map<String, String> options = new HashMap<>();
        options.put("A", "Answer A");

        Question q = new Question("Who?", 200, options, "A");

        assertTrue(category.getQuestions().isEmpty()); //should be empty after making a question

        category.addQuestion(q);
        List<Question> questions = category.getQuestions();
        assertEquals(1, questions.size());
        assertSame(q, questions.get(0)); //should have the same question added

        Question retrieved = category.getQuestion(200);
        assertNotNull(retrieved);
        assertEquals("Who?", retrieved.getQuestionText());
        assertEquals(200, retrieved.getValue()); //should have the same values
    }

    @Test
    public void testGetQuestionNotFoundReturnsNull() {
        Category category = new Category("Science");

        Map<String, String> options = new HashMap<>();
        options.put("A", "Answer A");

        Question q = new Question("What?", 100, options, "A");
        category.addQuestion(q);

        assertNull(category.getQuestion(999)); //error handling check for non-existent value
    }
}
