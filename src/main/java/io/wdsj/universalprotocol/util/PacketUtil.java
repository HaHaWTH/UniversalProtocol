package io.wdsj.universalprotocol.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class PacketUtil {
    private PacketUtil() {}
    /**
     * 创建一个协议数据包
     * @param consumer 用于处理ByteBuffer的Consumer
     * @return 包含协议数据的字节数组
     */
    public static byte[] createPayloadPacket(Consumer<ByteBuffer> consumer) {
        // 初始化 ByteArrayOutputStream 用于收集字节数据
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // 预先定义ByteBuffer的大小，根据实际情况调整
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 使用consumer处理ByteBuffer
        consumer.accept(byteBuffer);

        // 准备从ByteBuffer中读取数据
        byteBuffer.flip();

        // 将ByteBuffer中的数据写入ByteArrayOutputStream
        while (byteBuffer.hasRemaining()) {
            out.write(byteBuffer.get());
        }

        // 将收集到的字节数据转换为数组并返回
        return out.toByteArray();
    }

    // 读取变长整数（VarInt）的逻辑
    public static int readVarInt(ByteBuffer buffer) {
        int value = 0;
        int position = 0;
        byte currentByte;

        do {
            currentByte = buffer.get();
            value |= (currentByte & 0x7F) << position;

            if (position > 35) {
                throw new IllegalArgumentException("VarInt is too big");
            }
            position += 7;
        } while ((currentByte & 0x80) == 0x80);

        return value;
    }

    // 写入变长整数（VarInt）到ByteBuffer的逻辑
    public static void writeVarInt(ByteBuffer buffer, int value) {
        while ((value & 0xFFFFFF80) != 0L) {
            buffer.put((byte) ((value & 0x7F) | 0x80));
            value >>>= 7;
        }
        buffer.put((byte) (value & 0x7F));
    }


}
