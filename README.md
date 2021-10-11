
# Elrond Spring Boot Starter Reactive

This is a **Spring Boot Starter** project for integrating with the **Elrond Network**, with the goal of achieving an **effortless autoconfigured integration** with the network.\
The client is implemented using project Reactor as the Reactive Streams specification implementation,
allowing **fully non-blocking operations** and providing **eficient demand management** when interacting with the network, ideal for **building reactive microservices**.

[![Build Status](https://app.travis-ci.com/crldev-software/elrond-spring-boot-starter-reactive.svg?branch=main)](https://app.travis-ci.com/crldev-software/elrond-spring-boot-starter-reactive)

[![java](https://img.shields.io/badge/Java11-07405E?style=for-the-badge&logo=java&logoColor=white)](https://openjdk.java.net/projects/jdk/11)
[![spring](https://img.shields.io/badge/SpringBoot2.0-217346?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![reactor](https://img.shields.io/badge/reactor-navy?style=for-the-badge&logo=s&logoColor=white)](https://projectreactor.io/)



## Author

[@carlo-stanciu](https://www.github.com/carlo-stanciu)

[![](https://crldev.software/img/readme-banner.png)](#)

## Features

- Auto synchronise network configurations from the Elrond Network at startup based on the configured gateway
- Non-blocking network requests with the reactive Elrond client
- Easy to use Interactors for various blockchain operations
- A lot of abstracted complexity in creating addresses, wallets, transactions


## Usage
To use the starter, add the following dependency to the dependencies section of your build descriptor:

- Maven (in your pom.xml)

```
<dependency>
  <groupId>software.crldev</groupId>
  <artifactId>elrond-spring-boot-starter-reactive</artifactId>
  <version>1.0.1</version>
</dependency>
```
- Gradle (in your build.gradle file)
```
dependencies {
  implementation(group: 'software.crldev', name: 'elrond-spring-boot-starter-reactive', version: '1.0.1')
}
```

## Documentation

First part of integration is setting up ```application.yaml```. If nothing is set, defaults will be used.

```yml
spring:
  elrond:
    client:
      gateway: https://devnet-gateway.elrond.com (default)
      readTimeoutMillis: 10000 (default)
      writeTimeoutMillis: 10000 (default)
```


The project uses objects from the **blockchain terminology** like **Address, Wallet, Transaction, Nonce, Gas, Signature** etc ...
so it's required to be familiar with them.

An **[Address](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/account/Address.java)** can be instantiated in two ways:
```
- fromHex (public key in HEX String)
- fromBech32 (address in Bech32 String)
```

A **[Wallet](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/wallet/Wallet.java)** is used for **signing transactions**. It can be instantiated in two ways:
```
- fromPrivateKeyBuffer (private key in byte[] format)
- fromPrivateKeyHex (private key in HEX String)
```

As creating a wallet directly with the above methods is a bit out of hand as we rarely have the plain private key, we can use the **WalletCreator** component as the best approach.

**[WalletCreator](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/wallet/WalletCreator.java)** has two methods (for now) for creating Wallets:
- fromPemFile (using a PEM file as an input)
- fromMnemonic (using a mnemonic phrase)

We can generate a mnemonic phrase by using **[MnemonicsUtils](src/main/java/software/crldev/elrondspringbootstarterreactive/util/MnemonicsUtils.java)**.


The interaction with the Elrond Network is done with the help of a set of so called **Interactors**, which provide all the required functionalities based on segregated parts of the network:

**[Account Interactor](src/main/java/software/crldev/elrondspringbootstarterreactive/interactor/account/ErdAccountInteractor.java)**
```
- getAccountInfo
- getBalance
- getNonce
- getTransactions
- getStorageValue
- getStorage
```
**[Block Interactor](src/main/java/software/crldev/elrondspringbootstarterreactive/interactor/block/ErdBlockInteractor.java)**
```
- queryHyperblockByNonce
- queryHyperblockByHash
- queryShardBlockByNonceFromShard
- queryShardBlockByHashFromShard
```
**[Network Interactor](src/main/java/software/crldev/elrondspringbootstarterreactive/interactor/network/ErdNetworkInteractor.java)**
```
- getNetworkConfig
- getShardStatus
- getNodeHeartbeatStatus
```
**[Transaction Interactor](src/main/java/software/crldev/elrondspringbootstarterreactive/interactor/transaction/ErdTransactionInteractor.java)**
```
- sendTransaction
- sendBatchOfTransactions
- simulateTransaction
- estimateTransactionCost
- ueryTransactionInfo
- queryTransactionStatus
```
The Transaction Interactor can be used for a more granular approach to transaction sending.\
In order to create a sendable transaction, we must first create an instance of a transaction using **[Transaction](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/transaction/Transaction.java)** domain object (manually adding nonce, gasLimit), then sign using a wallet and transform it to a payload for the Transaction Interactor (using toSendable() method).

For a more simple way of doing transaction operations, another component is more suitable:

**[Simple Transaction Sender](src/main/java/software/crldev/elrondspringbootstarterreactive/sender/ErdSimpleTransactionSender.java)**
```
- sendTransaction
- sendBatchOfTransactions
- simulateTransaction
- estimateTransactionCost
```

This component uses the TransactionInteractor and abstracts the complexity of transaction creation: automatically configures nonce, computes fee and applies the signature, having as an input a request with minimum necessary data:

**[Transaction Request](src/main/java/software/crldev/elrondspringbootstarterreactive/sender/TransactionRequest.java)**
```
 - wallet
 - receiver address
 - value
 - data
```

Example usage:
```java
@Autowired ErdSimpleTransactionSender sender;

Mono<TransactionHash> sendTransaction(File pemFile) {

    var request = TransactionRequest.builder()
                .wallet(WalletCreator.fromPemFile(pemFile))
                .receiverAddress(Address.fromBech32("erd1gklqdvxxxxxxxxxxxxxxxxxxxxx"))
                .value(Balance.fromEgld(3.5))
                .data(PayloadData.fromString("hello elrond"))
                .build();
    
    return sender.send(request);
}
```






## Planned features

In the next releases the following features have been planned:
- Smart Contract Interactor
- Wallet Creator - method to create wallet from password-protected JSON keystore file


## Changelog

[Check the latest changes](CHANGELOG.md)


## Contributing

Contributions are always welcome!

You can get in touch with me using the links below and figure out how to make the project better.


## 🔗 Links
[![portfolio](https://img.shields.io/badge/crldev.software-red?style=for-the-badge&logo=noi&logoColor=white)](https://crldev.software)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/carlo-cristian-stanciu)
[![twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/carlo_stanciu)



  