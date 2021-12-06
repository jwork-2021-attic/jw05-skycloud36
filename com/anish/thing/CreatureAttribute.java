package com.anish.thing;

import asciiPanel.AsciiPanel;

public interface CreatureAttribute {    

    public default String getTeam(){
        return null;
    }

    public default String getEnemyTeam(){
        return null;
    }

    public default void beAttacked(Thing attacker){}
    
    public default void Attack(Thing victim){}

    public default void Attack(){}

    public default boolean ifExist(){
        return false;
    }

    public default boolean ifSelected(){
        return false;
    }

    public default void beDead(){}

    public default int getHP(){return 0;}

    public default int getDefence(){return 0;}
    
    public default int getATK(){return 0;}

    public default int getMaxHP(){return 0;};

    public default void disPlayout(AsciiPanel terminal){}

    public static int UP = 0;
    public static int DOWN = 1;
    public static int RIGHT = 2;
    public static int LEFT = 3;

    public static String FLOOR = "Floor";
    public static String WALL = "Wall";
    public static String FIRST = "First";
    public static String SECOND = "Second";
    public static String BLUETEAM = "BlueTeam";
    public static String REDTEAM = "RedTeam";
    public static String BULLET = "Bullet";
    public static String PLAYER = "Player";
    public static String TESTSECOND = "TestSecond";
}
