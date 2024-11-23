package game;

import game.cards.Card;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> currentDeck;
    private ArrayList<Card> currentHand; // cartile pe care le are in mana
    private ArrayList<Card> frontRow;
    private ArrayList<Card> backRow;
    private Hero hero;

    public boolean isUsedHero() {
        return usedHero;
    }

    public void setUsedHero(boolean usedHero) {
        this.usedHero = usedHero;
    }

    private boolean usedHero = false;

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(ArrayList<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }

    public ArrayList<Card> getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(ArrayList<Card> currentHand) {
        this.currentHand = currentHand;
    }

    public ArrayList<Card> getFrontRow() {
        return frontRow;
    }

    public void setFrontRow(ArrayList<Card> frontRow) {
        this.frontRow = frontRow;
    }

    public ArrayList<Card> getBackRow() {
        return backRow;
    }

    public void setBackRow(ArrayList<Card> backRow) {
        this.backRow = backRow;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
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
