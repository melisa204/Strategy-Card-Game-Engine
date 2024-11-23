package game;

import fileio.input.CardInput;
import fileio.input.Coordinates;
import fileio.input.StartGameInput;
import fileio.output.CardOutput;
import game.cards.Card;
import game.cards.minions.BackRowCard;
import game.cards.minions.FrontRowCard;
import game.cards.minions.specialMinions.Disciple;
import game.cards.minions.specialMinions.Miraj;
import game.cards.minions.specialMinions.TheCursedOne;
import game.cards.minions.specialMinions.TheRipper;
import game.heroes.EmpressThorina;
import game.heroes.GeneralKocioraw;
import game.heroes.KingMudface;
import game.heroes.LordRoyce;
import Utils.PlayersPacks;

import javax.management.openmbean.ArrayType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    int startingPlayer;
    Player player1 = new Player();
    Player player2 = new Player();
    int currentPlayer;
    PlayersPacks playersPacks = PlayersPacks.getInstance();
    int shuffleSeed;
    int currentRound = 0;

    // TODO: am nevoie de deck ul fiecarui jucator, de eroul selectat
    public Game(StartGameInput startGameInput) {
        player1.setHero(selectHero(startGameInput.getPlayerOneHero()));
        player2.setHero(selectHero(startGameInput.getPlayerTwoHero()));
        startingPlayer = startGameInput.getStartingPlayer();
        shuffleSeed = startGameInput.getShuffleSeed();

        player1.setCurrentDeck(this.shuffleDeck(copyDeck(playersPacks.decksPlayer1.get(startGameInput.getPlayerOneDeckIdx()))));
        player2.setCurrentDeck(this.shuffleDeck(copyDeck(playersPacks.decksPlayer2.get(startGameInput.getPlayerTwoDeckIdx()))));

        currentPlayer = startingPlayer;
        this.startRound();
    }
    public Hero selectHero(CardInput cardInput) {
        switch(cardInput.getName()) {
            case "Empress Thorina":
                return new EmpressThorina(cardInput);
            case "General Kocioraw":
                return new GeneralKocioraw(cardInput);
            case "King Mudface":
                return new KingMudface(cardInput);
            case "Lord Royce":
                return new LordRoyce(cardInput);
            default:
                return null;
        }
    }
    public ArrayList<Card> shuffleDeck(ArrayList<Card> deck) {
        Random random = new Random(shuffleSeed);

        ArrayList<Card> copiedDeck = new ArrayList<>();

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes, random);

        // copiez din deck-ul proaspat copiat cum trebuie in functie de mosteniri, in copiedDeck adaugand doar referintele deoarece nu stiu ce tip are fiecare carte
        for (int i = 0; i < deck.size(); i++) {
            copiedDeck.add(deck.get(indexes.get(i)));
        }

        return copiedDeck;
    }

    public ArrayList<CardOutput> getPlayerDeck(int currentPlayerIndex) {
        ArrayList<Card> currentDeck;
        if (currentPlayerIndex == 1) {
            currentDeck = player1.getCurrentDeck();
        } else {
            currentDeck = player2.getCurrentDeck();
        }
        ArrayList<CardOutput> result = new ArrayList<>();
        for (Card card: currentDeck) {
            result.add(new CardOutput(card));
        }
        return result;
    }

    public Hero getPlayerHero(int currentPlayerIndex) {
        Hero currentHero;
        if (currentPlayerIndex == 1) {
            currentHero = player1.getHero();
        } else {
            currentHero = player2.getHero();
        }
        return currentHero;
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }

    public void startRound() {
        if (player1.getCurrentDeck().size() > 0) { // TODO: trebuie sa si afisez ceva??
            player1.getCurrentHand().add(player1.getCurrentDeck().get(0));
            player1.getCurrentDeck().remove(0);
        }
        if (player2.getCurrentDeck().size() > 0) {
            player2.getCurrentHand().add(player2.getCurrentDeck().get(0));
            player2.getCurrentDeck().remove(0);
        }

        if (currentRound < 10) {
            currentRound++;
        }

        player1.setMana(player1.getMana() + currentRound);
        player2.setMana(player2.getMana() + currentRound);
//        System.out.println("player1 mana: " + player1.getMana());
//        System.out.println("player2 mana: " + player2.getMana());
    }

    public void endPlayerTurn() {
        // resetez cartile de pe masa
        if (currentPlayer == 1) {
            for (Card card : player1.getBackRow()) {
                if (card.isFrozen())
                    card.setFrozen(false);
                if (card.usedAttack())
                    card.setAttacked(false);
            }
            for (Card card : player1.getFrontRow()) {
                if (card.isFrozen())
                    card.setFrozen(false);
                if (card.usedAttack())
                    card.setAttacked(false);
            }
            player1.setUsedHero(false);
            currentPlayer = 2;
        } else {
            for (Card card : player2.getBackRow()) {
                if (card.isFrozen())
                    card.setFrozen(false);
                if (card.usedAttack())
                    card.setAttacked(false);
            }
            for (Card card : player2.getFrontRow()) {
                if (card.isFrozen())
                    card.setFrozen(false);
                if (card.usedAttack())
                    card.setAttacked(false);
            }
            player2.setUsedHero(false);
            currentPlayer = 1;
        }

        System.out.println("Acum sunt: " + currentPlayer);

        if (currentPlayer == startingPlayer)
            startRound();

    }

    public String placeCard(int handIdx) {
//        System.out.println("Placing card" + handIdx);
        String result = null;
        Player auxPlayer;
        if (currentPlayer == 1)
            auxPlayer = player1;
        else
            auxPlayer = player2;

        result = auxPlayer.getCurrentHand().get(handIdx).putCard(auxPlayer, handIdx);
//        if (result != "")
//            System.out.println(result);
//        else
//            System.out.println("Card placed without message");
        return result;
    }

    public ArrayList<CardOutput> getCardsInHand(int currentPlayerIndex) {
        ArrayList<Card> currentHand;
        if (currentPlayerIndex == 1) {
            currentHand = player1.getCurrentHand();
        } else {
            currentHand = player2.getCurrentHand();
        }
        ArrayList<CardOutput> result = deckToOutputDeck(currentHand);
        return result;
    }

    public int getPlayerMana(int playerIdx) {
        if (playerIdx == 1)
            return player1.getMana();
        else
            return player2.getMana();
    }

    public ArrayList<ArrayList<CardOutput>> getCardsOnTable() {
        ArrayList<ArrayList<CardOutput>> table = new ArrayList<>();

        table.add(deckToOutputDeck(player2.getBackRow()));
        table.add(deckToOutputDeck(player2.getFrontRow()));
        table.add(deckToOutputDeck(player1.getFrontRow()));
        table.add(deckToOutputDeck(player1.getBackRow()));

        return table;
    }

    public ArrayList<CardOutput> getFrozenCardsOnTable() {
        ArrayList<CardOutput> tableFrozen = new ArrayList<>();

        for (Card card : player2.getBackRow()) {
            if (card.isFrozen()) {
                tableFrozen.add(new CardOutput(card));
            }
        }

        for (Card card : player2.getFrontRow()) {
            if (card.isFrozen()) {
                tableFrozen.add(new CardOutput(card));
            }
        }

        for (Card card : player1.getFrontRow()) {
            if (card.isFrozen()) {
                tableFrozen.add(new CardOutput(card));
            }
        }

        for (Card card : player1.getBackRow()) {
            if (card.isFrozen()) {
                tableFrozen.add(new CardOutput(card));
            }
        }

        return tableFrozen;
    }

    public String cardUsesAttack(Coordinates attackerPos, Coordinates attackedPos) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackedPlayer = (currentPlayer == 2) ? player1 : player2;
        Card attackerCard = this.getCardAt(attackerPos);
        Card defenderCard = this.getCardAt(attackedPos);

        String result = "";

        // incep verificarile
        if ((attackedPos.getX() < 2 && attackerPos.getX() < 2) || (attackedPos.getX() >= 2 && attackerPos.getX() >= 2)) {
            // verific daca cartea atacata este una dintre cartile mele
            result = "Attacked card does not belong to the enemy.";
        } else if (attackerCard.usedAttack()) {
            // verific daca cartea a atacat deja
            result = "Attacker card has already attacked this turn.";
        } else if (attackerCard.isFrozen()) {
            // verific daca cartea este inghetata
            result = "Attacker card is frozen.";
        } else {
            // caut un tank, iar daca exista il atac pe acela
            if (!defenderCard.isTank()) {
                for (Card card : attackedPlayer.getFrontRow()) {
                    if (card.getName().equals("Goliath") || card.getName().equals("Warden")) {

                        if (card == defenderCard) {
                            result = "";
                            attackerCard.attackCard(card);
                            if (card.getHealth() <= 0) {
                                attackedPlayer.getFrontRow().remove(card);
                            }
                        } else {
                            result = "Attacked card is not of type 'Tank'.";
                        }
                        return result;
                    }
                }
            }
            if (attackerCard.usedAttack() == false) {
                // daca nu am gasit tank, atac cartea de pe pozitia attackedPos
                attackerCard.attackCard(defenderCard);
                if (defenderCard.getHealth() <= 0) {
                    // daca cartea atacata a murit, o elimin
                    switch (attackedPos.getX()) {
                        case 0:
                            player2.getBackRow().remove(attackedPos.getY());
                            break;
                        case 1:
                            player2.getFrontRow().remove(attackedPos.getY());
                            break;
                        case 2:
                            player1.getFrontRow().remove(attackedPos.getY());
                            break;
                        case 3:
                            player1.getBackRow().remove(attackedPos.getY());
                            break;
                    }
                }
            }
        }
        return result;
    }

    public String cardUsesAbility(Coordinates attackerPos, Coordinates attackedPos) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackedPlayer = (currentPlayer == 2) ? player1 : player2;
        Card attackerCard = this.getCardAt(attackerPos);
        Card defenderCard = this.getCardAt(attackedPos);

        String result = "";
        System.out.println("numele atacatorului este: " + attackerCard.getName());
        System.out.println("ataca jucatorul cu eroul: " + attackedPlayer.getHero().getName());

        System.out.println("attacker e freeze: " + attackerCard.isFrozen());
        System.out.println("attacker a atacat: " + attackerCard.usedAttack());
        System.out.println("e ripper?" + attackerCard.getName().equals("Disciple"));


        // incep verificarile
        if (attackerCard.isFrozen()) {
            // verific daca cartea atacatorului este inghetata
            result = "Attacker card is frozen.";
            System.out.println("am vazut ca nu e freeze");
        } else if (attackerCard.usedAttack()) {
            // verific daca cartea a atacat deja
            result = "Attacker card has already attacked this turn.";
            System.out.println("am vazut ca nu e used");
        } else if (attackerCard.getName().equals("Disciple")) {
            // Disciple nu trebuie sa atace tankuri prima data, il aplic direct pe cartea pe care am primit o
            if ((attackedPos.getX() < 2 && attackerPos.getX() > 1) || (attackedPos.getX() > 1 && attackerPos.getX() < 2))
                result = "Attacked card does not belong to the current player.";
            else {
                attackerCard.specialAbility(this.getCardAt(attackedPos));
            }
        } else if (attackerCard.getName().equals("The Ripper") || attackerCard.getName().equals("Miraj") || attackerCard.getName().equals("The Cursed One")) {
            System.out.println("am match pe nume");
            if ((attackedPos.getX() < 2 && attackerPos.getX() < 2) || (attackedPos.getX() >= 2 && attackerPos.getX() >= 2)) {
                // verific daca cartea atacata este una dintre cartile mele
                result = "Attacked card does not belong to the enemy.";
                System.out.println("am vazut ca nu e a mea");
            } else {
                // caut un tank, iar daca exista il atac pe acela
                if (!defenderCard.isTank()) {
                    for (Card card : attackedPlayer.getFrontRow()) {
                        if (card.isTank()) {
                            if (card == defenderCard) {
                                result = "";
                                attackerCard.specialAbility(card);
                                if (card.getHealth() <= 0) {
                                    attackedPlayer.getFrontRow().remove(card);
                                }
                            } else {
                                result = "Attacked card is not of type 'Tank'.";
                            }
                            System.out.println("Abilitate pe tank la " + attackedPos.getX() + " " + attackedPos.getY());
                            return result;
                        }
                    }
                }
                if (attackerCard.usedAttack() == false) {
                    // daca nu am gasit tank, atac cartea de pe pozitia attackedPos
                    attackerCard.specialAbility(this.getCardAt(attackedPos));
                    System.out.println("Abilitate pe " + attackedPos.getX() + " " + attackedPos.getY());
                    if (this.getCardAt(attackedPos).getHealth() <= 0) {
                        // daca cartea atacata a murit, o elimin
                        switch (attackedPos.getX()) {
                            case 0:
                                player2.getBackRow().remove(attackedPos.getY());
                                break;
                            case 1:
                                player2.getFrontRow().remove(attackedPos.getY());
                                break;
                            case 2:
                                player1.getFrontRow().remove(attackedPos.getY());
                                break;
                            case 3:
                                player1.getBackRow().remove(attackedPos.getY());
                                break;
                            }
                        }
                }
            }
        }
        System.out.println("Mesajul din abilitate: " + result);
        return result;
    }

    public String useAttackHero(Coordinates attackerPos) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackedPlayer = (currentPlayer == 2) ? player1 : player2;
        Card attackerCard = this.getCardAt(attackerPos);

        String result = "";
        if (attackerCard == null) {
            return "";
        }
        // incep verificarile
        if (attackerCard.isFrozen()) {
            // verific daca cartea este inghetata
            result = "Attacker card is frozen.";
        } else if (attackerCard.usedAttack()){
            result = "Attacker card has already attacked this turn.";
        } else {
            // caut un tank, iar daca exista il atac pe acela
            for (Card card : attackedPlayer.getFrontRow()) {
                if (card.getName().equals("Goliath") || card.getName().equals("Warden")) {
//                    attackerCard.attackCard(card);
                    result = "Attacked card is not of type 'Tank'.";
//                    if (card.getHealth() <= 0) {
//                        attackedPlayer.getFrontRow().remove(card);
//                    }
                    //result = "";
                    return result;
                }
            }
            if (attackerCard.usedAttack() == false) {
                // daca nu am gasit tank, atac cartea de pe pozitia attackedPos
                attackedPlayer.getHero().attackHero(attackerCard);
                if (attackedPlayer.getHero().getHealth() <= 0) {
                    // daca cartea atacata a murit, o elimin
                    if (currentPlayer == 1) {
                        result = "Player one killed the enemy hero.";
                        playersPacks.victoriesPlayer1++;
                    } else {
                        result = "Player two killed the enemy hero.";
                        playersPacks.victoriesPlayer2++;
                    }
                }
            }
        }
        return result;
    }

    public String useHeroAbility(int attackedRow) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackerPlayer = (currentPlayer == 2) ? player2 : player1;
        ArrayList<Card> affectedRow = this.getRowAt(attackedRow);

        String result = "";

        // incep verificarile
        if (attackerPlayer.getMana() < attackerPlayer.getHero().getMana()) {
            // nu am destula mana
            result = "Not enough mana to use hero's ability.";
        } else if (attackerPlayer.isUsedHero()) {
            result = "Hero has already attacked this turn.";
        } else if (attackerPlayer.getHero().getName().equals("Lord Royce") || attackerPlayer.getHero().getName().equals("Empress Thorina")) {
            if ((currentPlayer == 1 && attackedRow >= 2) || (currentPlayer == 2 && attackedRow < 2)) {
                result = "Selected row does not belong to the enemy.";
            } else {
                attackerPlayer.getHero().specialAbility(affectedRow);
                attackerPlayer.setMana(attackerPlayer.getMana() - attackerPlayer.getHero().getMana());
                attackerPlayer.setUsedHero(true);
            }
        } else if (attackerPlayer.getHero().getName().equals("General Kocioraw") || attackerPlayer.getHero().getName().equals("King Mudface")) {
            if ((currentPlayer == 1 && attackedRow < 2) || (currentPlayer == 2 && attackedRow >= 2)) {
                result = "Selected row does not belong to the current player.";
            } else {
                attackerPlayer.getHero().specialAbility(affectedRow);
                attackerPlayer.setMana(attackerPlayer.getMana() - attackerPlayer.getHero().getMana());
                attackerPlayer.setUsedHero(true);
            }
        }

        return result;
    }

    // utility functions:
    private ArrayList<CardOutput> deckToOutputDeck(ArrayList<Card> deck) {
        ArrayList<CardOutput> result = new ArrayList<>();
        for (Card card: deck) {
            result.add(new CardOutput(card));
        }
        return result;
    }

    private ArrayList<Card> copyDeck(ArrayList<Card> deck) {
        ArrayList<Card> copiedDeck = new ArrayList<>();
        for (Card card : deck) {
            switch (card.getName()) {
                case "Sentinel", "Berserker":
                    copiedDeck.add(new BackRowCard(card));
                    break;
                case "Goliath", "Warden":
                    copiedDeck.add(new FrontRowCard(card));
                    break;
                case "The Ripper":
                    copiedDeck.add(new TheRipper(card));
                    break;
                case "Miraj":
                    copiedDeck.add(new Miraj(card));
                    break;
                case "The Cursed One":
                    copiedDeck.add(new TheCursedOne(card));
                    break;
                case "Disciple":
                    copiedDeck.add(new Disciple(card));
                    break;
                default:
                    System.out.println("Invalid card name: " + card.getName() + " in createDeck method.");
                    return null;
            }

        }
        return copiedDeck;
    }

    public Card getCardAt(Coordinates position) {
        ArrayList<Card> currentRow = getRowAt(position.getX());

        if (position.getY() >= currentRow.size()) {
            return null;
        }
        return currentRow.get(position.getY());
    }

    public ArrayList<Card> getRowAt(int row) {
        switch (row) {
            case 0:
                return player2.getBackRow();
            case 1:
                return player2.getFrontRow();
            case 2:
                return player1.getFrontRow();
            case 3:
                return player1.getBackRow();
            default:
                return null;
        }
    }

    public Integer getTotalGamesPlayed() {
        return playersPacks.victoriesPlayer1 + playersPacks.victoriesPlayer2;
    }
    public Integer getPlayerOneWins() {
        return playersPacks.victoriesPlayer1;
    }
    public Integer getPlayerTwoWins() {
        return playersPacks.victoriesPlayer2;
    }

}
