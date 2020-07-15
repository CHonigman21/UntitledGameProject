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
public class World {
    Room start;

    public World(Room r){
        start = r;
    }
    
    public void addLeft(String t, int id, int n){
        Room newRoom = new Room(t, id, n);
        start.left = newRoom;
    }
    
 
}




