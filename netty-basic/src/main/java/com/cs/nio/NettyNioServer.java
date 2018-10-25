package com.cs.nio;

        import com.cs.utils.Constant;
        import io.netty.bootstrap.ServerBootstrap;
        import io.netty.buffer.ByteBuf;
        import io.netty.buffer.Unpooled;
        import io.netty.channel.*;
        import io.netty.channel.nio.NioEventLoopGroup;
        import io.netty.channel.socket.SocketChannel;
        import io.netty.channel.socket.nio.NioServerSocketChannel;
        import org.junit.Test;

        import java.net.InetSocketAddress;
        import java.nio.charset.Charset;

/**
 * @author benjaminChan
 * @date 2018/10/25 0025 上午 11:01
 */
public class NettyNioServer {

    @Test
    public void server() {
        final ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(Constant.PORT)).childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush(byteBuf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
