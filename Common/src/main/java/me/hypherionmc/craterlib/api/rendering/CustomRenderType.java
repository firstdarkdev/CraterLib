package me.hypherionmc.craterlib.api.rendering;

import net.minecraft.client.renderer.RenderType;

/**
 * Helper Interface for defining Block render types
 */
public interface CustomRenderType {

    /**
     * Get the render type of the block
     *
     * @return
     */
    RenderType getCustomRenderType();

}
