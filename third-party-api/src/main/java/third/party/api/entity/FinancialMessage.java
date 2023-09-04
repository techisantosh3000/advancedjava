package third.party.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FinancialMessage {

    @Id
    @GeneratedValue
    private Long id;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FinancialMessageEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
