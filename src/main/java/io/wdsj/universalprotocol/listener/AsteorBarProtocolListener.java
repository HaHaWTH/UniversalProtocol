package io.wdsj.universalprotocol.listener;

import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.ByteBuffer;
import java.util.HashSet;

public class AsteorBarProtocolListener implements Listener, PluginMessageListener {
    public static final HashSet<Player> ASTEOR_BAR_PLAYERS = new HashSet<>();
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte [] message) {
        ASTEOR_BAR_PLAYERS.add(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOnline() || ASTEOR_BAR_PLAYERS.contains(player)) return;
        UniversalProtocol.getScheduler().runTaskLaterAsynchronously(() -> {
            for (int i = 0; i < 10; i++) {
                if (!player.isOnline() || ASTEOR_BAR_PLAYERS.contains(player)) return;
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.AsteorBar.CHANNEL_NETWORK, ByteBuffer.allocate(2).put((byte) 3).put((byte)1).array());
            }
        }, 20L);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ASTEOR_BAR_PLAYERS.remove(event.getPlayer());
    }
}
