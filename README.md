# Elden Ring Game

## Overview
Welcome to my **Elden Ring**, a fan-made recreation of the hit action RPG *Elden Ring*. In this project, I aim to capture the essence of the game by developing key gameplay mechanics, combat systems, and an immersive story experience.

## Features
- **Combat Mechanics**: Implemented time-based combat with a variety of weapons and spells.
- **Story**: Explore an emotional and interconnected narrative with various enemies and heroes.
- **Character Customization**: Create your own character, choose weapons, and level up skills.
- **Boss Fights**: Challenging boss encounters with unique attack patterns and phases.

## Installation Instructions

1. Clone this repository in a command prompt:
   ```bash
   git clone https://github.com/jdesilver/EldenRing.git
   cd EldenRing
   ```
2. Install Java (JDK 17 or newer)
   Check first what is installed:
     ```
     java -version
     ```
   If the version is below 17, [download and install a current JDK](https://adoptium.net/).
   Make sure `java` and `javac` report the same version — if `java -version` shows 1.8 while
   `javac` is newer, put your JDK's `bin` directory first on your `PATH` (or call the JDK's
   `java`/`javac` by full path).
3. Compile the Game
     ```
     javac *.java
     ```
4. Run the Game
     ```
     java Main
     ```
