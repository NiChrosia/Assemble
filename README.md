# Assemble

A dynamic recipe library to replace vanilla's.

# Usage

```gradle
// build.gradle(.kts)
repositories {
    // [...]

    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }

    // [...]
}

dependencies {
    // [...]

    modApi("maven.modrinth:assemble:${assemble_version}") // can be found from looking at the versions on the Modrinth page

    // [...]
}
```
