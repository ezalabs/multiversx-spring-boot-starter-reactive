package software.crldev.elrondspringbootstarterreactive.interactor.block;

import software.crldev.elrondspringbootstarterreactive.api.model.Hyperblock;
import software.crldev.elrondspringbootstarterreactive.api.model.ShardBlock;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.interactor.Helper;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ErdBlockInteractorTest {

    ErdBlockInteractor blockInteractor;

    @Mock
    ErdProxyClient client;

    Long nonce = 10L;
    String hash = "af32f23fijo209j903f";

    @BeforeEach
    void setUp() {
        blockInteractor = new ErdBlockInteractorImpl(client);
    }

    @Test
    void queryHyperblock_byNonce_and_byHash() {
        var apiResponse = WrappedResponses.QueryHyperblock
                .builder()
                .hyperblock(Hyperblock
                        .builder()
                        .nonce(nonce)
                        .hash(hash)
                        .build())
                .build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> blockInteractor.queryHyperblockByNonce(nonce),
                (r) -> {
                    assertEquals(nonce, r.getNonce());
                    assertEquals(hash, r.getHash());
                }, HttpMethod.GET);

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> blockInteractor.queryHyperblockByHash(hash),
                (r) -> {
                    assertEquals(nonce, r.getNonce());
                    assertEquals(hash, r.getHash());
                }, HttpMethod.GET);
    }

    @Test
    void queryShardBlock_byNonce_and_byHash() {
        var apiResponse = WrappedResponses.QueryBlock
                .builder()
                .block(ShardBlock
                        .builder()
                        .nonce(nonce)
                        .hash(hash)
                        .build())
                .build();

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> blockInteractor.queryShardBlockByNonceFromShard(1, nonce),
                (r) -> {
                    assertEquals(nonce, r.getNonce());
                    assertEquals(hash, r.getHash());
                }, HttpMethod.GET);

        Helper.verifyInteractionOk(
                client,
                apiResponse,
                () -> blockInteractor.queryShardBlockByHashFromShard(1, hash),
                (r) -> {
                    assertEquals(nonce, r.getNonce());
                    assertEquals(hash, r.getHash());
                }, HttpMethod.GET);
    }

}