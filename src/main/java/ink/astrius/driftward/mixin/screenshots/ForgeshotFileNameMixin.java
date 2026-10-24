package ink.astrius.driftward.mixin.screenshots;

import com.carmellium.forgeshot.Mine;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.text.DateFormat;

@Mixin(Mine.class)
public class ForgeshotFileNameMixin {
    @Shadow
    @Final
    private static Minecraft CLIENT;

    @Shadow
    @Final
    private static DateFormat DATE_FORMAT;

    @ModifyArg(
        method = "getScreenshotPath",
        at = @At(
            value = "INVOKE",
            target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;",
            ordinal = 1
        )
    )
    private static String filenameWithSuffix(String orig) {
        final var dotIndex = orig.lastIndexOf('.');
        return orig.substring(5, dotIndex) + "_huge" + orig.substring(dotIndex);
    }
}
