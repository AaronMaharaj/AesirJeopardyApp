package com.jeopardy;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads game data from XML files.
 * Expects XML format with QuestionItem elements containing Category, Value, QuestionText, Options, and CorrectAnswer.
 */
public class XMLLoader implements GameDataLoader {

    /**
     * Loads categories and questions from an XML file.
     * @param fileName the path to the XML file to load
     * @return a List of Category objects parsed from the XML
     */
    @Override
    public List<Category> load(String fileName) {
        List<Category> categories = new ArrayList<>();
        Map<String, Category> categoryMap = new HashMap<>();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("QuestionItem");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String categoryName = eElement.getElementsByTagName("Category").item(0).getTextContent();
                    int value = Integer.parseInt(eElement.getElementsByTagName("Value").item(0).getTextContent());
                    String questionText = eElement.getElementsByTagName("QuestionText").item(0).getTextContent();
                    String correctAnswer = eElement.getElementsByTagName("CorrectAnswer").item(0).getTextContent();

                    Element optionsElement = (Element) eElement.getElementsByTagName("Options").item(0);
                    String optionA = optionsElement.getElementsByTagName("OptionA").item(0).getTextContent();
                    String optionB = optionsElement.getElementsByTagName("OptionB").item(0).getTextContent();
                    String optionC = optionsElement.getElementsByTagName("OptionC").item(0).getTextContent();
                    String optionD = optionsElement.getElementsByTagName("OptionD").item(0).getTextContent();

                    Category category = categoryMap.get(categoryName);
                    if (category == null) {
                        category = new Category(categoryName);
                        categoryMap.put(categoryName, category);
                        categories.add(category);
                    }

                    Map<String, String> options = new HashMap<>();
                    options.put("A", optionA);
                    options.put("B", optionB);
                    options.put("C", optionC);
                    options.put("D", optionD);

                    Question question = new Question(questionText, value, options, correctAnswer);
                    category.addQuestion(question);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }
}
