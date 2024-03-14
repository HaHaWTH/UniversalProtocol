package io.wdsj.universalprotocol.listener;

import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import io.wdsj.universalprotocol.setting.Settings;
import io.wdsj.universalprotocol.util.PacketUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.wdsj.universalprotocol.UniversalProtocol.settings;

public class XaeroMapProtocolListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        byte[] payload = PacketUtil.createPayloadPacket(buffer ->{
            buffer.put((byte) 0);
            buffer.putInt(settings.getProperty(Settings.XAERO_SERVER_ID));
        });
        player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.XaeroMap.XAERO_MINIMAP_CHANNEL, payload);
        player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.XaeroMap.XAERO_WORLDMAP_CHANNEL, payload);
    }
}
