package software.crldev.elrondspringbootstarterreactive.util;

import software.crldev.elrondspringbootstarterreactive.error.exception.CannotDeriveKeysException;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotGenerateMnemonicException;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static software.crldev.elrondspringbootstarterreactive.config.constants.WalletConstants.*;

/**
 * Utility class for mnemonics operations
 *
 * @author carlo_stanciu
 *
 *  source from
 */
public class MnemonicsUtils {

    /**
     * Method used to generate mnemonics which can be used on Wallet creation
     *
     * @return - mnemonic phrase as List of Strings
     */
    public static List<String> generateMnemonic() {
        try {
            var entropy = generateEntropy();
            var mnemonicCode = new MnemonicCode();
            return mnemonicCode.toMnemonic(entropy);
        } catch (IOException | MnemonicException.MnemonicLengthException error) {
            throw new CannotGenerateMnemonicException();
        }
    }

    /**
     * Method used to extract private key buffer from a list of mnemonics
     *
     * @param mnemonics - a mnemonic phrase as list of String objects
     * @param accountIndex - accountIndex
     * @return - buffer of the private key
     */
    public static byte[] privateKeyFromMnemonic(List<String> mnemonics, long accountIndex) {
        try {
            var mnemonicsAsString = mnemonics.stream().map(String::trim).collect(Collectors.joining(" "));
            var seed = mnemonicToBip39Seed(mnemonicsAsString);
            return bip39SeedToPrivateKey(seed, accountIndex);
        } catch (IOException error) {
            throw new CannotDeriveKeysException();
        }
    }

    private static byte[] mnemonicToBip39Seed(String mnemonic) {
        var mnemonicBytes = mnemonic.getBytes();
        var passphrase = BIP39_SALT_MODIFIER.getBytes();
        var generator = new PKCS5S2ParametersGenerator(new SHA512Digest());

        generator.init(mnemonicBytes, passphrase, BIP39_PBKDF2_ROUNDS);
        return ((KeyParameter) generator.generateDerivedParameters(512)).getKey();
    }

    private static byte[] generateEntropy() {
        var random = new SecureRandom();
        var entropy = new byte[DEFAULT_ENTROPY_BITS / 8];
        random.nextBytes(entropy);
        return entropy;
    }

    private static byte[] bip39SeedToPrivateKey(byte[] seed, long accountIndex) throws IOException {
        var keyAndChainCode = bip39SeedToMasterKey(seed);
        var key = keyAndChainCode.key;
        var chainCode = keyAndChainCode.chainCode;
        var derivationPath = Arrays.copyOf(ELROND_DERIVATION_PATH, ELROND_DERIVATION_PATH.length);

        derivationPath[derivationPath.length - 1] = accountIndex;

        for (var segment : derivationPath) {
            keyAndChainCode = ckdPriv(key, chainCode, segment + HARDENED_OFFSET);
            key = keyAndChainCode.key;
            chainCode = keyAndChainCode.chainCode;
        }

        return key;
    }

    private static KeyAndChainCode bip39SeedToMasterKey(byte[] seed) {
        var result = hmacSHA512(BIP32_SEED_MODIFIER.getBytes(), seed);
        var masterKey = Arrays.copyOfRange(result, 0, 32);
        var chainCode = Arrays.copyOfRange(result, 32, 64);

        return new KeyAndChainCode(masterKey, chainCode);
    }

    private static KeyAndChainCode ckdPriv(byte[] key, byte[] chainCode, long index) throws IOException {
        var indexBuffer = ByteBuffer.allocate(4);
        indexBuffer.order(ByteOrder.BIG_ENDIAN);
        indexBuffer.putInt((int) (index & 0xffffffffL));
        var indexBytes = indexBuffer.array();

        var dataStream = new ByteArrayOutputStream();
        dataStream.write(new byte[]{0});
        dataStream.write(key);
        dataStream.write(indexBytes);

        var data = dataStream.toByteArray();
        var result = hmacSHA512(chainCode, data);

        return new KeyAndChainCode(Arrays.copyOfRange(result, 0, 32), Arrays.copyOfRange(result, 32, 64));
    }

    private static byte[] hmacSHA512(byte[] key, byte[] message) {
        var result = new byte[64];
        var hmac = new HMac(new SHA512Digest());

        hmac.init(new KeyParameter(key));
        hmac.update(message, 0, message.length);
        hmac.doFinal(result, 0);

        return result;
    }

    private static class KeyAndChainCode {
        public final byte[] key;
        public final byte[] chainCode;

        private KeyAndChainCode(byte[] key, byte[] chainCode) {
            this.key = key;
            this.chainCode = chainCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            var that = (KeyAndChainCode) o;

            if (!Arrays.equals(key, that.key)) return false;
            return Arrays.equals(chainCode, that.chainCode);
        }

        @Override
        public int hashCode() {
            var result = Arrays.hashCode(key);
            result = 31 * result + Arrays.hashCode(chainCode);

            return result;
        }
    }

}
