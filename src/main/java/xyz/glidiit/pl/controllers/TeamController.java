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

    @RequestMapping(value = "/{id}")
    public Optional<Team> findOne(@PathVariable(name = "id") Integer id){
        return teamService.findOne(id);
    }
    @RequestMapping(value = "/list")
    public Page<Team> list(Pageable pageable) {
        return teamService.findAll(pageable);
    }

    @RequestMapping(value = "delete/{id}")
    public void delete(@PathVariable(name = "id") Integer id) {
        teamService.delete(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public Team setupAddForm(@RequestBody Team team) {
        return teamService.insert(team);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public Team setupUpdateForm(@PathVariable(name = "id") Integer id, @RequestBody Team team) {
        return teamService.update(id, team);
    }
}
