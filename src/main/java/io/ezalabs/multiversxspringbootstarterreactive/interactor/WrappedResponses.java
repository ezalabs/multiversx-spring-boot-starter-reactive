package io.ezalabs.multiversxspringbootstarterreactive.interactor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountOnNetwork;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ESDTToken;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.Hyperblock;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NFTData;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NetworkConfig;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NodeHeartbeatStatus;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ShardBlock;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ShardStatus;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.SimulationResults;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionForAddress;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionOnNetwork;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

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

  @Builder
  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonDeserialize(builder = ScQueryResponse.ScQueryResponseBuilder.class)
  public static class ScQueryResponse {

    @JsonProperty("data")
    private final ContractQueryResult result;

  }

  @Builder
  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonDeserialize(builder = AccountESDTsResponse.AccountESDTsResponseBuilder.class)
  public static class AccountESDTsResponse {

    @JsonProperty("esdts")
    private final Map<String, ESDTToken> result;

  }

  @Builder
  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonDeserialize(builder = GetTokenResponse.GetTokenResponseBuilder.class)
  public static class GetTokenResponse {

    @JsonProperty("tokens")
    private final Set<String> result;

  }

  @Builder
  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonDeserialize(builder = NFTDataResponse.NFTDataResponseBuilder.class)
  public static class NFTDataResponse {

    @JsonProperty("tokenData")
    private final NFTData result;

  }

}
