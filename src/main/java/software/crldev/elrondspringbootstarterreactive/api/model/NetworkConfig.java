package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

/**
 * API response when querying for network configuration
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NetworkConfig {

    @JsonProperty("erd_chain_id")
    String chainId;
    @JsonProperty("erd_denomination")
    Long denomination;
    @JsonProperty("erd_gas_per_data_byte")
    Long gasPerDataByte;
    @JsonProperty("erd_gas_price_modifier")
    Double gasPriceModifier;
    @JsonProperty("erd_latest_tag_software_version")
    String latestTagSoftwareVersion;
    @JsonProperty("erd_meta_consensus_group_size")
    Integer metaConsensusGroupSize;
    @JsonProperty("erd_min_gas_limit")
    BigInteger minGasLimit;
    @JsonProperty("erd_min_gas_price")
    BigInteger minGasPrice;
    @JsonProperty("erd_min_transaction_version")
    Integer minTransactionVersion;
    @JsonProperty("erd_num_metachain_nodes")
    Integer numMetachainNodes;
    @JsonProperty("erd_num_nodes_in_shard")
    Integer numNodesInShard;
    @JsonProperty("erd_num_shards_without_meta")
    Integer numShardsWithoutMeta;
    @JsonProperty("erd_rewards_top_up_gradient_poInteger")
    BigInteger rewardsTopUpGradientPoInteger;
    @JsonProperty("erd_round_duration")
    Long roundDuration;
    @JsonProperty("erd_rounds_per_epoch")
    Long roundPerEpoch;
    @JsonProperty("erd_shard_consensus_group_size")
    Integer shardConsensusGroupSize;
    @JsonProperty("erd_start_time")
    Long startTime;
    @JsonProperty("erd_top_up_factor")
    Double topUpFactor;

}