
<p align="center">

[![DepShield Badge](https://depshield.sonatype.org/badges/nabsha/mule4-property-placeholder/depshield.svg)](https://depshield.github.io)
[![Build Status](https://travis-ci.com/nabsha/mule4-property-placeholder.svg?branch=master)](https://travis-ci.com/nabsha/mule4-property-placeholder)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</p>


# Project Title

A simple mule 4 module that behaves like `context:property-placeholder` available in mule 3

## Description

This plugin targets to implement the same functionality provided `context:property-placeholder` module in mule 3.

## Getting Started

### Dependencies

Add following dependency to your mule 4 application pom.xml

```
<dependency>
    <groupId>com.github.nabsha.mule4.property.provider</groupId>
    <artifactId>mule4-property-placeholder-module</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

### Usage

```
<mule4-property-placeholder:config name="config" location="config.properties,secret.properties,notfound.properties" ignore-resource-not-found="true" ignore-unresolvable="false" file-encoding="UTF-8" />
```

#### Configuration

| Option Name               | Type    | Required | Default | Description                                                                                                                                                                                                                                                                                                                               |
|---------------------------|---------|----------|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| location                  | String  | Yes      |         | The location of the properties file to resolve placeholders against, as a Spring resource location: a URL, a "classpath:" pseudo URL, or a relative file path. Multiple locations may be specified, separated by commas.                                                                                                                  |
| ignore-resource-not-found | boolean | No       | false   | Specifies if failure to find the property resource location should be ignored. Default is "false", meaning that if there is no file in the location specified an exception will be raised at runtime.                                                                                                                                     |
| ignore-unresolvable       | boolean | No       | false   | Specifies if failure to find the property value to replace a key should be ignored. Default is "false", meaning that this placeholder configurer will raise an exception if it cannot resolve a key. Set to "true" to allow the configurer to pass on the key to any others in the context that have not yet visited the key in question. |
| file-encoding             | String  | No       |         | Specifies the encoding to use for parsing properties files. Default is none, using the java.util.Properties default encoding. Only applies to classic properties files, not to XML files.                                                                                                                                                 |



## Help
Please refer to the test cases for more information.

Feel free to raise issues or concerns.


## Version History

* 0.0.1
    * Initial Release

## License

This project is licensed under the MIT License - see the LICENSE file for details

