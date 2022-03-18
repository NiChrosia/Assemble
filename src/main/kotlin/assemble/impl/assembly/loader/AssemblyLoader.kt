package assemble.impl.assembly.loader

import assemble.api.assembly.Assembly
import assemble.impl.registrar.ListRegistrar
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import nucleus.common.impl.registrar.SimpleReversibleRegistrar
import java.io.InputStreamReader

class AssemblyLoader : SimpleSynchronousResourceReloadListener {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val key = Identifier("assemble", "loader")

    internal val types = SimpleReversibleRegistrar<Identifier, Assembly.Type<*, out Assembly<*>>>()
    internal val assemblies = ListRegistrar<Assembly.Type<*, out Assembly<*>>, Assembly<*>>()

    override fun getFabricId(): Identifier {
        return key
    }

    override fun reload(manager: ResourceManager) {
        val keys = manager.findResources("assemblies") { it.endsWith(".json") }

        for (key in keys) {
            val resource = manager.getResource(key)
            val stream = resource.inputStream
            val json = gson.fromJson(InputStreamReader(stream), JsonObject::class.java)

            val typeKey = Identifier(json["type"].asString)
            val type = types[typeKey]
            val assembly = type.read(json)

            assemblies.registerEntry(type, assembly)
        }
    }

    fun <I, A : Assembly<I>> register(key: Identifier, value: Assembly.Type<I, A>): Assembly.Type<I, A> {
        assemblies.register(value, mutableListOf())

        return types.register(key, value)
    }

    fun <I, A : Assembly<I>> register(type: Assembly.Type<I, A>, assembly: A): A {
        return assemblies.registerEntry(type, assembly)
    }
}