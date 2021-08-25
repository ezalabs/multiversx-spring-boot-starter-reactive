package software.crldev.elrondspringbootstarterreactive;

import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.sender.ErdSimpleTransactionSender;
import software.crldev.elrondspringbootstarterreactive.sender.ErdSimpleTransactionSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(ErdInteractorAutoConfiguration.class)
public class TransactionSenderServiceAutoconfiguration {

    @Autowired
    private ErdAccountInteractor accountInteractor;
    @Autowired
    private ErdTransactionInteractor transactionInteractor;

    @Bean
    public ErdSimpleTransactionSender transactionSender() {
        return new ErdSimpleTransactionSenderImpl(accountInteractor, transactionInteractor);
    }

}
