package com.github.challenges.gui;

import com.github.challenges.Challenges;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.net.http.WebSocket;
import java.util.Dictionary;

public class GUIManager implements Listener {

    private Inventory gui;

    public GUIManager() {
        gui = Bukkit.createInventory(null, 27, "Settings");

        setItems();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(gui)) return;


        event.setCancelled(true);


        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        switch (clickedItem.getType()) {
            case DIAMOND:
                player.sendMessage("diamond");
                event.getInventory().setItem(event.getSlot(), new ItemStack(Material.IRON_INGOT));
                break;
            case EMERALD:
                player.sendMessage("emerald");
                event.getInventory().setItem(event.getSlot(), new ItemStack(Material.DIAMOND));
                break;
            case GOLD_INGOT:
                player.sendMessage("gold");
                break;
            case IRON_INGOT:
                if (event.getSlot() == 11) {
                    player.sendMessage("lol");
                } else {
                    player.sendMessage("iron");
                }
                break;
            default:
                break;
        }

    }

    public void openGUI(Player player) {
        player.openInventory(gui);
    }

    private void setItems() {

        gui.setItem(11, new ItemStack(Material.DIAMOND));
        gui.setItem(13, new ItemStack(Material.GOLD_INGOT));
        gui.setItem(15, new ItemStack(Material.IRON_INGOT));

    }

}
