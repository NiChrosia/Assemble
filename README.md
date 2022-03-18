# Assemble

A dynamic recipe library to replace vanilla's.

# Installation

### Repository

```kotlin
repositories {
    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven/")
        
        content {
            includeGroup("maven.modrinth")
        }
    }
}
```

### Dependency

Versions can be found on [the modrinth page](https://modrinth.com/mod/assemble/versions),

```kotlin
dependencies {
    modApi("maven.modrinth:assemble:${assemble_version}")
}
```