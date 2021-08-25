package software.crldev.elrondspringbootstarterreactive.interactor.block;

import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.Hyperblock;
import software.crldev.elrondspringbootstarterreactive.api.model.ShardBlock;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@AllArgsConstructor
public class ErdBlockInteractorImpl implements ErdBlockInteractor {

    private final ErdProxyClient client;

    @Override
    public Mono<Hyperblock> queryHyperblockByNonce(Long nonce) {
        return client
                .get(format(ApiResourceURI.HYPERBLOCK_BY_NONCE.getURI(), nonce),
                        WrappedResponses.QueryHyperblock.class)
                .map(WrappedResponses.QueryHyperblock::getHyperblock);
    }

    @Override
    public Mono<Hyperblock> queryHyperblockByHash(String hash) {
        return client
                .get(format(ApiResourceURI.HYPERBLOCK_BY_HASH.getURI(), hash),
                        WrappedResponses.QueryHyperblock.class)
                .map(WrappedResponses.QueryHyperblock::getHyperblock);
    }

    @Override
    public Mono<ShardBlock> queryShardBlockByNonceFromShard(Integer shard, Long nonce) {
        return client
                .get(format(ApiResourceURI.BLOCK_BY_NONCE_FROM_SHARD.getURI(), shard, nonce),
                        WrappedResponses.QueryBlock.class)
                .map(WrappedResponses.QueryBlock::getBlock);
    }

    @Override
    public Mono<ShardBlock> queryShardBlockByHashFromShard(Integer shard, String hash) {
        return client
                .get(format(ApiResourceURI.BLOCK_BY_HASH_FROM_SHARD.getURI(), shard, hash),
                        WrappedResponses.QueryBlock.class)
                .map(WrappedResponses.QueryBlock::getBlock);
    }

}
