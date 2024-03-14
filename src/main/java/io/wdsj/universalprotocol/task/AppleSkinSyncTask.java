package io.wdsj.universalprotocol.task;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppleSkinSyncTask extends UniversalRunnable {

    private final UniversalProtocol plugin = UniversalProtocol.getInstance();
    private final Map<UUID, Float> previousSaturationLevels;
    private final Map<UUID, Float> previousExhaustionLevels;

    public AppleSkinSyncTask() {
        previousSaturationLevels = new HashMap<>();
        previousExhaustionLevels = new HashMap<>();
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            updatePlayer(player);
        }
    }

    private void updatePlayer(Player player) {
        float saturation = player.getSaturation();
        Float previousSaturation = previousSaturationLevels.get(player.getUniqueId());
        if(previousSaturation == null || saturation != previousSaturation) {
            player.sendPluginMessage(plugin, Channels.AppleSkin.SATURATION_CHANNEL, ByteBuffer.allocate(Float.BYTES).putFloat(saturation).array());
            previousSaturationLevels.put(player.getUniqueId(), saturation);
        }

        float exhaustion = player.getExhaustion();
        Float previousExhaustion = previousExhaustionLevels.get(player.getUniqueId());
        float MINIMUM_EXHAUSTION_CHANGE_THRESHOLD = 0.01F;
        if(previousExhaustion == null || Math.abs(exhaustion - previousExhaustion) >= MINIMUM_EXHAUSTION_CHANGE_THRESHOLD) {
            player.sendPluginMessage(plugin, Channels.AppleSkin.EXHAUSTION_CHANNEL, ByteBuffer.allocate(Float.BYTES).putFloat(exhaustion).array());
            previousExhaustionLevels.put(player.getUniqueId(), exhaustion);
        }
    }

    public void onPlayerLogIn(Player player) {
        previousSaturationLevels.remove(player.getUniqueId());
        previousExhaustionLevels.remove(player.getUniqueId());
    }

    public void onPlayerLogOut(Player player) {
        previousSaturationLevels.remove(player.getUniqueId());
        previousExhaustionLevels.remove(player.getUniqueId());
    }

}