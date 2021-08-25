package software.crldev.elrondspringbootstarterreactive.client;

import software.crldev.elrondspringbootstarterreactive.api.ApiResponse;
import software.crldev.elrondspringbootstarterreactive.config.ErdClientConfig;
import software.crldev.elrondspringbootstarterreactive.config.JsonMapper;
import software.crldev.elrondspringbootstarterreactive.error.exception.EmptyPayloadException;
import software.crldev.elrondspringbootstarterreactive.error.exception.ProxyRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

public class ErdProxyClientImpl implements ErdProxyClient {

    private final WebClient erdClient;

    public ErdProxyClientImpl(ErdClientConfig config) {
        this.erdClient = config.getErdClientBuilder().build();
    }

    public <T> Mono<T> get(String uri, Class<T> responseType) {
        return processRequest(uri, null, responseType, HttpMethod.GET);
    }

    public <T, P> Mono<T> post(String uri, P payload, Class<T> responseType) {
        return processRequest(uri, payload, responseType, HttpMethod.POST);
    }

    private <T, P> Mono<T> processRequest(String uri, P payload, Class<T> responseType, HttpMethod method) {
        var requestBuilder = erdClient
                .method(method)
                .uri(uri);

        if (method == HttpMethod.POST) {
            if (nonNull(payload)) {
                requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(payload);
            } else {
                throw new EmptyPayloadException();
            }
        }

        return requestBuilder.exchangeToMono(r -> r.bodyToMono(String.class))
                .map(r -> JsonMapper.deserializeApiResponse(r, responseType))
                .doOnSuccess(this::onSuccess)
                .doOnError(this::onError)
                .map(ApiResponse::getData);
    }

    private <T> void onSuccess(ApiResponse<T> response) {
        response.throwIfError();
    }

    private void onError(Throwable t) {
        var errorMessage = t.getMessage();
        throw new ProxyRequestException(errorMessage);
    }

}
