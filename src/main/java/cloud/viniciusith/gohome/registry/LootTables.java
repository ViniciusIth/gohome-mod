package cloud.viniciusith.gohome.registry;

import cloud.viniciusith.gohome.item.MagicMirror;
import cloud.viniciusith.gohome.potion.RecallPotion;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class LootTables {

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, supplier) -> {
            if (!supplier.isBuiltin()) {
                return;
            }

            if (net.minecraft.loot.LootTables.RUINED_PORTAL_CHEST.equals(id)) {
                RecallPotion.addLootTable(tableBuilder, 0f, 3f);
            } else if (net.minecraft.loot.LootTables.SPAWN_BONUS_CHEST.equals(id)) {
                RecallPotion.addLootTable(tableBuilder, 2f, 3f);
            } else if (net.minecraft.loot.LootTables.BASTION_OTHER_CHEST.equals(id)) {
                RecallPotion.addLootTable(tableBuilder, 0f, 1f);
            } else if (net.minecraft.loot.LootTables.BASTION_TREASURE_CHEST.equals(id)) {
                MagicMirror.addLootTable(tableBuilder, 1f, 1f);
            } else if (net.minecraft.loot.LootTables.NETHER_BRIDGE_CHEST.equals(id)) {
                MagicMirror.addLootTable(tableBuilder, 0f, 1f);
                RecallPotion.addLootTable(tableBuilder, 0f, 3f);
            } else if (net.minecraft.loot.LootTables.STRONGHOLD_LIBRARY_CHEST.equals(id)) {
                RecallPotion.addLootTable(tableBuilder, 0f, 2f);
            }
        });
    }
}
