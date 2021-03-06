package customskinloader.fabric.mixin;

import customskinloader.fake.FakeSkinBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.NativeImage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThreadDownloadImageData.class)
public abstract class MixinThreadDownloadImageData {
    @Final
    @Shadow
    private Runnable processTask;

    @Redirect(
        method = "Lnet/minecraft/client/renderer/ThreadDownloadImageData;loadTexture(Ljava/io/InputStream;)Lnet/minecraft/client/renderer/texture/NativeImage;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ThreadDownloadImageData;processLegacySkin(Lnet/minecraft/client/renderer/texture/NativeImage;)Lnet/minecraft/client/renderer/texture/NativeImage;"
        )
    )
    private NativeImage redirect_loadTexture(NativeImage image) {
        return FakeSkinBuffer.processLegacySkin(image, this.processTask);
    }
}
