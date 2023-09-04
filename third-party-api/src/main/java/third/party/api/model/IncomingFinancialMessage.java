package third.party.api.model;

import java.util.Objects;

/**
 * Core model
 */
public class IncomingFinancialMessage {
    private Long messageId;
    private String data;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IncomingFinancialMessage{" +
                "messageId=" + messageId +
                ", data='" + data + '\'' +
                '}';
    }
}
