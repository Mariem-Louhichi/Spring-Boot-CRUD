package xyz.glidiit.pl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import xyz.glidiit.pl.entities.Player;
import xyz.glidiit.pl.services.PlayerService;
import xyz.glidiit.pl.services.TeamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @GetMapping(value = "{id}")
    public Optional<Player> findOne(@PathVariable(name = "id") Integer id){
        return playerService.findOne(id);
    }

    @GetMapping("team/{teamId}")
    public List<Player> getAllPlayersByTeamId(@PathVariable (value = "teamId") Integer teamId) {
        return playerService.findByTeamId(teamId);
    }

    @PutMapping("")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @GetMapping("")
    public Page<Player> list(Pageable pageable) {
        return playerService.findAll(pageable);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable(name = "id") Integer id) {
        playerService.delete(id);
    }


    @PutMapping(value = "{id}")
    public Player update(@PathVariable(name = "id") Integer id, @RequestBody Player player) {
        return playerService.update(id, player);
    }
}
