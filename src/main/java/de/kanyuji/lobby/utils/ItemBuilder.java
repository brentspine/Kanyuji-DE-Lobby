package de.kanyuji.lobby.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

public class ItemBuilder {

    private ItemStack is;
    public ItemBuilder(Material m){
        this(m, 1);
    }
    public ItemBuilder(ItemStack is){
        this.is=is;
    }


    public ItemBuilder(Material m, int amount){
        is= new ItemStack(m, amount);
    }
    public ItemBuilder clone(){
        return new ItemBuilder(is);
    }
    public ItemBuilder setDurability(short dur){
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDisplayName(String name){
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level){
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench){
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(UUID owner){
        try{
            SkullMeta im = (SkullMeta)is.getItemMeta();
            //im.setOwner(owner);
            im.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
            is.setItemMeta(im);
        }catch(ClassCastException expected){}
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment ench, int level){
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments){
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setInfinityDurability(){
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLore(String... lore){
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if(!lore.contains(line))return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if(index<0||index>lore.size())return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(im.hasLore())lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos){
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color){
        try{
            LeatherArmorMeta im = (LeatherArmorMeta)is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        }catch(ClassCastException expected){}
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta im = is.getItemMeta();
        for(ItemFlag current : flags) {
            im.addItemFlags(current);
        }
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        ItemMeta im = is.getItemMeta();
        for(ItemFlag current : flags) {
            im.removeItemFlags(current);
        }
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder hideMetaTags(boolean hide) {
        ItemMeta im = is.getItemMeta();
        if(hide)
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        else
            im.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setCustomSkull(String url) {

        if (url.isEmpty()) return this;

        SkullMeta skullMeta = (SkullMeta) is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        is.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setPotionEffect(PotionEffect potionEffect) {
        try {
            PotionMeta im = (PotionMeta) is.getItemMeta();
            im.addCustomEffect(potionEffect, true);
            is.setItemMeta(im);
            return this;
        } catch (ClassCastException e) {
            return this;
        }
    }

    public ItemBuilder setArrowColor(Color color) {
        try {
            PotionMeta im = (PotionMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
            return this;
        } catch (ClassCastException e) {
            return this;
        }
    }

    public ItemStack build(){
        return is;
    }
}
