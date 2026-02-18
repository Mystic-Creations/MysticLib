# MysticLib
//README paste-in

## MysticLib in mod development

```gradle
repositories {
    maven { 
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
}
```

### Groovy DSL
```groovy
modImplementation "maven.modrinth:mysticlib:LIB_VERSION"
```

### Kotlin DSL
```kotlin
modImplementation("maven.modrinth:mysticlib:LIB_VERSION")
```

Replace `LIB_VERSION` with the desired release version.



Note from Millie: this documentation is very not finished