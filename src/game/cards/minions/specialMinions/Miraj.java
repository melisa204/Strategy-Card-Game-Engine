package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.FrontRowCard;

public class Miraj extends FrontRowCard {
    public Miraj(final CardInput card) {
        super(card);
    }
    public Miraj(final Card card) {
        super(card);
    }
    /**
     * Executes the ability of the current card or entity.
     * This method currently has no implementation but serves as a placeholder
     * to be overridden in subclasses with specific behavior.
     */
    public void useAbility() {
    }
    /**
     * Applies the special ability of the current card to the specified target card.
     * This ability swaps the health values between the current card and the target card.
     * The current card is also marked as having attacked.
     *
     * @param card the {@code Card} object that is the target of the special ability.
     */
    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);

        int auxHealth = card.getHealth();
        card.setHealth(this.getHealth());
        this.setHealth(auxHealth);
    }
}
