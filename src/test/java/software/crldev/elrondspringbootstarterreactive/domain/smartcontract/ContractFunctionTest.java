package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;

import java.math.BigInteger;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContractFunctionTest {

    @Test
    void getPayloadData() {
        var arg1 = FunctionArg.fromString("argument");
        var arg2 = FunctionArg.fromNumber(BigInteger.TWO);

        var scFunction = ContractFunction.builder()
                .functionName(FunctionName.fromString("deposit"))
                .args(List.of(arg1, arg2))
                .smartContractAddress(Address.zero())
                .build();

        assertEquals(format("deposit@%s@%s", arg1.getHex(), arg2.getHex()), scFunction.getPayloadData().toString());
    }
}