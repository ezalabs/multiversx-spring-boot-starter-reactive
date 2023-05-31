package software.crldev.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.NFTCreation;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTUri;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenRoyalties;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Hash;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class NFTCreationTest {

  final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  final TokenName nftName = TokenName.fromString("Yummy Token");
  final TokenIdentifier identifier = TokenIdentifier.fromString("YMY-19jf9a");
  final TokenAttributes attributes = TokenAttributes.fromString("ipfsCID/fileName.json", new String[]{"one", "two"});
  final TokenRoyalties royalties = TokenRoyalties.fromNumber(1000);
  final TokenInitialSupply quantity = TokenInitialSupply.fromNumber(BigInteger.valueOf(1000));
  final ESDTUri uri1 = ESDTUri.fromString("ipfsCID/img.jpeg", ESDTUri.Type.MEDIA);
  final ESDTUri uri2 = ESDTUri.fromString("ipfsCID/img2.jpeg", ESDTUri.Type.MEDIA);
  final Set<ESDTUri> uris = new LinkedHashSet<>(List.of(uri1, uri2));

  @Test
  void create() {
    var nft = builder().build();

    var req = nft.toTransactionRequest(wallet);

    assertEquals(wallet.getAddress().getBech32(), req.getReceiverAddress().getBech32());
    assertEquals(Balance.zero().getValue(), req.getValue().getValue());
    assertEquals(GasLimit.defaultNftCreate().getValue(), req.getGasLimit().getValue());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_NFT_CREATE_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(TokenInitialSupply.fromNumber(BigInteger.ONE).getHex(), dataArgs[2]);
    System.out.println("IS - " + TokenInitialSupply.fromNumber(BigInteger.ONE).getHex());
    assertEquals(nftName.getHex(), dataArgs[3]);
    assertEquals(royalties.getHex(), dataArgs[4]);
    assertEquals("00", dataArgs[5]);
    assertEquals(attributes.getHex(), dataArgs[6]);
    assertEquals(uri1.getHex(), dataArgs[7]);
    assertEquals(uri2.getHex(), dataArgs[8]);
  }

  @Test
  void createWithHashAndQuantity() {
    var hash = Hash.fromString("7582c5d37652217e5b0ed93b74019f45c93af4ba0522a5a2ebb4a1256a336e4d");
    var nft = builder().hash(hash).initialQuantity(quantity).build();

    var req = nft.toTransactionRequest(wallet);

    var dataArgs = req.getData().toString().split("@");
    assertEquals(quantity.getHex(), dataArgs[2]);
    assertEquals(hash.toString(), dataArgs[5]);
  }

  private NFTCreation.NFTCreationBuilder builder() {
    return NFTCreation.builder()
        .tokenIdentifier(identifier)
        .nftName(nftName)
        .tokenRoyalties(royalties)
        .tokenAttributes(attributes)
        .uris(uris);
  }

}