package third.party.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import third.party.api.entity.FinancialMessage;

@Repository
public interface FinancialMessageRepository extends CrudRepository<FinancialMessage, Long> {
}
