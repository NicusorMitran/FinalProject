// 12:26

import java.util.ArrayList;
import java.util.Scanner;

public class Elevator {
    public static void main(String[] args) {
        Elevator e = new Elevator();
        e.askPassenger();
    }

    //variables
    Scanner intel = new Scanner(System.in);
    ArrayList<Integer> listOFFloors;

    boolean isDoorOpen = false;

    final int maxP = 7; //max passengers
    final int maxF = 10; //max floors
    final int minP = 1; //minimum passengers
    final int minF = 1; //minimum floors

    int curF = 1; //current floor
    int desF = 0; //destination floor
    int passFloor = 0; //passenger floor, we will use this variable for asking passenger's floor
    int numOfPass = 0;

    int[] destination_lists = new int[maxF];

    void startSimulation(){
        System.out.println("Welcome to Elevator");
        delay(5000);
    }

    void askPassenger() { //ask how many passengers there is
            isDoorOpen = false;
        System.out.println("Elevator opening...");
            delay(1500);
            isDoorOpen = true;
        System.out.print(curF + "F | How many passengers: ");
            numOfPass = intel.nextInt();
            if (numOfPass < minP || numOfPass > maxP){
            System.out.println("Error. Valid nr of passengers [1-7]");
            askPassenger();
            }else {
                listOFFloors = new ArrayList<>();
                for(int a=0;a<numOfPass;a++){
                    int floor = askPassengerFloor(a);
                    if(!listOFFloors.contains(floor)) listOFFloors.add(floor);
                }
            }

            System.out.println("Elevator closing...");
            delay(1500);
            isDoorOpen = false;
            initialize_elevator();
    }
    int askPassengerFloor(int id) {
        boolean isValidEntry = false;
        int floor = 0;
        while (!isValidEntry) {
            System.out.print("Passenger #" + (id+1) + " enter your floor: ");
            floor = intel.nextInt();
            if (floor < minF || floor > maxF) {
                System.out.println("Error. You have entered out of range floor. [1-20]");
            } else if (floor == curF) {
                System.out.println("You are already in the " + curF + "F.");
            }else {
                destination_lists[floor-1]++;
                isValidEntry = true;
            }
        }
        return floor;
    }
    void initialize_elevator(){
        for (int a=0;a<listOFFloors.size();a++){
            int shortest = findShortest();
            System.out.println("Next destination: " + shortest + "F Passenger amount " + destination_lists[shortest-1]);
            delay(1500);
            while (curF < shortest) {
                up();
            }
            while (curF > shortest){
                down();
            }
            while (destination_lists[shortest-1] > 0) {
                System.out.println("Unloading passenger " + destination_lists[shortest-1]-- + " at " + curF + "F");
                delay(1500);
            }
        }
        askPassenger();
    }

    void up() {
        System.out.println(curF++ + "F | Going up...");
    }

    void down() {
        System.out.println(curF-- + "F | Going down...");
    }

    void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
    }

    int findShortest(){
        int shortest = Math.abs(curF - listOFFloors.get(0));
        int id=0;
        for (int a=1;a< listOFFloors.size();a++){
            if (shortest > Math.abs(curF - listOFFloors.get(a))) {
                shortest = Math.abs(curF - listOFFloors.get(a));
                id = a;
            }
        }
        shortest = listOFFloors.get(id);
        listOFFloors.set(id, 100);
        return shortest;
    }
}