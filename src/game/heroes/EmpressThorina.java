package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class EmpressThorina extends Hero {

    public EmpressThorina(final CardInput card) {
        super(card);
    }
    /**
     * Applies the special ability of the current card to the specified row.
     * This ability identifies the card with the highest health in the row and removes it.
     * If multiple cards have the same highest health, the first one encountered is removed.
     *
     * @param attackedRow an {@code ArrayList<Card>} representing the row of cards affected by the
     *                    ability.
     */
    @Override
    public void specialAbility(final ArrayList<Card> attackedRow) {
        Card attackedCard = attackedRow.get(0);
        int indexOfAttackedCard = 0;
        for (int i = 1; i < attackedRow.size(); i++) {
            if (attackedRow.get(i).getHealth() > attackedCard.getHealth()) {
                attackedCard = attackedRow.get(i);
                indexOfAttackedCard = i;
            }
        }

        attackedRow.remove(indexOfAttackedCard);
    }
}
