package com.anish.thing;

public interface CreatureAttribute {    

    public default String getTeam(){
        return null;
    }

    public default String getEnemyTeam(){
        return null;
    }

    public default void beAttacked(Thing attacker){}
    
    public default void Attack(Thing victim){}

    public default boolean ifExist(){
        return false;
    }

    public default void beDead(){}
}
