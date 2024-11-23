package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput card) {
        super(card);
    }
    /**
     * Applies the special ability of the current card to all cards in the specified row.
     * This ability increases the attack damage of each card in the row by 1.
     *
     * @param attackedRow an {@code ArrayList<Card>} representing the row of cards affected by the
     *                    ability.
     */
    @Override
    public void specialAbility(final ArrayList<Card> attackedRow) {
        for (Card card : attackedRow) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }

    }
}
