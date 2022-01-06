package software.crldev.elrondspringbootstarterreactive.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum containing URI for accessing resources on the network
 *
 * @author carlo_stanciu
 */
@AllArgsConstructor
@Getter
public enum ApiResourceURI {

    NETWORK_CONFIG("network/config"),
    SHARD_STATUS("network/status/%s"),
    ACCOUNT_ON_NETWORK("address/%s"),
    ACCOUNT_BALANCE("address/%s/balance"),
    ACCOUNT_NONCE("address/%s/nonce"),
    ACCOUNT_STORAGE_VALUE("address/%s/key/%s"),
    ACCOUNT_STORAGE("address/%s/keys"),
    ADDRESS_TRANSACTIONS("address/%s/transactions"),
    TRANSACTION_ON_NETWORK("transaction/%s?withResults=%s"),
    SEND_TRANSACTION("transaction/send"),
    SEND_MULTIPLE_TRANSACTIONS("transaction/send-multiple"),
    SIMULATE_TRANSACTION("transaction/simulate"),
    ESTIMATE_TRANSACTION_COST("transaction/cost"),
    TRANSACTION_STATUS("transaction/%s/status"),
    NODE_HEARTBEAT_STATUS("node/heartbeatstatus"),
    HYPERBLOCK_BY_NONCE("hyperblock/by-nonce/%s"),
    HYPERBLOCK_BY_HASH("hyperblock/by-hash/%s"),
    BLOCK_BY_NONCE_FROM_SHARD("block/%s/by-nonce/%s"),
    BLOCK_BY_HASH_FROM_SHARD("block/%s/by-hash/%s"),
    QUERY_SMART_CONTRACT("vm-values/query"),
    QUERY_SMART_CONTRACT_HEX("vm-values/hex"),
    QUERY_SMART_CONTRACT_STRING("vm-values/string"),
    QUERY_SMART_CONTRACT_INT("vm-values/int");

    private final String URI;

}
