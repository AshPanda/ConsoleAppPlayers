package ru.inno.course.player.myJUnitTestCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.inno.course.player.myJUnitTestCases.AddPlayer.playerId;
import static ru.inno.course.player.myJUnitTestCases.AddPlayer.service;

public class NegativeTests {

    private static final int ADDITIONAL_NEGATIVE_POINTS = -30;
    private static final int POSITIVE_POINTS = 30;
    private static final int NONEXISTINGPLAYER = 99999;

    @AfterEach
    public void removeFile() throws IOException {
        Files.deleteIfExists(Path.of("./data.json"));
    }

    @Test
    @DisplayName("Создать дубликат игрока (9)")
    void duplicatePlayerTest() {
        assertThrows(IllegalArgumentException.class, () -> service.createPlayer(AddPlayer.LOGIN));
    }

    @Test
    @DisplayName("Удалить игрока которого нет (2,3)")
    void removeNonExistingPlayerTest() {
        assertThrows(NoSuchElementException.class, () -> service.deletePlayer(NONEXISTINGPLAYER));
    }

    @Test
    @DisplayName("Получить игрока по несуществующему ID (7)")
    void getPlayerByNonExistingIDTest() {
        assertThrows(NoSuchElementException.class, () -> service.getPlayerById(NONEXISTINGPLAYER));

    }

    //BUG: Позволяет позволяет создать игрока с 16ю символами
    @Test
    @DisplayName("Проверить создание игрока с 16 символами (10)")
    void createPlayerWith16CharactersTest() {
        String longLogin = "fhskelajgnthdlsh";
        assertThrows(IllegalArgumentException.class, () -> service.createPlayer(longLogin));
    }

    @Test
    @DisplayName("Начислить очки несуществующему игроку (4)")
    void addPointsToNonExistingPlayerTest() {
        assertThrows(NoSuchElementException.class, () -> service.addPoints(NONEXISTINGPLAYER, POSITIVE_POINTS));
    }

    //BUG: Позволяет добавить отрицательное число очков
    @Test
    @DisplayName("Начислить отрицательное число очков (4,10)")
    void addNegativePointsToPlayerTest() {
        assertThrows(IllegalArgumentException.class, () -> service.addPoints(playerId, ADDITIONAL_NEGATIVE_POINTS));
    }

    //BUG: Позволяет создать игрока с пустым именем
    @Test
    @DisplayName("Создать игрока с пустым именем (10)")
    void createPlayerWithoutNameTest() {
        assertThrows(IllegalArgumentException.class, () -> service.createPlayer(""));
    }

}
