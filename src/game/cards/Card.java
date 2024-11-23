package game.cards;

import java.util.ArrayList;
import fileio.input.CardInput;
import game.Player;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors; // poate sa fie enum
    private String name;

    private boolean attacked = false;
    /**
     * Checks if the current card has already attacked during this turn.
     *
     * @return {@code true} if the card has attacked, {@code false} otherwise.
     */
    public boolean usedAttack() {
        return attacked;
    }

    public final void setAttacked(final boolean attacked) {
        this.attacked = attacked;
    }

    private boolean frozen = false;
    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
    public final boolean isFrozen() {
        return frozen;
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
    /**
     * Sets the health value of the current card.
     *
     * @param health the new health value to be assigned to the card as an integer.
     */
    public void setHealth(final int health) {
        this.health = health;
    }
    /**
     * Retrieves the description of the current card.
     *
     * @return the description of the card as a {@code String}.
     */
    public final String getDescription() {
        return description;
    }
    /**
     * Sets the description of the current card.
     *
     * @param description the new description to be assigned to the card as a {@code String}.
     */
    public void setDescription(final String description) {
        this.description = description;
    }
    /**
     * Retrieves the colors associated with the current card.
     *
     * @return an {@code ArrayList<String>} representing the colors of the card.
     */
    public final ArrayList<String> getColors() {
        return colors;
    }
    /**
     * Sets the colors associated with the current card.
     *
     * @param colors the {@code ArrayList<String>} representing the new colors to be
     *               assigned to the card.
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }
    /**
     * Retrieves the name of the current card.
     *
     * @return the name of the card as a {@code String}.
     */
    public final String getName() {
        return name;
    }
    /**
     * Sets the name of the current card.
     *
     * @param name the new name to be assigned to the card.
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * Retrieves the attack damage of the current card.
     *
     * @return the attack damage value of the card as an integer.
     */
    public final int getAttackDamage() {
        return attackDamage;
    }
    /**
     * Sets the attack damage of the current card.
     *
     * @param attackDamage the new attack damage value to be assigned to the card.
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.attackDamage = card.getAttackDamage();
        this.name = card.getName();
    }

    public Card(final Card card) {
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.attackDamage = card.getAttackDamage();
        this.name = card.getName();
    }
    /**
     * Attempts to place a card from the player's hand onto the game board.
     * This method currently has no implementation and always returns {@code null}.
     *
     * @param player the {@code Player} attempting to place the card.
     * @param handIdx the index of the card in the player's hand to be placed.
     * @return {@code null} since the method has no implementation.
     */
    public String putCard(final Player player, final int handIdx) {
        return null;
    }
    /**
     * Executes an attack from the current card on the specified target card.
     * The attack reduces the target card's health by the attack damage of the current card.
     * The current card is also marked as having attacked.
     *
     * @param card the {@code Card} object that is the target of the attack.
     */
    public void attackCard(final Card card) {
        card.setHealth(card.getHealth() - this.attackDamage);
        this.setAttacked(true); // AICI AM SCHIMBAT
    }
    /**
     * Checks if the current card is of type "Tank".
     * A card is considered a "Tank" if its name is "Goliath" or "Warden".
     *
     * @return {@code true} if the card is a "Tank", {@code false} otherwise.
     */
    public boolean isTank() {
        return this.getName().equals("Goliath")
                || this.getName().equals("Warden");
    }
    /**
     * Applies the special ability of the current card to the specified target card.
     * This method currently has no implementation but can be extended or overridden
     * in subclasses to define specific behavior.
     *
     * @param card the {@code Card} object that is the target of the special ability.
     */
    public void specialAbility(final Card card) {
    }

}
