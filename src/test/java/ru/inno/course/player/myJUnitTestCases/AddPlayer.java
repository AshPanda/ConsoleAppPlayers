package ru.inno.course.player.myJUnitTestCases;

import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerService;
import ru.inno.course.player.service.PlayerServiceImpl;

import java.util.Collection;

public class AddPlayer {
    public static PlayerService service = new PlayerServiceImpl();
    public static final String LOGIN = "Alena";

    static int playerId = service.createPlayer(LOGIN);
    static Collection<Player> players = service.getPlayers();

}
