# REST API Codegenerator Maven Plugin

Are you sick of always having to implement and maintain the client-side services for reaching your Rest API.
With the REST API Codegenerator Maven Plugin you are able to automatically generate the client-side code to reach your API.

For now the plugin generates java code as a proof of concept - see [example of usage](https://github.com/jansoren/akka-persistence-java-example), but feel free to create pull requests and extend the plugin to generate for example Ajax, AngularJS or ReactJS code.

## Example of generating Java code

- `generatedCodeFolder`: This is the folder where you want your generated code
- `generatedCodePackage`: This is the package of your generated class files
- `rootHost`: This is the host of your running application

Add the plugin to your `pom.xml` file and run `mvn clean install`. The plugin will scan your source code and generate code out of your resource classes.
Enjoy :-)

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
        <version>2.23.1</version>
    </dependency>
```

If you are using `MediaType.APPLICATION_JSON_TYPE` you need this dependency
```maven
    <dependency>
        <groupId>org.glassfish.jersey.media</groupId>
        <artifactId>jersey-media-json-jackson</artifactId>
        <version>2.13</version>
    </dependency>
```

## Example of generating ReactJS code

Not implemented yet

## How to get started developing

1. Clone this repo `git clone git@github.com:jansoren/restapi-codegen-maven-plugin.git`
1. Run `mvn clean install` in the 'restapi-codegen-maven-plugin'-folder
1. The unit tests have now generated some example code at [/src/test/java/com/example/helloworld/MyService.java](https://github.com/jansoren/restapi-codegen-maven-plugin/blob/master/src/test/java/com/example/helloworld/MyService.java)

## Why this plugin

Over the years I have created a few REST API's and always end up using alot of time implementing the client side code to reach the API's.
After working on an API with a quality test project that continously tested the API we used alot of time also creating java code to reach the API......