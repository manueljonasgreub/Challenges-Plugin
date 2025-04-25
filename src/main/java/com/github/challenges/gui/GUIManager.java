package com.github.challenges.gui;

import com.github.challenges.Challenges;
import com.github.challenges.challenge.quiz.Question;
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
            case 10:
                handleUltraHardcoreClick(player, clickedItem);
                break;
            case 11:
                handlePVPClick(player, clickedItem);
                break;
            case 12:
                handleSplitHeartsClick(player, clickedItem);
                break;
            case 13:
                handleQuestionToggleClick(player, clickedItem);
                break;
            case 14:
                handleJumpForbiddenClick(player, clickedItem);
                break;
            case 15:
                handleSneakForbiddenClick(player, clickedItem);
                break;

            default:
                break;
        }


    }

    private void setSplitHeartsItem(){
        ItemStack item = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isSplitHearts()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Split Hearts " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Syncs hearts between ", ChatColor.GRAY + "players when active"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Split Hearts " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Syncs hearts between ", ChatColor.GRAY + "players when active"));
        }


        item.setItemMeta(meta);
        gui.setItem(12, item);
    }
    private void setPVPItem(){
        ItemStack item = new ItemStack(Material.PUFFERFISH);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isPVP()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "PVP " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Enables Friendy Fire "));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "PVP " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Enables Friendy Fire "));
        }

        item.setItemMeta(meta);
        gui.setItem(11, item);
    }
    private void setUltraHardcoreItem(){
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isUltraHardcore()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Ultra Hardcore " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Disables natural ", ChatColor.GRAY + "regeneration when active"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Ultra Hardcore " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Disables natural ", ChatColor.GRAY + "regeneration when active"));
        }

        item.setItemMeta(meta);
        gui.setItem(10, item);
    }

    private void setAllDieOnDeathItem() {
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isAllDieOnDeath()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "All Die On Death " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "When a player dies,", ChatColor.GRAY + "all players will die and", ChatColor.GRAY + "be set to spectator mode."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "All Die On Death " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "When a player dies,", ChatColor.GRAY + "all players will die and", ChatColor.GRAY + "be set to spectator mode."));
        }

        item.setItemMeta(meta);
        gui.setItem(9, item);
    }

    private void setQuestionToggleItem() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().areQuestionsActive()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Questions all 5–7 minutes " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Every few minutes,",
                    ChatColor.GRAY + "a question is asked in the chat,",
                    ChatColor.GRAY + "which must be answered."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Questions all 5–7 minutes " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Every few minutes,",
                    ChatColor.GRAY + "a question is asked in the chat,",
                    ChatColor.GRAY + "which must be answered."));
        }

        item.setItemMeta(meta);
        gui.setItem(13, item);
    }

    private void setJumpForbiddenItem() {
        ItemStack item = new ItemStack(Material.OAK_FENCE);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isJumpForbidden()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Jump = forbidden " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Enables dying if you",
                    ChatColor.GRAY + "jump."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Jump = forbidden " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Enables dying if you",
                    ChatColor.GRAY + "jump."));
        }

        item.setItemMeta(meta);
        gui.setItem(14, item);
    }

    private void setSneakForbiddenItem() {
        ItemStack item = new ItemStack(Material.OAK_SLAB);
        ItemMeta meta = item.getItemMeta();

        if (Challenges.getInstance().getChallenge().isSneakForbidden()) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Sneak = forbidden " + ChatColor.GREEN + "(ENABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Enables dying if you",
                    ChatColor.GRAY + "sneak."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Sneak = forbidden " + ChatColor.RED + "(DISABLED)");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Enables dying if you",
                    ChatColor.GRAY + "sneak."));
        }

        item.setItemMeta(meta);
        gui.setItem(15, item);
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

    private void handleUltraHardcoreClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isUltraHardcore())) {
            Challenges.getInstance().getChallenge().setUltraHardcore(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setUltraHardcore(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        setItems();
        player.closeInventory();
        openGUI(player);
    }
    private void handlePVPClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isPVP())) {
            Challenges.getInstance().getChallenge().setPVP(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setPVP(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        setItems();
        player.closeInventory();
        openGUI(player);
    }

    private void handleSplitHeartsClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isSplitHearts())) {
            Challenges.getInstance().getChallenge().setSplitHearts(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setSplitHearts(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }


    private void handleQuestionToggleClick(Player player, ItemStack clickedItem) {
        Challenges.getInstance().getChallenge().toggleQuestions();

        if (Challenges.getInstance().getChallenge().areQuestionsActive()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        setItems();
        player.closeInventory();
        openGUI(player);
    }

    private void handleJumpForbiddenClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isJumpForbidden())) {
            Challenges.getInstance().getChallenge().setJumpForbidden(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setJumpForbidden(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        setItems();
        player.closeInventory();
        openGUI(player);
    }

    private void handleSneakForbiddenClick(Player player, ItemStack clickedItem) {
        if ((Challenges.getInstance().getChallenge().isSneakForbidden())) {
            Challenges.getInstance().getChallenge().setSneakForbidden(false);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
        } else {
            Challenges.getInstance().getChallenge().setSneakForbidden(true);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        setItems();
        player.closeInventory();
        openGUI(player);
    }

    private void setItems() {
        setAllDieOnDeathItem();
        setUltraHardcoreItem();
        setPVPItem();
        setSplitHeartsItem();
        setQuestionToggleItem();
        setJumpForbiddenItem();
        setSneakForbiddenItem();
    }

    public void openGUI(Player player) {
        player.openInventory(gui);
    }




}
