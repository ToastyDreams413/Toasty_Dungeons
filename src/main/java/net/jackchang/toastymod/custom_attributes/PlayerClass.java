package net.jackchang.toastymod.custom_attributes;

public class PlayerClass {

    private String className;
    public int classNumber;
    public int HP, MP, defense, attack, trueDefense, speed, evasion, luckOfTheSoul, level, xp;
    public int[] stats;

    public PlayerClass() {

    }

    public PlayerClass(String className) {
        this.className = className;
    }

    public String getClassName() { return className; }

}
