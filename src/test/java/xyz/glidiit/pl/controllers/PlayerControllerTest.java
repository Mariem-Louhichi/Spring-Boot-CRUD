package xyz.glidiit.pl.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.glidiit.pl.entities.Player;
import xyz.glidiit.pl.entities.Team;
import xyz.glidiit.pl.services.PlayerService;
import xyz.glidiit.pl.services.TeamService;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerService playerServiceImpl;

    @MockBean
    private TeamService teamServiceMock;

    private Player robertson = new Player();

    private Team liverpool = new Team();

    @Before
    public void setUp() throws Exception {
        liverpool.setId(1);
        robertson.setId(1);
        robertson.setName("Robertson");
        robertson.setGoals(1);
        robertson.setAssists(7);
        robertson.setTeam(liverpool);
        given(playerServiceImpl.findOne(1)).willReturn(Optional.of(robertson));
    }

    @Test
    public void findOne() throws Exception {

        mvc.perform(get("/player/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.name", is(robertson.getName())));

    }
}
