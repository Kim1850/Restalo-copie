package ca.ulaval.glo2003;

public class Restaurant {
    private String name;
    private Integer capacity;
    private Hours hours;


    public Restaurant(String name, Integer capacity, Hours hours) {
        this.name = name;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }
}
