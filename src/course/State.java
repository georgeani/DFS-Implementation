package course;

/*
* The state class is used to represent the states of all 3 jugs
* That means that the state class have the sizes of all jugs as well as
* the current capacity they have
* The datastructures used are arrays, size array shows the size of the jugs
* the capacity array shows the ammount of water that they are currently holding
* A boolean visited is used to designate whether a state has been visited
*/

public class State {
    private final int[] size = new int[3];
    private final int[] capacity = new int[3];
    private boolean visited = false;

    public State(int jugA, int jugB, int jugC, int sizeA, int sizeB, int sizeC){
        capacity[0] = jugA;
        capacity[1] = jugB;
        capacity[2] = jugC;
        size[0] = sizeA;
        size[1] = sizeB;
        size[2] = sizeC;
    }

    /*
    * These are the getter and setter methods
    * Setter methods are only used for the visited value, in order to mark a note as visited
    * A to String method has been implemented in order to present the states properly
    */

    public int getCA(){
        return capacity[0];
    }

    public int getSizeA(){
        return size[0];
    }

    public int getCB(){
        return capacity[1];
    }

    public int getSizeB(){
        return size[1];
    }

    public int getCC(){
        return capacity[2];
    }

    public int getSizeC(){
        return size[2];
    }

    public boolean isVisited() {
        return visited;
    }

    public void visited() {
        this.visited = true;
    }

    @Override
    public String toString() {
        return "" + getCA() + ", " + getCB() + ", " + getCC();
    }
}
