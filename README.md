# algebraic-graphs-kotlin

[![](https://jitpack.io/v/alexandrepiveteau/algebraic-graphs-kotlin.svg)](https://jitpack.io/#alexandrepiveteau/algebraic-graphs-kotlin)

This repository contains some utilies for functional programming in the Kotlin programming language.
The OSS license can be found in the [LICENSE.md](LICENSE.md) file of the repository.

## Installation
This library is available on [JitPack.io](https://jitpack.io/#alexandrepiveteau/algebraic-graphs-kotlin). Make
sure to add the following Maven repository in your root **build.gradle** file :

```
allprojects {
    repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

You can now add the library modules in your application **build.gradle** file :

```
dependencies {
    implementation "com.github.alexandrepiveteau.algebraic-graphs-kotlin:algebraic-graphs:0.3.0"
}
```

## Usage
The library contains the following modules :

- **algebraic-graphs** - Offers some utilities for working with algebraic graphs.

## Credits

The library is based on the paper [Algebraic Graphs with Class](https://github.com/snowleopard/alga-paper).