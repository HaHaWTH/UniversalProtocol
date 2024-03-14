package io.wdsj.universalprotocol.listener;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import io.wdsj.universalprotocol.protocol.chatimage.ChatImageIndex;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatImageProtocolListener implements PluginMessageListener {

    private static final Map<String, HashMap<Integer, String>> SERVER_BLOCK_CACHE = new HashMap<>();
    private static final Map<String, Integer> FILE_COUNT_MAP = new HashMap<>();
    private static final Map<String, List<String>> USER_CACHE_MAP = new HashMap<>();
    private static final Gson gson = new Gson();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        switch (channel) {
            case Channels.ChatImage.CHANNEL_FILE_INFO:
                String url = new String(message, StandardCharsets.UTF_8);
                if (SERVER_BLOCK_CACHE.containsKey(url) && FILE_COUNT_MAP.containsKey(url)) {
                    HashMap<Integer, String> list = SERVER_BLOCK_CACHE.get(url);
                    Integer total = FILE_COUNT_MAP.get(url);
                    if (total == list.size()) {
                        for (Map.Entry<Integer, String> entry : list.entrySet()) {
                            player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.ChatImage.CHANNEL_DOWNLOAD_FILE_CHANNEL, entry.getValue().getBytes());
                        }
                        return;
                    }
                }
                String notFound = "null->" + url;
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.ChatImage.CHANNEL_FILE_INFO, notFound.getBytes(StandardCharsets.UTF_8));
                List<String> names = USER_CACHE_MAP.containsKey(url) ? USER_CACHE_MAP.get(url) : Lists.newArrayList();
                names.add(player.getUniqueId().toString());
                USER_CACHE_MAP.put(url, names);
                break;
            case Channels.ChatImage.CHANNEL_FILE_CHANNEL:
                String res = new String(message, StandardCharsets.UTF_8);
                ChatImageIndex title = gson.fromJson(res, ChatImageIndex.class);
                HashMap<Integer, String> blocks = SERVER_BLOCK_CACHE.containsKey(title.url) ? SERVER_BLOCK_CACHE.get(title.url) : new HashMap<>();
                blocks.put(title.index, res);
                SERVER_BLOCK_CACHE.put(title.url, blocks);
                FILE_COUNT_MAP.put(title.url, title.total);
                if (title.total == blocks.size()) {
                    if (USER_CACHE_MAP.containsKey(title.url)) {
                        List<String> names_1 = USER_CACHE_MAP.get(title.url);
                        for (String uuid : names_1) {
                            Player player1 = Bukkit.getPlayer(UUID.fromString(uuid));
                            if (player1 != null) {
                                String fileInfo = "true->" + title.url;
                                player1.sendPluginMessage(UniversalProtocol.getInstance(), Channels.ChatImage.CHANNEL_FILE_INFO, fileInfo.getBytes(StandardCharsets.UTF_8));
                            }
                        }
                        USER_CACHE_MAP.put(title.url, Lists.newArrayList());
                    }
                }
                break;
            default:
        }
    }
}
