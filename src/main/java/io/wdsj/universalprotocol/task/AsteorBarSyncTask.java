package io.wdsj.universalprotocol.task;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import io.wdsj.universalprotocol.listener.AsteorBarProtocolListener;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class AsteorBarSyncTask extends UniversalRunnable {
    public static final HashMap<Player, Float> EXHAUSTION_MAP = new HashMap<>();
    public static final HashMap<Player, Float> SATURATION_MAP = new HashMap<>();
    @Override
    public void run() {
        for (Player player : AsteorBarProtocolListener.ASTEOR_BAR_PLAYERS) {
            float exhaustion = player.getExhaustion();
            float saturation = player.getSaturation();
            float oldExhaustion = EXHAUSTION_MAP.getOrDefault(player, exhaustion);
            float oldSaturation = SATURATION_MAP.getOrDefault(player, saturation);
            if (Math.abs(oldExhaustion - exhaustion) >= 0.01f) {
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.AsteorBar.CHANNEL_NETWORK, ByteBuffer.allocate(1 + Float.BYTES).put((byte) 0).putFloat(exhaustion).array());
            }
            if (Math.abs(oldSaturation - saturation) >= 0.01f) {
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.AsteorBar.CHANNEL_NETWORK, ByteBuffer.allocate(1 + Float.BYTES).put((byte) 1).putFloat(saturation).array());
            }
        }
    }
}
