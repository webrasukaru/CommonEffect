package net.devras.ceffect.arrowtrail;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.devras.ceffect.Main;
import net.devras.ceffect.ParticleAPI.EnumParticle;

public class TrailCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			UUID uuid = p.getUniqueId();

			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("off")) {
					if (ArrowTrail.players.containsKey(uuid)) {
						ArrowTrail.players.remove(uuid);
					}
					p.sendMessage(Main.prefix + "ArrowTrail: Success!");
					return true;
				}
				try {
					for (EnumParticle e : EnumParticle.values()) {
						if (args[0].equalsIgnoreCase(e.name())) {
							ArrowTrail.players.put(uuid, new Trail(e));
							p.sendMessage(Main.prefix + "ArrowTrail: Success!");
							return true;
						}
					}
					p.sendMessage(Main.prefix + "ArrowTrail: Not found particle!");
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage(Main.prefix + "ArrowTrail: Success!");
				}
			}else {
				String text = "";
				for (EnumParticle part : EnumParticle.values()) {
					if (text.equals("")) {
						text = part.name();
					}else {
						text += ", " + part.name();
					}
				}
				text += ", off";
				p.sendMessage(Main.prefix + "particle: " + text);
				p.sendMessage(Main.prefix + "/trail <particle>");
			}
			return true;
		}
		return false;
	}

}
