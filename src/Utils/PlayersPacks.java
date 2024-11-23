package Utils;

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

public class PlayersPacks {
    private static PlayersPacks instance = null;
    private PlayersPacks() {}

    public static PlayersPacks getInstance() {
        if (instance == null) {
            instance = new PlayersPacks();
        }
        return instance;
    }

    // TODO: ia tot de deckuri si pune aici
    int nrCardsInDeckPlayer1, nrCardsInDeckPlayer2;
    public int nrDecksPlayer1, nrDecksPlayer2;
    public ArrayList<ArrayList<Card>> decksPlayer1 = new ArrayList<>();
    public ArrayList<ArrayList<Card>> decksPlayer2 = new ArrayList<>();
    public Integer victoriesPlayer1;
    public Integer victoriesPlayer2;
    public void addDecks(Input inputData) {
        nrCardsInDeckPlayer1 = inputData.getPlayerOneDecks().getNrCardsInDeck();
        nrCardsInDeckPlayer2 = inputData.getPlayerTwoDecks().getNrCardsInDeck();

        nrDecksPlayer1 = inputData.getPlayerOneDecks().getNrDecks();
        nrDecksPlayer2 = inputData.getPlayerTwoDecks().getNrDecks();

//        System.out.println("nrDecksPlayer1: " + nrDecksPlayer1);
        for (int i = 0; i < nrDecksPlayer1; i++) {
            decksPlayer1.add(createDeck(inputData.getPlayerOneDecks().getDecks().get(i)));

//            System.out.println("Deck " + i + ":");
//            for (int j = 0; j < decksPlayer1.get(i).size(); j++) {
//                System.out.print(decksPlayer1.get(i).get(j).getName() + " ");
//            }
        }

//        System.out.println("nrDecksPlayer2: " + nrDecksPlayer2);
        for (int i = 0; i < nrDecksPlayer2; i++) {
            decksPlayer2.add(createDeck(inputData.getPlayerTwoDecks().getDecks().get(i)));

//            System.out.println("Deck " + i + ":");
//            for (int j = 0; j < decksPlayer2.get(i).size(); j++) {
//                System.out.print(decksPlayer2.get(i).get(j).getName() + " ");
//            }
        }
    }

    public ArrayList<Card> createDeck(ArrayList<CardInput> deck) {
        ArrayList<Card> newDeck = new ArrayList<>();
        for (CardInput card: deck) {
            switch(card.getName()) {
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
                    System.out.println("Invalid card name: " + card.getName() + " in createDeck method.");
                    return null;
            }
        }
        return newDeck;
    }
    public void clear() {
        decksPlayer1.clear();
        decksPlayer2.clear();
    }
}
