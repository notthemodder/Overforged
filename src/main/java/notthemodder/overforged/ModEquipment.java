package notthemodder.overforged;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;

/**
 * Registers all tool and armor items for every gear tier defined in {@link #GEARS}.
 * Also maintains lookup maps for crystallized, rubyforged, and emeraldforged sword variants
 * used by the Forger machine.
 */
public final class ModEquipment {
    /**
     * Immutable data record describing the stats for a single gear tier.
     * Used to derive both the {@link net.minecraft.world.item.ToolMaterial} and
     * {@link net.minecraft.world.item.equipment.ArmorMaterial} for that tier.
     */
    private record Gear(String name, int durability, float speed, float attack, int enchantability,
                        int armorDurability, int helmet, int chest, int legs, int boots,
                        int armorEnchantability, float toughness, float knockback,
                        String harvestTier, boolean thuranium) {}

    private static final Gear[] GEARS = {
            new Gear("aluminum", 230, 5.8F, 1.9F, 13, 14, 2, 5, 4, 2, 12, 0, 0, "iron", false),
            new Gear("bismuth", 270, 6.1F, 2.0F, 16, 16, 2, 6, 4, 2, 15, 0.25F, 0, "iron", false),
            new Gear("tin", 190, 5.2F, 1.5F, 17, 13, 2, 5, 4, 1, 16, 0, 0, "stone", false),
            new Gear("silver", 170, 5.5F, 1.7F, 18, 12, 2, 5, 4, 1, 18, 0, 0, "stone", false),
            new Gear("platinum", 400, 6.5F, 2.1F, 15, 18, 3, 6, 5, 2, 16, 0.5F, 0, "iron", false),
            new Gear("osmium", 520, 7.0F, 2.25F, 18, 20, 3, 7, 5, 2, 16, 0.5F, 0, "iron", false),
            new Gear("titanium", 900, 7.8F, 2.8F, 14, 27, 3, 7, 6, 3, 14, 1.5F, 0.02F, "diamond", false),
            new Gear("tungsten", 1850, 8.5F, 3.5F, 12, 35, 3, 8, 6, 3, 13, 2.5F, 0.05F, "diamond", false),
            new Gear("emerald", 700, 7.5F, 2.5F, 18, 24, 3, 7, 5, 2, 17, 1.0F, 0, "iron", false),
            new Gear("ruby", 1250, 7.8F, 2.8F, 16, 30, 3, 8, 6, 3, 15, 1.5F, 0.02F, "diamond", false),
            new Gear("vismite", 2300, 10.0F, 4.5F, 16, 40, 4, 9, 7, 4, 16, 3.5F, 0.15F, "diamond", false),
            new Gear("lintium", 2700, 11.0F, 5.0F, 18, 45, 4, 10, 8, 4, 18, 4.0F, 0.2F, "diamond", false),
            new Gear("thuranium", 3200, 12.0F, 6.0F, 20, 52, 5, 11, 9, 5, 20, 5.0F, 0.3F, "diamond", true)
    };

    /** All registered equipment items, keyed by their registry name. */
    private static final Map<String, Item> REGISTERED = new LinkedHashMap<>();
    /** Maps a base sword to its Crystal-upgraded variant. */
    private static final Map<Item, Item> CRYSTALLIZED_SWORDS = new LinkedHashMap<>();
    /** Maps a base sword to its Ruby-forged variant. */
    private static final Map<Item, Item> RUBYFORGED_SWORDS = new LinkedHashMap<>();
    /** Maps a base sword to its Emerald-forged variant. */
    private static final Map<Item, Item> EMERALDFORGED_SWORDS = new LinkedHashMap<>();

    private ModEquipment() {}

    public static void initialize() {
        for (Gear gear : GEARS) {
            ToolMaterial tool = toolMaterial(gear);
            ArmorMaterial armor = armorMaterial(gear);
            registerTools(gear, tool);
            registerArmor(gear, armor);
        }
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output ->
                REGISTERED.forEach((id, item) -> {
                    if (id.endsWith("_pickaxe") || id.endsWith("_axe") || id.endsWith("_shovel") || id.endsWith("_hoe")) output.accept(item);
                }));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output ->
                REGISTERED.forEach((id, item) -> {
                    if (id.endsWith("_sword") || id.endsWith("_helmet") || id.endsWith("_chestplate") || id.endsWith("_leggings") || id.endsWith("_boots")) output.accept(item);
                }));
    }

    private static ToolMaterial toolMaterial(Gear gear) {
        TagKey<Block> incorrect = blockTag("incorrect_for_" + gear.name + "_tool");
        TagKey<Item> repairs = itemTag("repairs_" + gear.name + "_gear");
        return new ToolMaterial(incorrect, gear.durability, gear.speed, gear.attack, gear.enchantability, repairs);
    }

    private static ArmorMaterial armorMaterial(Gear gear) {
        Map<ArmorType, Integer> defense = Map.of(
                ArmorType.HELMET, gear.helmet,
                ArmorType.CHESTPLATE, gear.chest,
                ArmorType.LEGGINGS, gear.legs,
                ArmorType.BOOTS, gear.boots
        );
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID, ProgressiveGearMod.id(gear.name));
        return new ArmorMaterial(gear.armorDurability, defense, gear.armorEnchantability,
                SoundEvents.ARMOR_EQUIP_IRON, gear.toughness, gear.knockback,
                itemTag("repairs_" + gear.name + "_gear"), asset);
    }

    private static void registerTools(Gear gear, ToolMaterial material) {
        String n = gear.name;
        Item.Properties sword = material.applySwordProperties(new Item.Properties(), 3.0F, -2.4F);
        if (gear.thuranium) sword.attributes(combatKnockback(n + "_sword", 3.0F + gear.attack, -2.4F, 1.8));
        register(n + "_sword", sword);
        CRYSTALLIZED_SWORDS.put(REGISTERED.get(n + "_sword"), registerSwordVariant(gear, "crystallized_" + n + "_sword", 1.0F, 1.25F));
        RUBYFORGED_SWORDS.put(REGISTERED.get(n + "_sword"), registerSwordVariant(gear, "rubyforged_" + n + "_sword", 1.5F, 1.15F));
        EMERALDFORGED_SWORDS.put(REGISTERED.get(n + "_sword"), registerSwordVariant(gear, "emeraldforged_" + n + "_sword", 1.0F, 1.10F));
        register(n + "_pickaxe", material.applyToolProperties(new Item.Properties(), net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE, 1.0F, -2.8F, 0));
        Item.Properties axe = material.applyToolProperties(new Item.Properties(), net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE, 5.0F, -3.0F, 5);
        if (gear.thuranium) axe.attributes(combatKnockback(n + "_axe", 5.0F + gear.attack, -3.0F, 2.2));
        register(n + "_axe", axe);
        register(n + "_shovel", material.applyToolProperties(new Item.Properties(), net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL, 1.5F, -3.0F, 0));
        register(n + "_hoe", material.applyToolProperties(new Item.Properties(), net.minecraft.tags.BlockTags.MINEABLE_WITH_HOE, 0, -3.0F, 0));
    }

    private static Item registerSwordVariant(Gear gear, String id, float addedDamage, float durabilityScale) {
        ToolMaterial material = new ToolMaterial(
                blockTag("incorrect_for_" + gear.name + "_tool"),
                Math.round(gear.durability * durabilityScale), gear.speed + 0.25F,
                gear.attack + addedDamage, gear.enchantability,
                itemTag("repairs_" + gear.name + "_gear"));
        return register(id, material.applySwordProperties(new Item.Properties(), 3.0F, -2.4F));
    }

    /**
     * Returns the catalyst-upgraded sword for the given base sword and catalyst item,
     * or {@code null} if no recipe exists for that combination.
     *
     * @param base     the base sword item
     * @param catalyst the catalyst item (Crystal, Ruby, or Emerald)
     * @return the upgraded sword item, or {@code null}
     */
    public static Item enhancedSword(Item base, Item catalyst) {
        if (catalyst == ModItems.CRYSTAL) return CRYSTALLIZED_SWORDS.get(base);
        if (catalyst == ModItems.RUBY) return RUBYFORGED_SWORDS.get(base);
        if (catalyst == net.minecraft.world.item.Items.EMERALD) return EMERALDFORGED_SWORDS.get(base);
        return null;
    }

    /**
     * Returns {@code true} if the given item is a registered base mod sword
     * (i.e. it has a crystallized variant).
     *
     * @param item the item to check
     */
    public static boolean isModSword(Item item) {
        return CRYSTALLIZED_SWORDS.containsKey(item);
    }

    private static void registerArmor(Gear gear, ArmorMaterial material) {
        for (ArmorType type : new ArmorType[]{ArmorType.HELMET, ArmorType.CHESTPLATE, ArmorType.LEGGINGS, ArmorType.BOOTS}) {
            Item.Properties properties = new Item.Properties().humanoidArmor(material, type);
            if (gear.thuranium) {
                ItemAttributeModifiers attrs = material.createAttributes(type).withModifierAdded(
                        Attributes.MAX_HEALTH,
                        new AttributeModifier(ProgressiveGearMod.id("thuranium_health_" + type.getName()), 2.0,
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.bySlot(type.getSlot())
                );
                properties.attributes(attrs);
            }
            register(gear.name + "_" + type.getName(), properties);
        }
    }

    private static Item register(String name, Item.Properties properties) {
        var key = ResourceKey.create(Registries.ITEM, ProgressiveGearMod.id(name));
        Item item = Registry.register(BuiltInRegistries.ITEM, key, new Item(properties.setId(key)));
        REGISTERED.put(name, item);
        return item;
    }

    private static ItemAttributeModifiers combatKnockback(String name, float damage, float speed, double knockback) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(ProgressiveGearMod.id(name + "_damage"), damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(ProgressiveGearMod.id(name + "_speed"), speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ProgressiveGearMod.id(name + "_knockback"), knockback, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    private static TagKey<Block> blockTag(String path) { return TagKey.create(Registries.BLOCK, ProgressiveGearMod.id(path)); }
    private static TagKey<Item> itemTag(String path) { return TagKey.create(Registries.ITEM, ProgressiveGearMod.id(path)); }
}
