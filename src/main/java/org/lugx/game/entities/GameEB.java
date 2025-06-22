package org.lugx.game.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_games")
public class GameEB {
    @Id
    @SequenceGenerator(name = "games_seq", sequenceName = "games_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_seq")
    private Long id;

    private String title;

    private String category;

    private String releaseDate;

    private String price;   // Prices are in USD

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
