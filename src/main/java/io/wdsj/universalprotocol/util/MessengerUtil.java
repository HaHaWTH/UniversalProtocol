package io.wdsj.universalprotocol.util;
import io.wdsj.universalprotocol.UniversalProtocol;
import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessengerUtil {
    private MessengerUtil(){}
    private static final UniversalProtocol plugin = UniversalProtocol.getInstance();

    public static void registerOut(String channel) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }
    public static void registerIn(String channel, PluginMessageListener listener) {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, listener);
    }
}
