package xyz.glidiit.pl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.glidiit.pl.entities.Player;
import xyz.glidiit.pl.repositories.PlayerRepository;
import xyz.glidiit.pl.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public Page<Player> findAll(Pageable pageable){
        return playerRepository.findAll(pageable);
    }

    public Optional<Player> findOne(Integer id){
        return playerRepository.findById(id);
    }

    public List<Player> findByTeamId(Integer id){
        return playerRepository.findByTeamId(id);
    }
    public Player createPlayer(Player player){
        Assert.notNull(player, "Cannot save an empty player");
        Assert.isNull(player.getId(),"You are trying to save a player with an ID");
        Assert.hasText(player.getName(),"The player should have a name");
        Assert.notNull(player.getTeam(), "The team doesnt exist");
        Assert.notNull(player.getTeam().getId(), "Team ID is null");
        player.setTeam(teamRepository.findById(player.getTeam().getId())
                .orElseThrow(() -> new IllegalArgumentException("Team doesnt exist")));
        return playerRepository.save(player);
    }


    public Player update(Integer id, Player player){
        Assert.notNull(player, "Cannot save an empty player");
        Assert.notNull(id,"You are trying to alter a player with a null ID");
        Assert.isTrue(playerRepository.existsById(id), "You are trying to alter a player that doesnt exists");
        Assert.isNull(player.getId(),"This team shouldnt have an ID");
        Assert.hasText(player.getName(),"The Team should have a name");

        player.setId(id);
        return playerRepository.saveAndFlush(player);
    }

    public void delete(Integer id){
        Assert.isTrue(id != null && playerRepository.existsById(id),
                "There is no player to delete");
        playerRepository.deleteById(id);
    }

}
