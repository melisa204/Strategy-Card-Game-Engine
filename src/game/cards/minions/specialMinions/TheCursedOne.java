package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.BackRowCard;

public class TheCursedOne extends BackRowCard {
    public TheCursedOne(CardInput card) {
        super(card);
    }
    public TheCursedOne(Card card) {
        super(card);
    }
    public void useAbility() {
    }

    @Override
    public void specialAbility(Card card) {
        this.setAttacked(true);

        int auxilliar = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(auxilliar);
    }
}
