package chunkbuster.buster;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Buster extends JavaPlugin {
    public static Buster plugin;
    public ItemStack chunkbuster = new ItemStack(Material.BEACON);

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "ChunkBuster Successfully Enabled!");
        this.getCommand("chunkbuster").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Events(), this);

        ItemMeta chunkbusterMeta = chunkbuster.getItemMeta();
        chunkbusterMeta.setDisplayName(ChatColor.AQUA+ "Chunk Buster");

        ArrayList<String> chunkbusterLore = new ArrayList<>();
        chunkbusterLore.add(ChatColor.WHITE + "Place me down in a chunk!");
        chunkbusterLore.add(ChatColor.YELLOW + "Warning: Everything in this chunk will be REMOVED!");
        chunkbusterMeta.setLore(chunkbusterLore);

        chunkbusterMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        chunkbusterMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        chunkbusterMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        chunkbusterMeta.addEnchant(Enchantment.DURABILITY, 512, true);


        chunkbuster.setItemMeta(chunkbusterMeta);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin = null;
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "ChunkBuster Successfully Disabled!");
    }

    public static Buster getInstance() {
        return plugin;
    }
}
