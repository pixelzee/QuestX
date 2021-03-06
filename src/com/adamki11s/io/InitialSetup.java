package com.adamki11s.io;

import java.io.File;
import java.io.IOException;

import com.adamki11s.bundle.LocaleBundle;
import com.adamki11s.poll.PollManager;
import com.adamki11s.questx.QuestX;
import com.adamki11s.sync.io.configuration.SyncConfiguration;
import com.adamki11s.sync.io.objects.SyncObjectIO;

public class InitialSetup {

	public static void run() {
		folderSetup(new File(FileLocator.root));
		folderSetup(new File(FileLocator.config_root));
		folderSetup(new File(FileLocator.data_root));
		folderSetup(new File(FileLocator.npc_data_root));
		folderSetup(new File(FileLocator.quest_data_root));
		folderSetup(new File(FileLocator.rep_data_root));
		folderSetup(new File("plugins" + File.separator + "QuestX" + File.separator + "Locale"));
		
		File f = FileLocator.getNPCFixedSpawnsFile();
		if (!f.exists()) {
			fileSetup(f);
			SyncObjectIO io = new SyncObjectIO(f);
			io.add("NPC_COUNT", 0);
			io.write();
		}

		f = FileLocator.getHotspotFile();
		if (!f.exists()) {
			fileSetup(f);
			SyncObjectIO io = new SyncObjectIO(f);
			io.add("DEFAULT_HOTSPOT", 0);
			io.write();
		}
		
		f = FileLocator.getNPCPresetPathingFile();
		if (!f.exists()) {
			fileSetup(f);
			SyncObjectIO io = new SyncObjectIO(f);
			io.add("NULL", 0);
			io.write();
		}
		
		f = FileLocator.getPollFile();
		
		if(!f.exists()){
			fileSetup(f);
			SyncConfiguration c = new SyncConfiguration(f);
			c.addComment("The files are polled every hour, to automatically erase a progression file after x hours add them to this file in the specified format.");
			c.addComment("<type>:<name>,<hours>");
			c.addComment("Task example (Clear each players progression if last time touched was more than 3 hours) --> TASK:npc_name,3");
			c.addComment("Quest example (Clear each players progression if last time touched was more than 7 hours) --> QUEST:questname,7");
			c.write();
		}

		File dbConfig = FileLocator.getDatabaseConfig();

		if (!dbConfig.exists()) {
			fileSetup(dbConfig);
			SyncConfiguration cfg = new SyncConfiguration(dbConfig);
			cfg.add("LOGGING_FREQUENCY_MINUTES", 3);
			cfg.addComment("After how many minutes the SQLite database will be updated with population density values (This determines where NPC's are more likely to spawn.");
			cfg.addComment("It is reccommended to keep this value between 3-5 minutes for the first 4-5 days of running this plugin on your server. Then after this time it should have calibrated sufficiently and the rate can be increased to 10-15.");
			cfg.write();
		}

		File genConfig = FileLocator.getGeneralConfig();

		if (!genConfig.exists()) {
			fileSetup(genConfig);
			SyncConfiguration conf = new SyncConfiguration(genConfig);
			conf.add("TAG_API_SUPPORT", false);
			conf.add("CHECK_UPDATES", true);
			conf.add("AUTO_DOWNLOAD_UPDATES", false);
			conf.add("NOTIFY_ADMIN", true);
			conf.add("EXTRACT_NPC_PAYLOAD", true);
			conf.addComment("Whether to extract the NPC's included with QuestX, note if you set this to false you will not have any NPCs but"
					+ " the ones you create yourself, unless you extract the NPCs from the payload file yourself.");
			conf.add("EXTRACT_QUEST_PAYLOAD", true);
			conf.addComment("Whether to extract the Quests and related NPCs included with QuestX, note if you set this to false you will not have any Quests but"
					+ " the ones you create yourself, unless you extract the Quests from the payload file yourself.");
			conf.write();
		}
		
		File wConfig = FileLocator.getWorldConfig();
		if (!wConfig.exists()) {
			fileSetup(wConfig);
			SyncConfiguration conf = new SyncConfiguration(wConfig);
			conf.add("NPC_ACTIVITY_RANGE", 80);
			conf.addComment("The distance within which NPCs will undergo pathfinding if a Player is within this distance. Every 15 seconds the locations of all players are "
					+ "compared to the locations of NPCs. If any distance between a player and an NPC is less than the activity range then the NPC will be allowed to pathfind, "
					+ "that is walk around. Else the NPC will not pathfind as there is no need since there would be no players around to see it.");
			conf.add("MAX_SPAWNS_PER_CHUNK", 2);
			conf.add("MAX_SPAWNS_PER_WORLD", 200);
			conf.addComment("Whether QuestX will colour player name tags depending on their reputation level");
			conf.add("DESPAWN_IFUNTOUCHED_MINUTES", 45);
			conf.addComment("After how many minutes this NPC will despawn if no one has interacted with it. Does not apply to fixed spawns");
			conf.add("SPAWNABLE_WORLDS", "world");
			conf.addComment("List the worlds in which you want NPC's to spawn in, if you do not want NPC's spawning in a certain world simply exclude their name from the list.");
			conf.addComment("Multiple Worlds should be delimited by commas as such world1,world2,world3");
			conf.write();
		}
		File currentQ = FileLocator.getCurrentQuestFile();
		if (!currentQ.exists()) {
			fileSetup(currentQ);
		}
		
		PollManager.load();
		WorldConfigData.loadWorldConfigData();
		GeneralConfigData.load();
		DatabaseConfigData.load();
	}

	static void folderSetup(File f) {
		if (!f.exists()) {
			QuestX.logMSG("Directory Created : " + f.getPath());
			f.mkdirs();
		}
	}

	static void fileSetup(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
