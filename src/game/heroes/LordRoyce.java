package game.heroes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(CardInput card) {
        super(card);
    }

    @Override
    public void specialAbility(ArrayList<Card> attackedRow) {
        for (Card card : attackedRow) {
            card.setFrozen(true);
        }
    }
}
