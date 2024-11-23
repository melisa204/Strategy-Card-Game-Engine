package game.cards.minions;

import fileio.input.CardInput;
import game.Player;
import game.cards.Card;

public class BackRowCard extends Card {
    public BackRowCard(CardInput card) {
        super(card);
    }
    public BackRowCard(Card card) {
        super(card);
    }

    @Override
    public String putCard (Player player, int handIdx) {
//        System.out.println("My mana: " + player.getMana() + " Card mana: " + player.getCurrentHand().get(handIdx).getMana());
        if (player.getMana() < player.getCurrentHand().get(handIdx).getMana())
            return "Not enough mana to place card on table.";
        else if (player.getBackRow().size() >= 5)
            return "Cannot place card on table since row is full.";
        else {
            player.setMana(player.getMana() - player.getCurrentHand().get(handIdx).getMana());
            player.getBackRow().add(player.getCurrentHand().get(handIdx));
            player.getCurrentHand().remove(handIdx);
//            System.out.println("BackRowCard placed");
            return "";
        }
    }
}
