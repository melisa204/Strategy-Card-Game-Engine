package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.FrontRowCard;

public class Miraj extends FrontRowCard {
    public Miraj(CardInput card) {
        super(card);
    }
    public Miraj(Card card) {
        super(card);
    }
    public void useAbility() {
    }
    @Override
    public void specialAbility(Card card) {
        this.setAttacked(true);

        int auxHealth = card.getHealth();
        card.setHealth(this.getHealth());
        this.setHealth(auxHealth);
    }
}
