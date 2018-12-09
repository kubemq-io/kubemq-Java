# KubeMQ SDK for Java

The **KubeMQ SDK for Java** enables Java developers to easily work with [KubeMQ](https://kubemq.io/). 

## Getting Started

### Prerequisites

KubeMQ-SDK-Java works with **JDK 8+**.

### Installing
 
The recommended way to use the SDK for Java in your project is to consume it from Maven.

```
<dependency>
  <groupId>io.kubemq</groupId>
  <artifactId>kubemq-java-sdk-bom</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

To build with Gradle, add the dependency below to your build.gradle file.

```
compile group: 'io.kubemq.sdk', name: 'kubemq-java-sdk', version: '1.0.1'
```

## Generating Documentation

Javadoc is used for documentation. You can generate HTML locally with the following:

```
.gradlew javadoc
```

## Running the examples

The [examples](https://github.com/kubemq-io/Java_SDK/tree/v1.0.1/examples) 
are standalone projects that showcase the usage of the SDK.

To run the examples you need to have a running instance of KubeMQ. 

You can use the Gradle tasks to run the examples:

```
.gradlew eventChannel
.gradlew eventSender
.gradlew eventSubscriber
.gradlew commandQueryChannel
.gradlew commandQueryInitiator
.gradlew commandQueryResponder
```

## Building from source

Once you check out the code from GitHub, you can build it using Gradle.

```
.gradlew build
```

## Running the tests

To run the automated tests for this system execute:

```
.gradlew test
```

## Built With

* [Protocol Buffers](https://developers.google.com/protocol-buffers/) - Serializing structured data.
* [Gradle](https://gradle.org/) - Dependency management

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details
