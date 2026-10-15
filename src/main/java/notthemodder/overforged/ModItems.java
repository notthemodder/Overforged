package notthemodder.overforged;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

/**
 * Registers all raw material and ingot items for the mod and adds them
 * to the {@link net.minecraft.world.item.CreativeModeTabs#INGREDIENTS} creative tab.
 */
public final class ModItems {
    /** Tracks all registered material items for bulk creative tab insertion. */
    private static final List<Item> MATERIAL_ITEMS = new ArrayList<>();

    public static final Item CRYSTAL_SHARD = material("crystal_shard");
    public static final Item CRYSTAL = material("crystal");

    public static final Item RAW_TUNGSTEN = material("raw_tungsten");
    public static final Item TUNGSTEN_INGOT = material("tungsten_ingot");
    public static final Item RAW_TITANIUM = material("raw_titanium");
    public static final Item TITANIUM_INGOT = material("titanium_ingot");
    public static final Item RAW_INDIUM = material("raw_indium");
    public static final Item INDIUM_INGOT = material("indium_ingot");
    public static final Item RAW_ALUMINUM = material("raw_aluminum");
    public static final Item ALUMINUM_INGOT = material("aluminum_ingot");
    public static final Item RAW_SILVER = material("raw_silver");
    public static final Item SILVER_INGOT = material("silver_ingot");
    public static final Item RAW_PLATINUM = material("raw_platinum");
    public static final Item PLATINUM_INGOT = material("platinum_ingot");
    public static final Item RAW_TIN = material("raw_tin");
    public static final Item TIN_INGOT = material("tin_ingot");
    public static final Item RAW_BISMUTH = material("raw_bismuth");
    public static final Item BISMUTH_INGOT = material("bismuth_ingot");
    public static final Item RAW_OSMIUM = material("raw_osmium");
    public static final Item OSMIUM_INGOT = material("osmium_ingot");
    public static final Item RUBY = material("ruby");
    public static final Item RAW_LINTIUM = material("raw_lintium");
    public static final Item LINTIUM_INGOT = material("lintium_ingot");
    public static final Item THURANIUM_INGOT = material("thuranium_ingot");
    public static final Item VISMITE_INGOT = material("vismite_ingot");
    public static final Item PLUTONIUM_INGOT = material("plutonium_ingot");
    public static final Item TITANSTENE_INGOT = material("titanstene_ingot");
    public static final Item BISMUTHANE_INGOT = material("bismuthane_ingot");
    public static final Item SILVARIUM_INGOT = material("silvarium_ingot");
    public static final Item PLATINE_INGOT = material("platine_ingot");
    public static final Item REINFORCED_TITANSTENE_INGOT = material("reinforced_titanstene_ingot");
    public static final Item REINFORCED_BISMUTHANE_INGOT = material("reinforced_bismuthane_ingot");
    public static final Item REINFORCED_SILVARIUM_INGOT = material("reinforced_silvarium_ingot");
    public static final Item REINFORCED_PLATINE_INGOT = material("reinforced_platine_ingot");
    public static final Item ELECTRUM_INGOT = material("electrum_ingot");

    private ModItems() {
    }

    /**
     * Registers a new material item under the mod namespace and tracks it
     * for creative tab insertion.
     *
     * @param name the item registry name
     * @return the registered {@link Item}
     */
    private static Item material(String name) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ProgressiveGearMod.id(name));
        Item item = Registry.register(
                BuiltInRegistries.ITEM,
                key,
                new Item(new Item.Properties().setId(key))
        );
        MATERIAL_ITEMS.add(item);
        return item;
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(output -> MATERIAL_ITEMS.forEach(output::accept));
    }
}
