package third.party.api.model;

public class CustomerResponse {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
