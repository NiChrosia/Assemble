package assemble.impl.assembly.adapter.progress

interface ProgressAdapter {
    // the increment by which progress in increased; the speed of the assembly
    fun getIncrement(): Int

    fun getProgress(): Int
    fun getMaxProgress(): Int

    fun setProgress(progress: Int)
}