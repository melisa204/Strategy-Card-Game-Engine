package game.cards.minions;

import fileio.input.CardInput;
import game.Player;
import game.cards.Card;

import java.util.ArrayList;

public class FrontRowCard extends Card {
    public FrontRowCard(CardInput card) {
        super(card);
    }
    public FrontRowCard(Card card) {
        super(card);
    }

    @Override
    public String putCard (Player player, int handIdx) {
        System.out.println("My mana: " + player.getMana() + " Card mana: " + player.getCurrentHand().get(handIdx).getMana());
        if (player.getMana() < player.getCurrentHand().get(handIdx).getMana())
            return "Not enough mana to place card on table.";
        else if (player.getFrontRow().size() >= 5)
            return "Cannot place card on table since row is full.";
        else {
            player.setMana(player.getMana() - player.getCurrentHand().get(handIdx).getMana());
            player.getFrontRow().add(player.getCurrentHand().get(handIdx));
            player.getCurrentHand().remove(handIdx);
            System.out.println("FrontRowCard placed");
            return "";
        }
    }

}
