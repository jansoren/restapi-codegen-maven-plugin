# REST API Codegenerator Maven Plugin

Are you sick of always having to implement and maintain the client-side services for reaching your Rest API.
With the REST API Codegenerator Maven Plugin you are able to automatically generate the client-side code to reach your API.

For now the plugin scans your code for [Jersey](https://jersey.java.net/) resources and generates java code - see example of usage in the [akka-persistence-java-example](https://github.com/jansoren/akka-persistence-java-example)-project. In my next project I will extend the plugin to generate ReactJS code aswell.

If you find the plugin useful please give it a star. Also feel free to create pull requests and extend the plugin to generate for example Ajax, AngularJS or ReactJS code.

## How to use the plugin for generating Java code

1. Add the plugin to your `pom.xml` file, see example below.
1. Run `mvn clean install` in your project folder
1. The plugin will scan your source code and generate code out of your resource classes.

### Plugin example

- `generatedCodeFolder`: This is the folder where you want your generated code
- `generatedCodePackage`: This is the package of your generated class files
- `rootHost`: This is the host of your running application

```maven
<plugin>
    <groupId>no.jansoren</groupId>
    <artifactId>restapi-codegen-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <configuration>
        <generatedCodeFolder>../server-qtest/src/main/java</generatedCodeFolder>
        <generatedCodePackage>com.example.qtest.services</generatedCodePackage>
        <rootHost>http://localhost:8080</rootHost>
    </configuration>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>codegen</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Dependencies required
```maven
<dependency>
    <groupId>org.glassfish.jersey.core</groupId>
    <artifactId>jersey-client</artifactId>
    <version>2.23.2</version>
</dependency>
```

If you are using `MediaType.APPLICATION_JSON_TYPE` you also need this dependency
```maven
<dependency>
    <groupId>org.glassfish.jersey.media</groupId>
    <artifactId>jersey-media-json-jackson</artifactId>
    <version>2.13.2</version>
</dependency>
```

## Example of generating ReactJS code

Not implemented yet

## How to get started developing

1. Clone this repo `git clone git@github.com:jansoren/restapi-codegen-maven-plugin.git`
1. Run `mvn clean install` in the `restapi-codegen-maven-plugin`-folder
1. The unit tests have now generated some example code at [/src/test/java/com/example/helloworld/MyService.java](https://github.com/jansoren/restapi-codegen-maven-plugin/blob/master/src/test/java/com/example/helloworld/MyService.java)

## Why this plugin

Over the years I have created a few REST API's, and I always end up using alot of time implementing the client-side code for reaching the API's.
In one of the projects we had a Quality-test application implemented in java that ran tests continuously, something that required updating the client-side code rapidly.
With this challenge we ended up creating alot of the same boilerplate code for each new service to reach the API, and I felt that this code should and could be automaticly generated.

In the pursuit of finding such a product I ended up creating a proof of concept of this myself, at least to solve my current need.

I hope this can be useful for others as well.
