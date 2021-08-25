package software.crldev.elrondspringbootstarterreactive.interactor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import software.crldev.elrondspringbootstarterreactive.api.model.*;

import java.util.List;
import java.util.Map;

public class WrappedResponses {

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetAccountTransactionsWrapper.GetAccountTransactionsWrapperBuilder.class)
    public static class GetAccountTransactionsWrapper {

        @JsonProperty("transactions")
        private final List<TransactionForAddress> list;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetAccountWrapper.GetAccountWrapperBuilder.class)
    public static class GetAccountWrapper {

        @JsonProperty("account")
        private final AccountOnNetwork account;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetAllAccountStorageWrapper.GetAllAccountStorageWrapperBuilder.class)
    public static class GetAllAccountStorageWrapper {

        @JsonProperty("pairs")
        private final Map<String, String> pairs;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = QueryHyperblock.QueryHyperblockBuilder.class)
    public static class QueryHyperblock {

        @JsonProperty("hyperblock")
        private final Hyperblock hyperblock;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = QueryBlock.QueryBlockBuilder.class)
    public static class QueryBlock {

        @JsonProperty("block")
        private final ShardBlock block;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetNetworkConfigResponse.GetNetworkConfigResponseBuilder.class)
    public static class GetNetworkConfigResponse {

        @JsonProperty("config")
        private final NetworkConfig networkConfig;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetShardStatusResponse.GetShardStatusResponseBuilder.class)
    public static class GetShardStatusResponse {

        @JsonProperty("status")
        private final ShardStatus shardStatus;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = GetNodeHeartbeatStatusResponse.GetNodeHeartbeatStatusResponseBuilder.class)
    public static class GetNodeHeartbeatStatusResponse {

        @JsonProperty("heartbeats")
        private final List<NodeHeartbeatStatus> heartbeatstatus;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = QueryTransactionResponse.QueryTransactionResponseBuilder.class)
    public static class QueryTransactionResponse {

        @JsonProperty("transaction")
        private final TransactionOnNetwork transaction;

    }

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(builder = SimulateTransactionResponse.SimulateTransactionResponseBuilder.class)
    public static class SimulateTransactionResponse {

        @JsonProperty("result")
        private final SimulationResults result;

    }

}
