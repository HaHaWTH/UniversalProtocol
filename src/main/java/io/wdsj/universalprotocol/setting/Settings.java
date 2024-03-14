package io.wdsj.universalprotocol.setting;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import io.wdsj.universalprotocol.util.RandomUtil;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class Settings implements SettingsHolder {
    @Comment({"Enable AppleSkin Protocol",
            "是否启用AppleSkin协议支持"})
    public static final Property<Boolean> APPLE_SKIN_PROTOCOL = newProperty("appleSkin.appleSkinProtocol", true);
    @Comment({"Enable ChatImage Protocol",
            "是否启用ChatImage协议支持"})
    public static final Property<Boolean> CHAT_IMAGE_PROTOCOL = newProperty("chatImage.chatImageProtocol", true);
    @Comment({"Enable XaeroMap Protocol",
            "是否启用XaeroMap协议支持"})
    public static final Property<Boolean> XAERO_MAP_PROTOCOL = newProperty("xaeroMap.xaeroMapProtocol", true);
    @Comment({"XaeroMap server id",
            "XaeroMap的服务器唯一标识符"})
    public static final Property<Integer> XAERO_SERVER_ID = newProperty("xaeroMap.serverIdentifier", RandomUtil.getRandomInt(10000,10000000));
}
