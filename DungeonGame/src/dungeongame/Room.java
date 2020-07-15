/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeongame;

/**
 *
 * @author Honigmaster
 */
public class Room {
    String type;
    int ID;
    int numChars;
    Room left;
    Room right;
    Room center;
    Room parent;

    
    public Room(String t, int id, int n){
        type = t;
        ID = id;
        numChars = n;
        left = null;
        center = null;
        right = null;
        parent = null;
    }
    
    public void addLeft(String t, int id, int n){
        Room newRoom = new Room(t, id, n);
        left = newRoom;
    }
    
    public void addRight(String t, int id, int n){
        Room newRoom = new Room(t, id, n);
        right = newRoom;
    }   

    public void addCenter(String t, int id, int n){
        Room newRoom = new Room(t, id, n);
        center = newRoom;
    }
    
}
