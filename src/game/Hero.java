package game;

import fileio.input.CardInput;
import game.cards.Card;

import java.util.ArrayList;

public abstract class Hero {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    static final int DEFAULT_HEALTH = 30;

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

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public Hero(final CardInput card) {
        this.mana = card.getMana();
        this.health = DEFAULT_HEALTH;
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }
    /**
     * Defines the special ability of the card, to be implemented by subclasses.
     * This ability is applied to all cards in the specified row.
     *
     * @param attackedRow an {@code ArrayList<Card>} representing the row of cards affected by the
     *                    ability.
     */
    public abstract void specialAbility(ArrayList<Card> attackedRow);
    /**
     * Executes an attack on the hero by the specified card.
     * This method reduces the hero's health by the attack damage of the card
     * and marks the card as having attacked.
     *
     * @param card the {@code Card} object performing the attack on the hero.
     */
    public void attackHero(final Card card) {
        card.setAttacked(true);
        this.setHealth(this.getHealth() - card.getAttackDamage());
    }
}
