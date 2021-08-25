package software.crldev.elrondspringbootstarterreactive.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum containing error messages
 * used in custom Exceptions
 *
 * @author carlo_stanciu
 */
@AllArgsConstructor
@Getter
public enum ErrorMessage {

    WRONG_NETWORK_ADDRESS("Wrong network address. %s network required."),
    INVALID_HEX_VALUE("Could not create address. Invalid hex value %s."),
    PRIVATE_KEY_LENGTH("Invalid private key length: %s"),
    PAYLOAD("Payload cannot be null."),
    CANNOT_CREATE_ADDRESS("Cannot create address from %s."),
    CANNOT_CREATE_SIGNATURE("Cannot create signature from %s."),
    CANNOT_DECODE_BECH32_ADDRESS("Cannot decode bech32 address."),
    CANNOT_CONVERT_BITS("There was an error in bits conversion."),
    CANNOT_GENERATE_MNEMONIC("Cannot generate mnemonic"),
    CANNOT_DERIVE_KEYS("Cannot derive keys"),
    CANNOT_SIGN_TRANSACTION("Cannot sign transaction. %s."),
    SIGNATURE_EMPTY("Cannot retrieve hex value, signature is empty."),
    CANNOT_CREATE_WALLET("Cannot create wallet. %s."),
    INVALID_NONCE("Invalid nonce: %s."),
    INVALID_BALANCE("Invalid balance: %s."),
    INVALID_TRANSACTION_VERSION("Invalid transaction version: %s."),
    INVALID_TRANSACTION_STATUS("Invalid transaction status: %s."),
    INVALID_SENT_TRANSACTIONS("Transactions sent were invalid and not processed."),
    INVALID_CHAIN_ID("Invalid chainID: %s."),
    MISSING_TRANSACTIONS_REQUESTS("Missing transactions request, could not create sendables."),
    NEGATIVE_GAS("Negative gas: %s.");

    private final String value;

}
