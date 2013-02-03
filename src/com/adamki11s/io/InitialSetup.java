package com.adamki11s.io;

import java.io.File;
import java.io.IOException;

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
		File wConfig = FileLocator.getWorldConfig();
		if(!wConfig.exists()){
			fileSetup(wConfig);
			SyncConfiguration conf = new SyncConfiguration(wConfig);
			conf.add("MAX_SPAWNS_PER_CHUNK", 2);
			conf.add("MAX_SPAWNS_PER_WORLD", 200);
			conf.add("TAG_API_SUPPORT", false);
			conf.addComment("Whether QuestX will colour player name tags depending on their reputation level");
			conf.add("DESPAWN_IFUNTOUCHED_MINUTES", 45);
			conf.addComment("After how many minutes this NPC will despawn if no one has interacted with it. Does not apply to fixed spawns");
			conf.add("SPAWNABLE_WORLDS", "world");
			conf.addComment("List the worlds in which you want NPC's to spawn in, if you do not want NPC's spawning in a certain world simply exclude their name from the list.");
			conf.addComment("Multiple Worlds should be delimited by commas as such world1,world2,world3");
			conf.write();
		}
		File currentQ = FileLocator.getCurrentQuestFile();
		if(!currentQ.exists()){
			fileSetup(currentQ);
		}
		WorldConfigData.loadWorldConfigData();
	}

	static void folderSetup(File f) {
		if (!f.exists()) {
			System.out.println("Directory Created : " + f.getPath());
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
