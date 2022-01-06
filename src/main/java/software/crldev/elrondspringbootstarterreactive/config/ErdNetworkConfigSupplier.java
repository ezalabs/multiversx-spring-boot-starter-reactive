package software.crldev.elrondspringbootstarterreactive.config;

import software.crldev.elrondspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.elrondspringbootstarterreactive.interactor.network.ErdNetworkInteractor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

/**
 * Network configuration provider class
 * which synchronises at startup with the Elrond Gateway
 *
 * Source for network configuration values
 * Uses default static values from D chain in case synchronising doesn't succeed
 *
 * @author carlo_stanciu
 */
@Slf4j
public class ErdNetworkConfigSupplier {

    private final ErdNetworkInteractor networkInteractor;
    public static NetworkConfig config;

    public static final Integer DEFAULT_VERSION = 1;

    /**
     * Main constructor used for dependency injection
     *
     * @param networkInteractor - component used to extract configuration from the network
     */
    public ErdNetworkConfigSupplier(ErdNetworkInteractor networkInteractor) {
        this.networkInteractor = networkInteractor;
    }

    static {
        config = NetworkConfig
                .builder()
                .chainId("D")
                .gasPerDataByte(1500L)
                .minGasLimit(BigInteger.valueOf(70_000L))
                .minGasPrice(BigInteger.valueOf(1_000_000_000L))
                .minTransactionVersion(DEFAULT_VERSION)
                .build();
    }

    /**
     * Method called at startup
     * which is using the NetworkInteractor
     * to synchronise the configuration from the network
     */
    @PostConstruct
    public void sync() {
        networkInteractor.getNetworkConfig()
                .doOnSuccess(r -> {
                    config = r;
                    log.info("Elrond network configuration synced. Chain ID: {}", r.getChainId());
                })
                .doOnError(e ->
                        log.error("Could not sync Elrond network configuration at startup. Using default configs for chain ID {}.",
                                config.getChainId()))
                .subscribe();
    }

}
