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

    public static double distanceToSqr(Entity entity1, Entity entity2) {
        if (entity1.getWorld().equals(entity2.getWorld())) {
            double xDiff = entity1.getLocation().getX() - entity2.getLocation().getX();
            double yDiff = entity1.getLocation().getY() - entity2.getLocation().getY();
            double zDiff = entity1.getLocation().getZ() - entity2.getLocation().getZ();

            // 计算距离的平方，避免了平方根计算
            return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
        } else {
            // 如果实体不在同一个世界，可以认为它们之间的距离无限大，或者抛出异常
            return Double.MAX_VALUE;
            // 或者抛出一个异常，取决于你的需求
            // throw new IllegalArgumentException("Entities are in different worlds");
        }
    }
}
