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
public class Character {
    String name;
    int maxHealth;
    int currHealth;
    int level;
    double xp;
    double maxXp;
    int weapon;
    
    public Character(){
        maxHealth = 50;
        currHealth = 50;
        level = 1;
        xp = 0;
        maxXp = 50;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMaxHealth(){
        return maxHealth;
    }
    
    public int getCurrHealth(){
        return currHealth;
    }
    
    public int getLevel(){
        return level;
    }
    
    public double getXp(){
        return xp;
    }
    
    public double getMaxXp(){
        return maxXp;
    }
    
    public int getWeaponVal(){
        return weapon;
    }
    
    public int attack(){
        return weapon;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public void setCurrHealth(int c){
        currHealth = c;
    }
    
    public void equip(int w){
        weapon = w;
    }
    
    public void levelUp(){
        currHealth = maxHealth;
        maxHealth = maxHealth + 10;
        xp = xp - maxXp;
        maxXp = maxXp + (maxXp * 1.2);
    }
}
