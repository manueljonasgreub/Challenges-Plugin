package com.github.challenges.gui;

import com.github.challenges.Challenges;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.http.WebSocket;
import java.util.Arrays;
import java.util.Dictionary;

public class GUIManager implements Listener {

    private Inventory gui;

    public GUIManager() {
        gui = Bukkit.createInventory(null, 27, "Settings");
        setItems();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!event.getView().getTitle().equals("Settings")) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        switch (event.getSlot()) {
            case 9:
                handleAllDieOnDeathClick(player, clickedItem);
                break;
            default:
                break;

        }

    }

    private void setAllDieOnDeathItem() {
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isAllDieOnDeath()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "All Die On Death " + ChatColor.GREEN + "(AKTIVIERT)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "When a player dies,", ChatColor.GRAY + "all players will die and", ChatColor.GRAY + "be set to spectator mode."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "All Die On Death " + ChatColor.RED + "(DEAKTIVIERT)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "When a player dies,", ChatColor.GRAY + "all players will die and", ChatColor.GRAY + "be set to spectator mode."));
        }

        item.setItemMeta(meta);
        gui.setItem(9, item);
    }

    private void handleAllDieOnDeathClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isAllDieOnDeath())) {
            Challenges.getInstance().getChallenge().setAllDieOnDeath(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setAllDieOnDeath(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        setItems();
        player.closeInventory();
        openGUI(player);
    }

    private void setItems() {
        setAllDieOnDeathItem();

    }

    public void openGUI(Player player) {
        player.openInventory(gui);
    }




}
