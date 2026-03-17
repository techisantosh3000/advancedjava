package org.interview.model;

public class Apple {
    // All optional parameter
    private String color;
    private Double weight;
    private Double price;

    // Define 0 arg constructor if we are defining all arg constructor.
    public Apple(AppleBuilder builder){
        this.color = builder.color;
        this.price = builder.price;
        this.weight = builder.weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

    // inner static class builder
    public static class AppleBuilder{
        private String color;
        private Double weight;
        private Double price;

        public AppleBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public AppleBuilder setWeight(Double weight) {
            this.weight = weight;
            return this;
        }

        public AppleBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Apple build(){
            return new Apple(this);
        }

    }
}
