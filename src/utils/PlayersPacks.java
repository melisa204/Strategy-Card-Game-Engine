package utils;

import java.util.ArrayList;

import fileio.input.Input;
import game.cards.Card;
import fileio.input.CardInput;
import game.cards.minions.BackRowCard;
import game.cards.minions.FrontRowCard;
import game.cards.minions.specialMinions.TheRipper;
import game.cards.minions.specialMinions.Disciple;
import game.cards.minions.specialMinions.Miraj;
import game.cards.minions.specialMinions.TheCursedOne;

public final class PlayersPacks {
    private static PlayersPacks instance = null;
    private PlayersPacks() { }
    /**
     * Retrieves the singleton instance of the {@code PlayersPacks} class.
     * If the instance does not already exist, it is created.
     *
     * @return the singleton instance of {@code PlayersPacks}.
     */
    public static PlayersPacks getInstance() {
        if (instance == null) {
            instance = new PlayersPacks();
        }
        return instance;
    }

    // ia tot de deckuri si pune aici
    private int nrCardsInDeckPlayer1, nrCardsInDeckPlayer2;
    private int nrDecksPlayer1, nrDecksPlayer2;
    public ArrayList<ArrayList<Card>> decksPlayer1 = new ArrayList<>();
    public ArrayList<ArrayList<Card>> decksPlayer2 = new ArrayList<>();
    public Integer victoriesPlayer1;
    public Integer victoriesPlayer2;
    /**
     * Initializes and adds decks for both Player 1 and Player 2 based on the provided input data.
     * This method performs the following actions:
     * - Retrieves the number of cards per deck and the number of decks for each player.
     * - Creates decks for Player 1 and Player 2 using the input data and adds them to their
     * respective collections.
     *
     * @param inputData an {@code Input} object containing the details of the decks for both
     *                  players, including the number of decks and the cards in each deck.
     */
    public void addDecks(final Input inputData) {
        nrCardsInDeckPlayer1 = inputData.getPlayerOneDecks().getNrCardsInDeck();
        nrCardsInDeckPlayer2 = inputData.getPlayerTwoDecks().getNrCardsInDeck();

        nrDecksPlayer1 = inputData.getPlayerOneDecks().getNrDecks();
        nrDecksPlayer2 = inputData.getPlayerTwoDecks().getNrDecks();

        for (int i = 0; i < nrDecksPlayer1; i++) {
            decksPlayer1.add(createDeck(inputData.getPlayerOneDecks().getDecks().get(i)));

        }

        for (int i = 0; i < nrDecksPlayer2; i++) {
            decksPlayer2.add(createDeck(inputData.getPlayerTwoDecks().getDecks().get(i)));
        }
    }
    /**
     * Creates a new deck of cards based on the given input list.
     * Each card in the input list is converted to a specific card type
     * and added to the new deck based on its name.
     *
     * @param deck an {@code ArrayList<CardInput>} representing the card data used to create the
     *            deck.
     * @return an {@code ArrayList<Card>} containing the created deck, or {@code null}
     *         if an unrecognized card name is encountered.
     */
    public ArrayList<Card> createDeck(final ArrayList<CardInput> deck) {
        ArrayList<Card> newDeck = new ArrayList<>();
        for (CardInput card: deck) {
            switch (card.getName()) {
                case "Sentinel", "Berserker":
                    newDeck.add(new BackRowCard(card));
                    break;
                case "Goliath", "Warden":
                    newDeck.add(new FrontRowCard(card));
                    break;
                case "The Ripper":
                    newDeck.add(new TheRipper(card));
                    break;
                case "Miraj":
                    newDeck.add(new Miraj(card));
                    break;
                case "The Cursed One":
                    newDeck.add(new TheCursedOne(card));
                    break;
                case "Disciple":
                    newDeck.add(new Disciple(card));
                    break;
                default:
                    return null;
            }
        }
        return newDeck;
    }
    /**
     * Clears all decks for both Player 1 and Player 2.
     * This method removes all cards from the decks of both players.
     */
    public void clear() {
        decksPlayer1.clear();
        decksPlayer2.clear();
    }
}
