package cloud.viniciusith.gohome.potion;

import cloud.viniciusith.gohome.effect.RecallEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecallPotion {
    public static Potion RECALL_POTION;

    public static void registerRecallPotion() {
        RECALL_POTION = Registry.register(
                Registries.POTION,
                new Identifier("gohome", "recall_potion"),
                new Potion(new StatusEffectInstance(RecallEffect.RECALL, 1))
        );

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.ENDER_PEARL, RECALL_POTION);
    }
}
