package io.ezalabs.multiversxspringbootstarterreactive.config.constants;

/**
 * Class containing static config values used in Wallet construction and validation
 *
 * @author carlo_stanciu
 */
public class WalletConstants {

  public static final int DEFAULT_ENTROPY_BITS = 256;
  public static final int PRIVATE_KEY_LENGTH = 64;
  public static final String BIP39_SALT_MODIFIER = "mnemonic";
  public static final int BIP39_PBKDF2_ROUNDS = 2048;
  public static final String BIP32_SEED_MODIFIER = "ed25519 seed";
  public static final long[] MX_DERIVATION_PATH = {44, 508, 0, 0, 0};
  public static final long HARDENED_OFFSET = 0x80000000;

}
