package software.crldev.elrondspringbootstarterreactive.config;

import software.crldev.elrondspringbootstarterreactive.api.ApiResponse;
import software.crldev.elrondspringbootstarterreactive.error.exception.DeserializationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Component used to serialisation / deserialization based on JacksonMapper
 *
 * @author carlo_stanciu
 */
public class JsonMapper {

    private static final ObjectMapper mapper;

    static {
        mapper = new Jackson2ObjectMapperBuilder()
                .build()
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * Deserializes responses received from the gateway
     *
     * @param response - JSON response as String
     * @param responseType - response class type object type for deserialization
     * @param <T> - response object
     * @return - ApiResponse containing the parametrized data value
     */
    public static <T> ApiResponse<T> deserializeApiResponse(String response, Class<T> responseType) {
        try {
            var type = mapper.getTypeFactory().constructParametricType(ApiResponse.class, responseType);
            return mapper.readValue(response, type);
        } catch (JsonProcessingException e) {
            throw new DeserializationException(e.getMessage());
        }
    }

    /**
     * Serializes an Object the buffer of its JSON String
     *
     * @param object - target object
     * @return - buffer of the JSON String
     * @throws JsonProcessingException
     */
    public static byte[] serializeToJsonBuffer(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object).getBytes();
    }

    /**
     * Deserializes a JSON file to an Object
     *
     * @param file - target JSON file
     * @param type - class of the target Object type
     * @param <T> - target Object type
     * @return - an instance of the deserialized Object
     */
    public static <T> T deserializeFile(File file, Class<T> type) {
        try {
            return mapper.readValue(file, type);
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }

}
