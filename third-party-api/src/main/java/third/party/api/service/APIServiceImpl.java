package third.party.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import third.party.api.converter.ModelToEntityConverterImpl;
import third.party.api.entity.FinancialMessage;
import third.party.api.model.IncomingFinancialMessage;
import org.springframework.stereotype.Service;
import third.party.api.repository.FinancialMessageRepository;
import third.party.api.service.spi.APIService;

import java.util.ArrayList;
import java.util.List;

@Service
public class APIServiceImpl implements APIService {
    private static final Logger log = LoggerFactory.getLogger(APIServiceImpl.class);

    @Autowired
    private FinancialMessageRepository repository;

    @Autowired
    private ModelToEntityConverterImpl modelToEntityConverterImpl;

    @Override
    public FinancialMessage saveMessage(IncomingFinancialMessage incomingFinancialMessage) {
        FinancialMessage message = null;
        try {
            message = modelToEntityConverterImpl.convert(incomingFinancialMessage);
            repository.save(message);
        } catch (RuntimeException re) {
            log.error(re.getMessage());
        }
        return message;
    }

    @Override
    public List<FinancialMessage> findAll() {
        List<FinancialMessage> listOfMessages = new ArrayList<>();
        try {
            repository.findAll().forEach(listOfMessages::add);
        } catch (RuntimeException re) {
            log.error(re.getMessage());
        }
        return listOfMessages;
    }
}
