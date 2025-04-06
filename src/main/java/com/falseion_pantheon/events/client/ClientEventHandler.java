package com.falseion_pantheon.events.client;

import com.falseion_pantheon.FalseionCredentials;
import com.falseion_pantheon.FalseionRenderTypes;
import com.falseion_pantheon.items.custom.FratricideItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = FalseionCredentials.Id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {

    /**
     * Changes color of tooltip's background
     * @param event
     */
    @SubscribeEvent
    public static void onRenderTooltip(RenderTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof FratricideItem)) return;

        PoseStack poseStack = event.getPoseStack();
        int x = event.getX();
        int y = event.getY();
        List<ClientTooltipComponent> components = event.getComponents();
        Font font = event.getFont();

        // Calculate tooltip size (width, height)
        int width = 0;
        for (ClientTooltipComponent component : components) {
            int compWidth = component.getWidth(font);
            if (compWidth > width) width = compWidth;
        }
        int height = components.stream().mapToInt(c -> c.getHeight()).sum() + 2;

        // Render red glint overlay
        //renderGlintOverlay(poseStack, x, y, width, height);
    }

    private static void renderGlintOverlay(PoseStack poseStack, int x, int y, int width, int height) {
        RenderType glintType = FalseionRenderTypes.getRedGlint();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = buffer.getBuffer(glintType);

        poseStack.pushPose();
        poseStack.translate(0, 0, 500); // Render on top of tooltip
        Matrix4f matrix = poseStack.last().pose();

        consumer.vertex(matrix, x, y + height, 0).uv(0, 1).endVertex();
        consumer.vertex(matrix, x + width, y + height, 0).uv(1, 1).endVertex();
        consumer.vertex(matrix, x + width, y, 0).uv(1, 0).endVertex();
        consumer.vertex(matrix, x, y, 0).uv(0, 0).endVertex();

        poseStack.popPose();
        buffer.endBatch(glintType); // Flush only the glint layer
    }
}
