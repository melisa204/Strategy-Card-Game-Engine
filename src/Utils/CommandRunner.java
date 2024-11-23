package Utils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.ActionsInput;
import fileio.input.Coordinates;
import fileio.output.CardOutput;
import game.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandRunner {
    private ArrayList<ActionsInput> actions;
    private ArrayNode output;
    private Game game;
    private ObjectMapper objectMapper = new ObjectMapper();
    int commnandIndex = 0;

    public CommandRunner(ArrayList<ActionsInput> actions, ArrayNode output, Game game){
        this.actions = actions;
        this.output = output;
        this.game = game;
    }

    public void startGame(){
        ObjectNode resultedNode = null;
        for (ActionsInput action: actions) {
            commnandIndex++;
            System.out.println(commnandIndex + ":" + action.getCommand());
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
            if (resultedNode != null)
                output.add(resultedNode);
            resultedNode = null;
        }
    }

    public void endPlayerTurn(ActionsInput action) {
        game.endPlayerTurn();
    }

    public ObjectNode placeCard(ActionsInput action) {

        String result = game.placeCard(action.getHandIdx());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
//            objectNode.put("index", commnandIndex);
            objectNode.put("command", action.getCommand());
            objectNode.put("error", result); // ASA?????????????????????
            objectNode.put("handIdx", action.getHandIdx());
//            output.add(objectNode);

            return objectNode;
        }

        return null;
    }

    public ObjectNode cardUsesAttack(ActionsInput action) {
        String result = game.cardUsesAttack(action.getCardAttacker(), action.getCardAttacked());

        if (result != "") {
            ObjectNode objectNode = objectMapper.createObjectNode();
//            objectNode.put("index", commnandIndex);
            objectNode.put("command", action.getCommand());
            objectNode.put("cardAttacker", objectMapper.valueToTree(action.getCardAttacker()));
            objectNode.put("cardAttacked", objectMapper.valueToTree(action.getCardAttacked()));
            objectNode.put("error", result);

            return objectNode;
        }

        return null;

    }

    public ObjectNode cardUsesAbility(ActionsInput action) {
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

    public ObjectNode useAttackHero(ActionsInput action) {
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

    public ObjectNode useHeroAbility(ActionsInput action) {
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

    public ObjectNode getCardsInHand(ActionsInput action) {
        ArrayList<CardOutput> result = game.getCardsInHand(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }

    public ObjectNode getPlayerDeck(ActionsInput action) {
        ArrayList<CardOutput> result = game.getPlayerDeck(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }

    public ObjectNode getCardsOnTable(ActionsInput action) {
        ArrayList<ArrayList<CardOutput>> result = game.getCardsOnTable();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }

    public ObjectNode getPlayerTurn(ActionsInput action) {
        int result = game.getPlayerTurn();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }

    public ObjectNode getPlayerHero(ActionsInput action) {
        Hero result = game.getPlayerHero(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }

    public ObjectNode getCardAtPosition(ActionsInput action) {
        // TODO
        Coordinates coordinates = new Coordinates();
        coordinates.setX(action.getX());
        coordinates.setY(action.getY());

        Card result = game.getCardAt(coordinates);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("x", action.getX());
        objectNode.put("y", action.getY());
        if (result != null)
            objectNode.put("output", objectMapper.valueToTree(new CardOutput((result))));
        else {
            objectNode.put("output", "No card available at that position.");

        }

        return objectNode;

    }

    public ObjectNode getPlayerMana(ActionsInput action) {
        int result = game.getPlayerMana(action.getPlayerIdx());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("playerIdx", action.getPlayerIdx());
        objectNode.put("output", result);

        return objectNode;
    }

    public ObjectNode getFrozenCardsOnTable(ActionsInput action) {
        ArrayList<CardOutput> result = game.getFrozenCardsOnTable();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", objectMapper.valueToTree(result));

        return objectNode;
    }

    public ObjectNode getTotalGamesPlayed(ActionsInput action) {
        Integer result = game.getTotalGamesPlayed();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }

    public ObjectNode getPlayerOneWins(ActionsInput action) {
        Integer result = game.getPlayerOneWins();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }

    public ObjectNode getPlayerTwoWins(ActionsInput action) {
        Integer result = game.getPlayerTwoWins();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", action.getCommand());
        objectNode.put("output", result);

        return objectNode;
    }
}
