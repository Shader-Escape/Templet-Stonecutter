package com.example.exittemplate.mixin;

import com.example.exittemplate.ExampleMod;
import ml.mypals.exit.api.Exit;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Where the example hooks Exit: at the end of level rendering, just before Exit (priority 2000, injected after this
 * mixin) merges its target. To protect an existing renderer of another mod instead, wrap its render call the same
 * way, e.g. {@code @WrapOperation} around the call and {@code Exit.portal(() -> original.call(...))}.
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	//? if >=26.2 {
	@Inject(method = "render", at = @At("RETURN"))
	//?} else {
	/*@Inject(method = "renderLevel", at = @At("RETURN"))
	*///?}
	private void exit_template$render(CallbackInfo ci) {
		Exit.portal(ExampleMod::render);
	}
}
