package software.crldev.elrondspringbootstarterreactive.interactor.network;

import software.crldev.elrondspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.elrondspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import software.crldev.elrondspringbootstarterreactive.api.model.ShardStatus;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface used for interaction with network configuration
 *
 * @author carlo_stanciu
 */
public interface ErdNetworkInteractor {

    /**
     * Method used to query network configuration
     *
     * @return - NetworkConfig API response
     */
    Mono<NetworkConfig> getNetworkConfig();

    /**
     * Method used to query a shard's status based on id
     *
     * @param shardId - value used as search id
     * @return - ShardStatus API response
     */
    Mono<ShardStatus> getShardStatus(String shardId);

    /**
     * Method used to query a node's heartbeat status
     *
     * @return - list of NodeHeartbeatStatus API response
     */
    Mono<List<NodeHeartbeatStatus>> getNodeHeartbeatStatus();

}
