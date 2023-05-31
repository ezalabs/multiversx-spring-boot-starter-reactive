package software.crldev.multiversxspringbootstarterreactive.interactor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;

public class Helper {

  public static <R> void verifyInteractionOk(MxProxyClient client,
      Object apiResponse,
      Supplier<Publisher<R>> action,
      Consumer<R> assertion, HttpMethod method) {

    mockClientMethod(client, apiResponse, method);

    StepVerifier.create(action.get())
        .assertNext(assertion)
        .verifyComplete();
  }

  public static <R> void verifyInteractionException(MxProxyClient client,
      Object apiResponse,
      Supplier<Publisher<R>> action,
      Class<? extends RuntimeException> exception, HttpMethod method) {

    mockClientMethod(client, apiResponse, method);

    StepVerifier.create(action.get())
        .verifyError(exception);
  }

  private static void mockClientMethod(MxProxyClient client, Object apiResponse, HttpMethod method) {
    if (method == HttpMethod.GET) {
      when((client.get(anyString(), any()))).thenReturn(Mono.just(apiResponse));
    } else {
      when((client.post(anyString(), any(), any()))).thenReturn(Mono.just(apiResponse));
    }
  }

}
