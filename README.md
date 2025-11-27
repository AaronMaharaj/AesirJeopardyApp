Aesir Jeopardy App
A console-based Jeopardy game application developed in Java. This project allows users to load game data from various file formats (JSON, XML, CSV), play a multiplayer game of Jeopardy, and generate post-game reports.

Features
Multi-format Data Loading: Support for loading game questions from JSON, XML, and CSV files.
Multiplayer Support: Play with 1-4 players.
Interactive Console UI: Select categories, choose question values, and answer multiple-choice questions.
Score Tracking: Real-time score updates for all players.
Reporting: Generate game summaries in TXT, PDF, or DOCX formats.
Event Logging: Tracks game events for debugging and review.

Prerequisites
Java Development Kit (JDK) 21 or higher.
Maven (for building and running the project).

Installation
Clone the repository:
git clone <repository-url>
cd AesirJeopardyApp

Build the project:
mvn clean install

Usage
Running the Game
You can run the application directly using Maven:

mvn exec:java


How to Play
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

Documentation
To generate the Javadocs for this project:
mvn javadoc:javadoc

Documentation pre-generated for this project in javadocs folder.
