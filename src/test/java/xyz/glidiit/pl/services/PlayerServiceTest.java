package xyz.glidiit.pl.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.glidiit.pl.entities.Player;
import xyz.glidiit.pl.entities.Team;
import xyz.glidiit.pl.repositories.PlayerRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepositoryImpl = mock(PlayerRepository.class);

    @InjectMocks
    PlayerService playerServiceMock;
    Team team =new Team();
    Player player =new Player();

    @Before
    public void setUp() throws Exception {
        team.setId(2);
        player.setName("Mane");
        player.setGoals(5);
        player.setAssists(9);
        player.setTeam(team);
    }

    @Test
    public void findOne() {
        when(playerServiceMock.findOne(1)).thenReturn(Optional.of(player));
        assertEquals(Optional.of(player).get().getName(),playerServiceMock.findOne(1).get().getName());
    }


}