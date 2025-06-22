package org.lugx.game.repos;

import org.lugx.game.entities.GameEB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<GameEB, Long>, JpaSpecificationExecutor<GameEB> {
}
