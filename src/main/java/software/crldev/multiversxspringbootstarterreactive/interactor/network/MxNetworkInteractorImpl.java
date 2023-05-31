package software.crldev.multiversxspringbootstarterreactive.interactor.network;

import static java.lang.String.format;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.multiversxspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.multiversxspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import software.crldev.multiversxspringbootstarterreactive.api.model.ShardStatus;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.interactor.WrappedResponses;

@AllArgsConstructor
@Slf4j
public class MxNetworkInteractorImpl implements MxNetworkInteractor {

  private final MxProxyClient client;

  @Override
  public Mono<NetworkConfig> getNetworkConfig() {
    return client
        .get(ApiResourceURI.NETWORK_CONFIG.getURI(),
            WrappedResponses.GetNetworkConfigResponse.class)
        .map(WrappedResponses.GetNetworkConfigResponse::getNetworkConfig);
  }

  @Override
  public Mono<ShardStatus> getShardStatus(String shardId) {
    return client
        .get(format(ApiResourceURI.SHARD_STATUS.getURI(), shardId),
            WrappedResponses.GetShardStatusResponse.class)
        .map(WrappedResponses.GetShardStatusResponse::getShardStatus);
  }

  @Override
  public Mono<List<NodeHeartbeatStatus>> getNodeHeartbeatStatus() {
    return client
        .get(ApiResourceURI.NODE_HEARTBEAT_STATUS.getURI(),
            WrappedResponses.GetNodeHeartbeatStatusResponse.class)
        .map(WrappedResponses.GetNodeHeartbeatStatusResponse::getHeartbeatstatus);
  }
}
