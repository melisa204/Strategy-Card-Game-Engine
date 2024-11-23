package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.FrontRowCard;

public class TheRipper extends FrontRowCard {
    public TheRipper(CardInput card) {
        super(card);
    }
    public TheRipper(Card card) {
        super(card);
    }
    public void useAbility() {
    }
    @Override
    public void specialAbility(Card card) {
        this.setAttacked(true);
        card.setAttackDamage(card.getAttackDamage() - 2);

        if (card.getAttackDamage() < 0) {
            card.setAttackDamage(0);
        }
    }
}
