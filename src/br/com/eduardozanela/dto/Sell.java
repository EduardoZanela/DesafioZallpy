package br.com.eduardozanela.dto;

import java.util.List;

public class Sell {

    private String saleId;
    private List<Item> item;
    private String salesmanName;

    public Sell() {}

    public Sell(String saleId, List<Item> item, String salesmanName) {
        this.saleId = saleId;
        this.item = item;
        this.salesmanName = salesmanName;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    @Override
    public String toString() {
        return "Sell{" +
                "saleId='" + saleId + '\'' +
                ", item=" + item +
                ", salesmanName='" + salesmanName + '\'' +
                '}';
    }
}
