package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.LoginUtil;
import util.SessionUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        } else {
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("身份校验通过，移除handler");
        } else {
            System.out.println("身份校验未通过，关闭当前chanel");
        }
    }
}
