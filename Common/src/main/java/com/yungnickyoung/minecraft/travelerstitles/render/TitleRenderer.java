package com.yungnickyoung.minecraft.travelerstitles.render;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

import java.util.LinkedList;
import java.util.function.Predicate;

public class TitleRenderer<T> {
    public final LinkedList<T> recentEntries = new LinkedList<>();
    public Component displayedTitle = null;
    public Component displayedSubTitle = null;
    /** -1 hides the vignette, otherwise index into the 8-column thumbnail atlas. */
    public int displayedThumbnail = -1;
    public int titleTimer = 0;
    public int cooldownTimer = 0;

    // User-customizable text effects
    public int maxRecentListSize;
    public boolean enabled;
    public int titleFadeInTicks;
    public int titleDisplayTime;
    public int titleFadeOutTicks;
    public int titleTextcolor;
    public String titleDefaultTextColor;
    public boolean showTextShadow;
    public float titleTextSize;
    public int titleXOffset;
    public int titleYOffset;
    public boolean isTextCentered;

    public TitleRenderer(
        int maxRecentListSize,
        boolean enabled,
        int fadeInTicks,
        int displayTicks,
        int fadeOutTicks,
        String textColor,
        boolean showTextShadow,
        double textSize,
        int xOffset,
        int yOffset,
        boolean centerText
    ) {
        this.maxRecentListSize = maxRecentListSize;
        this.enabled = enabled;
        this.titleFadeInTicks = fadeInTicks;
        this.titleDisplayTime = displayTicks;
        this.titleFadeOutTicks = fadeOutTicks;
        this.setColor(textColor);
        this.titleDefaultTextColor = textColor;
        this.showTextShadow = showTextShadow;
        this.titleTextSize = (float)textSize;
        this.titleXOffset = xOffset;
        this.titleYOffset = yOffset;
        this.isTextCentered = centerText;
    }

    public void renderText(float partialTicks, GuiGraphicsExtractor guiGraphics) {
        if (displayedTitle != null && titleTimer > 0) {
            float age = (float) titleTimer - partialTicks;
            int opacity = 255;
            if (titleTimer > titleFadeOutTicks + titleDisplayTime) {
                float r = (float) (titleFadeInTicks + titleDisplayTime + titleFadeOutTicks) - age;
                opacity = (int) (r * 255.0F / (float) titleFadeInTicks);
            }

            if (titleTimer <= titleFadeOutTicks) {
                opacity = (int) (age * 255.0F / (float) titleFadeOutTicks);
            }

            opacity = Mth.clamp(opacity, 0, 255);
            if (opacity > 8) {
                // Set up render system
                guiGraphics.pose().pushMatrix();
                if (this.isTextCentered) {
                    guiGraphics.pose().translate(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2f,
                                                 (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2f));
                }

                // Render title
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().scale(titleTextSize, titleTextSize);
                int alpha = opacity << 24 & 0xFF000000;
                Font fontRenderer = Minecraft.getInstance().font;
                int titleWidth = fontRenderer.width(displayedTitle);
                // 12px (pre-scale) framed miniature + 3px between miniature and label.
                int thumbnailSpace = displayedThumbnail >= 0 ? 15 : 0;
                int combinedWidth = titleWidth + thumbnailSpace;

                // Determine centered bounds using both thumbnail and text.
                int xOffset = this.isTextCentered
                    ? this.titleXOffset - combinedWidth / 2
                    : this.titleXOffset;

                if (displayedThumbnail >= 0) {
                    // Draw the FULL-COLOR texture separately; the biome text tint must
                    // not tint the picture. Apply only the title fade alpha.
                    int u = displayedThumbnail % BiomeThumbnails.ATLAS_COLUMNS * BiomeThumbnails.TILE_SIZE;
                    int v = displayedThumbnail / BiomeThumbnails.ATLAS_COLUMNS * BiomeThumbnails.TILE_SIZE;
                    guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED, BiomeThumbnails.ATLAS,
                        xOffset, titleYOffset - 2, u, v,
                        12, 12, BiomeThumbnails.TILE_SIZE, BiomeThumbnails.TILE_SIZE,
                        BiomeThumbnails.ATLAS_COLUMNS * BiomeThumbnails.TILE_SIZE,
                        BiomeThumbnails.ATLAS_ROWS * BiomeThumbnails.TILE_SIZE,
                        alpha | 0x00FFFFFF
                    );
                }

                // Biome names and dimension titles keep their original translations and colors.
                guiGraphics.text(fontRenderer, displayedTitle, xOffset + thumbnailSpace,
                    titleYOffset, titleTextcolor | alpha, showTextShadow);
                guiGraphics.pose().popMatrix();

                // Subtitle render. Currently unused
                if (displayedSubTitle != null) {
                    guiGraphics.pose().pushMatrix();
                    guiGraphics.pose().scale(1.3F, 1.3F);
                    int subtitleWidth = fontRenderer.width(displayedSubTitle);
                    drawBackdrop(guiGraphics, 5, subtitleWidth, 0xFFFFFF | alpha);
                    guiGraphics.text(fontRenderer, displayedSubTitle, -subtitleWidth / 2, -35, 0xFFFFFF | alpha, showTextShadow);
                    guiGraphics.pose().popMatrix();
                }

                guiGraphics.pose().popMatrix();
            }
        }
    }

    public void tick() {
        if (titleTimer > 0) {
            --titleTimer;
            if (titleTimer <= 0) {
                clearTimer();
            }
        }
        if (cooldownTimer > 0) {
            --cooldownTimer;
        }
    }

    public void displayTitle(Component titleText, Component subtitleText) {
        displayTitle(titleText, subtitleText, -1);
    }

    public void displayTitle(Component titleText, Component subtitleText, int thumbnailIndex) {
        displayedTitle = titleText;
        displayedSubTitle = subtitleText;
        displayedThumbnail = thumbnailIndex;
        titleTimer = titleFadeInTicks + titleDisplayTime + titleFadeOutTicks;
    }

    public void clearTimer() {
        titleTimer = 0;
    }

    public void setColor(String textColor) {
        try {
            this.titleTextcolor = (int) Long.parseLong(textColor, 16);
        } catch (Exception e) {
            TravelersTitlesCommon.LOGGER.error("Text color {} is not a valid RGB color. Defaulting to white...", textColor);
            TravelersTitlesCommon.LOGGER.error(e.toString());
            this.titleTextcolor = 0xFFFFFF;
        }
    }

    public void addRecentEntry(T entry) {
        if (this.recentEntries.size() >= this.maxRecentListSize && this.recentEntries.size() > 0) {
            this.recentEntries.removeFirst();
        }
        if (this.maxRecentListSize > 0) {
            recentEntries.addLast(entry);
        }
    }

    public boolean matchesAnyRecentEntry(Predicate<T> entryMatchPredicate) {
        return this.recentEntries.stream().anyMatch(entryMatchPredicate);
    }

    protected void drawBackdrop(GuiGraphicsExtractor guiGraphics, int yOffset, int width, int color) {
        int textBackgroundColor = Minecraft.getInstance().options.getBackgroundColor(0.0F);
        if (textBackgroundColor != 0) {
            int xOffset = -width / 2;
            guiGraphics.fill(xOffset - 2, yOffset - 2, xOffset + width + 2, yOffset + 9 + 2, ARGB.multiply(textBackgroundColor, color));
        }
    }
}
