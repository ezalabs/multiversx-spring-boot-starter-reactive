# Change Log

## [1.0.0] - 04-Oct-2021

First version, containing:
- API request & response models
- Reactive ErdProxyClient
- Custom JSON Mapper for API responses
- Configuration (static & dynamic)
- Domain & value objects (account, wallet, transaction, signature etc)
- Specific exceptions runtime exceptions
- Utilities (gas, hex, mnemonics, pem)
- Interactors (interfaces & implementations) for account, block, network, transaction
- Javadoc
- Lombok
- unit tests with Jacoco code coverage
- gradle build tool

## [1.0.1] - 11-Oct-2021

Fixed spring.factories for ErdSimpleTransactionSender.

## [1.0.2] - 15-Nov-2021

- Changed Sendable as part of Transaction class.
- Refactoring in ErdSimpleTransactionSender.
- Updated dependencies.
- Small tweaks.

## [1.0.3] - 06-Jan-2022

- Added the SmartContractInteractor interface & implementation
- Added SC API & domain objects
- Removed ErdSimpleTransactionSender and moved methods as overloaded to TransactionInteractor
- Refactored gateway selection properties mechanism
- Upgraded Log4J version to v2.17.1 to prevent vulnerability
- Minor bug fixes

## [1.0.4] - 07-Jan-2022

- Updated Spring Boot version

## [1.0.5] - 11-Jan-2022

- Removed WalletCreator
- Added creation from PEM & mnemonics to Wallet main class
- Added support for PEM creation through reactive FilePart
