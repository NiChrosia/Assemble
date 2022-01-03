package assemble.common.content

import assemble.common.Assemble
import assemble.common.type.api.registrar.AssemblySerializerRegistrar
import assemble.common.type.impl.assembly.serializer.FluidStackType
import assemble.common.type.impl.assembly.serializer.IngredientStackType
import assemble.common.type.impl.assembly.serializer.ItemStackType

class AAssemblySerializerRegistrar(root: Assemble) : AssemblySerializerRegistrar<Assemble>(root) {
    val ingredientStack by memberOf(root.identify("ingredient_stack")) { IngredientStackType() }
    val itemStack by memberOf(root.identify("item_stack")) { ItemStackType() }

    val fluidStack by memberOf(root.identify("fluid_stack")) { FluidStackType() }
}