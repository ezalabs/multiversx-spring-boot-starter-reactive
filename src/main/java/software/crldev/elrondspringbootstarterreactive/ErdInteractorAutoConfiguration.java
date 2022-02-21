package software.crldev.elrondspringbootstarterreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractorImpl;
import software.crldev.elrondspringbootstarterreactive.interactor.block.ErdBlockInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.block.ErdBlockInteractorImpl;
import software.crldev.elrondspringbootstarterreactive.interactor.esdt.ErdESDTInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.esdt.ErdESDTInteractorImpl;
import software.crldev.elrondspringbootstarterreactive.interactor.network.ErdNetworkInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.network.ErdNetworkInteractorImpl;
import software.crldev.elrondspringbootstarterreactive.interactor.smartcontract.ErdSmartContractInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.smartcontract.ErdSmartContractInteractorImpl;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractorImpl;

@Configuration
@ConditionalOnClass({ErdNetworkInteractor.class, ErdAccountInteractor.class, ErdTransactionInteractor.class})
@AutoConfigureAfter(ErdClientAutoConfiguration.class)
public class ErdInteractorAutoConfiguration {

    @Autowired
    private ErdProxyClient erdProxyClient;

    @Bean
    public ErdNetworkInteractor networkInteractor() {
        return new ErdNetworkInteractorImpl(erdProxyClient);
    }

    @Bean
    public ErdAccountInteractor accountInteractor() {
        return new ErdAccountInteractorImpl(erdProxyClient);
    }

    @Bean
    public ErdTransactionInteractor transactionInteractor() {
        return new ErdTransactionInteractorImpl(erdProxyClient, accountInteractor());
    }

    @Bean
    public ErdSmartContractInteractor smartContractInteractor() {
        return new ErdSmartContractInteractorImpl(erdProxyClient, transactionInteractor());
    }

    @Bean
    public ErdBlockInteractor blockInteractor() {
        return new ErdBlockInteractorImpl(erdProxyClient);
    }

    @Bean
    public ErdESDTInteractor esdtInteractor() {
        return new ErdESDTInteractorImpl(erdProxyClient, transactionInteractor(), smartContractInteractor());
    }

}
