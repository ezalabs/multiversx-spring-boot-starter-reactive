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
    INVALID_GATEWAY("Invalid gateway input. Must be mainnet, testnet or devnet."),
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
    RESPONSE_NOT_SUCCESSFUL("Response not successful."),
    INVALID_NONCE("Invalid nonce: %s."),
    INVALID_BALANCE("Invalid balance: %s."),
    INVALID_TRANSACTION_VERSION("Invalid transaction version: %s."),
    INVALID_TRANSACTION_STATUS("Invalid transaction status: %s."),
    INVALID_SENT_TRANSACTIONS("Transactions sent were invalid and not processed."),
    INVALID_CHAIN_ID("Invalid chainID: %s."),
    INVALID_PEM_FILE("Invalid PEM file. Error: %s."),
    MISSING_TRANSACTIONS_REQUESTS("Missing transactions request, could not create sendables."),
    NEGATIVE_GAS("Negative gas: %s."),
    FUNCTION_ARGS_NULL("Function args is empty or nul.l"),
    FUNCTION_NAME("Function name cannot be null or empty."),
    TOKEN_NAME("Invalid ESDT token name. Length must be between 3 and 20, alphanumeric chars only."),
    TOKEN_IDENTIFIER("Invalid ESDT token identifier."),
    TOKEN_TICKER("Invalid ESDT token ticker. Length must be between 3 and 10, alphanumeric chars only."),
    TOKEN_DECIMALS("Invalid number of decimals for ESDT token. Numerical value must be even and between 0 and 18."),
    INITIAL_SUPPLY("Invalid number for initial supply of ESDT token."),
    TOKEN_PROPERTY("Invalid ESDT token properties. Must be canFreeze, canWipe, canPause, canChangeOwner, canUpgrade, canAddSpecialRoles, canTransferNFTCreateRole."),
    TOKEN_ROLE("Invalid role. Must be ESDTRoleLocalBurn and / or ESDTRoleLocalMint."),
    ADDRESS_EMPTY("Target address cannot be empty."),
    TOKEN_ROYALTIES("Token royalties value must be between 0 and 10000 (0 meaning 0% and 10000 meaning 100%)."),
    TOKEN_ATTRIBUTES("Token attributes cannot be null or empty. Metadata URI must point to a JSON file."),
    NFT_MEDIA_URI("Invalid NFT media URI. File format can only be png, jpeg, jpg, gif, acc, flac, m4a, mp3, wav, mov, quicktime, mp4 and webm."),
    NFT_METADATA_URI("Invalid NFT metadata URI. File format can only be json.");

    private final String value;

}
