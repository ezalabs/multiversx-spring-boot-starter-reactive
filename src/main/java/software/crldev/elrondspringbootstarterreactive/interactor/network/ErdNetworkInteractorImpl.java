package software.crldev.elrondspringbootstarterreactive.interactor.network;

import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.NetworkConfig;
import software.crldev.elrondspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import software.crldev.elrondspringbootstarterreactive.api.model.ShardStatus;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.String.format;

@AllArgsConstructor
@Slf4j
public class ErdNetworkInteractorImpl implements ErdNetworkInteractor {

    private final ErdProxyClient client;

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
