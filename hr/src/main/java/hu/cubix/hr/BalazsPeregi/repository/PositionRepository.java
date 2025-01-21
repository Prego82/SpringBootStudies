package hu.cubix.hr.BalazsPeregi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.cubix.hr.BalazsPeregi.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
	Position findByName(String name);
}
