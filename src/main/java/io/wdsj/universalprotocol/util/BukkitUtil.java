package io.wdsj.universalprotocol.util;

import org.bukkit.World;
import org.bukkit.entity.Entity;

public class BukkitUtil {
    private BukkitUtil() {
    }

    /**
     * 根据 entityId 在给定的世界中查找实体
     * @param world 要搜索的世界
     * @param entityId 要查找的实体ID
     * @return 匹配的实体，如果没有找到则返回 null
     */
    public static Entity getEntityById(World world, int entityId) {
        for (Entity entity : world.getEntities()) {
            if (entity.getEntityId() == entityId) {
                return entity;
            }
        }
        return null; // 没有找到对应ID的实体
    }
}
