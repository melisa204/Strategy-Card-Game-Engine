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

    public boolean usedAttack() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    private boolean frozen = false;
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    public boolean isFrozen() {
        return frozen;
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

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Card(CardInput card) {
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.attackDamage = card.getAttackDamage();
        this.name = card.getName();
    }

    public Card(Card card) { // TODO: de adaugat restul de campuri dupa ce le adaug
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.attackDamage = card.getAttackDamage();
        this.name = card.getName();
    }

    // ceva cu isfrozen? -> camp si metoda?
    // metoda unde le asez?
    public String putCard(Player player, int handIdx) {
        return null;
    }

    public void attackCard(Card card) {
        card.setHealth(card.getHealth() - this.attackDamage);
        this.setAttacked(true); // AICI AM SCHIMBAT
    }

    public boolean isTank() {
        return this.getName().equals("Goliath") || this.getName().equals("Warden");
    }

    public void specialAbility(Card card) {
    }

}
