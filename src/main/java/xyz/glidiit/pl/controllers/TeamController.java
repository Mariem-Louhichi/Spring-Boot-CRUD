package xyz.glidiit.pl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import xyz.glidiit.pl.entities.Team;
import xyz.glidiit.pl.services.TeamService;

import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/{id}")
    public Optional<Team> findOne(@PathVariable(name = "id") Integer id){
        return teamService.findOne(id);
    }

    @GetMapping(value = "")
    public Page<Team> list(Pageable pageable) {
        return teamService.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") Integer id) {
        teamService.delete(id);
    }

    @PutMapping(value = "")
    public Team setupAddForm(@RequestBody Team team) {
        return teamService.insert(team);
    }

    @PutMapping(value = "/{id}")
    public Team setupUpdateForm(@PathVariable(name = "id") Integer id, @RequestBody Team team) {
        return teamService.update(id, team);
    }
}
