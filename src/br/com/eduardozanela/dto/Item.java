package br.com.eduardozanela.dto;

public class Item {

    private Long id;
    private Integer quantity;
    private Double price;

    public Item() {}

    public Item(Long id, Integer quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
