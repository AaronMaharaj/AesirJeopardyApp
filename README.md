# AesirJeopardyApp Wiki

## 1. Introduction
**AesirJeopardyApp** is a robust, console-based implementation of the classic Jeopardy! game. It is designed to be flexible, supporting multiple data formats (JSON, XML, CSV) for game content and providing detailed post-game reporting. The application supports 1-4 players and tracks scores, game events, and user interactions in real-time.

### Key Features
*   **Dynamic Data Loading**: Load questions from various file sources.
*   **Multiplayer Engine**: Turn-based logic for up to 4 players.
*   **Event Tracking**: Comprehensive logging of every game action (Observer Pattern).
*   **Reporting**: Export game summaries to PDF, DOCX, or TXT.

---

## 2. Design & Architecture

### Design Patterns
We utilized several standard design patterns to ensure the codebase is maintainable and extensible:

*   **Observer Pattern**: Used in the `EventManager` to notify listeners (like `GameEventLogger`) of game activities (e.g., `ANSWER_QUESTION`, `SCORE_UPDATED`). This decouples the core game logic from logging and UI updates.
*   **Factory Pattern**: The `DataLoaderFactory` encapsulates the logic for creating specific `GameDataLoader` instances (CSV, JSON, XML) based on file extensions.
*   **Strategy Pattern**: Implemented in the `ReportGenerator` interface. The `Game` class can switch between different reporting strategies (`PDFReport`, `TXTReport`, `DOCXReport`) at runtime without changing its internal code.

### SOLID Principles
*   **Single Responsibility Principle (SRP)**: Each class has a distinct purpose. For example, `Game` manages state, `CSVLoader` parses files, and `EventManager` handles notifications.
*   **Open/Closed Principle (OCP)**: The system is open for extension but closed for modification. New data formats can be added by creating a new implementation of `GameDataLoader` without modifying the `Game` class.
*   **Liskov Substitution Principle (LSP)**: All implementations of `GameDataLoader` (CSV, JSON, XML) can be used interchangeably by the `Game` class without breaking functionality.
*   **Interface Segregation Principle (ISP)**: Interfaces like `GameDataLoader` and `ReportGenerator` are focused and minimal, ensuring implementing classes aren't forced to define unused methods.
*   **Dependency Inversion Principle (DIP)**: High-level modules (`Game`) depend on abstractions (`GameDataLoader`, `ReportGenerator`) rather than concrete implementations.

### Class Diagram
<img width="7730" height="3591" alt="OOP2-GroupProject" src="https://github.com/user-attachments/assets/df4afd96-03d7-4a19-81dc-866ced7ac878" />

---

## 3. Implementation Details

### Core Components
*   **`Game`**: The central controller. It manages the game loop, player turns, and interacts with the `EventManager`.
*   **`EventManager`**: Handles the subscription and notification of game events.
*   **`DataLoaderFactory`**: Determines which loader to use based on input files.

### Data Flow
1.  **Initialization**: `Main` initializes `Game`.
2.  **Loading**: User inputs a filename -> `DataLoaderFactory` selects loader -> Data is parsed into `Category` and `Question` objects.
3.  **Gameplay**: `Game` loops through players -> `EventManager` fires events for every action -> `GameEventLogger` records them.
4.  **Reporting**: Game ends -> User selects format -> `ReportGenerator` strategy creates the file.

---

## 4. Testing Strategy

### Unit Testing
We used **JUnit 5** for robust unit testing of individual components.
*   **`CategoryTest`**: Verifies question retrieval and category integrity.
*   **`GameTest`**: Tests core game logic, scoring, and player management.
*   **`EventManagerTest`**: Ensures listeners are correctly registered and notified.

### Integration Testing
*   **`DataLoaderFactoryTest`**: Verifies that the factory correctly identifies file types and returns the proper loader.
*   **`EventLoggerTest`**: Checks that events generated during a mock game flow are correctly recorded in the log.

### Test Coverage
We aimed for high coverage on critical business logic, particularly the scoring and data loading modules, to ensure a fair and crash-free game experience.

## 5. AI Generation Tracking

|Date|Group Member|AI Prompt Used|Code/Artifact Generated|Purpose/Notes|
|---|---|---|---|---|
|**Nov 22**|Jonathan|"How do I generate PDF and DOCX files in Java using Apache PDFBox and POI? Give me a simple example class for each."|`PDFReport.java`, `DOCXReport.java`|We needed to implement the reporting feature but weren't familiar with the specific libraries. The AI provided the boilerplate code for file generation.|
|**Nov 23**|Aaron|"Generate standard Javadoc comments for this Java class. Explain the methods, parameters, and return types clearly."|Javadocs for `Main.java` and `Game.java`|Used to quickly document the core game logic and entry point without manually writing every comment.|
|**Nov 25**|Jonathan|"Write a JUnit 5 test class for an EventManager that follows the Observer pattern. It should test adding listeners and firing events."|`EventManagerTest.java`|We wanted to ensure the event system worked correctly but were running out of time. The AI generated the test cases to verify the listener registration and event firing.|
|**Nov 25**|Aaron|"How do I mock a file input stream in Java for unit testing a data loader?"|`DataLoaderFactoryTest.java` (Partial)|Assisted in creating the test setup for the data loaders to avoid reading actual files during testing.|
---

# QuickStart Aesir Jeopardy App
A console-based Jeopardy game application developed in Java. This project allows users to load game data from various file formats (JSON, XML, CSV), play a multiplayer game of Jeopardy, and generate post-game reports.

## Features
Multi-format Data Loading: Support for loading game questions from JSON, XML, and CSV files.
Multiplayer Support: Play with 1-4 players.
Interactive Console UI: Select categories, choose question values, and answer multiple-choice questions.
Score Tracking: Real-time score updates for all players.
Reporting: Generate game summaries in TXT, PDF, or DOCX formats.
Event Logging: Tracks game events for debugging and review.

## Prerequisites
Java Development Kit (JDK) 21 or higher.
Maven (for building and running the project).

## Installation
Clone the repository:
`git clone <repository-url>`
`cd AesirJeopardyApp`

Build the project:
`mvn clean install`

## Usage
Running the Game
You can run the application directly using Maven:

`mvn exec:java`


## How to Play
Load Game Data: When prompted, enter the filename of the game data you want to load.
Example: sample_game_JSON.json
Note: Ensure the file is in the project root directory.
Setup Players: Enter the number of players (1-4) and their names.
Play:
The current player will be shown.
Select a Category from the list.
Select a Question Value (e.g., 200, 400).
Enter your answer (A, B, C, or D).
Game Over: The game ends when all questions have been answered.
Reports: Choose to generate a summary report in TXT, PDF, or DOCX format.

## Documentation
To generate the Javadocs for this project:
`mvn javadoc:javadoc`

Documentation pre-generated for this project in javadocs folder.

