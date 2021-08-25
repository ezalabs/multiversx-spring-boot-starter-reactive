package software.crldev.elrondspringbootstarterreactive.util;

import software.crldev.elrondspringbootstarterreactive.error.exception.CannotConvertBitsException;

import java.io.ByteArrayOutputStream;

public class BitsConverter {

    public static byte[] convertBits(byte[] data, int fromBits, int toBits, boolean pad) {
        int acc = 0;
        int bits = 0;
        var ret = new ByteArrayOutputStream();
        int maxv = (1 << toBits) - 1;
        int maxAcc = (1 << (fromBits + toBits - 1)) - 1;

        for (byte value : data) {
            var valueAsInt = value & 0xff;

            if (valueAsInt >>> fromBits != 0) {
                throw new CannotConvertBitsException();
            }

            acc = ((acc << fromBits) | valueAsInt) & maxAcc;
            bits += fromBits;

            while (bits >= toBits) {
                bits -= toBits;
                ret.write((acc >>> bits) & maxv);
            }
        }

        if (pad) {
            if (bits > 0) {
                ret.write((acc << (toBits - bits)) & maxv);
            }
        } else if (bits >= fromBits || ((acc << (toBits - bits)) & maxv) != 0) {
            throw new CannotConvertBitsException();
        }

        return ret.toByteArray();
    }

}
