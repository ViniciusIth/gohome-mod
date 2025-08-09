package io.github.viniciusith.gohome.registration;

import io.github.viniciusith.gohome.Constants;
import io.github.viniciusith.gohome.effect.RecallEffect;
import io.github.viniciusith.gohome.item.MagicMirror;
import io.github.viniciusith.gohome.platform.registry.RegistrationProvider;
import io.github.viniciusith.gohome.platform.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.Item;


public class ModRegistry {
    public static final RegistrationProvider<MobEffect> EFFECTS = RegistrationProvider.get(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);
    public static final RegistryObject<MobEffect> RECALL_EFFECT = EFFECTS.register("recall", () -> new RecallEffect(MobEffectCategory.BENEFICIAL, 0x15A5C1));

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Constants.MOD_ID);
    public static final RegistryObject<Item> MAGIC_MIRROR = ITEMS.register("magic_mirror", () -> new MagicMirror(MagicMirror.MAGIC_MIRROR_PROPERTIES));

    public static void load(){}
}
