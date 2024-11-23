package game;

import game.cards.Card;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> currentDeck;
    private ArrayList<Card> currentHand; // cartile pe care le are in mana
    private ArrayList<Card> frontRow;
    private ArrayList<Card> backRow;
    private Hero hero;
    /**
     * Checks if the player's hero has been used during the current turn.
     *
     * @return {@code true} if the hero has been used, {@code false} otherwise.
     */
    public boolean isUsedHero() {
        return usedHero;
    }
    /**
     * Sets whether the player's hero has been used during the current turn.
     *
     * @param usedHero a {@code boolean} indicating if the hero has been used:
     *                 {@code true} if the hero has been used, {@code false} otherwise.
     */
    public void setUsedHero(final boolean usedHero) {
        this.usedHero = usedHero;
    }

    private boolean usedHero = false;
    /**
     * Retrieves the player's current deck of cards.
     *
     * @return an {@code ArrayList<Card>} representing the cards in the player's current deck.
     */
    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }
    /**
     * Sets the player's current deck to the specified list of cards.
     *
     * @param currentDeck the {@code ArrayList<Card>} representing the new deck of cards for the
     *                    player.
     */
    public void setCurrentDeck(final ArrayList<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }
    /**
     * Retrieves the player's current hand of cards.
     *
     * @return an {@code ArrayList<Card>} representing the cards in the player's current hand.
     */
    public ArrayList<Card> getCurrentHand() {
        return currentHand;
    }
    /**
     * Sets the player's current hand to the specified list of cards.
     *
     * @param currentHand the {@code ArrayList<Card>} representing the new hand of cards for the
     *                    player.
     */
    public void setCurrentHand(final ArrayList<Card> currentHand) {
        this.currentHand = currentHand;
    }
    /**
     * Retrieves the player's front row of cards.
     *
     * @return an {@code ArrayList<Card>} representing the cards in the player's front row.
     */
    public ArrayList<Card> getFrontRow() {
        return frontRow;
    }
    /**
     * Sets the player's front row to the specified list of cards.
     *
     * @param frontRow the {@code ArrayList<Card>} representing the new front row of cards
     *                 for the player.
     */
    public void setFrontRow(final ArrayList<Card> frontRow) {
        this.frontRow = frontRow;
    }
    /**
     * Retrieves the player's back row of cards.
     *
     * @return an {@code ArrayList<Card>} representing the cards in the player's back row.
     */
    public ArrayList<Card> getBackRow() {
        return backRow;
    }
    /**
     * Sets the player's back row to the specified list of cards.
     *
     * @param backRow the {@code ArrayList<Card>} representing the new back row of cards for the
     *                player.
     */
    public void setBackRow(final ArrayList<Card> backRow) {
        this.backRow = backRow;
    }
    /**
     * Retrieves the player's current hero.
     *
     * @return the {@code Hero} object representing the player's hero.
     */
    public Hero getHero() {
        return hero;
    }
    /**
     * Sets the player's hero to the specified hero.
     *
     * @param hero the {@code Hero} object to be assigned to the player.
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }
    /**
     * Retrieves the current mana value of the player.
     *
     * @return the current mana value as an integer.
     */
    public int getMana() {
        return mana;
    }
    /**
     * Sets the player's mana to the specified value.
     *
     * @param mana the new mana value to be assigned to the player.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    private int mana = 0; // initial e 0

    public Player() {
        currentDeck = new ArrayList<>();
        currentHand = new ArrayList<>();
        frontRow = new ArrayList<>();
        backRow = new ArrayList<>();
    }
}
