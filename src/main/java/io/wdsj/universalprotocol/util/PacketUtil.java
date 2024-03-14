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
}
