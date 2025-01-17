package io.github.thebusybiscuit.electricspawners;

import java.util.logging.Level;

//import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;

public class ElectricSpawners extends JavaPlugin implements Listener, SlimefunAddon {

    @Override
    public void onEnable() {
        Config cfg = new Config(this);

        // Setting up bStats
        //new Metrics(this, 6163);

        Category category = new Category(new NamespacedKey(this, "electric_spawners"), new CustomItem(SkullItem.fromHash("db6bd9727abb55d5415265789d4f2984781a343c68dcaf57f554a5e9aa1cd"), "&9电力刷怪笼"));
        Research research = new Research(new NamespacedKey(this, "electric_spawners"), 4820, "头颅刷怪笼?", 30);

        for (String mob : cfg.getStringList("mobs")) {
            try {
                EntityType type = EntityType.valueOf(mob);
                new ElectricSpawner(category, mob, type, research).register(this);
            } catch (IllegalArgumentException x) {
                getLogger().log(Level.WARNING, "An Error has occured while adding an Electric Spawner for the (posibly outdated or invalid) EntityType \"{0}\"", mob);
            } catch (Exception x) {
                getLogger().log(Level.SEVERE, x, () -> "An Error has occured while adding an Electric Spawner for the EntityType \"" + mob + "\"");
            }
        }

        research.register();
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Ltfjx/ElectricSpawners/issues";
    }
}
