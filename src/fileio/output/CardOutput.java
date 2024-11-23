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


    public CardOutput(final Card card) {
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

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }
}
