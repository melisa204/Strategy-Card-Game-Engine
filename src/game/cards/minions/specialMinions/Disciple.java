package game.cards.minions.specialMinions;

import fileio.input.CardInput;
import game.cards.Card;
import game.cards.minions.BackRowCard;

public class Disciple extends BackRowCard {
    public Disciple(final CardInput card) {
        super(card);
    }
    public Disciple(final Card card) {
        super(card);
    }
    /**
     * Executes the ability of the current card or entity.
     * This method currently has no implementation but can be extended or overridden
     * in subclasses to define specific behavior for the entity's ability.
     */
    public void useAbility() {
    }
    /**
     * Applies the special ability of the current card to the specified target card.
     * This ability increases the target card's health by 2.
     * The current card is also marked as having attacked.
     *
     * @param card the {@code Card} object that is the target of the special ability.
     */
    @Override
    public void specialAbility(final Card card) {
        this.setAttacked(true);

        card.setHealth(card.getHealth() + 2);
    }
}
