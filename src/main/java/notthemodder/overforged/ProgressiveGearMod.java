package notthemodder.overforged;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main mod initializer for Overforged. Registers all blocks, items, menus,
 * block entities, equipment tiers, and ore world generation features.
 */
public class ProgressiveGearMod implements ModInitializer {

	/** The mod's namespace, used for all resource identifiers. */
	public static final String MOD_ID = "overforged";

	/** Shared logger for the mod. */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/** Placed feature key for the stone-hosted crystal ore. */
	private static final ResourceKey<PlacedFeature> CRYSTAL_ORE_PLACED =
			ResourceKey.create(
					Registries.PLACED_FEATURE,
					id("crystal_ore_placed")
			);

	/** Placed feature key for the deepslate-hosted crystal ore. */
	private static final ResourceKey<PlacedFeature> DEEPSLATE_CRYSTAL_ORE_PLACED =
			ResourceKey.create(
					Registries.PLACED_FEATURE,
					id("deepslate_crystal_ore_placed")
			);

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
		ModMenus.initialize();
		AlloyBlastFurnaceBlockEntity.initialize();
		ModEquipment.initialize();
		ModForgerItems.initialize();
		ForgerBlockEntity.initialize();

		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				CRYSTAL_ORE_PLACED
		);

		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				DEEPSLATE_CRYSTAL_ORE_PLACED
		);

		for (String ore : new String[]{
				"tungsten", "titanium", "indium", "aluminum", "silver", "platinum",
				"tin", "bismuth", "osmium", "ruby", "lintium"
		}) {
			addOreFeatures(ore);
		}

		LOGGER.info("Registered Overforged materials and ores.");
	}

	/**
	 * Registers both the stone and deepslate placed feature variants for the given ore name
	 * into all overworld biomes.
	 *
	 * @param ore the base ore name (e.g. {@code "tungsten"})
	 */
	private static void addOreFeatures(String ore) {
		for (String variant : new String[]{ore + "_ore_placed", "deepslate_" + ore + "_ore_placed"}) {
			ResourceKey<PlacedFeature> feature =
					ResourceKey.create(Registries.PLACED_FEATURE, id(variant));
			BiomeModifications.addFeature(
					BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_ORES,
					feature
			);
		}
	}

	/**
	 * Creates a mod-namespaced {@link net.minecraft.resources.Identifier} for the given path.
	 *
	 * @param path the resource path
	 * @return an {@code Identifier} under the {@value #MOD_ID} namespace
	 */
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
