
# AstroGo

I developed an application called **AstroGo**.  

## Overview
In the first screen of the app, the user can set up their game preferences. They can choose between two game modes:  
- **Fast-paced mode**  
- **Slow-paced mode**  

Additionally, they can select their preferred control method:  
- **Buttons**: Use on-screen controls to move.  
- **Tilt**: Tilt the device to navigate.  

The settings can be adjusted using the switches I implemented. The first screen also includes two buttons:  
1. **Start Game**: Begins the game immediately.  
2. **View Records**: Allows users to see their previous scores and where they achieved them.  

If this is their first time playing, no records or pins will appear on the map, as they haven’t achieved any scores yet.  

## Gameplay
If the user clicks on **Start Game**, they are taken to the gameplay screen. In the game:  
- The player needs to avoid obstacles (specifically asteroids) while collecting coins to increase their score.  
- There are five lanes available, and the player can move left or right between these lanes.  

### Control Methods:
- **Buttons**: Tap the on-screen arrows to navigate.  
- **Tilt**: Physically tilt the device to move left or right across the lanes.  

### Objectives:
- Avoid the asteroids and collect as many coins as possible.  
- The current score is displayed on the screen throughout the game.  

The player starts with three lives, and each time they hit an obstacle, one life is lost. If all three lives are lost, the game ends, and the player is directed to the **Game Over** screen.  

## Game Over and Records
When the player reaches the **Game Over** screen, they have the option to view their record by clicking a button. This takes them to the **Records** screen, which consists of two fragments:  

### 1. Score Fragment
This fragment displays the player’s top ten scores. For each score, it shows:  
- The player’s name.  
- The coordinates of the location where the score was achieved.  
- The number of coins collected during that game.  

The record table is dynamic and always updates to show only the player’s top ten scores.  
- If a new score is achieved and there are already ten records, the system will compare the new score to the lowest score in the table.  
- If the new score is higher, it will replace the lowest score, and the new record will be saved.  

### 2. Map Fragment
The second fragment contains a Google Map that shows pins marking the locations where the top scores were achieved.  
- If the player clicks on a specific score, the map will enlarge and provide more details about the exact location where that score was achieved.  
- The pins on the map are also updated dynamically, reflecting only the current top ten scores.  

This setup ensures that the player always has an up-to-date record of their best achievements and a clear visual representation of their top performances.
