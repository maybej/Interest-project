package com.leafive.maybe.web.service.serverSide;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleChatServer {

	private int port;

	public SimpleChatServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		// (1)NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，Netty 提供了许多不同的 EventLoopGroup
		// 的实现用来处理不同的传输。在这个例子中我们实现了一个服务端的应用，因此会有2个 NioEventLoopGroup
		// 会被使用。第一个经常被叫做‘boss’，用来接收进来的连接。第二个经常被叫做‘worker’，用来处理已经被接收的连接，
		// 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。如何知道多少个线程已经被使用，如何映射到已经创建的
		// Channel上都需要依赖于 EventLoopGroup 的实现，并且可以通过构造函数来配置他们的关系。
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// (2) ServerBootstrap 是一个启动 NIO 服务的辅助启动类。你可以在这个服务中直接使用
			// Channel，但是这会是一个复杂的处理过程，在很多情况下你并不需要这样做。
			ServerBootstrap b = new ServerBootstrap(); // (2)
			// (3)这里我们指定使用 NioServerSocketChannel 类来举例说明一个新的 Channel 如何接收进来的连接
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					// (4)这里的事件处理类经常会被用来处理一个最近的已经接收的 Channel。SimpleChatServerInitializer
					// 继承自ChannelInitializer 是一个特殊的处理类，他的目的是帮助使用者配置一个新的 Channel。也许你想通过增加一些处理类比如
					// SimpleChatServerHandler 来配置一个新的 Channel 或者其对应的ChannelPipeline
					// 来实现你的网络程序。当你的程序变的复杂时，可能你会增加更多的处理类到 pipline 上，然后提取这些匿名类到最顶层的类上。
					.childHandler(new SimpleChatServerInitializer()) // (4)
					// (5)你可以设置这里指定的 Channel 实现的配置参数。我们正在写一个TCP/IP 的服务端，因此我们被允许设置 socket
					// 的参数选项比如tcpNoDelay 和 keepAlive
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					// (6)option() 是提供给NioServerSocketChannel 用来接收进来的连接。childOption() 是提供给由父管道
					// ServerChannel 接收到的连接，在这个例子中也是 NioServerSocketChannel。
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			System.out.println("SimpleChatServer 启动了");

			// 绑定端口，开始接收进来的连接我们继续，剩下的就是绑定端口然后启动服务。这里我们在机器上绑定了机器所有网卡上的 8080 端口。当然现在你可以多次调用
			// bind() 方法(基于不同绑定地址)。
			ChannelFuture f = b.bind(port).sync(); // (7)

			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();

		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();

			System.out.println("SimpleChatServer 关闭了");
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new SimpleChatServer(port).run();

	}

}
