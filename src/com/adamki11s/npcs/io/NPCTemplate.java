package com.adamki11s.npcs.io;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.adamki11s.data.ItemStackDrop;
import com.adamki11s.npcs.NPCHandler;
import com.adamki11s.npcs.SimpleNPC;
import com.adamki11s.pathing.preset.PresetPath;
import com.adamki11s.questx.QuestX;

public class NPCTemplate {

	final String name;
	final boolean moveable, attackable;
	final int minPauseTicks, maxPauseTicks, maxVariation, respawnTicks, maxHealth, damageMod;
	final double retalliationMultiplier;
	final ItemStackDrop inventory;
	final ItemStack[] gear;// boots 1, legs 2, chest 3, head 4, arm 5

	public NPCTemplate(String name, boolean moveable, boolean attackable, int minPauseTicks, int maxPauseTicks, int maxVariation, int health, int respawnTicks, ItemStackDrop inventory,
			ItemStack[] gear, int damageMod, double retalliationMultiplier) {
		this.name = name;
		this.moveable = moveable;
		this.attackable = attackable;
		this.minPauseTicks = minPauseTicks;
		this.maxPauseTicks = maxPauseTicks;
		this.maxVariation = maxVariation;
		this.maxHealth = health;
		this.respawnTicks = respawnTicks;
		this.inventory = inventory;
		this.gear = gear;
		this.damageMod = damageMod;
		this.retalliationMultiplier = retalliationMultiplier;
	}

	// int health, int respawnTicks, ItemStackDrop inventory, ItemStack[] gear,
	// int damageMod, double retalliationMultiplier) {
	public SimpleNPC registerSimpleNPCFixedSpawn(NPCHandler handle, Location fixedLocation) {
		SimpleNPC snpc = new SimpleNPC(handle, name, moveable, attackable, minPauseTicks, maxPauseTicks, maxVariation, maxHealth, respawnTicks, inventory, gear, damageMod,
				retalliationMultiplier);
		snpc.setFixedLocation(fixedLocation);
		snpc.setNewSpawnLocation(fixedLocation);
		snpc.spawnNPC();
		return snpc;
	}

	public void addSimpleNPCToWaitingList(NPCHandler handle) {
		QuestX.logDebug("Adding new simple npc to list");
		SimpleNPC snpc = new SimpleNPC(handle, name, moveable, attackable, minPauseTicks, maxPauseTicks, maxVariation, maxHealth, respawnTicks, inventory, gear, damageMod,
				retalliationMultiplier);
		handle.addToWaitingList(snpc);
	}

}
