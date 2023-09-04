package third.party.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import third.party.api.entity.FinancialMessage;
import third.party.api.model.IncomingFinancialMessage;

@Component
public class ModelToEntityConverterImpl implements Converter<IncomingFinancialMessage, FinancialMessage> {
    @Override
    public FinancialMessage convert(IncomingFinancialMessage source) {
        FinancialMessage entity = new FinancialMessage();
        entity.setMessage(source.getData());
        return entity;
    }
}
