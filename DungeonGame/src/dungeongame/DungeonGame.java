/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeongame;
import java.util.*;


/**
 *
 * @author Honigmaster
 */
public class DungeonGame {
    Room currRoom;
    
    public DungeonGame(){
       currRoom = null; 
    }
    
    
    //Generates the dungeon were the game will be played.
    //The dungeon is a random ternary tree structure.
    public Room createWorld(){
        Random rand = new Random();
        int[] pruferArray = new int[8];
        int[][] dungeon;
        int[] freq = new int[10];
        Room start = null;
        
        
        //Creates a Prufer sequence, which is used to create a matrix represnetation
        //a the dungeon's nodes and edges. Additionally, it makes sure that each
        //room has no more than 3 exits (other than the edge used to enter the room.
        System.out.print("Prufer: ");
        for(int i = 0; i < 8; i ++){
            int cont = 1;
            while(cont == 1){
                pruferArray[i] = rand.nextInt(10);
                if(freq[pruferArray[i]] < 3){
                    freq[pruferArray[i]] = freq[pruferArray[i]] + 1;
                    cont = 0;
                }
            }
            System.out.print(pruferArray[i]);
        }
        System.out.print("\n");
        
        
        
        //Calls function to convert Prufer sequence to matrix
        dungeon = convertToTree(pruferArray);
        
        
        
        //Creates an array of rooms and assigns a unique ID number to each room.
        Room[] roomList = new Room[10];
        for(int i = 0; i < 10; i++){
            roomList[i] = new Room("combat", i, 1);
        }
        
        
        
        //This segment uses the 2-d "dungeon" matrix to construct a tree structure
        //Each time a room is added to the dungeon tree, the position of the edge 
        //(left, right or center) is randomly chosen. When the node is added, the
        //parent attribute of that node is kept track of.
        int lrc;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(dungeon[i][j] == 1){
                    int cont = 1;
                    while(cont == 1){
                        lrc = rand.nextInt(3);
                        if(lrc == 0 && roomList[i].left == null){
                            roomList[i].left = roomList[j];
                            roomList[j].parent = roomList[i];
                            cont = 0;
                        }
                        else if(lrc == 1 && roomList[i].center == null){
                            roomList[i].center = roomList[j];
                            roomList[j].parent = roomList[i];
                            cont = 0;
                        }
                        else if(lrc == 2 && roomList[i].right == null){
                            roomList[i].right = roomList[j];
                            roomList[j].parent = roomList[i];
                            cont = 0;
                        }
                    }
                }
            }
        }
        
        
        
        //This method always leaves one node without a parent node, this loop
        //finds that node and sets that as the root node of the tree and the 
        //starting room of the game.
        for(int i = 0; i < 10; i++){
            if(roomList[i].parent == null){
                System.out.println(i);
                start = roomList[i];
            }
            
        }
        
        return start;
        //printMap(roomList[start]);


   }   
    

    
    //Converts Prufer sequence to 2-d matrix representation of a tree.
    private int[][] convertToTree(int[] prufer){
        int[][] dungeon = new int[10][10];
        int[] degree = new int[10];
        
        
        //Each node will have at least one edge connected to it, so each node
        //starts with a degree of one.
        for(int i = 0; i < 10; i++){
           degree[i] = 1; 
        }
        
        
        //Then, for each node, add a a degree for each time that node appears in
        //Prufer sequence.
        for(int i = 0; i < 8; i++){
            degree[prufer[i]] = degree[prufer[i]] + 1;
        }
        
        
        
        //Find the first node in the degree array with a degree of one.
        //Add the edge between that node and the current node in the Prufer
        //sequence. Decrement the degree of each node.
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 10; j++){
                if(degree[j] == 1){
                    dungeon[prufer[i]][j] = 1;
                    degree[prufer[i]] = degree[prufer[i]] - 1;
                    degree[j] = degree[j] - 1;
                    break;
                }
            }
        }
        
        
        //This method always leaves exactly two nodes in the degree array with
        //degrees of one. This segment finds those nodes and adds an edge between
        //them in the matrix.
        int u = -1;
        int v = -1;
        for(int i = 0; i < 10; i++){
            if(degree[i] == 1){
                if(u == -1){
                    u = i;
                }
                else{
                    v = i;
                    break;
                }    
            }
        }
        dungeon[u][v] = 1;
        degree[u] = degree[u] - 1;
        degree[v] = degree[v] - 1;
        
        return dungeon;
    }

    
    
    //Function for testing tree structure
    private void printMap(Room root){
        if(root != null){
           printMap(root.left);
           System.out.println(root.ID);
           printMap(root.center);
           printMap(root.right);
        }
    }
        
        
        
    //Function for asking which tunnel player will take
    private Room chooseNextRoom(Room room1){
        Scanner input = new Scanner(System.in);
        int cont = 1;
        String direction = "";
        Room nextRoom = room1;
                
        //All three tunnels are present
        if(room1.left != null && room1.center != null && room1.right != null){
            System.out.println("There are four tunnels in this cave, one to your left, one directly infront of you, one to your right "
                    + "and the tunnel you came in through. Which way will you go?");
            
                      
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("left")){
                    nextRoom = room1.left;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    nextRoom = room1.center;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    nextRoom = room1.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmm, that doesn't seem right, which way should I go?");
                }
            }
                        
        }
        
        //Only left and center tunnels present
        else if(room1.left != null && room1.center != null && room1.right == null){
            System.out.println("There are three tunnels in this cave, one to your left, one directly infront of you and one back the way"
                    + " you came. Which way will you go?");
            
                       
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("left")){
                    nextRoom = room1.left;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    nextRoom = room1.center;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }
        }
        
        //Only left and right tunnels present
        else if(room1.left != null && room1.center == null && room1.right != null){
            System.out.println("There are three tunnels in this cave, one to your left, one to your right and one back the way you came."
                    + " Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                }
                else if(direction.equals("left")){
                    nextRoom = room1.left;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    nextRoom = room1.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        //Only center and right tunnels present
        else if(room1.left == null && room1.center != null && room1.right != null){
            System.out.println("There are three tunnels in this cave, one directly infront of you, one to your right and one back the way "
                    + "you came. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    nextRoom = room1.center;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    nextRoom = room1.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }                        
        }
        
        //Only left tunnel present
        else if(room1.left != null && room1.center == null && room1.right == null){
            System.out.println("There are two tunnels in this cave, one to your left and one back the way you came. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("left")){
                    nextRoom = room1.left;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        //Only center tunnel present
        else if(room1.left == null && room1.center != null && room1.right == null){
            System.out.println("There are two tunnels in this cave, one directly infront of you and one back the way you came. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    nextRoom = room1.center;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        //Only right tunnel present
        else if(room1.left == null && room1.center == null && room1.right != null){
            System.out.println("There are two tunnels in this cave, one to your right and one back the way you came. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    nextRoom = room1.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        else{
            System.out.println("This cave is a dead end, the only way is back the way you came. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("back")){
                    nextRoom = room1.parent;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmm, I think I should go back the way I came.");
                }
            }
        }
        
       return nextRoom; 
    }

    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Map of weapons and corresponding damages
        Map<String, Integer> weapons = new HashMap<String, Integer>();
        weapons.put("Daggers", 15);
        weapons.put("Staff", 20);
        weapons.put("Sword", 10);
        
        //Create game world and get the starting room
        DungeonGame game = new DungeonGame();
        Room currRoom = game.createWorld();
        
        //Array that tracks how many rooms have been visited
        int[] visited = {0,0,0,0,0,0,0,0,0,0};
        visited[currRoom.ID] = 1;
        
        /*for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(dungeon[i][j]);
            }
            System.out.print("\n");
        }
        for(int i = 0; i < 10; i++){
            System.out.print(dungeon[i]);
    
        }*/
        
        //Character Creation
        String charClass = "";
        Scanner input = new Scanner(System.in);
        System.out.println("What is your character's name?");
        String name = input.nextLine();
        
        System.out.println("Choose a character class: Rogue, Mage or Warrior.");
        int cont = 1;
        while(cont == 1){           
            charClass = input.nextLine().toLowerCase();
            if(charClass.equals("rogue")){
                Rogue pc = new Rogue();
                pc.setName(name);
                pc.equip(weapons.get("Daggers"));
                
                cont = 0;
            }
            
            else if(charClass.equals("mage")){
                Mage pc = new Mage();
                pc.setName(name);
                pc.equip(weapons.get("Staff"));
                
                cont= 0;
            }
            
            else if(charClass.equals("warrior")){
                Warrior pc = new Warrior();
                pc.setName(name);
                pc.equip(weapons.get("Sword"));
                
                cont = 0;
            }
            else{
                System.out.println("Invalid class, please enter Rogue, Mage or Warrior");
            }
        }
        
        
        
        
        //First room sequence
        System.out.println("You awake in a dark room, your head pounds as you slowly rise a knee. "
                + "The damp smell tells you that you are definetely not in your room. As your eyes "
                + "adjust to the darkness, you see that you are in a some kind of cave.");
        
        cont = 1;
        String direction = "";
                
        //All three tunnels are present
        if(currRoom.left != null && currRoom.center != null && currRoom.right != null){
            System.out.println("There are three tunnels in this cave, one to your left, one directly infront of you and one to your right. "
                    + "Which way will you go?");
            
                      
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("left")){
                    currRoom = currRoom.left;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    currRoom = currRoom.center;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    currRoom = currRoom.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }
            }
            //Call function for entering new room    
        }
        
        //Only left and center tunnels present
        else if(currRoom.left != null && currRoom.center != null && currRoom.right == null){
            System.out.println("There are two tunnels in this cave, one to your left and one directly infront of you. Which way will you go?");
            
                       
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("left")){
                    currRoom = currRoom.left;
                    cont = 0;
                }
                else if(direction.equals("center")){
                    currRoom = currRoom.center;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?"); 
                }                
            }
        }
        
        //Only left and right tunnels present
        else if(currRoom.left != null && currRoom.center == null && currRoom.right != null){
            System.out.println("There are two tunnels in this cave, one to your left and one to your right. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("left")){
                    currRoom = currRoom.left;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    currRoom = currRoom.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?"); 
                }                
            }            
        }
        
        //Only center and right tunnels present
        else if(currRoom.left == null && currRoom.center != null && currRoom.right != null){
            System.out.println("There are two tunnels in this cave, one directly infront of you and one to your right. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("center")){
                    currRoom = currRoom.center;
                    cont = 0;
                }
                else if(direction.equals("right")){
                    currRoom = currRoom.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }                        
        }
        
        //Only left tunnel present
        else if(currRoom.left != null && currRoom.center == null && currRoom.right == null){
            System.out.println("There is a tunnel to your left. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("left")){
                    currRoom = currRoom.left;
                    cont = 0;                    
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        //Only center tunnel present
        else if(currRoom.left == null && currRoom.center != null && currRoom.right == null){
            System.out.println("There is a tunnel directly infront of you. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("center")){
                    currRoom = currRoom.center;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        //Only right tunnel present
        else if(currRoom.left == null && currRoom.center == null && currRoom.right != null){
            System.out.println("There is a tunnel to your right. Which way will you go?");
            
            while(cont == 1){
                direction = input.nextLine().toLowerCase();
                if(direction.equals("right")){
                    currRoom = currRoom.right;
                    cont = 0;
                }
                else{
                    System.out.println("Hmmmm, that doesn't seem like a good idea, which direction should I go?");
                }                
            }            
        }
        
        int roomCount = 1;
        System.out.println(currRoom.ID);
        /*visited[currRoom.ID] = 1;
        
        for(int i = 0; i < 10; i++){
            System.out.println(visited[i]);
        }*/
        
        
        //Traverses the map until all rooms have been visited at least once
        while(roomCount < 10){
            currRoom = game.chooseNextRoom(currRoom);
            System.out.println(currRoom.ID);
            if(visited[currRoom.ID] == 0){
                visited[currRoom.ID] = 1;
                roomCount++;
            }
        }
        
    }
}
