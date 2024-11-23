package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero{
    public GeneralKocioraw(CardInput card) {
        super(card);
    }

    public void specialAbility(ArrayList<Card> attackedRow) {
        for (Card card : attackedRow) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }

    }
}
