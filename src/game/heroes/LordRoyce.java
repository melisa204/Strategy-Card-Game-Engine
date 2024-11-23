package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(final CardInput card) {
        super(card);
    }
    /**
     * Applies the special ability of the current card to all cards in the specified row.
     * This ability freezes each card in the row, preventing them from attacking during their
     * next turn.
     *
     * @param attackedRow an {@code ArrayList<Card>} representing the row of cards affected by the
     *                    ability.
     */
    @Override
    public void specialAbility(final ArrayList<Card> attackedRow) {
        for (Card card : attackedRow) {
            card.setFrozen(true);
        }
    }
}
