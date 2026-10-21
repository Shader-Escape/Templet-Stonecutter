package com.example.exittemplate;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ClientModInitializer {
	public static final String MOD_ID = "exit_template";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Exit template loaded");
	}

	/**
	 * Your world rendering. {@code LevelRendererMixin} calls it inside {@code Exit.portal}, so with a shader pack on,
	 * what it draws into the main render target goes to Exit's target and is merged after the shader pack.
	 * Put your own RenderPass code (or the call into the renderer of the mod you add Iris support to) here.
	 */
	public static void render() {
	}
}
