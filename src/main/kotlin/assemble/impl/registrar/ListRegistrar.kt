package assemble.impl.registrar

import nucleus.common.api.registrar.Registrar

open class ListRegistrar<K, V> : Registrar<K, MutableList<V>> {
    val content = mutableMapOf<K, MutableList<V>>()

    override fun <T : MutableList<V>> register(key: K, value: T): T {
        content[key] = value

        return value
    }

    override fun get(key: K): MutableList<V> {
        return content[key] ?: throw noElementFound(key)
    }

    fun <T : V> registerEntry(key: K, value: T): T {
        if (content[key] == null) {
            register(key, mutableListOf(value))
        } else {
            this[key].add(value)
        }

        return value
    }
}