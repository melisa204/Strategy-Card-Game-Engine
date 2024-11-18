package fileio.output;

import java.util.ArrayList;
import game.cards.Card;

public class CardOutput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;

    private ArrayList<String> colors; // poate sa fie enum
    private String name;


    public CardOutput(Card card) {
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = new ArrayList<>();
        for (String color : card.getColors()) {
            colors.add(color);
        }
        this.name = card.getName();
        this.attackDamage = card.getAttackDamage();
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}
