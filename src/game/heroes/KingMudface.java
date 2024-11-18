package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class KingMudface extends Hero {
    public KingMudface(CardInput card) {
        super(card);
    }

    public void specialAbility(ArrayList<Card> attackedRow) {
        for (Card card : attackedRow) {
            card.setHealth(card.getHealth() + 1);
        }
    }
}
