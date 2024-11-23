package utils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.ActionsInput;
import fileio.input.Coordinates;
import fileio.output.CardOutput;
import game.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class CommandRunner {
    private ArrayList<ActionsInput> actions;
    private ArrayNode output;
    private Game game;
    private ObjectMapper objectMapper = new ObjectMapper();
    private int commnandIndex = 0;

    public CommandRunner(final ArrayList<ActionsInput> actions, final ArrayNode output,
                         final Game game) {
        this.actions = actions;
        this.output = output;
        this.game = game;
    }
    /**
     * Starts the game and processes a series of actions sequentially.
     * The method iterates through each action in the {@code actions} list, executes the
     * corresponding game logic based on the action's command, and stores the resulting JSON
     * response in the {@code output}.
     *
     * The actions processed include commands like "endPlayerTurn", "placeCard", "cardUsesAttack",
     * "useHeroAbility", etc. After executing each action, the result is stored in an
     * {@code ObjectNode}
     * and added to the {@code output} list, if applicable.
     */
    public void startGame() {
        ObjectNode resultedNode = null;
        for (ActionsInput action: actions) {
            commnandIndex++;
            switch (action.getCommand()) {
                case "endPlayerTurn":
                    endPlayerTurn(action);
                    break;
                case "placeCard":
                    resultedNode = placeCard(action);
                    break;
                case "cardUsesAttack":
                    resultedNode = cardUsesAttack(action);
                    break;
                case "cardUsesAbility":
                    resultedNode = cardUsesAbility(action);
                    break;
                case "useAttackHero":
                    resultedNode = useAttackHero(action);
                    break;
                case "useHeroAbility":
                    resultedNode = useHeroAbility(action);
                    break;
                case "getCardsInHand":
                    resultedNode = getCardsInHand(action);
                    break;
                case "getPlayerDeck":
                    resultedNode = getPlayerDeck(action);
                    break;
                case "getCardsOnTable":
                    resultedNode = getCardsOnTable(action);
                    break;
                case "getPlayerTurn":
                    resultedNode = getPlayerTurn(action);
                    break;
                case "getPlayerHero":
                    resultedNode = getPlayerHero(action);
                    break;
                case "getCardAtPosition":
                    resultedNode = getCardAtPosition(action);
                    break;
                case "getPlayerMana":
                    resultedNode = getPlayerMana(action);
                    break;
                case "getFrozenCardsOnTable":
                    resultedNode = getFrozenCardsOnTable(action);
                    break;
                case "getTotalGamesPlayed":
                    resultedNode = getTotalGamesPlayed(action);
                    break;
                case "getPlayerOneWins":
                    resultedNode = getPlayerOneWins(action);
                    break;
                case "getPlayerTwoWins":
                    resultedNode = getPlayerTwoWins(action);
                    break;
                default:
                    break;
            }
            if (resultedNode != null) {
                output.add(resultedNode);
            }
            resultedNode = null;
        }
    }
    /**
     * Ends the current player's turn and prepares the game for the next player's turn.
     * This method calls the underlying game logic to handle the end of the player's turn.
     *
     * @param action the {@code ActionsInput} object containing the action details for ending the
     *               turn.
     */
    public void endPlayerTurn(final ActionsInput action) {
        game.endPlayerTurn();
    }
    /**
     * Attempts to place a card from the player's hand onto the game table and formats the
     * result as an {@code ObjectNode}.
     * If an error occurs during the placement, the method constructs a JSON object containing
     * the command, the error message, and the index of the card in the player's hand.
     * If no error occurs, the method returns {@code null}.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               index of the card in the player's hand.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "error": the error message, if any, generated during the placement.
     *         - "handIdx": the index of the card in the player's hand being placed on the table.
     *         Returns {@code null} if no error occurs and the card is successfully placed.
     */
    public ObjectNode placeCard(final ActionsInput action) {

        String result = game.placeCard(action.getHandIdx());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", action.getCommand());
            objectNode.put("error", result);
            objectNode.put("handIdx", action.getHandIdx());

            return objectNode;
        }
        return null;
    }
    /**
     * Executes an attack from the attacking card on the target card and formats the result as an
     * {@code ObjectNode}.
     * If an error occurs during the attack, the method constructs a JSON object containing the
     * command, the attacking card, the attacked card, and the error message.
     * If no error occurs, the method returns {@code null}.
     *
     * @param action the {@code ActionsInput} object containing the command information, the
     *               attacking card, and the attacked card.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "cardAttacker": the card performing the attack, serialized as a {@code CardOutput}.
     *         - "cardAttacked": the card being attacked, serialized as a {@code CardOutput}.
     *         - "error": the error message, if any, generated during the attack.
     *         Returns {@code null} if no error occurs.
     */
    public ObjectNode cardUsesAttack(final ActionsInput action) {
        String result = game.cardUsesAttack(action.getCardAttacker(), action.getCardAttacked());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", action.getCommand());
            objectNode.put("cardAttacker", objectMapper.valueToTree(action.getCardAttacker()));
            objectNode.put("cardAttacked", objectMapper.valueToTree(action.getCardAttacked()));
            objectNode.put("error", result);

            return objectNode;
        }

        return null;
    }
    /**
     * Executes the ability of the attacking card on the target card and formats the result as an
     * {@code ObjectNode}.
     * If an error occurs during the ability execution, the method constructs a JSON object
     * containing the command, the attacker card, the attacked card, and the error message.
     * If no error occurs, the method returns {@code null}.
     *
     * @param action the {@code ActionsInput} object containing the command information, the
     *               attacking card, and the attacked card.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "cardAttacker": the card performing the ability, serialized as a
     *         {@code CardOutput}.
     *         - "cardAttacked": the card receiving the ability, serialized as a
     *         {@code CardOutput}.
     *         - "error": the error message, if any, generated during the ability execution.
     *         Returns {@code null} if no error occurs.
     */
    public ObjectNode cardUsesAbility(final ActionsInput action) {
        String result = game.cardUsesAbility(action.getCardAttacker(), action.getCardAttacked());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", action.getCommand());
            objectNode.put("cardAttacker", objectMapper.valueToTree(action.getCardAttacker()));
            objectNode.put("cardAttacked", objectMapper.valueToTree(action.getCardAttacked()));
            objectNode.put("error", result);

            return objectNode;
        }

        return null;
    }
    /**
     * Executes an attack from the specified card on the hero and formats the result as an
     * {@code ObjectNode}.
     * If an error occurs during the attack, the method constructs a JSON object containing the
     * command, the attacker card, and the error message. If the game ends after the attack, the
     * response includes a "gameEnded" field. If no error occurs and the game doesn't end, the
     * method returns {@code null}.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               attacker card.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "cardAttacker": the card performing the attack, serialized as a {@code CardOutput}.
     *         - "error": the error message, if any, generated during the attack.
     *         - "gameEnded": a message indicating the game has ended, if applicable.
     *         Returns {@code null} if no error occurs and the game is still ongoing.
     */
    public ObjectNode useAttackHero(final ActionsInput action) {
        String result = game.useAttackHero(action.getCardAttacker());

        if (result != "") {
            if (!result.startsWith("Player ")) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", action.getCommand());
                objectNode.put("cardAttacker", objectMapper.valueToTree(action.getCardAttacker()));
                objectNode.put("error", result);
                return objectNode;
            } else {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("gameEnded", result);
                return objectNode;
            }
        }
        return null;
    }
    /**
     * Executes the hero ability based on the provided input and formats the result as an
     * {@code ObjectNode}.
     * If an error occurs during the execution of the ability, the method constructs a JSON object
     * containing the command, the affected row, and the error message.
     * If no error occurs, the method returns {@code null}.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               affected row.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "affectedRow": the row affected by the hero's ability.
     *         - "error": the error message, if any, generated during the ability execution.
     *         Returns {@code null} if no error occurs.
     */
    public ObjectNode useHeroAbility(final ActionsInput action) {
        String result = game.useHeroAbility(action.getAffectedRow());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", action.getCommand());
            objectNode.put("affectedRow", action.getAffectedRow());
            objectNode.put("error", result);
            return objectNode;
        }

        return null;
    }
    /**
     * Retrieves the cards currently in the specified player's hand and formats the result as an
     * {@code ObjectNode}.
     * The method constructs a JSON object containing the command, the player index, and the output
     * result, where the output is a list of cards in the player's hand serialized as
     * {@code CardOutput} objects.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               player index.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "playerIdx": the index of the player whose hand is being retrieved.
     *         - "output": a JSON array representing the cards in the specified player's hand.
     */
    public ObjectNode getCardsInHand(final ActionsInput action) {
        ArrayList<CardOutput> result = game.getCardsInHand(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves the deck of the specified player and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command, the player index, and the
     * output result, where the output is a list of cards in the player's deck serialized as
     * {@code CardOutput} objects.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               player index.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "playerIdx": the index of the player whose deck is being retrieved.
     *         - "output": a JSON array representing the cards in the specified player's deck.
     */
    public ObjectNode getPlayerDeck(final ActionsInput action) {
        ArrayList<CardOutput> result = game.getPlayerDeck(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves the cards currently on the game table and formats the result as an
     * {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result, where the
     * output
     * is a list of rows of cards serialized as a JSON array of {@code CardOutput} objects.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": a JSON array representing all the cards on the table, organized by rows.
     */
    public ObjectNode getCardsOnTable(final ActionsInput action) {
        ArrayList<ArrayList<CardOutput>> result = game.getCardsOnTable();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves the current player's turn and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": the index of the player whose turn it currently is.
     */
    public ObjectNode getPlayerTurn(final ActionsInput action) {
        int result = game.getPlayerTurn();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }
    /**
     * Retrieves the hero of the specified player and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command, the player index, and the output
     * result.
     * The hero is serialized into a JSON object if found.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               player index.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "playerIdx": the index of the player whose hero is retrieved.
     *         - "output": the hero of the specified player, serialized as a JSON object.
     */
    public ObjectNode getPlayerHero(final ActionsInput action) {
        Hero result = game.getPlayerHero(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves the card at a specific position on the game board and formats the result as an
     * {@code ObjectNode}.
     * The method constructs a JSON object containing the command, the coordinates (x, y), and the
     * output result.
     * If a card is found at the specified position, it is serialized as a {@code CardOutput}
     * object; otherwise, a message indicating no card is available is included in the output.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               coordinates (x, y).
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "x": the x-coordinate of the specified position.
     *         - "y": the y-coordinate of the specified position.
     *         - "output": the card at the specified position as a {@code CardOutput},
     *           or the message "No card available at that position."
     */
    public ObjectNode getCardAtPosition(final ActionsInput action) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(action.getX());
        coordinates.setY(action.getY());

        Card result = game.getCardAt(coordinates);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("x", action.getX());
        objectNode.put("y", action.getY());
        if (result != null) {
            objectNode.put("output", objectMapper.valueToTree(new CardOutput((result))));
        } else {
            objectNode.put("output", "No card available at that position.");
        }

        return objectNode;
    }
    /**
     * Retrieves the mana of the specified player and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command, the player index, and the output
     * result.
     *
     * @param action the {@code ActionsInput} object containing the command information and the
     *               player index.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "playerIdx": the index of the player whose mana is retrieved.
     *         - "output": the mana of the specified player.
     */
    public ObjectNode getPlayerMana(final ActionsInput action) {
        int result = game.getPlayerMana(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", result);

        return objectNode;
    }
    /**
     * Retrieves the frozen cards currently on the game table and formats the result as an
     * {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result, where the
     * output is a list of frozen cards serialized as a JSON array.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": a JSON array of frozen cards on the table, represented as
     *         {@code CardOutput} objects.
     */
    public ObjectNode getFrozenCardsOnTable(final ActionsInput action) {
        ArrayList<CardOutput> result = game.getFrozenCardsOnTable();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves the total number of games played and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": the total number of games played.
     */
    public ObjectNode getTotalGamesPlayed(final ActionsInput action) {
        Integer result = game.getTotalGamesPlayed();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }
    /**
     * Retrieves the number of wins for Player One and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": the number of wins for Player One.
     */
    public ObjectNode getPlayerOneWins(final ActionsInput action) {
        Integer result = game.getPlayerOneWins();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }
    /**
     * Retrieves the number of wins for Player Two and formats the result as an {@code ObjectNode}.
     * The method constructs a JSON object containing the command and the output result.
     *
     * @param action the {@code ActionsInput} object containing the command information.
     * @return an {@code ObjectNode} representing the JSON response, including:
     *         - "command": the command from the input action.
     *         - "output": the number of wins for Player Two.
     */
    public ObjectNode getPlayerTwoWins(final ActionsInput action) {
        Integer result = game.getPlayerTwoWins();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }
}
