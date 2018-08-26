package xyz.glidiit.pl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.glidiit.pl.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
