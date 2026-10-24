package ink.astrius.driftward.mixin.screenshots;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.lgatodu47.screenshot_viewer.ScreenshotViewerUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;
import java.util.List;

@Mixin(ScreenshotViewerUtils.class)
public class ScreenshotViewerUtilsMixin {
    @Overwrite
    public static File getVanillaScreenshotsFolder() {
        return new File("screenshots");
    }

    @Overwrite
    public static File getDefaultThumbnailFolder() {
        return new File("screenshots/thumbnails");
    }

    @WrapMethod(method = "getScreenshotFiles")
    private static List<File> absoluteScreenshotsPath(File screenshotsFolder, Operation<List<File>> original) {
        return original.call(screenshotsFolder.getAbsoluteFile());
    }
}
