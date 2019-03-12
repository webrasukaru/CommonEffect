package net.devras.ceffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.devras.ceffect.ParticleAPI.EnumParticle;
import net.devras.ceffect.arrowtrail.ArrowShoot;
import net.devras.ceffect.arrowtrail.ArrowTrail;
import net.devras.ceffect.doublejump.DoubleJump;

public class Main extends JavaPlugin {

	public static Main Instance;
	public static String prefix;

	public static JavaPlugin getPlugin() {
		return Instance;
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		Instance = this;
		saveDefaultConfig();

		FileConfiguration config = getConfig();

		prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix", "&6&l[CEF]&r"));

		if (config.getBoolean("enable.doublejump", true)) {
			Bukkit.getPluginManager().registerEvents(new DoubleJump(), this);
		}
		if (config.getBoolean("enable.arrowtrail", true)) {
			ArrowTrail.init();
		}

		DoubleJump.cooldownTime = config.getInt("defaults.doublejump.cooldownTime", 10);

		String playSound = config.getString("defaults.doublejump.playSound");
		String playParticle = config.getString("defaults.doublejump.playParticle");
		String defaultParticle = config.getString("defaults.arrowtrail.defaultParticle");
		for (Sound s : Sound.values()) {
			if (playSound.equalsIgnoreCase(s.name())) {
				DoubleJump.playSound = s;
			}
		}
		for (EnumParticle p : EnumParticle.values()) {
			if (playParticle.equalsIgnoreCase(p.name())) {
				DoubleJump.playParticle = p;
			}
			if (defaultParticle.equalsIgnoreCase(p.name())) {
				ArrowShoot.defaultParticle = p;
			}
		}
	}
}
