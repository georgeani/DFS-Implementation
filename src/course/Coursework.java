package course;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/*
* The datastructures used have been implemented using the Java Native libraries
*/

public class Coursework {

    /*
    * This is the implementation of the DFS algorithm designed by me
    * It is using an ArrayList that contains all the unique solutions currently being found
    * The idea is that this list will be used in order to check if a state has been met before
    * if this is the case it is discarded and the algorithm back tracts,
    * as the possible states will be in another algorithm
    * A stack that contains all the previous levels is also used in order to back tract to all possible levels
    * a navNumber is used to guide the algorithm in which node to go to next
    * The navStack is used to backtrack to the previous level
    * and to indicate which node was tested in the previous level
    * The ArrayList currentLevel is used to keep all the current states in that level
    * When the algorithm is moved to the next level the arraylist is pushed on to the previousLevels stack
    */
    public static ArrayList<State> DFS(int a, int b, int c){
        //the datastructures used
        ArrayList<State> uniqueStates = new ArrayList<>();
        Stack<ArrayList<State>> previousLevels = new Stack<>();
        int navNumber = 0;
        Stack<State> navStack = new Stack<>();
        ArrayList<State> currentLevel;

        //the implementation of the first level
        State currentState = new State(0,0,0,a,b,c);

        currentLevel = getNextStates(currentState);
        currentState.visited();
        navStack.push(currentState);
        uniqueStates.add(currentState);
        currentState = currentLevel.get(navNumber);

        //this loop is used go through the tree of all possible states, it only stops when the navStack is empty
        while (!navStack.empty()){

            //in case the current state already exists we then look at the next node in the same level

            if (exists(currentState, uniqueStates)){
                navNumber++;

                //if all nodes have been tested then we back tract to the previous level
                if (navNumber< currentLevel.size()){
                    currentState = currentLevel.get(navNumber);
                } else {
                    navStack.pop();
                    //in case the navStack is not empty we load the load the previous level and find the unvisited node
                    if (!navStack.empty()){
                        currentLevel = previousLevels.pop();
                        navNumber = findNumber(currentState, currentLevel);

                        if (navNumber!=-1){
                            currentState = currentLevel.get(navNumber);
                        }

                    }
                }

            /* In case the state has not be seen before
            * we mark it as visited, mark the node in the level as visited
            * and push both in their respective stacks
            * and then we generate the next level of states
            * and update the current state
            */
            } else {
                currentLevel.get(navNumber).visited();
                previousLevels.push(currentLevel);
                currentState.visited();
                navStack.push(currentState);
                uniqueStates.add(currentState);
                navNumber = 0;
                currentLevel = getNextStates(currentState);
                currentState = currentLevel.get(navNumber);

            }

        }


        return uniqueStates;
    }

    /*
    * this method is used to create the next possible states from a state
    * it takes as an input a state and then it outputs all the possible states
    */
    public static ArrayList<State> getNextStates(State startingState){

        ArrayList<State> nextState = new ArrayList<>();

        //we use this if statement in order to create the states starting directly from the starting state
        if (startingState.getCA()==0 && startingState.getCB()==0 && startingState.getCC()==0){

            nextState.add(newState(startingState.getSizeA(), 0, 0, startingState));
            //System.out.println(newState(startingState.getSizeA(), 0, 0, startingState).toString());
            nextState.add(newState(0, startingState.getSizeB(), 0, startingState));
            //System.out.println(newState(0, startingState.getSizeB(), 0, startingState).toString());
            nextState.add(newState(0, 0, startingState.getSizeC(), startingState));
            //System.out.println(newState(0, 0, startingState.getSizeC(), startingState).toString());

        }else {


            //creating the possible states of A
            nextState.add(newState(0, startingState.getCB(), startingState.getCC(), startingState));

            if (startingState.getCA() != startingState.getSizeA()){
                nextState.add(newState(startingState.getSizeA(), startingState.getCB(), startingState.getCC(), startingState));
            }

            //creating the possible states with combinations of B
            if (startingState.getCB()>0){

                if (startingState.getCB() != startingState.getSizeB()){

                    if (startingState.getCA() < (startingState.getSizeB() - startingState.getCB())){
                        nextState.add(newState(0, startingState.getCB() + startingState.getCA(),
                                startingState.getCC(), startingState));


                    } else {
                        nextState.add(newState(startingState.getCA() - (startingState.getSizeB()-startingState.getCB()),
                                startingState.getSizeB(),
                                startingState.getCC(), startingState));

                    }

                    if (startingState.getCB() >= (startingState.getSizeA() - startingState.getCA())
                            && (startingState.getSizeA()!= startingState.getCA()) && startingState.getCA()>0){
                        nextState.add(newState(startingState.getSizeA(),
                                startingState.getCB() - (startingState.getSizeA() - startingState.getCA()), startingState.getCC(), startingState));

                    } else if (startingState.getSizeA()!= startingState.getCA()){
                        nextState.add(newState(startingState.getSizeA(), 0, startingState.getCC(), startingState));

                    }

                    nextState.add(newState(startingState.getCA(), 0, startingState.getCC(), startingState));

                    nextState.add(newState(startingState.getCA(), startingState.getSizeB(), startingState.getCC(), startingState));

                    if (startingState.getCB() > (startingState.getSizeC() - startingState.getCC())
                            && startingState.getSizeC()!= startingState.getCC()){

                        nextState.add(newState(startingState.getCA(),
                                startingState.getCB() - (startingState.getSizeC() - startingState.getCC()),
                                startingState.getSizeC(), startingState));

                    } else if (startingState.getCC() != startingState.getSizeC()){
                        nextState.add(newState(startingState.getCA(), 0, startingState.getSizeC(), startingState));
                    }

                } else {

                    nextState.add(newState(startingState.getCA(), 0, startingState.getCC(), startingState));

                    if (startingState.getCB() > (startingState.getSizeC() - startingState.getCC())
                            && startingState.getSizeC()!= startingState.getCC()){

                        nextState.add(newState(startingState.getCA(),
                                startingState.getCB() - (startingState.getSizeC() - startingState.getCC()),
                                startingState.getSizeC(), startingState));

                    } else if (startingState.getCC() != startingState.getSizeC()){
                        nextState.add(newState(startingState.getCA(), 0, startingState.getSizeC(), startingState));
                    }

                    if (startingState.getCB() >= (startingState.getSizeA() - startingState.getCA())
                            && (startingState.getSizeA()!= startingState.getCA()) && startingState.getCA()>0){
                        nextState.add(newState(startingState.getSizeA(),
                                startingState.getCB() - (startingState.getSizeA() - startingState.getCA()), startingState.getCC(), startingState));

                    } else if (startingState.getSizeA()!= startingState.getCA()){
                        nextState.add(newState(startingState.getSizeA(), 0, startingState.getCC(), startingState));

                    }

                }

            } else if (startingState.getSizeB() < startingState.getCA()){
                nextState.add(newState(startingState.getCA() - startingState.getSizeB(), startingState.getSizeB(),
                        startingState.getCC(), startingState));

                nextState.add(newState(startingState.getCA(), startingState.getSizeB(), startingState.getCC(), startingState));

            } else {
                nextState.add(newState(0, startingState.getCA(), startingState.getCC(), startingState));
                nextState.add(newState(startingState.getCA(), startingState.getSizeB(), startingState.getCC(), startingState));
            }

            //checking for value C for A

            if (startingState.getCC()>0){

                if (startingState.getCC() != startingState.getSizeC()){

                    if (startingState.getCA() < (startingState.getSizeC() - startingState.getCC())){
                        nextState.add(newState(0, startingState.getCB(),
                                startingState.getCC() + startingState.getCA(), startingState));

                    } else {
                        nextState.add(newState(startingState.getCA() - (startingState.getSizeC()-startingState.getCC()),
                                startingState.getCB(), startingState.getSizeC(), startingState));

                    }

                    nextState.add(newState(startingState.getCA(), startingState.getCB(), 0, startingState));

                    nextState.add(newState(startingState.getCA(), startingState.getCB(), startingState.getSizeC(), startingState));

                    if (startingState.getCC() > (startingState.getSizeB() - startingState.getCB())
                            && startingState.getSizeB()!= startingState.getCB()){

                        nextState.add(newState(startingState.getCA(), startingState.getSizeB(),
                                startingState.getCC() - (startingState.getSizeB() - startingState.getCB()), startingState));


                    } else if (startingState.getSizeB()!= startingState.getCB()){
                        nextState.add(newState(startingState.getCA(), startingState.getSizeB(), 0, startingState));

                    }

                    if (startingState.getCC() >= (startingState.getSizeA() - startingState.getCA())
                            && (startingState.getSizeA()!= startingState.getCA()) && startingState.getCA()>0){
                        nextState.add(newState(startingState.getSizeA(), startingState.getCB(),
                                startingState.getCC() - (startingState.getSizeA() - startingState.getCA()), startingState));

                    } else if (startingState.getSizeA()!= startingState.getCA()){
                        nextState.add(newState(startingState.getSizeA(), startingState.getCB(), 0, startingState));

                    }

                } else {

                    nextState.add(newState(startingState.getCA(), startingState.getCB(), 0, startingState));

                    if (startingState.getCC() > (startingState.getSizeB() - startingState.getCB())
                            && startingState.getSizeB()!= startingState.getCB()){

                        nextState.add(newState(startingState.getCA(), startingState.getSizeB(),
                                startingState.getCC() - (startingState.getSizeB() - startingState.getCB()), startingState));


                    } else if (startingState.getSizeB()!= startingState.getCB()){

                        nextState.add(newState(startingState.getCA(), startingState.getCC(), 0, startingState));
                    }

                    if (startingState.getCC() >= (startingState.getSizeA() - startingState.getCA())
                            && (startingState.getSizeA()!= startingState.getCA()) && startingState.getCA()>0){
                        nextState.add(newState(startingState.getSizeA(), startingState.getCB(),
                                startingState.getCC() - (startingState.getSizeA() - startingState.getCA()), startingState));

                    } else if (startingState.getSizeA()!= startingState.getCA()){
                        nextState.add(newState(startingState.getSizeA(), startingState.getCB(), 0, startingState));
                    }

                }

            } else if (startingState.getSizeC() < startingState.getCA()){
                nextState.add(newState(startingState.getCA() - startingState.getSizeC(), startingState.getCB(),
                        startingState.getSizeC(), startingState));

                nextState.add(newState(startingState.getCA(), startingState.getCB(), startingState.getSizeC(), startingState));

            } else {
                nextState.add(newState(0, startingState.getCB(), startingState.getCA(),startingState));
                nextState.add(newState(startingState.getCA(), startingState.getCB(), startingState.getSizeC(), startingState));
            }

        }

        return nextState;
    }

    //this method checks whether the state currently visiting has previously been visited
    public static boolean exists(State state, ArrayList<State> uniqueStates){
        for (State uniqueState : uniqueStates) {
            if (state.getCA() == uniqueState.getCA()
                    && state.getCB() == uniqueState.getCB() && state.getCC() == uniqueState.getCC()) {
                return true;
            }
        }

        return false;
    }

    /*
    * This method is used to find the next unvisited node in a level
    * In case all nodes have been visited a -1 is returned signaling that all nodes have been visited
    */
    public static int findNumber(State currentState, ArrayList<State> currentLevel){

        for (int i=0;i<currentLevel.size();i++) {
            if (currentState.getCA() == currentLevel.get(i).getCA()
                    && currentState.getCB() == currentLevel.get(i).getCB()
                    && currentState.getCC() == currentLevel.get(i).getCC() && currentLevel.get(i).isVisited()) {
                i++;
                return i;
            }
        }

        return -1;
    }

    //This method is used to create a new state with custom values
    public static State newState(int CA, int CB, int CC, State startingState){
        return new State(CA, CB, CC, startingState.getSizeA(),
                startingState.getSizeB(), startingState.getSizeC());
    }

    /*
    * This is the main method used to run the coursework and solve the problem
    * It allows the user to input different values
    * it also checks if the values are correct
    * It outputs all the possible states as well as the number of all possible states
    */

    public static void main(String[] args){
        int a = 0;
        int b = 0;
        int c = 0;

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input value A: ");
            a = Integer.parseInt(scanner.next());
            System.out.println("Input value B: ");
            b = Integer.parseInt(scanner.next());
            System.out.println("Input value C: ");
            c = Integer.parseInt(scanner.next());

            if (a<=0 || b<=0 || c<=0){
                System.out.println("Wrong Input");
                System.exit(0);
            }

        } catch (Exception e){
            System.out.println("Wrong Input");
            System.exit(0);
        }
        ArrayList<State> possibleSolutions = DFS(a,b,c);
        System.out.println("Possible solutions \n");

        for (State possibleSolution : possibleSolutions) {
            System.out.println(possibleSolution.toString());
        }

        System.out.println("The number of all solutions is: " + possibleSolutions.size());

    }
}
