package assemble.common.type.api.assembly

import assemble.common.type.api.assembly.type.AssemblyType
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import java.io.InputStreamReader

open class AssemblyManager(val id: Identifier) : SimpleSynchronousResourceReloadListener {
    open val types = mutableMapOf<Identifier, AssemblyType<*, out Assembly<*>>>()
    open val assemblies = mutableMapOf<AssemblyType<*, out Assembly<*>>, MutableList<Assembly<*>>>()

    override fun getFabricId(): Identifier {
        return id
    }

    override fun reload(manager: ResourceManager) {
        val ids = manager.findResources(path) { it.endsWith(".json") || it.endsWith(".json5") }

        for (id in ids) {
            val resource = manager.getResource(id)
            val stream = resource.inputStream
            val json = gson.fromJson(InputStreamReader(stream), JsonObject::class.java)

            val typeString = json["type"].asString
            val typeId = Identifier(typeString)
            val type = types[typeId]

            if (type != null) {
                val assembly = type.deserialize(id, json)

                assemblies.compute(type) { _, assemblies ->
                    assemblies?.also { it.add(assembly) } ?: mutableListOf(assembly)
                }
            }
        }
    }

    companion object {
        val gson = GsonBuilder().setPrettyPrinting().create()

        const val path = "assemblies"
    }
}