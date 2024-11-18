package game.heroes;

import fileio.input.CardInput;
import game.Hero;
import game.cards.Card;

import java.util.ArrayList;

public class EmpressThorina extends Hero{

    public EmpressThorina(CardInput card) {
        super(card);
    }

    public void specialAbility(ArrayList<Card> attackedRow) {
        Card attackedCard = attackedRow.get(0);
        int indexOfAttackedCard = 0;
        for (int i = 1; i < attackedRow.size(); i++) {
            if (attackedRow.get(i).getHealth() > attackedCard.getHealth()) {
                attackedCard = attackedRow.get(i);
                indexOfAttackedCard = i;
            }
        }

        attackedRow.remove(indexOfAttackedCard); // TODO: sau o sterg cand ma intorc in Game?
    }
}
