package ink.astrius.driftward.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import java.util.ArrayList;
import java.util.List;
import net.mehvahdjukaar.jeed.common.EffectInfo;
import net.mehvahdjukaar.jeed.Jeed;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

import static net.mehvahdjukaar.jeed.common.Constants.ID_COMPARATOR;

@Mixin(EffectInfo.class)
public class JeedSpectrumIntegrationMixin {
    @Redirect(
        method = "computeItemProviders",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/crafting/RecipeManager;getAllRecipesFor(Lnet/minecraft/world/item/crafting/RecipeType;)Ljava/util/List;",
            ordinal = 1
        )
    )
    private static List disableSimplePotions(RecipeManager manager, RecipeType type) {
        return new ArrayList<>();
    }

    @ModifyReturnValue(method = "computeItemProviders", at = @At("RETURN"))
    private static List<ItemStack> addSpectrumPotions(
        List<ItemStack> original,
        @Local(argsOnly = true) MobEffect effect,
        @Local Level level
    ) {
        var brewingRecipes = level.getRecipeManager().getAllRecipesFor(SpectrumRecipeTypes.POTION_WORKSHOP_BREWING);
        for (var recipeHolder : brewingRecipes) {
            PotionWorkshopBrewingRecipe recipe = recipeHolder.value();
            if (recipe.getStatusEffect() == effect) {
                original.add(recipe.getResultItem(level.registryAccess()));
            }
        }

        if (Jeed.sortIngredients()) {
            original.sort((o1, o2) -> ID_COMPARATOR.compare(
                o1.getItemHolder().unwrapKey().get().location(),
                o2.getItemHolder().unwrapKey().get().location()
            ));
        }

        return original;
    }
}
