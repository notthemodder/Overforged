package notmodder.progressivegearmod;

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

public class ProgressiveGearMod implements ModInitializer {

	public static final String MOD_ID = "overforged";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ResourceKey<PlacedFeature> CRYSTAL_ORE_PLACED =
			ResourceKey.create(
					Registries.PLACED_FEATURE,
					id("crystal_ore_placed")
			);

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

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
