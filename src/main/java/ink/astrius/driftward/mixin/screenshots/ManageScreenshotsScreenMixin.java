package ink.astrius.driftward.mixin.screenshots;

import io.github.lgatodu47.screenshot_viewer.screens.manage_screenshots.ManageScreenshotsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.File;

@Mixin(ManageScreenshotsScreen.class)
public class ManageScreenshotsScreenMixin {
    @ModifyArg(
        method = "lambda$init$3",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/Util$OS;openFile(Ljava/io/File;)V"
        )
    )
    private static File absoluteScreenshotFolderPath(File file) {
        return file.getAbsoluteFile();
    }
}
