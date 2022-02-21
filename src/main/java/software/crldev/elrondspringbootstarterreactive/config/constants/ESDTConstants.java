package software.crldev.elrondspringbootstarterreactive.config.constants;

import java.math.BigInteger;

public class ESDTConstants {

    public static final String ESDT_ISSUER_BECH32_ADDRESS = "erd1qqqqqqqqqqqqqqqpqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzllls8a5w6u";
    public static final BigInteger ESDT_ISSUANCE_EGLD_COST = BigInteger.valueOf(50_000_000_000_000_000L);
    public static final BigInteger ESDT_ISSUANCE_GAS_COST = BigInteger.valueOf(60_000_000L);
    public static final BigInteger ESDT_LOCAL_OP_GAS_COST = BigInteger.valueOf(300_000L);
    public static final BigInteger ESDT_GLOBAL_OP_GAS_COST = BigInteger.valueOf(60_000_000L);
    public static final BigInteger ESDT_TRANSFER_GAS_COST = BigInteger.valueOf(500_000L);
    public static final BigInteger ESDT_MULTI_TRANSFER_GAS_COST_BASE = BigInteger.valueOf(1_100_000L);
    public static final BigInteger ESDT_NFT_CREATE_COST = BigInteger.valueOf(5_000_000L);

    public static final String ESDT_GET_PROPERTIES_CALL = "getTokenProperties";
    public static final String ESDT_GET_SPECIAL_ROLES_CALL = "getSpecialRoles";
    public static final String ESDT_FUNGIBLE_ISSUANCE_CALL = "issue";
    public static final String ESDT_SEMI_FUNGIBLE_ISSUE_CALL = "issueSemiFungible";
    public static final String ESDT_NON_FUNGIBLE_ISSUE_CALL = "issueNonFungible";
    public static final String META_ESDT_ISSUE_CALL = "registerMetaESDT";
    public static final String ESDT_UPGRADE_CALL = "controlChanges";
    public static final String ESDT_NFT_CREATE_CALL = "ESDTNFTCreate";
    public static final String ESDT_NFT_ADD_URI_CALL = "ESDTNFTAddURI";
    public static final String ESDT_NFT_UPDATE_ATTRIBUTES_CALL = "ESDTNFTUpdateAttributes";
    public static final String ESDT_TRANSFER_CALL = "ESDTTransfer";
    public static final String ESDT_MULTI_TRANSFER_CALL = "MultiESDTNFTTransfer";
    public static final String ESDT_MINT_CALL = "ESDTLocalMint";
    public static final String ESDT_NFTSFT_ADD_CALL = "ESDTNFTAddQuantity";
    public static final String ESDT_NFTSFT_BURN_CALL = "ESDTNFTBurn";
    public static final String ESDT_BURN_CALL = "ESDTLocalBurn";
    public static final String ESDT_FREEZE_CALL = "freeze";
    public static final String ESDT_NFT_FREEZE_CALL = "freezeSingleNFT";
    public static final String ESDT_UNFREEZE_CALL = "unFreeze";
    public static final String ESDT_NFT_UNFREEZE_CALL = "unFreezeSingleNFT";
    public static final String ESDT_PAUSE_CALL = "pause";
    public static final String ESDT_UNPAUSE_CALL = "unPause";
    public static final String ESDT_WIPE_CALL = "wipe";
    public static final String ESDT_NFT_WIPE_CALL = "wipeSingleNFT";
    public static final String ESDT_SET_ROLE_CALL = "setSpecialRole";
    public static final String ESDT_UNSET_ROLE_CALL = "unSetSpecialRole";
    public static final String ESDT_TRANSFER_OWNERSHIP_CALL = "transferOwnership";
    public static final String ESDT_NFT_CREATE_ROLE_TRANSFER_CALL = "transferNFTCreateRole";
    public static final String ESDT_NFT_STOP_CREATION_CALL = "stopNFTCreate";

    public static final String ESDT_BURN_ROLE = "ESDTRoleLocalBurn";
    public static final String ESDT_MINT_ROLE = "ESDTRoleLocalMint";
    public static final String ESDT_NFT_ADD_QUANTITY_ROLE = "ESDTRoleNFTAddQuantity";
    public static final String ESDT_NFT_CREATE_ROLE = "ESDTRoleNFTCreate";
    public static final String ESDT_NFT_BURN_ROLE = "ESDTRoleNFTBurn";
    public static final String ESDT_NFT_UPDATE_ATTRIBUTES = "ESDTRoleNFTUpdateAttributes";
    public static final String ESDT_NFT_ADD_URI = "ESDTRoleNFTAddURI";
    public static final String ESDT_TRANSFER = "ESDTTransferRole";


}
