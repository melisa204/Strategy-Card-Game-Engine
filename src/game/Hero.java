package game;

import fileio.input.CardInput;
import game.cards.Card;

import java.util.ArrayList;

public abstract class Hero {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors; // poate sa fie enum
    private String name;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero(CardInput card) {
        this.mana = card.getMana();
        this.health = 30;
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    abstract public void specialAbility(ArrayList<Card> attackedRow);

    public void attackHero(Card card) {
        this.setHealth(this.getHealth() - card.getAttackDamage());
    }
}
