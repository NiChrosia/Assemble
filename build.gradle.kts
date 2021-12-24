@file:Suppress("unused")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// utilities

fun extension(name: String): String {
    return project.ext[name] as String
}

fun DependencyHandler.bundledMod(notation: Any, impl: (Any) -> Dependency? = this::modImplementation): Dependency? {
    return include(notation)?.let(impl)
}

fun DependencyHandler.optionallyBundled(notation: Any, bundled: Boolean, impl: (Any) -> Dependency? = this::modImplementation): Dependency? {
    return if (bundled) bundledMod(notation, impl) else modImplementation(notation)
}

// libraries

// repositories

fun RepositoryHandler.jitpack() {
    maven {
        name = "Jitpack"
        setUrl("https://jitpack.io")
    }
}

fun RepositoryHandler.modmuss() {
    maven {
        name = "Modmuss50"
        setUrl("https://maven.modmuss50.me/")
        content {
            includeGroup("RebornCore")
            includeGroup("TechReborn")
            includeGroup("teamreborn")
        }
    }
}

fun RepositoryHandler.buildcraft() {
    maven {
        name = "BuildCraft"
        setUrl("https://mod-buildcraft.com/maven")
    }
}

fun RepositoryHandler.patchouli() {
    maven {
        name = "Patchouli"
        setUrl("https://maven.blamejared.com")
        content {
            includeGroup("vazkii.patchouli")
        }
    }
}

fun RepositoryHandler.devan() {
    maven {
        name = "Devan"
        setUrl("https://storage.googleapis.com/devan-maven/")
    }
}

fun RepositoryHandler.cottonMc() {
    maven {
        name = "CottonMC"
        setUrl("https://server.bbkr.space/artifactory/libs-release")
    }
}

fun RepositoryHandler.modrinth() {
    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

// dependencies

fun DependencyHandlerScope.mcVersion(version: String = extension("minecraft_version")): String {
    return "com.mojang:minecraft:$version"
}

fun DependencyHandlerScope.yarn(version: String = extension("yarn_mappings")): String {
    return "net.fabricmc:yarn:$version:v2"
}

fun DependencyHandlerScope.loader(version: String = extension("loader_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("net.fabricmc:fabric-loader:$version", bundled)
}

fun DependencyHandlerScope.fabricApi(version: String = extension("fabric_api_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("net.fabricmc.fabric-api:fabric-api:$version", bundled)
}

fun DependencyHandler.fabricKotlin(version: String = extension("fabric_kotlin_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("net.fabricmc:fabric-language-kotlin:$version", bundled)
}

fun DependencyHandler.arrp(version: String = extension("arrp_version"), bundled: Boolean = true): Dependency? {
    return optionallyBundled("net.devtech:arrp:$version", bundled)
}

fun DependencyHandler.patchouli(version: String = extension("patchouli_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("vazkii.patchouli:Patchouli:$version", bundled)
}

fun DependencyHandler.libgui(version: String = extension("libgui_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("io.github.cottonmc:LibGui:$version", bundled)
}

fun DependencyHandler.nucleus(version: String = extension("nucleus_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("maven.modrinth:nucleus:$version", bundled, this::modApi)
}

fun DependencyHandler.rebornEnergy(version: String = extension("tech_reborn_energy_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("teamreborn:energy:$version", bundled, this::modApi)
}

fun DependencyHandler.dataTagLib(version: String = extension("data_tag_lib_version"), bundled: Boolean = false): Dependency? {
    return optionallyBundled("com.github.NathanPB:KtDataTagLib:$version", bundled, this::modApi)
}

fun DependencyHandler.vorbis(version: String = extension("vorbis_version")): Dependency? {
    return implementation("com.googlecode.soundlibs:vorbisspi:$version")
}

// END libraries

plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

base {
    archivesName.set(extension("archives_base_name"))
    version = extension("mod_version")
    group = extension("maven_group")
}

repositories {
    jitpack()
    modmuss()
    modrinth()
    devan()
}

dependencies {
    minecraft(mcVersion())
    mappings(yarn())

    loader()
    fabricApi()
    fabricKotlin()

    arrp()
    nucleus()
}

loom {
    accessWidenerPath.set(file("src/main/resources/assemble.accesswidener"))

    runs {
        create("serverTest") {
            server()
            source(sourceSets.main.get())
            vmArg("-Dfabric-api.gametest=1")
        }
    }
}

java {
    withSourcesJar()
}

tasks {
    "compileJava"(JavaCompile::class) {
        options.encoding = "UTF-8"
        options.release.set(16)
    }

    "compileKotlin"(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_16.toString()
        }
    }

    "processResources"(ProcessResources::class) {
        // change ${version} in fabric.mod.json to match the one in gradle.properties
        val version = extension("mod_version")
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand("version" to version)
        }
    }

    "jar"(Jar::class) {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}"}
        }
    }
}