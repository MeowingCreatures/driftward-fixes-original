package ink.astrius.driftward.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.util.stack.EntityEmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.stream.Stream;

import static ink.astrius.driftward.DriftwardEmi.LARGE_SLOT_SIZE;
import static ink.astrius.driftward.DriftwardEmi.SLOT_SIZE;

public class EmiEntityInteractionRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiIngredient left, output;
    private final WorldIngredient right;
    private final List<EmiIngredient> inputs;

    public EmiEntityInteractionRecipe(
        ResourceLocation id,
        Entity entity,
        WorldIngredient right,
        EmiIngredient output
    ) {
        this.id = id;
        // TODO: scale/offset/rotation?
        this.left = EntityEmiStack.of(entity);
        this.right = right;
        this.output = output;

        this.inputs = Stream.of(new WorldIngredient(left, false), right)
            .filter(i -> !i.catalyst).map(i -> i.inner).toList();
        if (right.catalyst) {
            for (final var stack : right.inner.getEmiStacks()) {
                if (stack.getRemainder().isEmpty()) {
                    stack.setRemainder(stack.copy());
                }
            }
        }
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return VanillaEmiRecipeCategories.WORLD_INTERACTION;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        if (right.catalyst) {
            return List.of(right.inner);
        } else {
            return List.of();
        }
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output.getEmiStacks();
    }

    @Override
    public int getDisplayWidth() {
        return 125;
    }

    @Override
    public int getDisplayHeight() {
        return 26;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int smPadding = (LARGE_SLOT_SIZE - SLOT_SIZE) / 2;
        int lr = LARGE_SLOT_SIZE;
        int or = getDisplayWidth() - smPadding;
        int ol = or - SLOT_SIZE;
        int rl = (lr + ol) / 2 - SLOT_SIZE / 2;
        int rr = rl + SLOT_SIZE;

        widgets.addSlot(left, 0, 0).large(true);
        widgets.addSlot(right.inner, rl, smPadding).catalyst(right.catalyst);
        widgets.addSlot(output, ol, smPadding).recipeContext(this);

        widgets.addTexture(EmiTexture.PLUS, (lr + rl) / 2 - EmiTexture.PLUS.width / 2, smPadding + SLOT_SIZE / 2 - EmiTexture.PLUS.height / 2 - 1);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, (rr + ol) / 2 - EmiTexture.EMPTY_ARROW.width / 2, smPadding + SLOT_SIZE / 2 - EmiTexture.EMPTY_ARROW.height / 2 - 1);
    }


    public static record WorldIngredient(EmiIngredient inner, boolean catalyst) {
    }
}
