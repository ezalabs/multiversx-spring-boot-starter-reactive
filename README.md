# Elrond Spring Boot Starter Reactive

This is a **Spring Boot Starter** project for integrating with the **Elrond Network**, with the goal of achieving an **effortless autoconfigured integration** with the network.\
The client is implemented using project Reactor as the Reactive Streams' specification implementation,
allowing **fully non-blocking operations** and providing **efficient demand management** when interacting with the network, ideal for building **scalable reactive microservices**.

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
- Easy to use Interactors for executing various blockchain operations
- A lot of abstracted complexity in creating addresses, wallets, transactions


## Usage
To use the starter, add the following dependency to the dependencies section of your build descriptor:

- Maven (in your pom.xml)

```
<dependency>
  <groupId>software.crldev</groupId>
  <artifactId>elrond-spring-boot-starter-reactive</artifactId>
  <version>1.0.5</version>
</dependency>
```
- Gradle (in your build.gradle file)
```
dependencies {
  implementation(group: 'software.crldev', name: 'elrond-spring-boot-starter-reactive', version: '1.0.5')
}
```
- And some other required dependencies for cryptographic functions:
```
implementation group: 'org.bouncycastle', name: 'bcmail-jdk15on', version: '1.69'
implementation group: 'org.bouncycastle', name: 'bcpkix-jdk15on', version: '1.69'
implementation group: 'org.bouncycastle', name: 'bcprov-jdk15on', version: '1.69'
implementation group: 'org.bouncycastle', name: 'bcprov-ext-jdk15on', version: '1.69'
implementation group: 'org.bitcoinj', name: 'bitcoinj-core', version: '0.15.10'
```

## Documentation

First part of integration is setting up ```application.yaml```. If nothing is set, defaults will be used.

```yml
spring:
  elrond:
    client:
      gateway: devnet (default) (mainnet | testnet | devnet)
      readTimeoutMillis: 10000 (default)
      writeTimeoutMillis: 10000 (default)
```


The project uses object notations from the **blockchain terminology** like **Address, Wallet, Transaction, Nonce, Gas, Signature** etc ...
so it's required to be familiar with them.

An **[Address](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/account/Address.java)** can be instantiated in two ways:
```
- fromHex (public key in HEX String)
- fromBech32 (address in Bech32 String)
```

A **[Wallet](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/wallet/Wallet.java)** is used for **signing transactions**. It can be instantiated in multiple ways:
```
- fromPrivateKeyBuffer (private key in byte[] format)
- fromPrivateKeyHex (private key in HEX String)
- fromPemFile (using a PEM file as an input) (both File & reactive FilePart supported)
- fromMnemonic (using a mnemonic phrase)
- fromKeyStore (not yet implemented)
```

We can generate a mnemonic phrase by using **[MnemonicsUtils](src/main/java/software/crldev/elrondspringbootstarterreactive/util/MnemonicsUtils.java)**.


The interaction with the Elrond Network is done with the help of a set components called **Interactors**, which provide all the required functionalities based on segregated parts of the network:

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
- queryTransactionInfo
- queryTransactionStatus
```
The Transaction Interactor has methods used for a more granular approach to transaction operations.\
In order to create a sendable transaction, we must first create an instance of a transaction using **[Transaction](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/transaction/Transaction.java)** domain object (setting **nonce, gasLimit, version** etc), then sign it using a wallet and transform it to a payload for the Transaction Interactor (using **toSendable()** method).

For a more simple way of doing transaction operations, the interactor also has overloaded methods for **sending, simulating and estimating.**
The methods are abstracting the complexity of transaction creation: automatically assigns proper nonce value, computes fee based on data input and applies the signature before execution. The required inputs are a **[Wallet](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/wallet/Wallet.java)**  and the following payload with minimum necessary data:

**[TransactionRequest](src/main/java/software/crldev/elrondspringbootstarterreactive/sender/TransactionRequest.java)**
```
 - receiver address
 - value
 - data
 - gas limit (optional)
```

Example usage:
```java
@Autowired ErdTransactionInteractor interactor;

Mono<TransactionHash> sendTransaction(File pemFile) {
    var wallet = WalletCreator.fromPemFile(pemFile);

    var tRequest = TransactionRequest.builder()
                .receiverAddress(Address.fromBech32("erd1gklqdvxxxxxxxxxxxxxxxxxxxxx"))
                .value(Balance.fromEgld(3.5))
                .data(PayloadData.fromString("hello elrond"))
                .build();
    
    return interactor.sendTransaction(wallet, tRequest);
}
```

**[Smart Contract Interactor](src/main/java/software/crldev/elrondspringbootstarterreactive/interactor/smartcontract/ErdSmartContractInteractor.java)**
```
- callFunction
- query
- queryHex
- queryString
- queryInt
```

The Sc Interactor has methods which interact with the smart contracts. 

In order to call a smart contract function, we need to pass an instance of ScFunction:

**[ScFunction](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/smartcontract/ScFunction.java)**
```
 - smart contract address
 - function name
 - array of arguments
 - value
 - gas limit (optional)
```

Example usage:
```java
@Autowired ErdSmartContractInteractor interactor;

Mono<TransactionHash> callFunction(File pemFile) {
    var wallet = WalletCreator.fromPemFile(pemFile);

    var function = ScFunction.builder()
        .smartContractAddress(Address.fromBech32("erd1xxxxxxxxxxxxxxxxxxxx8llllsh6u4jp"))
        .functionName(FunctionName.fromString("addName"))
        .args(FunctionArgs.fromString("elrond"))
        .value(Balance.zero())
        .build();
    
    return interactor.callFunction(wallet, function);
}
```

The ScFunction generates payload based on function name and args (HEX encoded), creates, assigns nonce, gas (if not specified, default is used), signs and executes a transaction.

Also, for querying we can use the following object:

**[ScQuery](src/main/java/software/crldev/elrondspringbootstarterreactive/domain/smartcontract/ScQuery.java)**
```
 - smart contract address
 - function name
 - array of arguments
 - value
 - caller address (optional)
```
<br>

For more details regarding the implementation, please consult the project's **[official Javadoc documentation](https://crldev.software/docs/elrond-spring-boot-starter-reactive/)**.

## Demo

You can find an example of a spring-boot service using this framework **[HERE](https://github.com/crldev-software/elrond-spring-boot-demo)**.

## Next features

In the next releases the following features have been planned:
- ESDT Token Interactor
- Wallet Creator - method to instantiate wallet from password-protected JSON keystore file
- Other enhancements


## Changelog

[All notable features and changes to this project will be documented in CANGELOG.md file](CHANGELOG.md)


## Contributing

Contributions are always welcome!

You can get in touch with me using the links below and figure out together how to make the project better.

Also, if you appreciate my effort and want to help me develop & maintain the Elrond Spring SDK, you can buy me some coffee via Maiar. 

<img src="https://play-lh.googleusercontent.com/3k3Xj7e87QIwDCwoYbiPEAxuaJDSZKvP0M9HFPHGvi28d1OuT9uzSriXsI2FHQZrsNs" alt="drawing" width="25"/><font size="6"> **@carlo**</font>


## 🔗 Links
[![portfolio](https://img.shields.io/badge/crldev.software-red?style=for-the-badge&logo=noi&logoColor=white)](https://crldev.software)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/carlo-cristian-stanciu)
[![twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/carlo_stanciu)



  