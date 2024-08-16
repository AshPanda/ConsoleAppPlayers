package ru.inno.course.player.myJUnitTestCases;

import org.junit.jupiter.api.*;
import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static ru.inno.course.player.myJUnitTestCases.AddPlayer.*;

public class PositiveTests {

    private static final int ADDITIONAL_POSITIVE_POINTS = 30;
    private static final int ADDITIONAL_POSITIVE_POINTS_2 = 20;

    @AfterEach
    public void removeFile() throws IOException {
        Files.deleteIfExists(Path.of("./data.json"));
    }

    @Test
    @DisplayName("Добавить игрока - проверить наличие в списке (1,3,6)")
    void addPlayerAndCheckInListTest() {
        Collection<Player> newPlayers = service.getPlayers();
        assertTrue(newPlayers.stream().anyMatch(player -> player.getId() == playerId));
        assertEquals(1, playerId);
    }

    @Test
    @DisplayName("Добавить игрока, удалить игрока - проверить отсутствие в списке (2,3)")
    void addPlayerAndDeleteTest() {
        AddPlayer.service.deletePlayer(playerId);
        assertFalse(AddPlayer.players.stream().anyMatch(player -> player.getId() == playerId));

    }

    @Test
    @DisplayName("Начислить баллы существующему игроку (4)")
    void addPointsToPlayerTest() {
        service.addPoints(playerId, ADDITIONAL_POSITIVE_POINTS);
        Player player = service.getPlayerById(playerId);
        assertEquals(ADDITIONAL_POSITIVE_POINTS, player.getPoints());

    }

    @Test
    @DisplayName("Добавить очков поверх существующих (4)")
    void addPointsToPointsTest() {
        int playerId = service.createPlayer(LOGIN);
        service.addPoints(playerId, ADDITIONAL_POSITIVE_POINTS);
        service.addPoints(playerId, ADDITIONAL_POSITIVE_POINTS_2);
        Player player = service.getPlayerById(playerId);
        assertEquals(ADDITIONAL_POSITIVE_POINTS + ADDITIONAL_POSITIVE_POINTS_2, player.getPoints());
    }

    @Test
    @DisplayName("Проверить создание игрока с 15 символами (10)")
    void createPlayerWith15CharactersTest() {
        service = new PlayerServiceImpl();
        String longLogin = "ASfhkdgwvndkghrn";
        int newPlayerId = service.createPlayer(longLogin);
        Player player2 = service.getPlayerById(newPlayerId);
        assertEquals(longLogin, player2.getNick());
    }

    @Test
    @DisplayName("Проверить, что id всегда уникальный (1,2)")
    void uniquePlayerIdTest() {
        int player1 = service.createPlayer("Player1");
        service.deletePlayer(player1);
        int player2 = service.createPlayer("Player2");
        assertNotEquals(player1, player2);
    }

    @Test
    @DisplayName("Нет json-файла, запросить список игроков (3,8)")
    void requestPlayerListWithoutJSONFileTest() {
        service = new PlayerServiceImpl();
        Collection<Player> players = service.getPlayers();
        assertEquals(0, players.size());
    }

    @Test
    @DisplayName("Есть json-файл, добавить игрока (1,6)")
    void addPlayerWhenJSONFileExistsTest() {
        service.createPlayer("NewPlayer");
        new AddPlayer();
        Player player = service.getPlayerById(playerId);
        assertNotNull(player);

    }

    @Test
    @DisplayName("Нет json-файл, добавить игрока (1,6,8)")
    void addPlayerWithoutJSONFileTest(){
        int newPlayerId = service.createPlayer("NewPlayer");
        Player player = service.getPlayerById(newPlayerId);
        assertNotNull(player);

    }

}
