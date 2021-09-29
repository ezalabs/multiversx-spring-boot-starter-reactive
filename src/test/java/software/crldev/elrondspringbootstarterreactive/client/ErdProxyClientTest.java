package software.crldev.elrondspringbootstarterreactive.client;

import software.crldev.elrondspringbootstarterreactive.api.ApiResponse;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClientImpl;
import software.crldev.elrondspringbootstarterreactive.config.ErdClientConfig;
import software.crldev.elrondspringbootstarterreactive.error.exception.EmptyPayloadException;
import software.crldev.elrondspringbootstarterreactive.error.exception.ProxyRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErdProxyClientTest {

    ErdProxyClient proxyClient;

    @Mock
    ErdClientConfig clientConfig;

    @Test
    void get() throws JsonProcessingException {
        var apiResponse = new ApiResponse<ResponseData>();
        apiResponse.setCode("successful");
        apiResponse.setData(new ResponseData("all good"));

        setupClient(apiResponse, HttpStatus.OK);
        StepVerifier.create(proxyClient.get("/account", ResponseData.class))
                .assertNext(r -> assertEquals(apiResponse.getData().getStatus(), r.getStatus()))
                .verifyComplete();
    }

    @Test
    void get_with_api_response_error() throws JsonProcessingException {
        var apiResponse = new ApiResponse<ResponseData>();
        apiResponse.setError("fail");

        setupClient(apiResponse, HttpStatus.OK);
        StepVerifier.create(proxyClient.get("/account", ResponseData.class))
                .expectErrorMessage("fail")
                .verify();
    }

    @Test
    void get_with_client_error() throws JsonProcessingException {
        setupClient(null, HttpStatus.INTERNAL_SERVER_ERROR);
        StepVerifier.create(proxyClient.get("/account", ResponseData.class))
                .expectError(ProxyRequestException.class)
                .verify();
    }

    @Test
    void post() throws JsonProcessingException {
        var apiResponse = new ApiResponse<ResponseData>();
        apiResponse.setCode("successful");
        apiResponse.setData(new ResponseData("all good"));

        setupClient(apiResponse, HttpStatus.OK);
        StepVerifier.create(proxyClient.post("/transaction", "payload", ResponseData.class))
                .assertNext(r -> assertEquals(apiResponse.getData().getStatus(), r.getStatus()))
                .verifyComplete();
    }

    @Test
    void post_when_body_null() throws JsonProcessingException {
        var apiResponse = new ApiResponse<ResponseData>();

        setupClient(apiResponse, HttpStatus.OK);
        assertThrows(EmptyPayloadException.class, () -> proxyClient.post("/transaction", null, ResponseData.class));
    }

    private void setupClient(ApiResponse<ResponseData> response, HttpStatus status) throws JsonProcessingException {
        var apiResponseString = new ObjectMapper().writeValueAsString(response);
        var clientResponse = ClientResponse
                .create(status)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(apiResponseString).build();

        var exchangeFunction = new ExchangeFunction() {
            @Override
            public @NonNull Mono<ClientResponse> exchange(@NonNull ClientRequest clientRequest) {
                return Mono.just(clientResponse);
            }
        };

        var webClientBuilder = WebClient.builder().exchangeFunction(exchangeFunction);
        when(clientConfig.getErdClientBuilder()).thenReturn(webClientBuilder);

        proxyClient = new ErdProxyClientImpl(clientConfig);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ResponseData {
        private String status;
    }
}