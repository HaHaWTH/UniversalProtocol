package io.wdsj.universalprotocol.task;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import io.wdsj.universalprotocol.UniversalProtocol;
import io.wdsj.universalprotocol.channel.Channels;
import io.wdsj.universalprotocol.listener.AsteorBarProtocolListener;
import io.wdsj.universalprotocol.util.PacketUtil;
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
            byte[] exhaustPacket = PacketUtil.createPayloadPacket(buf -> {
                buf.put((byte) 0);
                buf.putFloat(exhaustion);
            }, 1 + Float.BYTES);
            byte[] saturationPacket = PacketUtil.createPayloadPacket(buf -> {
                buf.put((byte) 1);
                buf.putFloat(saturation);
            }, 1 + Float.BYTES);
            if (Math.abs(oldExhaustion - exhaustion) >= 0.01f) {
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.AsteorBar.CHANNEL_NETWORK, exhaustPacket);
            }
            if (Math.abs(oldSaturation - saturation) >= 0.01f) {
                player.sendPluginMessage(UniversalProtocol.getInstance(), Channels.AsteorBar.CHANNEL_NETWORK, saturationPacket);
            }
        }
    }
}
