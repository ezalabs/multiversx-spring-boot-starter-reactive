package io.ezalabs.multiversxspringbootstarterreactive.client;

import static java.util.Objects.nonNull;

import io.ezalabs.multiversxspringbootstarterreactive.config.JsonMapper;
import io.ezalabs.multiversxspringbootstarterreactive.config.MxClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.api.ApiResponse;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.EmptyPayloadException;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.ProxyRequestException;

@Slf4j
public class MxProxyClientImpl implements MxProxyClient {

  private final WebClient mxClient;

  public MxProxyClientImpl(MxClientConfig config) {
    this.mxClient = config.getMxClientBuilder().build();
  }

  public <T> Mono<T> get(String uri, Class<T> responseType) {
    log.debug("[ProxyClient] executing GET {}", uri);
    return processRequest(uri, null, responseType, HttpMethod.GET);
  }

  public <T, P> Mono<T> post(String uri, P payload, Class<T> responseType) {
    log.debug("[ProxyClient] executing POST {}", uri);
    return processRequest(uri, payload, responseType, HttpMethod.POST);
  }

  private <T, P> Mono<T> processRequest(String uri, P payload, Class<T> responseType, HttpMethod method) {
    var requestBuilder = mxClient
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
