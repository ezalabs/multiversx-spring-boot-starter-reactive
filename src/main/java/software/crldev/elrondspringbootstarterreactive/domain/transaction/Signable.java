package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * An Interface describing the behaviour
 * of a signable component by an wallet
 *
 */
public interface Signable {

    /**
     * Method used for serializing the object
     * in a JSON buffer to be ready for signing by a wallet
     *
     * @return - JSON buffer
     * @throws - JsonProcessingException
     */
    byte[] serializeForSigning() throws JsonProcessingException;

    /**
     * Method used for applying signature to Signable object
     *
     * @param signature - input for applying
     */
    void applySignature(Signature signature);

}
