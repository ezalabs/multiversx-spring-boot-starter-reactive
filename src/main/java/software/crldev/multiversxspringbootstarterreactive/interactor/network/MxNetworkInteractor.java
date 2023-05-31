package software.crldev.multiversxspringbootstarterreactive.interactor.network;

import java.util.List;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.multiversxspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import software.crldev.multiversxspringbootstarterreactive.api.model.ShardStatus;

/**
 * Interface used for interaction with network configuration
 *
 * @author carlo_stanciu
 */
public interface MxNetworkInteractor {

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
