package chunkbuster.buster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Events implements Listener {
    private HashMap<Chunk, Integer> chunks = new HashMap<>();
    private int buffer = 0;
    private Block blockplaced;
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {

        if (e.getItemInHand().getItemMeta().getEnchants().toString().equalsIgnoreCase("{Enchantment[minecraft:unbreaking, DURABILITY]=512}") && e.getBlockPlaced().getType() == Material.BEACON) {
            Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Use A Chunkbuster?");

            ItemStack accept = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            ItemMeta acceptMeta = accept.getItemMeta();
            acceptMeta.setDisplayName("ACCEPT");
            accept.setItemMeta(acceptMeta);

            ItemStack neutral = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta neutralMeta = accept.getItemMeta();
            neutralMeta.setDisplayName("");
            neutral.setItemMeta(neutralMeta);

            ItemStack deny = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta denyMeta = accept.getItemMeta();
            denyMeta.setDisplayName("DENY");
            deny.setItemMeta(denyMeta);

            inv.setItem(0, accept);
            inv.setItem(1, accept);
            inv.setItem(2, accept);
            inv.setItem(3, accept);

            inv.setItem(4, neutral);

            inv.setItem(5, deny);
            inv.setItem(6, deny);
            inv.setItem(7, deny);
            inv.setItem(8, deny);

            e.getPlayer().openInventory(inv);

            blockplaced = e.getBlockPlaced();

        }
    }
    @EventHandler
    public void InvenClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Use A Chunkbuster?")) {
            event.setCancelled(true);

            if(event.getCurrentItem() == null) {
                return;
            } else if(event.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                if(!chunks.containsKey(player.getLocation().getChunk())) {
                    chunks.put(player.getLocation().getChunk(), 1);
                    //Bukkit.broadcastMessage("ACCEPT");
                    buffer = 0;
                    Chunk chunk = player.getLocation().getChunk();
                    //Bukkit.broadcastMessage("CHUNK BUSTER");
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 256; y++) {
                            buffer++;
                            breaks(x, y, chunk, blockplaced);
                        }
                    }
                    player.closeInventory();
                } else {
                    player.closeInventory();
                    player.sendMessage(ChatColor.RED + "A Chunk Buster has already been placed in this chunk!");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            blockplaced.setType(Material.AIR);
                            player.getInventory().addItem(Buster.plugin.chunkbuster);
                            return;
                        }
                    }.runTaskLater(Buster.getInstance(), 20);
                }
            } else if(event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                //Bukkit.broadcastMessage("DENY");
                blockplaced.setType(Material.LEGACY_AIR);
                player.getInventory().addItem(Buster.plugin.chunkbuster);
                chunks.remove(player.getLocation().getChunk());
                player.closeInventory();
                return;
            }
        }
    }


    public void breaks(final int x, final int y, Chunk chunk, Block block) {

        new BukkitRunnable() {
            @Override
            public void run() {
                for(int z = 0; z < 16; z++) {
                    if(!(chunk.getBlock(x,y,z).getType().toString().equals("AIR") || chunk.getBlock(x,y,z).getType().toString().equals("BEACON") || chunk.getBlock(x,y,z).getType() == Material.BEDROCK)) {
                        chunk.getBlock(x,y,z).setType(Material.LEGACY_AIR);
                        if(z == 2) {
                            block.setType(Material.LEGACY_AIR);
                        }
                    }
                }
            }
        }.runTaskLater(Buster.getInstance(), 20 + buffer);

    }
}
