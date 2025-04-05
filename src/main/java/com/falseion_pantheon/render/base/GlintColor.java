package com.falseion_pantheon.render.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author WireSegal
 * Created at 10:00 AM on 12/23/23.
 */
public class GlintColor implements StringRepresentable {

    private static final HashMap<String, GlintColor> BY_NAME = new HashMap<>();
    private static final HashMap<DyeColor, GlintColor> BY_COLOR = new HashMap<>();

    public static final GlintColor WHITE = new GlintColor(DyeColor.WHITE);
    public static final GlintColor ORANGE = new GlintColor(DyeColor.ORANGE);
    public static final GlintColor MAGENTA = new GlintColor(DyeColor.MAGENTA);
    public static final GlintColor LIGHT_BLUE = new GlintColor(DyeColor.LIGHT_BLUE);
    public static final GlintColor YELLOW = new GlintColor(DyeColor.YELLOW);
    public static final GlintColor LIME = new GlintColor(DyeColor.LIME);
    public static final GlintColor PINK = new GlintColor(DyeColor.PINK);
    public static final GlintColor GRAY = new GlintColor(DyeColor.GRAY);
    public static final GlintColor LIGHT_GRAY = new GlintColor(DyeColor.LIGHT_GRAY);
    public static final GlintColor CYAN = new GlintColor(DyeColor.CYAN);
    public static final GlintColor PURPLE = new GlintColor(DyeColor.PURPLE);
    public static final GlintColor BLUE = new GlintColor(DyeColor.BLUE);
    public static final GlintColor BROWN = new GlintColor(DyeColor.BROWN);
    public static final GlintColor GREEN = new GlintColor(DyeColor.GREEN);
    public static final GlintColor RED = new GlintColor(DyeColor.RED);
    public static final GlintColor BLACK = new GlintColor(DyeColor.BLACK, 0x404040);
    public static final GlintColor RAINBOW = new GlintColor("rainbow", ChatFormatting.WHITE);
    public static final GlintColor BLANK = new GlintColor("blank", ChatFormatting.GRAY);

    static{
        //extra color mods
        for(DyeColor color : DyeColor.values()) {
            if(BY_COLOR.get(color) == null) {
                new GlintColor(color);
            }
        }
    }

    private final DyeColor dyeColor;
    private final String name;
    private final TextColor textColor;

    GlintColor(DyeColor color) {
        this(color, color.getTextColor());
    }

    GlintColor(DyeColor color, int textColor) {
        this(color.getSerializedName(), textColor, color);
    }

    GlintColor(String name, ChatFormatting textColor) {
        this(name, textColor.getColor() != null ? textColor.getColor() : -1, null);
    }

    // Want to register your own rune? Just instantiate this class
    public GlintColor(String name, int textColor, @Nullable DyeColor dyeColor) {
        this.dyeColor = dyeColor;
        this.name = name;
        this.textColor = TextColor.fromRgb(textColor);
        BY_NAME.put(name, this);
        if(dyeColor != null) BY_COLOR.put(dyeColor, this);
    }

    public static Collection<GlintColor> values() {
        return BY_NAME.values();
    }

    public TextColor getTextColor() {
        return textColor;
    }

    @Nullable
    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public static GlintColor byName(String name) {
        return BY_NAME.get(name);
    }

    @Nullable
    public static GlintColor byDyeColor(DyeColor dyeColor) {
        return BY_COLOR.get(dyeColor);
    }

}