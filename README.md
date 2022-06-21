# DFS-Implementation

This is a DFS implementation for the water jug pouring problem. This was a coursework for the Artiicial Intelligence module.

The JAR file can be found [here](AI_Coursework.jar).

## Problem Deffinition

Consider the following water-jug problem. There are 3 jugs that have capacities
of A gallons, B gallons, and C gallons. A pump with an unlimited supply of water is
available which can be used to fill the jugs. Water may be poured from one jug to
another or to the ground. None of the jugs has any measuring marks on it. In the start
state, all the three jugs are empty. This is denoted (0, 0, 0); the first number is the
amount of water in the A-gallon jug, the middle number is the amount of water in the
B-gallon jug, and the last number is the amount of water in the C-gallon jug. For
example, if A=8, B=5, and C=3, a possible next state is (8, 0, 0) which is reached by
pouring water into the 8-gallon jug. From the state (8, 0, 0) a possible next state is (5,
0, 3) which is reached by pouring water from the 8-gallon jug to the 3-gallon jug. From
the state (5, 0, 3) a possible next state is (0, 0, 3) which is reached by pouring water
from the 8-gallon jug to the ground.

## Algorithm definition implemented

### Overview
In this algorithm I have decided to define these moves accordingly, the algorithm can empty the jugs, meaning no water is left inside, and to fill the jugs completely and partially. Another move that can be performed is the transfer of water from one jug to another, in this move the water that can be transferred is as much as needed to fill the other jug or the jug being emptied runs out of water. One move can be performed each time.

### Permitted moves:
1. Fill A
2. Fill B
3. Fill C
4. A→B, transfer water from A to B
5. A→C, transfer water from A to C
6. B→A, transfer water from B to A
7. B→C, transfer water from B to C
8. C→A, transfer water from C to A
9. C→B, transfer water from C to B
10. Empty A
11. Empty B
12. Empty C

### Methods Used and data structures used:
previouslySeen¸ this is a list that contains all the previously visited states
navStack, this is a stack that contains all the states visited, it is used to go back to the previous depth
previousLevels, this is a stack that contains the lists with all the previous depths visited
nextLevelStates(), a method that finds all the next possible states
currentLevel, this is a list of states of the current level.
navNumber, the state that we are currently going through in the current level
state, an object that contains the values inside the jugs as well as the sizes of the jugs
add(), this method is used to add an element in an array
Searched(), a method that checks whether the current state selected has been seen before
Visited(), a method that marks that a state has been visited
findNumber(), finds the unvisited state in a current state used when navigating backwards
Empty(), checks if a stack is empty
createState(), a method that creates the object state that represents the different states of jugs

### Algorithm used

    function Depth-First-Search(A, B, C) returns a list:

      static: A, the input for the size of jug A
              B, the input for the size of jug B
              C, the input for the size of jug C
          
       state ← createState(0, 0, 0, A, B, C, false)
       navNumber ← 0
       currentLevel ← nextLevelStates(state)
       state[6] ← true
       push state at navStack
       push state at previouslySeen
       state <- currentLevel[navNumber]
       while Empty(navStack) is false:
      
        if Searched(state) is true:
            navNumber ← navNumber + 1
            if navNumber is not Lenght(currentLevel):
                state ← currentLevel[navNumber]
            else:
                pop from navStack
                if empty(navStack) is false:
                        currentLevel ← pop from previousLevels
                        navNumber ← findNumber(currentLevel)
                        if navNumber is not -1:
                            state ← currentLevel[navNumber]
        else:
            currentLevel[navNumber] ← Visited(state)
            push currentLevel at previousLevels
            push Visited(state) at navStack
            add state at previouslySeen
            navNumber ← 0
            currentLevel ← nextLevelStates(state)
            state ← currentLevel[navNumber]

## Instructions to rub the JAR file

The program written for question 2b is written in Java and it is in a jar file form. The file was written in Java 8 and as such it requires a minimum of Java 8 to be installed in the computer to run.
To run the program, the Command Prompt of Windows 10 should be used. The command java -jar, followed by the absolute path of the file, with \AI_Coursework.jar at the end of the path. It will then run the program and it will prompt the user to enter the size of the jugs that need to be tested. In the case that the user inputs a wrong input, a letter or a number smaller than one the program will exit.

The program has run successfully on Windows 10 64-bit Home Edition as well as in the labs’ computers running Windows 10.
