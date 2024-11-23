package game;

import Utils.CommandRunner;
import Utils.PlayersPacks;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.Input;

public class Session {
    Game game;
    PlayersPacks playersPacks;
    CommandRunner commandRunner;
    ArrayNode output;
    public Session(Input inputData, ArrayNode output) {
        this.output = output;
        // salvez toate datele legate de pachete intr un singleton
        playersPacks = PlayersPacks.getInstance();
        playersPacks.clear();
        playersPacks.addDecks(inputData);
        playersPacks.victoriesPlayer1 = 0;
        playersPacks.victoriesPlayer2 = 0;

        // de aici ar trb sa incep sa iterez cumva prin jocuri -> sa am grija ce trb sa resetez de la unul la altul

        // salvez datele legate de jocul propriu zis
        for (Integer i = 0; i < inputData.getGames().size(); i++) {
            game = new Game(inputData.getGames().get(i).getStartGame());
            // salvez actiunile
            commandRunner = new CommandRunner(inputData.getGames().get(i).getActions(), this.output, game);
            commandRunner.startGame();
        }
//        game = new Game(inputData.getGames().get(0).getStartGame());
//        // salvez actiunile
//        commandRunner = new CommandRunner(inputData.getGames().get(0).getActions(), this.output, game);
//        commandRunner.startGame();
    }
}
