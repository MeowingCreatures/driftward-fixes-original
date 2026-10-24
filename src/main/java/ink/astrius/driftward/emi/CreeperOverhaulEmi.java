package ink.astrius.driftward.emi;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;

import java.util.List;

public class CreeperOverhaulEmi {
    public static void registerEmi(EmiRegistry registry) {
        final var shears = EmiIngredient.of(Tags.Items.TOOLS_SHEAR);
        shears.getEmiStacks().forEach(stack -> {
            final var is = stack.getItemStack().copy();
            is.setDamageValue(1);
            stack.setRemainder(EmiStack.of(is));
        });
        final var level = Minecraft.getInstance().level;
        for (final var typeSupplier : List.of(ModEntities.DESERT_CREEPER, ModEntities.BADLANDS_CREEPER)) {
            final var type = typeSupplier.get();
            final var name = BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath();
            registry.addRecipe(
                new EmiEntityInteractionRecipe(
                    ResourceLocation.fromNamespaceAndPath(
                        "driftward", "/world/entity_interaction/" + name
                    ),
                    type.create(level),
                    new EmiEntityInteractionRecipe.WorldIngredient(shears, true),
                    EmiStack.of(ModItems.TINY_CACTUS.get())
                )
            );
        }
    }
}
