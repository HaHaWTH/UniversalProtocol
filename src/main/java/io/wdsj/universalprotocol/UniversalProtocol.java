package io.wdsj.universalprotocol;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import io.wdsj.universalprotocol.channel.Channels;
import io.wdsj.universalprotocol.listener.AppleSkinProtocolListener;
import io.wdsj.universalprotocol.listener.ChatImageProtocolListener;
import io.wdsj.universalprotocol.listener.XaeroMapProtocolListener;
import io.wdsj.universalprotocol.setting.Settings;
import io.wdsj.universalprotocol.task.AppleSkinSyncTask;
import io.wdsj.universalprotocol.util.MessengerUtil;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class UniversalProtocol extends JavaPlugin {
    private static UniversalProtocol instance;
    private final File CONFIG_FILE = new File(getDataFolder(), "config.yml");
    public static UniversalProtocol getInstance() {
        return instance;
    }
    private static TaskScheduler scheduler;
    public static TaskScheduler getScheduler() {
        return scheduler;
    }
    public static AppleSkinSyncTask ASTask;
    public static SettingsManager settings;
    @Override
    public void onLoad() {
        instance = this;
        settings = SettingsManagerBuilder
                .withYamlFile(CONFIG_FILE)
                .configurationData(Settings.class)
                .useDefaultMigrationService()
                .create();
    }

    @Override
    public void onEnable() {
        scheduler = UniversalScheduler.getScheduler(this);
        if (settings.getProperty(Settings.APPLE_SKIN_PROTOCOL)) {
            ASTask = new AppleSkinSyncTask();
            ASTask.runTaskTimer(this, 0L, 1L);
            Bukkit.getPluginManager().registerEvents(new AppleSkinProtocolListener(), this);
            MessengerUtil.registerOut(Channels.AppleSkin.EXHAUSTION_CHANNEL);
            MessengerUtil.registerOut(Channels.AppleSkin.SATURATION_CHANNEL);
            getLogger().info("AppleSkin protocol registered.");
        }
        if (settings.getProperty(Settings.CHAT_IMAGE_PROTOCOL)) {
            MessengerUtil.registerOut(Channels.ChatImage.CHANNEL_FILE_CHANNEL);
            MessengerUtil.registerIn(Channels.ChatImage.CHANNEL_FILE_CHANNEL, new ChatImageProtocolListener());
            MessengerUtil.registerOut(Channels.ChatImage.CHANNEL_DOWNLOAD_FILE_CHANNEL);
            MessengerUtil.registerOut(Channels.ChatImage.CHANNEL_FILE_INFO);
            MessengerUtil.registerIn(Channels.ChatImage.CHANNEL_FILE_INFO, new ChatImageProtocolListener());
            getLogger().info("ChatImage protocol registered.");
        }
        if (settings.getProperty(Settings.XAERO_MAP_PROTOCOL)) {
            MessengerUtil.registerOut(Channels.XaeroMap.XAERO_MINIMAP_CHANNEL);
            MessengerUtil.registerOut(Channels.XaeroMap.XAERO_WORLDMAP_CHANNEL);
            getServer().getPluginManager().registerEvents(new XaeroMapProtocolListener(), this);
            getLogger().info("XaeroMap protocol registered.");
        }
        int pluginId = 21326;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("appleskin_protocol", () -> settings.getProperty(Settings.APPLE_SKIN_PROTOCOL) ? "Enabled" : "Disabled"));
        metrics.addCustomChart(new SimplePie("chatimage_protocol", () -> settings.getProperty(Settings.CHAT_IMAGE_PROTOCOL) ? "Enabled" : "Disabled"));
        metrics.addCustomChart(new SimplePie("xaeromap_protocol", () -> settings.getProperty(Settings.XAERO_MAP_PROTOCOL) ? "Enabled" : "Disabled"));
        getLogger().info("UniversalProtocol enabled.");
    }

    @Override
    public void onDisable() {
        if (ASTask != null) ASTask.cancel();
        HandlerList.unregisterAll(this);
    }
}
