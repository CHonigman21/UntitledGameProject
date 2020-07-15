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
public class Weapon {
    int damage;
    
    public Weapon(int d){
        damage = d;
    }
    
    public int getDamage(){
        return damage;
    }
}
