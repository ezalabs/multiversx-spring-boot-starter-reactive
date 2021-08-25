package software.crldev.elrondspringbootstarterreactive;

import software.crldev.elrondspringbootstarterreactive.config.ErdNetworkConfigSupplier;
import software.crldev.elrondspringbootstarterreactive.interactor.network.ErdNetworkInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(ErdInteractorAutoConfiguration.class)
public class ErdNetworkAutoConfiguration {

    @Autowired
    private ErdNetworkInteractor networkInteractor;

    @Bean
    public ErdNetworkConfigSupplier proxyNetworkConfig() {
        return new ErdNetworkConfigSupplier(networkInteractor);
    }

}
