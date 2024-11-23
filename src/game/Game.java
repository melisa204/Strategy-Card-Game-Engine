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
import utils.PlayersPacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private int startingPlayer;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private int currentPlayer;
    private PlayersPacks playersPacks = PlayersPacks.getInstance();
    private int shuffleSeed;
    private int currentRound = 0;
    /**
     * Initializes a new game instance with the specified game settings.
     * The constructor performs the following actions:
     * - Sets the heroes for both players based on the input.
     * - Determines the starting player.
     * - Initializes and shuffles the decks for both players based on the input.
     * - Sets the current player to the starting player.
     * - Starts the first round of the game.
     *
     * @param startGameInput the {@code StartGameInput} object containing
     *                       the game configuration, including:
     *                       - The heroes for both players.
     *                       - The starting player.
     *                       - The shuffle seed for deck randomization.
     *                       - The deck indices for both players.
     */
    public Game(final StartGameInput startGameInput) {
        player1.setHero(selectHero(startGameInput.getPlayerOneHero()));
        player2.setHero(selectHero(startGameInput.getPlayerTwoHero()));
        startingPlayer = startGameInput.getStartingPlayer();
        shuffleSeed = startGameInput.getShuffleSeed();

        player1.setCurrentDeck(this.shuffleDeck(
                copyDeck(playersPacks.decksPlayer1.get(startGameInput.getPlayerOneDeckIdx()))));
        player2.setCurrentDeck(this.shuffleDeck(
                copyDeck(playersPacks.decksPlayer2.get(startGameInput.getPlayerTwoDeckIdx()))));

        currentPlayer = startingPlayer;
        this.startRound();
    }
    /**
     * Creates and returns a hero object based on the specified card input.
     * The hero type is determined by the name of the card.
     *
     * @param cardInput the {@code CardInput} object containing the name and attributes
     *                  used to initialize the hero.
     * @return a {@code Hero} object corresponding to the specified card name:
     *         - "Empress Thorina" creates an instance of {@code EmpressThorina}.
     *         - "General Kocioraw" creates an instance of {@code GeneralKocioraw}.
     *         - "King Mudface" creates an instance of {@code KingMudface}.
     *         - "Lord Royce" creates an instance of {@code LordRoyce}.
     *         Returns {@code null} if the card name does not match any known hero.
     */
    public Hero selectHero(final CardInput cardInput) {
        switch (cardInput.getName()) {
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
    /**
     * Shuffles the given deck of cards using a predefined random seed for reproducibility.
     * The method returns a new deck with the cards in a randomized order based on the shuffle
     * algorithm.
     *
     * @param deck the {@code ArrayList<Card>} representing the deck of cards to be shuffled.
     * @return a new {@code ArrayList<Card>} representing the shuffled deck. The original deck
     * remains unmodified.
     */
    public ArrayList<Card> shuffleDeck(final ArrayList<Card> deck) {
        Random random = new Random(shuffleSeed);

        ArrayList<Card> copiedDeck = new ArrayList<>();

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes, random);

        // copiez din deck-ul proaspat copiat cum trebuie in functie de mosteniri, in copiedDeck
        // adaug doar referintele deoarece nu stiu ce tip are fiecare carte
        for (int i = 0; i < deck.size(); i++) {
            copiedDeck.add(deck.get(indexes.get(i)));
        }

        return copiedDeck;
    }
    /**
     * Retrieves the current deck of the specified player as a list of {@code CardOutput} objects.
     * The cards are converted from {@code Card} objects to {@code CardOutput} objects for
     * output purposes.
     *
     * @param currentPlayerIndex the index of the player whose deck is being retrieved.
     *                           Use 1 for Player 1 and 2 for Player 2.
     * @return an {@code ArrayList<CardOutput>} representing the cards in the specified player's
     * deck.
     */
    public ArrayList<CardOutput> getPlayerDeck(final int currentPlayerIndex) {
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
    /**
     * Retrieves the hero of the specified player.
     *
     * @param currentPlayerIndex the index of the player whose hero is being retrieved.
     *                           Use 1 for Player 1 and 2 for Player 2.
     * @return the {@code Hero} object representing the specified player's hero.
     */
    public Hero getPlayerHero(final int currentPlayerIndex) {
        Hero currentHero;
        if (currentPlayerIndex == 1) {
            currentHero = player1.getHero();
        } else {
            currentHero = player2.getHero();
        }
        return currentHero;
    }

    public final int getPlayerTurn() {
        return currentPlayer;
    }
    /**
     * Starts a new round in the game by performing the following actions:
     * - Each player draws the top card from their deck and adds it to their hand,
     *   if their deck is not empty.
     * - Increments the current round number, up to a maximum of 10.
     * - Increases each player's mana by the current round number.
     */
    public void startRound() {
        if (player1.getCurrentDeck().size() > 0) {
            player1.getCurrentHand().add(player1.getCurrentDeck().get(0));
            player1.getCurrentDeck().remove(0);
        }
        if (player2.getCurrentDeck().size() > 0) {
            player2.getCurrentHand().add(player2.getCurrentDeck().get(0));
            player2.getCurrentDeck().remove(0);
        }

        final int maxMana = 10;
        if (currentRound < maxMana) {
            currentRound++;
        }

        player1.setMana(player1.getMana() + currentRound);
        player2.setMana(player2.getMana() + currentRound);
    }
    /**
     * Ends the current player's turn and prepares the game state for the next turn.
     * This method performs the following actions:
     * - Resets all cards on the current player's board so they are no longer frozen or marked as
     * having attacked.
     * - Resets the hero's ability usage for the current player.
     * - Switches the current player to the opponent.
     * - Starts a new round if the next player is the starting player.
     */
    public void endPlayerTurn() {
        // resetez cartile de pe masa sa nu mai fie frozen sau sa fi atacat
        if (currentPlayer == 1) {
            for (Card card : player1.getBackRow()) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
                if (card.usedAttack()) {
                    card.setAttacked(false);
                }
            }
            for (Card card : player1.getFrontRow()) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
                if (card.usedAttack()) {
                    card.setAttacked(false);
                }
            }
            player1.setUsedHero(false);
            currentPlayer = 2;
        } else {
            for (Card card : player2.getBackRow()) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
                if (card.usedAttack()) {
                    card.setAttacked(false);
                }
            }
            for (Card card : player2.getFrontRow()) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
                if (card.usedAttack()) {
                    card.setAttacked(false);
                }
            }
            player2.setUsedHero(false);
            currentPlayer = 1;
        }

        if (currentPlayer == startingPlayer) {
            startRound();
        }
    }
    /**
     * Places the card at the specified index from the current player's hand onto the game board.
     * The method delegates the action to the card's {@code putCard} method, which handles
     * the placement logic.
     *
     * @param handIdx the index of the card in the current player's hand to be placed.
     * @return a {@code String} message indicating the outcome of the action,
     *         or {@code null} if no message is generated.
     */

    public String placeCard(final int handIdx) {
        String result = null;
        Player auxPlayer;
        if (currentPlayer == 1) {
            auxPlayer = player1;
        } else {
            auxPlayer = player2;
        }

        result = auxPlayer.getCurrentHand().get(handIdx).putCard(auxPlayer, handIdx);

        return result;
    }
    /**
     * Retrieves the cards currently in the hand of the specified player.
     * The method converts the cards in the player's hand into {@code CardOutput} objects
     * for output purposes.
     *
     * @param currentPlayerIndex the index of the player whose hand is being retrieved.
     *                           Use 1 for Player 1 and 2 for Player 2.
     * @return an {@code ArrayList<CardOutput>} representing the cards in the specified player's
     * hand.
     */
    public ArrayList<CardOutput> getCardsInHand(final int currentPlayerIndex) {
        ArrayList<Card> currentHand;
        if (currentPlayerIndex == 1) {
            currentHand = player1.getCurrentHand();
        } else {
            currentHand = player2.getCurrentHand();
        }
        ArrayList<CardOutput> result = deckToOutputDeck(currentHand);
        return result;
    }
    /**
     * Retrieves the current mana of the specified player.
     *
     * @param playerIdx the index of the player whose mana is being retrieved.
     *                  Use 1 for Player 1 and 2 for Player 2.
     * @return the mana of the specified player as an integer.
     */

    public int getPlayerMana(final int playerIdx) {
        if (playerIdx == 1) {
            return player1.getMana();
        } else {
            return player2.getMana();
        }
    }
    /**
     * Retrieves all cards currently on the game table, organized by rows.
     * The method returns a list of lists, where each inner list represents a row on the table:
     * - Row 0: Back row of Player 2.
     * - Row 1: Front row of Player 2.
     * - Row 2: Front row of Player 1.
     * - Row 3: Back row of Player 1.
     *
     * Each card in the rows is converted to a {@code CardOutput} object for output purposes.
     *
     * @return an {@code ArrayList<ArrayList<CardOutput>>} representing all cards on the table,
     *         organized by rows.
     */
    public ArrayList<ArrayList<CardOutput>> getCardsOnTable() {
        ArrayList<ArrayList<CardOutput>> table = new ArrayList<>();

        table.add(deckToOutputDeck(player2.getBackRow()));
        table.add(deckToOutputDeck(player2.getFrontRow()));
        table.add(deckToOutputDeck(player1.getFrontRow()));
        table.add(deckToOutputDeck(player1.getBackRow()));

        return table;
    }
    /**
     * Retrieves all frozen cards currently on the game table.
     * This method iterates through all rows of both players and collects
     * the cards that are frozen into a list of {@code CardOutput} objects.
     *
     * @return an {@code ArrayList<CardOutput>} containing all frozen cards
     *         on the table, represented as {@code CardOutput} objects.
     */
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
    /**
     * Executes a basic attack from the card at the specified attacker position
     * to the card at the specified attacked position. The method performs several checks
     * to ensure the attack is valid and applies the appropriate effects.
     *
     * The method checks for the following conditions:
     * - Whether the attacked card belongs to the enemy player. An attacker cannot attack its own
     * cards.
     * - Whether the attacker card has already attacked this turn.
     * - Whether the attacker card is frozen.
     * - Whether an enemy "Tank" card exists that must be attacked first.
     *
     * If the attack is valid, the defending card's health is reduced. If the health of the
     * defender drops to 0 or below, the card is removed from play.
     *
     * @param attackerPos the Coordinates of the attacking card on the game board.
     * @param attackedPos the Coordinates of the target card on the game board.
     * @return a String message indicating the outcome:
     * - "Attacked card does not belong to the enemy." if the attacker attempts to attack its own
     * card.
     * - "Attacker card has already attacked this turn." if the attacker has already used its
     * attack.
     * - "Attacker card is frozen." if the attacker is frozen and cannot attack.
     * - "Attacked card is not of type 'Tank'." if a "Tank" card exists on the enemy's front row
     *   but the attacker targets another card.
     * - An empty string ("") if the attack is successful and no errors occurred.
     */

    public String cardUsesAttack(final Coordinates attackerPos, final Coordinates attackedPos) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackedPlayer = (currentPlayer == 2) ? player1 : player2;
        Card attackerCard = this.getCardAt(attackerPos);
        Card defenderCard = this.getCardAt(attackedPos);

        String result = "";

        // incep verificarile
        if ((attackedPos.getX() < 2 && attackerPos.getX() < 2) || (attackedPos.getX() >= 2
                && attackerPos.getX() >= 2)) {
            // verific daca cartea atacata este una dintre cartile mele
            result = "Attacked card does not belong to the enemy.";
        } else if (attackerCard.usedAttack()) {
            // verific daca cartea a atacat deja
            result = "Attacker card has already attacked this turn.";
        } else if (attackerCard.isFrozen()) {
            // verific daca cartea este inghetata
            result = "Attacker card is frozen.";
        } else {
            // caut un tank, iar daca exista il atac
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
            if (!attackerCard.usedAttack()) {
                // daca nu am gasit tank, atac cartea
                attackerCard.attackCard(defenderCard);
                if (defenderCard.getHealth() <= 0) {
                    final int rowZero = 0;
                    final int rowOne = 1;
                    final int rowTwo = 2;
                    final int rowThree = 3;

                    // daca cartea atacata a murit, o elimin
                    switch (attackedPos.getX()) {
                        case rowZero:
                            player2.getBackRow().remove(attackedPos.getY());
                            break;
                        case rowOne:
                            player2.getFrontRow().remove(attackedPos.getY());
                            break;
                        case rowTwo:
                            player1.getFrontRow().remove(attackedPos.getY());
                            break;
                        case rowThree:
                            player1.getBackRow().remove(attackedPos.getY());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return result;
    }
    /**
     * Executes the special ability of the attacking card on the target card at the specified
     * positions.
     * The method performs several checks before applying the ability, including:
     * - Whether the attacker card is frozen.
     * - Whether the attacker card has already used its ability this turn.
     * - Whether the target card belongs to the correct player (enemy or current player)
     *   based on the attacking card's type.
     * - Whether the enemy has a "Tank" card that must be attacked first.
     *
     * Depending on the cards' abilities and results of the attack, the target card may be removed
     * from play if its health drops to 0.
     *
     * @param attackerPos the Coordinates of the attacking card on the game board.
     * @param attackedPos the Coordinates of the target card on the game board.
     * @return a String message indicating the outcome:
     * - "Attacker card is frozen." if the attacker is frozen and cannot use its ability.
     * - "Attacker card has already attacked this turn." if the attacker has already used its
     * ability.
     * - "Attacked card does not belong to the current player." if the target card belongs to the
     * enemy, but the attacking card's ability requires a friendly card.
     * - "Attacked card does not belong to the enemy." if the target card belongs to the current
     * player, but the attacking card's ability requires an enemy card.
     * - "Attacked card is not of type 'Tank'." if the enemy has a "Tank" card that must be
     * attacked first.
     * - An empty string ("") if the ability was successfully applied and no errors occurred.
     */

    public String cardUsesAbility(final Coordinates attackerPos, final Coordinates attackedPos) {
        // imi setez datele pentru a nu imi incurca playerii
        Player attackedPlayer = (currentPlayer == 2) ? player1 : player2;
        Card attackerCard = this.getCardAt(attackerPos);
        Card defenderCard = this.getCardAt(attackedPos);

        String result = "";

        // incep verificarile
        if (attackerCard.isFrozen()) {
            // verific daca cartea atacatorului este inghetata
            result = "Attacker card is frozen.";
        } else if (attackerCard.usedAttack()) {
            // verific daca cartea a atacat deja
            result = "Attacker card has already attacked this turn.";
        } else if (attackerCard.getName().equals("Disciple")) {
            // Disciple nu trebuie sa atace tankuri prima data, il aplic direct pe carte
            if ((attackedPos.getX() < 2 && attackerPos.getX() > 1) || (attackedPos.getX() > 1
                    && attackerPos.getX() < 2)) {
                result = "Attacked card does not belong to the current player.";
            } else {
                attackerCard.specialAbility(this.getCardAt(attackedPos));
            }
        } else if (attackerCard.getName().equals("The Ripper")
                || attackerCard.getName().equals("Miraj")
                || attackerCard.getName().equals("The Cursed One")) {
            if ((attackedPos.getX() < 2 && attackerPos.getX() < 2) || (attackedPos.getX() >= 2
                    && attackerPos.getX() >= 2)) {
                // verific daca cartea atacata este una dintre cartile mele
                result = "Attacked card does not belong to the enemy.";
            } else {
                // caut un tank
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

                            return result;
                        }
                    }
                }
                if (!attackerCard.usedAttack()) {
                    // daca nu am gasit tank, atac cartea de pe pozitia attackedPos
                    attackerCard.specialAbility(this.getCardAt(attackedPos));
                    if (this.getCardAt(attackedPos).getHealth() <= 0) {
                        final int rowZero = 0;
                        final int rowOne = 1;
                        final int rowTwo = 2;
                        final int rowThree = 3;

                        // daca cartea atacata a murit, o elimin
                        switch (attackedPos.getX()) {
                            case rowZero:
                                player2.getBackRow().remove(attackedPos.getY());
                                break;
                            case rowOne:
                                player2.getFrontRow().remove(attackedPos.getY());
                                break;
                            case rowTwo:
                                player1.getFrontRow().remove(attackedPos.getY());
                                break;
                            case rowThree:
                                player1.getBackRow().remove(attackedPos.getY());
                                break;
                            default:
                                break;
                            }
                        }
                }
            }
        }
        return result;
    }

    /**
     * Uses the card at the specified position to attack the enemy hero.
     * The method performs several checks before the attack is executed, including:
     *   Whether the attacker card is frozen.
     *   Whether the attacker card has already attacked this turn.
     *   Whether a "Tank" card is present in the front row of the enemy player,
     *       which must be attacked first.
     * If all conditions are met, the hero is attacked, and if the hero's health drops
     * to 0 or below, the current player is declared the winner of the game.
     *
     * @param attackerPos the {@code Coordinates} of the attacking card on the game board.
     * @return a {@code String} message indicating the outcome:
     *           "Attacker card is frozen." - if the attacking card is frozen.
     *           "Attacker card has already attacked this turn." - if the attacking card
     *           has used its attack.
     *           "Attacked card is not of type 'Tank'." - if a "Tank" card exists in the
     *           enemy front row.
     *           "Player one killed the enemy hero." - if Player 1 wins by killing the
     *           enemy hero.
     *           "Player two killed the enemy hero." - if Player 2 wins by killing the
     *           enemy hero.
     *           An empty string ("") if the attacker card is invalid (e.g., null)
     *           or the attack is successful but the hero survives.
     */

    public String useAttackHero(final Coordinates attackerPos) {
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
        } else if (attackerCard.usedAttack()) {
            result = "Attacker card has already attacked this turn.";
        } else {
            // caut un tank
            for (Card card : attackedPlayer.getFrontRow()) {
                if (card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                    result = "Attacked card is not of type 'Tank'.";

                    return result;
                }
            }
            if (!attackerCard.usedAttack()) {
                // daca nu am gasit tank, atac cartea de pe pozitia attackedPos
                attackedPlayer.getHero().attackHero(attackerCard);
                if (attackedPlayer.getHero().getHealth() <= 0) {
                    // cresc numarul de victorii
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
    /**
     * Uses the hero's ability on the specified row, applying its effects
     * if all conditions are met. The method performs several validations, including:
     *  Whether the hero has enough mana to use the ability.
     *  Whether the hero's ability has already been used this turn.
     *  Whether the selected row belongs to the correct player (current or enemy)
     *       based on the hero's type.
     *
     * @param attackedRow the index of the row to which the hero's ability is applied.
     *                    Rows are indexed as follows:
     *                      0-1: Rows belonging to Player 2
     *                      2-3: Rows belonging to Player 1
     * @return a {@code String} message indicating the outcome:
     *           "Not enough mana to use hero's ability." - if the hero lacks sufficient mana.
     *           "Hero has already attacked this turn." - if the hero's ability
     *           has already been used.
     *           "Selected row does not belong to the enemy." - if an enemy row is
     *           required but not selected.
     *           "Selected row does not belong to the current player." - if a friendly
     *           row is required but not selected.
     *            An empty string ("") if the ability was successfully applied.
     */
    public String useHeroAbility(final int attackedRow) {
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
        } else if (attackerPlayer.getHero().getName().equals("Lord Royce")
                || attackerPlayer.getHero().getName().equals("Empress Thorina")) {
            if ((currentPlayer == 1 && attackedRow >= 2) || (currentPlayer == 2
                    && attackedRow < 2)) {
                result = "Selected row does not belong to the enemy.";
            } else {
                attackerPlayer.getHero().specialAbility(affectedRow);
                attackerPlayer.setMana(attackerPlayer.getMana()
                        - attackerPlayer.getHero().getMana());
                attackerPlayer.setUsedHero(true);
            }
        } else if (attackerPlayer.getHero().getName().equals("General Kocioraw")
                || attackerPlayer.getHero().getName().equals("King Mudface")) {
            if ((currentPlayer == 1 && attackedRow < 2) || (currentPlayer == 2
                    && attackedRow >= 2)) {
                result = "Selected row does not belong to the current player.";
            } else {
                attackerPlayer.getHero().specialAbility(affectedRow);
                attackerPlayer.setMana(attackerPlayer.getMana()
                        - attackerPlayer.getHero().getMana());
                attackerPlayer.setUsedHero(true);
            }
        }

        return result;
    }

    // utility functions:
    private ArrayList<CardOutput> deckToOutputDeck(final ArrayList<Card> deck) {
        ArrayList<CardOutput> result = new ArrayList<>();
        for (Card card: deck) {
            result.add(new CardOutput(card));
        }
        return result;
    }

    private ArrayList<Card> copyDeck(final ArrayList<Card> deck) {
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
                    return null;
            }

        }
        return copiedDeck;
    }
    /**
     * Retrieves the card at the specified position in the game board.
     * The position is determined by the coordinates:
     * X-coordinate specifies the row (see {@link #getRowAt(int)} for row mapping).
     * Y-coordinate specifies the index within the row.
     *
     * @param position the {@code Coordinates} object representing the row (X)
     *                 and column (Y) of the desired card.
     * @return the {@code Card} object at the specified position,
     *         or {@code null} if the Y-coordinate is out of bounds for the specified row.
     */
    public Card getCardAt(final Coordinates position) {
        ArrayList<Card> currentRow = getRowAt(position.getX());

        if (position.getY() >= currentRow.size()) {
            return null;
        }
        return currentRow.get(position.getY());
    }
    /**
     * Returns the row of cards based on the specified row index.
     * The row index maps to specific rows for Player 1 or Player 2:
     * 0 - Back row of Player 2
     * 1 - Front row of Player 2
     * 2 - Front row of Player 1
     * 3 - Back row of Player 1
     *
     * @param row the index of the row to retrieve (0 to 3).
     * @return an ArrayList of {@code Card} objects representing the specified row,
     *         or {@code null} if the row index is out of range.
     */
    public ArrayList<Card> getRowAt(final int row) {
        final int rowZero = 0;
        final int rowOne = 1;
        final int rowTwo = 2;
        final int rowThree = 3;

        switch (row) {
            case rowZero:
                return player2.getBackRow();
            case rowOne:
                return player2.getFrontRow();
            case rowTwo:
                return player1.getFrontRow();
            case rowThree:
                return player1.getBackRow();
            default:
                return null;
        }
    }

    public final Integer getTotalGamesPlayed() {
        return playersPacks.victoriesPlayer1 + playersPacks.victoriesPlayer2;
    }
    public final Integer getPlayerOneWins() {
        return playersPacks.victoriesPlayer1;
    }
    public final Integer getPlayerTwoWins() {
        return playersPacks.victoriesPlayer2;
    }

}
