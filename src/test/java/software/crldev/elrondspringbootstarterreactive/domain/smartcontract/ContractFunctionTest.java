package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class ContractFunctionTest {

    @Test
    void getPayloadData() {
        var arg1 = "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr";
        var arg2 = "erd2gklqdv77my5y8n45hsz3907gq54q9xk0tmzdh8v5vkfstd64aw7se92304f";
        var arg1Hex = Hex.toHexString(arg1.getBytes());
        var arg2Hex = Hex.toHexString(arg2.getBytes());

        var scFunction = ContractFunction.builder()
                .functionName(FunctionName.fromString("deposit"))
                .args(FunctionArgs.fromString(arg1, arg2))
                .smartContractAddress(Address.zero())
                .build();

        assertEquals(format("deposit@%s@%s", arg1Hex, arg2Hex), scFunction.getPayloadData().toString());
    }
}