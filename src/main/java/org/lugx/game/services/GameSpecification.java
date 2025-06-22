package org.lugx.game.services;

import org.lugx.game.entities.GameEB;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta. persistence. criteria. Predicate;

public class GameSpecification {
    private GameSpecification() {
        // Stop instantiation
    }

    public static Specification<GameEB> filterBy(String title, String category) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
            }

            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
