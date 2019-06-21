## Introduction

This mule 4 plugin is a drop-in replacement for context:property-placeholder used in mule 3


## Usage

Add following dependency to your mule 4 application pom.xml

```
		<dependency>
			<groupId>com.github.nabsha.mule4.property.provider</groupId>
            <artifactId>mule4-property-placeholder-module</artifactId>
            <version>${latest}</version>
            <classifier>mule-plugin</classifier>
		</dependency>

```

and then use it in mule xml as

```
    <mule4-property-placeholder:config name="config" location="config.properties,secret.properties,notfound.properties" ignore-resource-not-found="true" ignore-unresolvable="false" file-encoding="UTF-8" />
```

## More

Please refer to the test cases for more information.

Feel free to raise issues.