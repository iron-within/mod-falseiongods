package com.falseion_pantheon;

import com.falseion_pantheon.items.custom.FratricideItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FalseionItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "falseion_pantheon");

    public static final RegistryObject<Item> FRATRICIDE = ITEMS.register("fratricide", () ->
            new FratricideItem(new Item.Properties()
                    .tab(FalseionCreativeTab.instance)
                    .stacksTo(1)
                    .fireResistant()
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KHORNE_ICON = ITEMS.register("khorne_icon", () ->
            new Item(new Item.Properties().tab(FalseionCreativeTab.instance).fireResistant()));
}
