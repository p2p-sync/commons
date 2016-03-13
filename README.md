# P2P-Sync Commons
[![Build Status](https://travis-ci.org/p2p-sync/commons.svg?branch=master)](https://travis-ci.org/p2p-sync/commons)
[![Coverage Status](https://coveralls.io/repos/p2p-sync/commons/badge.svg?branch=master&service=github)](https://coveralls.io/github/p2p-sync/commons?branch=master)

# Install using Maven

To add this library using Maven, add the following to your `pom.xml`:

```xml

<repositories>
  <repository>
    <id>persistence-mvn-repo</id>
    <url>https://raw.github.com/p2p-sync/commons/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.rmatil.sync.commons</groupId>
    <artifactId>sync-commons</artifactId>
    <version>0.1-SNAPSHOT</version>
  </dependency>
</dependencies>

```

# Overview

This component provides some common functionalities used in [p2p-sync/sync](https://github.com/p2p-sync/sync).

## Hashing

Multiple methods are provided to hash files, directories or just the content of a byte array.
This module supports four different hash functions: `MD5`, `SHA 1`, `SHA 256` and `SHA 512`.

### Hashing Files or Directories

```java

import org.rmatil.sync.commons.hashing.Hash;
import org.rmatil.sync.commons.hashing.HashingAlgorithm;

import java.io.File;

// ...

  File file = new File("path/to/file.txt");
  String hash1 = Hash.hash(HashingAlgorithm.SHA_1, file);

  File directory = new File("path/to/directory");
  String hash2 = Hash.hash(HashingAlgorithm.SHA_1, directory);

  
// ..

```

### Hashing String Values

```java

import org.rmatil.sync.commons.hashing.Hash;
import org.rmatil.sync.commons.hashing.HashingAlgorithm;

// ...

  String string = "some string to hash";
  String hash = Hash.hash(HashingAlgorithm.SHA_1, string);

  
// ..

```

### Hashing Byte contents

```java

import org.rmatil.sync.commons.hashing.Hash;
import org.rmatil.sync.commons.hashing.HashingAlgorithm;

// ...

  byte[] bytes = new byte[10];
  String hash = Hash.hash(HashingAlgorithm.SHA_1, bytes);

  
// ..

```


## Pair

A simple Pair class is also provided:

```java

import org.rmatil.sync.commons.collection.Pair;

// ...

  Pair<String, Integer> pair = new Pair<>("first", 2);
  pair.getFirst();
  pair.getSecond();
  
// ...

```


## Lists

To check whether a particular list contains an element of a provided class, this module provides the following function:

```java

import org.rmatil.sync.commons.list.Lists;

import java.util.ArrayList;
import java.util.List;

// ...

  List<String> list = new ArrayList<>();
  list.add("some string");

  List<String> entries = Lists.getInstances(list, String.class);

// ...

```


## Naming

Finally, some utility methods used for handling or creating file names are included:


```java

import org.rmatil.sync.commons.path.Naming;

// ...

  boolean isFile = true;
  String clientDeviceId = "Node1";

  // returns relative/path/to/myFile_Node1.txt
  String conflictName = Naming.getConflictFileName("relative/path/to/myFile.txt", isFile, "txt", clientDeviceId);

// ...

  // returns relative/path/to
  String pathWithoutFileName = Naming.getPathWithoutFileName("myFile.txt", "relative/path/to/myFile.txt");

// ...

  // returns relative/path/to
  String parentPath = Naming.getParentPath("relative/path/to/myFile.txt");
  
// ...
```


