package org.lugx.game.dtos;

import org.lugx.game.entities.GameEB;

public class GameData {
    private Long id;
    private String title;
    private String category;
    private String releaseDate;
    private String price;

    public void mapToGameData(GameEB gameEB) {
        id = gameEB.getId();
        title = gameEB.getTitle();
        category = gameEB.getCategory();
        releaseDate = gameEB.getReleaseDate();
        price = gameEB.getPrice();
    }

    public GameEB mapToGameEB() {
        GameEB gameEB = new GameEB();
        // If update request, ID will present
        if (id != null) {
            gameEB.setId(id);
        }
        gameEB.setTitle(title);
        gameEB.setCategory(category);
        gameEB.setReleaseDate(releaseDate);
        gameEB.setPrice(price);

        return gameEB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
