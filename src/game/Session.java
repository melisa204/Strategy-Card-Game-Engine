package game;

import utils.CommandRunner;
import utils.PlayersPacks;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.Input;

public class Session {
    private Game game;
    private PlayersPacks playersPacks;
    private CommandRunner commandRunner;
    private ArrayNode output;
    public Session(final Input inputData, final ArrayNode output) {
        this.output = output;
        // salvez toate datele legate de pachete intr un singleton
        playersPacks = PlayersPacks.getInstance();
        playersPacks.clear();
        playersPacks.addDecks(inputData);
        playersPacks.victoriesPlayer1 = 0;
        playersPacks.victoriesPlayer2 = 0;

        // salvez datele legate de jocul propriu zis
        for (Integer i = 0; i < inputData.getGames().size(); i++) {
            game = new Game(inputData.getGames().get(i).getStartGame());
            // salvez actiunile
            commandRunner = new CommandRunner(inputData.getGames().get(i).getActions(),
                    this.output, game);
            commandRunner.startGame();
        }
    }
}
