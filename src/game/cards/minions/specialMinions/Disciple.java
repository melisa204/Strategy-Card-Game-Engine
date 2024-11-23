package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.BackRowCard;

public class Disciple extends BackRowCard {
    public Disciple(CardInput card) {
        super(card);
    }
    public Disciple(Card card) {
        super(card);
    }
    public void useAbility() {
    }
    @Override
    public void specialAbility(Card card) {
        this.setAttacked(true);

        card.setHealth(card.getHealth() + 2);
    }
}
