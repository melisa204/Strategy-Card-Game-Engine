package game.cards.minions;

import fileio.input.CardInput;
import game.Player;
import game.cards.Card;

public class BackRowCard extends Card {
    public BackRowCard(final CardInput card) {
        super(card);
    }
    public BackRowCard(final Card card) {
        super(card);
    }
    /**
     * Places a card from the player's hand onto the back row of the table, if allowed.
     * The method performs the following checks:
     * - Ensures the player has enough mana to place the card.
     * - Ensures the back row is not full (maximum 5 cards allowed).
     * If the conditions are met, the card is removed from the player's hand, added to the back
     * row, and the player's mana is reduced by the card's mana cost.
     *
     * @param player the {@code Player} attempting to place the card.
     * @param handIdx the index of the card in the player's hand to be placed.
     * @return a {@code String} message indicating the outcome:
     * - "Not enough mana to place card on table." if the player lacks sufficient mana.
     * - "Cannot place card on table since row is full." if the back row already has the maximum
     * number of cards.
     * - An empty string ("") if the card is successfully placed.
     */
    @Override
    public String putCard(final Player player, final int handIdx) {
        final int maxCards = 5;
        if (player.getMana() < player.getCurrentHand().get(handIdx).getMana()) {
            return "Not enough mana to place card on table.";
        } else if (player.getBackRow().size() >= maxCards) {
            return "Cannot place card on table since row is full.";
        } else {
            player.setMana(player.getMana() - player.getCurrentHand().get(handIdx).getMana());
            BackRowCard card = (BackRowCard) player.getCurrentHand().get(handIdx);
            card.setAttacked(false);
            player.getBackRow().add(card);
            player.getCurrentHand().remove(handIdx);
            return "";
        }
    }
}
