package assemble.impl.assembly.adapter

// cursed class yes but it avoids code duplication + it can't be done by interface because adapters have to have their
// get and set methods unique to avoid conflicts
class Adapters {
    companion object {
        // addition
        fun add(get: () -> Int, set: (Int) -> Unit, addition: Int) {
            val current = get()

            set(current + addition)
        }

        fun add(get: () -> Long, set: (Long) -> Unit, addition: Long) {
            val current = get()

            set(current + addition)
        }

        fun add(get: (Int) -> Int, set: (Int, Int) -> Unit, slot: Int, addition: Int) {
            val current = get(slot)

            set(slot, current + addition)
        }

        fun add(get: (Int) -> Long, set: (Int, Long) -> Unit, slot: Int, addition: Long) {
            val current = get(slot)

            set(slot, current + addition)
        }

        // subtraction
        fun subtract(get: () -> Int, set: (Int) -> Unit, reduction: Int) {
            val current = get()

            set(current - reduction)
        }

        fun subtract(get: () -> Long, set: (Long) -> Unit, reduction: Long) {
            val current = get()

            set(current - reduction)
        }

        fun subtract(get: (Int) -> Int, set: (Int, Int) -> Unit, slot: Int, reduction: Int) {
            val current = get(slot)

            set(slot, current - reduction)
        }

        fun subtract(get: (Int) -> Long, set: (Int, Long) -> Unit, slot: Int, reduction: Long) {
            val current = get(slot)

            set(slot, current - reduction)
        }
    }
}