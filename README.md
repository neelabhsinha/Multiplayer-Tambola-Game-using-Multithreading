# Multiplayer-Tambola-Game-using-Multithreading
This repository contains a multithreaded application based on 'n' player Tambola game developed using Observer Design Pattern, Singleton Design Pattern and Multi-threading in Java.

## Problem Formulation - 

Let there be one Moderator and ‘n’ Players. The moderator displays 10 random numbers (between 0 – 50) on a display screen. Each player is given a card containing 10 random numbers (between 0 - 50). As the number is displayed, the player strikes the number on his card if it matches with the one on the screen. The player who strikes three numbers first will be announced as a winner and the moderator stops generating numbers if a player wins before all the 10 numbers are generated. The numbers generated by the moderator and the player can be redundant.
The moderator generates 10 numbers and stores it in an array list of integers. When the moderator is generating the number, the players should not read the array list. The moderator should generate the next number only after all the players have read the previously generated number. The array list should not be read by two players at the same time.

## Design Approach -

- Observer Design Pattern is used as the skeleton of the application. In this case, as per the observer pattern, moderator is the subject and players are the observers. The change in state is generation of a new number and update by the observers is checking the number and striking it off, if present 
- Singleton Design Pattern is used in Moderator class because only one moderator is needed to run the game
- Table is the shared data and GameController is the class that contains the main function and runs the game
- Inter-thread communication between the threads take place through appropriate use of locks over the shared data and wait and notifyAll() methods

Concepts used - Design Patterns, Object Oriented Programming, Collections Framework of Java, Multithreading, Java Object Model

*For More Details, open the explanation.pdf file and the Java files. The code is properly commented to explain what is happening at each instant, and conceptual explanation is given in [explanation.pdf](https://github.com/neelabhsinha/Multiplayer-Tambola-Game-using-Multithreading/blob/master/explanation.pdf) file*

## Steps to exeute - 
- Make sure to have JDK and/or JRE (as per requirement) installed
- Execute the Following Commands -

```
$ git clone https://github.com/neelabhsinha/Multiplayer-Tambola-Game-using-Multithreading.git
$ cd Multiplayer-Tambola-Game-using-Multithreading.git'
```

- If you are using Eclipse, open the project and run the GameController.java class. For any other IDE as well, compile all the classes and run the GameController.java class
