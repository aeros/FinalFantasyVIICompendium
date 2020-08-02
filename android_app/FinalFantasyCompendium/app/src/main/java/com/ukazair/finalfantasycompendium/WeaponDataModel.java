package com.ukazair.finalfantasycompendium;

public class WeaponDataModel {

    private String character, name, attack, accuracy, magic, cost;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "character: " + character + '\n' +
                "name: " + name + '\n' +
                "attack: " + attack + '\n' +
                "accuracy: " + accuracy + '\n' +
                "magic: " + magic + '\n' +
                "cost: " + cost + '\n';
    }
}

