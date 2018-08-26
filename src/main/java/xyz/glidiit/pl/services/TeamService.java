package xyz.glidiit.pl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.glidiit.pl.entities.Team;
import xyz.glidiit.pl.repositories.TeamRepository;

import java.util.Optional;


@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Page<Team> findAll(Pageable pageable){
        return teamRepository.findAll(pageable);
    }

    public Optional<Team> findOne(Integer id){
        return teamRepository.findById(id);
    }

    public Team insert(Team team){
        Assert.notNull(team, "Cannot save an empty team");
        Assert.isNull(team.getId(),"You are trying to save a team with an ID");
        Assert.hasText(team.getName(),"The Team should have a name");
        return teamRepository.save(team);
    }

    public Team update(Integer id, Team team){
        Assert.notNull(team, "Cannot save an empty team");
        Assert.notNull(id,"You are trying to alter a team with a null ID");
        Assert.isTrue(teamRepository.existsById(id), "You are trying to alter a team that dorsnt exists");
        Assert.isNull(team.getId(),"This team shouldnt have an ID");
        Assert.hasText(team.getName(),"The Team should have a name");

        team.setId(id);
        return teamRepository.saveAndFlush(team);
    }

    public void delete(Integer id){
        Assert.isTrue(id != null && teamRepository.existsById(id),
                "There is no team to delete");
        teamRepository.deleteById(id);
    }

}
