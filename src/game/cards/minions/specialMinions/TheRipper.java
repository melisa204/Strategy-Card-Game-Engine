package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.FrontRowCard;

public class TheRipper extends FrontRowCard {
    public TheRipper(final CardInput card) {
        super(card);
    }
    public TheRipper(final Card card) {
        super(card);
    }
    /**
     * Executes the ability of the current card.
     * This method is intended to be overridden by subclasses to define specific behavior.
     */
    public void useAbility() {
    }
    /**
     * Applies the special ability of the current card to the specified target card.
     * The ability reduces the target card's attack damage by 2. If the resulting attack damage
     * is less than 0, it is set to 0. The current card is also marked as having attacked.
     *
     * @param card the {@code Card} object that is the target of the special ability.
     */
    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);
        card.setAttackDamage(card.getAttackDamage() - 2);

        if (card.getAttackDamage() < 0) {
            card.setAttackDamage(0);
        }
    }
}
