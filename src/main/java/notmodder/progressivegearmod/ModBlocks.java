package notmodder.progressivegearmod;

import java.util.function.Function;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {

    public static final Block CRYSTAL_ORE = register(
            "crystal_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(3.0F, 3.0F)
                    .requiresCorrectToolForDrops(),
            true
    );

    public static final Block DEEPSLATE_CRYSTAL_ORE = register(
            "deepslate_crystal_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(4.5F, 4.5F)
                    .requiresCorrectToolForDrops(),
            true
    );

    public static final Block ALLOY_BLAST_FURNACE = register(
            "alloy_blast_furnace",
            AlloyBlastFurnaceBlock::new,
            BlockBehaviour.Properties.of().strength(3.5F, 3.5F).requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(AlloyBlastFurnaceBlock.LIT) ? 13 : 0),
            true
    );

    public static final Block FORGER = register(
            "forger",
            ForgerBlock::new,
            BlockBehaviour.Properties.of().strength(5.0F, 1200.0F).requiresCorrectToolForDrops(),
            true
    );

    public static final Block TUNGSTEN_ORE = ore("tungsten_ore", 3.0F);
    public static final Block DEEPSLATE_TUNGSTEN_ORE = ore("deepslate_tungsten_ore", 4.5F);
    public static final Block TITANIUM_ORE = ore("titanium_ore", 3.0F);
    public static final Block DEEPSLATE_TITANIUM_ORE = ore("deepslate_titanium_ore", 4.5F);
    public static final Block INDIUM_ORE = ore("indium_ore", 3.0F);
    public static final Block DEEPSLATE_INDIUM_ORE = ore("deepslate_indium_ore", 4.5F);
    public static final Block ALUMINUM_ORE = ore("aluminum_ore", 3.0F);
    public static final Block DEEPSLATE_ALUMINUM_ORE = ore("deepslate_aluminum_ore", 4.5F);
    public static final Block SILVER_ORE = ore("silver_ore", 3.0F);
    public static final Block DEEPSLATE_SILVER_ORE = ore("deepslate_silver_ore", 4.5F);
    public static final Block PLATINUM_ORE = ore("platinum_ore", 3.0F);
    public static final Block DEEPSLATE_PLATINUM_ORE = ore("deepslate_platinum_ore", 4.5F);
    public static final Block TIN_ORE = ore("tin_ore", 3.0F);
    public static final Block DEEPSLATE_TIN_ORE = ore("deepslate_tin_ore", 4.5F);
    public static final Block BISMUTH_ORE = ore("bismuth_ore", 3.0F);
    public static final Block DEEPSLATE_BISMUTH_ORE = ore("deepslate_bismuth_ore", 4.5F);
    public static final Block OSMIUM_ORE = ore("osmium_ore", 3.0F);
    public static final Block DEEPSLATE_OSMIUM_ORE = ore("deepslate_osmium_ore", 4.5F);
    public static final Block RUBY_ORE = ore("ruby_ore", 3.0F);
    public static final Block DEEPSLATE_RUBY_ORE = ore("deepslate_ruby_ore", 4.5F);
    public static final Block LINTIUM_ORE = ore("lintium_ore", 3.5F);
    public static final Block DEEPSLATE_LINTIUM_ORE = ore("deepslate_lintium_ore", 5.0F);

    private ModBlocks() {
    }

    private static Block ore(String name, float strength) {
        return register(
                name,
                Block::new,
                BlockBehaviour.Properties.of()
                        .strength(strength, strength)
                        .requiresCorrectToolForDrops(),
                true
        );
    }

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties,
            boolean registerItem
    ) {
        Identifier identifier = ProgressiveGearMod.id(name);

        ResourceKey<Block> blockKey =
                ResourceKey.create(Registries.BLOCK, identifier);

        Block block = blockFactory.apply(properties.setId(blockKey));

        if (registerItem) {
            ResourceKey<Item> itemKey =
                    ResourceKey.create(Registries.ITEM, identifier);

            BlockItem blockItem = new BlockItem(
                    block,
                    new Item.Properties()
                            .setId(itemKey)
                            .useBlockDescriptionPrefix()
            );

            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.NATURAL_BLOCKS
        ).register(output -> {
            output.accept(CRYSTAL_ORE);
            output.accept(DEEPSLATE_CRYSTAL_ORE);
            output.accept(TUNGSTEN_ORE);
            output.accept(DEEPSLATE_TUNGSTEN_ORE);
            output.accept(TITANIUM_ORE);
            output.accept(DEEPSLATE_TITANIUM_ORE);
            output.accept(INDIUM_ORE);
            output.accept(DEEPSLATE_INDIUM_ORE);
            output.accept(ALUMINUM_ORE);
            output.accept(DEEPSLATE_ALUMINUM_ORE);
            output.accept(SILVER_ORE);
            output.accept(DEEPSLATE_SILVER_ORE);
            output.accept(PLATINUM_ORE);
            output.accept(DEEPSLATE_PLATINUM_ORE);
            output.accept(TIN_ORE);
            output.accept(DEEPSLATE_TIN_ORE);
            output.accept(BISMUTH_ORE);
            output.accept(DEEPSLATE_BISMUTH_ORE);
            output.accept(OSMIUM_ORE);
            output.accept(DEEPSLATE_OSMIUM_ORE);
            output.accept(RUBY_ORE);
            output.accept(DEEPSLATE_RUBY_ORE);
            output.accept(LINTIUM_ORE);
            output.accept(DEEPSLATE_LINTIUM_ORE);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(output -> {
                    output.accept(ALLOY_BLAST_FURNACE);
                    output.accept(FORGER);
                });
    }
}
