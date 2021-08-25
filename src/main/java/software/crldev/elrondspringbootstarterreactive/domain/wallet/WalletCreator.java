package software.crldev.elrondspringbootstarterreactive.domain.wallet;

import software.crldev.elrondspringbootstarterreactive.error.exception.CannotCreateWalletException;
import software.crldev.elrondspringbootstarterreactive.util.MnemonicsUtils;
import software.crldev.elrondspringbootstarterreactive.util.PemUtils;

import java.io.File;
import java.util.List;

/**
 * Creator Class used for generating Wallet instances
 */
public class WalletCreator {

    /**
     * Method used for creating Wallet instance
     * using private key from PEM file
     *
     * @param pem - required PEM file
     * @return - an instance of Wallet
     */
    public static Wallet fromPemFile(File pem) {
        try {
            var privateKeyHex = PemUtils.extractKeyHex(pem);
            return Wallet.fromPrivateKeyHex(privateKeyHex);
        } catch (Exception e) {
            throw new CannotCreateWalletException(e);
        }
    }

    /**
     * Method used for creating Wallet instance
     * using mnemonics
     *
     * @param mnemonics    - mnemonic phrase in a List
     * @param accountIndex - long value of index
     * @return - an instance of Wallet
     */
    public static Wallet fromMnemonic(List<String> mnemonics, long accountIndex) {
        var privateKey = MnemonicsUtils.privateKeyFromMnemonic(mnemonics, accountIndex);
        return Wallet.fromPrivateKeyBuffer(privateKey);
    }

}
