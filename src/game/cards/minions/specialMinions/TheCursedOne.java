package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.BackRowCard;

public class TheCursedOne extends BackRowCard {
    public TheCursedOne(final CardInput card) {
        super(card);
    }
    public TheCursedOne(final Card card) {
        super(card);
    }
    /**
     * Executes the ability of the current card.
     * This method currently has no implementation but can be extended or overridden in subclasses.
     * to define specific behavior.
     */
    public void useAbility() {
    }
    /**
     * Applies the special ability of the current card to the specified target card.
     * This ability swaps the target card's health and attack damage values.
     * The current card is also marked as having attacked.
     *
     * @param card the {@code Card} object that is the target of the special ability.
     */
    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);

        int auxilliar = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(auxilliar);
    }
}
