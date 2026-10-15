package notthemodder.overforged;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

/**
 * Registers all crystallized ingot variants for every base mod ingot and adds
 * them to the {@link net.minecraft.world.item.CreativeModeTabs#INGREDIENTS} creative tab.
 * Also provides lookup methods used by {@link ForgerRecipes}.
 */
public final class ModForgerItems {
    /** Maps each base ingot to its crystallized variant. */
    private static final Map<Item, Item> CRYSTALLIZED_INGOTS = new LinkedHashMap<>();
    private static boolean initialized;

    private ModForgerItems() {}

    public static void initialize() {
        if (initialized) return;
        register("tungsten", ModItems.TUNGSTEN_INGOT);
        register("titanium", ModItems.TITANIUM_INGOT);
        register("indium", ModItems.INDIUM_INGOT);
        register("aluminum", ModItems.ALUMINUM_INGOT);
        register("silver", ModItems.SILVER_INGOT);
        register("platinum", ModItems.PLATINUM_INGOT);
        register("tin", ModItems.TIN_INGOT);
        register("bismuth", ModItems.BISMUTH_INGOT);
        register("osmium", ModItems.OSMIUM_INGOT);
        register("lintium", ModItems.LINTIUM_INGOT);
        register("thuranium", ModItems.THURANIUM_INGOT);
        register("vismite", ModItems.VISMITE_INGOT);
        register("plutonium", ModItems.PLUTONIUM_INGOT);
        register("titanstene", ModItems.TITANSTENE_INGOT);
        register("bismuthane", ModItems.BISMUTHANE_INGOT);
        register("silvarium", ModItems.SILVARIUM_INGOT);
        register("platine", ModItems.PLATINE_INGOT);
        register("reinforced_titanstene", ModItems.REINFORCED_TITANSTENE_INGOT);
        register("reinforced_bismuthane", ModItems.REINFORCED_BISMUTHANE_INGOT);
        register("reinforced_silvarium", ModItems.REINFORCED_SILVARIUM_INGOT);
        register("reinforced_platine", ModItems.REINFORCED_PLATINE_INGOT);
        register("electrum", ModItems.ELECTRUM_INGOT);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(output -> CRYSTALLIZED_INGOTS.values().forEach(output::accept));
        initialized = true;
    }

    private static void register(String name, Item base) {
        String id = "crystallized_" + name + "_ingot";
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ProgressiveGearMod.id(id));
        Item item = Registry.register(BuiltInRegistries.ITEM, key, new Item(new Item.Properties().setId(key)));
        CRYSTALLIZED_INGOTS.put(base, item);
    }

    /**
     * Returns the crystallized ingot for the given base ingot,
     * or {@code null} if none is registered.
     *
     * @param base the base ingot item
     * @return the crystallized ingot, or {@code null}
     */
    public static Item crystallizedIngot(Item base) { return CRYSTALLIZED_INGOTS.get(base); }
    /**
     * Returns {@code true} if the given item is a registered base ingot
     * that has a crystallized variant.
     *
     * @param item the item to check
     */
    public static boolean isIngot(Item item) { return CRYSTALLIZED_INGOTS.containsKey(item); }
    /**
     * Returns an unmodifiable copy of the base-to-crystallized ingot map.
     *
     * @return immutable map of base ingot to crystallized ingot
     */
    public static Map<Item, Item> ingots() { return Map.copyOf(CRYSTALLIZED_INGOTS); }
}
