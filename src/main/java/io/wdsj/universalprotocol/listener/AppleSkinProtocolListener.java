package io.wdsj.universalprotocol.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.wdsj.universalprotocol.UniversalProtocol.ASTask;

public class AppleSkinProtocolListener implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ASTask.onPlayerLogIn(player);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ASTask.onPlayerLogOut(player);
    }
}
