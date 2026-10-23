package ink.astrius.driftward.mixin.modonomicon;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.gui.book.index.BookCategoryIndexOnNodeScreen;
import com.klikli_dev.modonomicon.client.gui.book.search.BookSearchScreen;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import ink.astrius.driftward.modonomicon.IScaleFactor;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition("modonomicon"))
@Mixin({BookCategoryIndexOnNodeScreen.class, BookEntryScreen.class, BookSearchScreen.class})
public class SimpleClickMixin {
    @WrapMethod(method = "mouseClicked")
    public boolean scaleClicks(double pMouseX, double pMouseY, int pButton, Operation<Boolean> original) {
        final var scaleFactor = ((IScaleFactor) this).driftward$getScaleFactor();
        return original.call(
            pMouseX / scaleFactor,
            pMouseY / scaleFactor,
            pButton
        );
    }
}
