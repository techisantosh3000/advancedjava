package org.functional.prog.supplier.analysis;

import java.util.function.Supplier;

class Message {
    protected Long id;
    protected String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

class FinancialMessage extends Message {
    private String financialCode;

    public String getFinancialCode() {
        return financialCode;
    }

    public void setFinancialCode(String financialCode) {
        this.financialCode = financialCode;
    }

    @Override
    public String toString() {
        return "FinancialMessage{" +
                "financialCode='" + financialCode + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

public class SupplierAnalysis {
    public static void main(String[] args) {
        Message defaultMessage = messageFactory(Message::new);
        System.out.println("Print default message : " + defaultMessage);

        FinancialMessage fm = (FinancialMessage) messageFactory(FinancialMessage::new);
        System.out.println("Print fm message : " + fm);

    }

    public static Message messageFactory(Supplier<? extends Message> input) {
        Message message = input.get();
        if (message.getId() == null) {
            message.setId(10000L);
        }
        message.setText("Blah Blah Blah.");
        return message;
    }
}
