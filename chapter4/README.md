# Shoot-em-up Games

## The Game Design Document
A _game design document_ (GDD) is a blueprint for creating a game: it describes the overall vision of a game, provide clarity and focus, and serves as a guide and a reference to the people working on the game.
A GDD is often based on a framework, such as MDA.

The [Mechanics-Dynamis-Aesthetics](https://en.wikipedia.org/wiki/MDA_framework) (MDA) _framework_ is a useful way to categorize the components of a game.
In appendix A in the [book](https://www.apress.com/gp/book/9781484233238) on page 402 there is a list of GDD questions which are very helpful with the creation of a GDD.
The core questions can be summarised as follows: 
1. Overall Vision

    a. Write a short paragraph explaining the game
    
    b. Describe the genre
    
    c. What is the target audience?
    
    d. Why play this game?
    
2. Mechanics: the rules of the game world

    a. What are the character's goals?
    
    b. What abilities does the character have?
    
    c. What obstacles or difficulties will the character face?
    
    d. What item can the character obtain
    
    e. What resources must me managed?
    
    f. Describe the game-world environment.  

3. Dynamics: the interaction between the player and the game mechanics

    a. What hardware is required by the game?
    
    b. What type of proficiency will the player need to develop to become proficient at the game?
    
    c. What gameplay data is displayed during the game?
    
    d. What menus, screens, or overlays will there be?
    
    e. How does the player interact with the game at the software level?

4. Aesthetics: the visual, audio, narrative, and psychological aspects of the game

    a. Describe the style and feel of the game.
    
    b. Does the game use pixel art, line art, or realistic graphics?
    
    c. What style of background music, ambient sounds will the game use?
    
    d. What is the relevant backstory for the game?
    
    e. What emotional state(s) does te game try to provoke?
    
    f. What makes the game fun?
        
5. Development

    a. List the team members and their roles, responsibilities, and skills.
    
    b. What equipment is needed for this project?
    
    c. What are the tasks that need to be accomplished to create this game?
    
    d. What points in the development process are suitable for playtesting?
    
    e. What are the plans for publication?    

## Discrete Input
A discrete action happens at a _single point_ in time. E.g actions are triggered the instant a key is pressed.
The ```InputProcessor``` interface handles discrete events.
An ```InputMultiplexer``` contains a list of InputProcessors, which makes sure every screen overlay will be responsive.  

## Input Polling
Continuous actions takes place over an _interval_ of time. E.g holding down a key for a period of time.
Input polling repeatedly checks the status of an input device (such as a keyboard).

## New Imports
**import com.badlogic.gdx.InputMultiplexer** - An InputProcessor that delegates to an ordered list of other InputProcessors. Delegation for an event stops if a processor returns true, which indicates that the event was handled.