package third.party.api.service.spi;

import third.party.api.entity.FinancialMessage;
import third.party.api.model.IncomingFinancialMessage;

import java.util.List;

public interface APIService {
    FinancialMessage saveMessage(IncomingFinancialMessage incomingFinancialMessage);

    List<FinancialMessage> findAll();
}
