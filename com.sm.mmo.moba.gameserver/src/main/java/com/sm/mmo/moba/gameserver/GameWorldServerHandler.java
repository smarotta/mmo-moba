package com.sm.mmo.moba.gameserver;

import java.util.List;

import com.google.protobuf.Message;
import com.sm.mmo.moba.domain.ConnectedPlayer;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.NetworkInput;
import com.sm.mmo.moba.domain.message.NetworkOutput;
import com.sm.mmo.moba.gameserver.domain.ConnectedPlayersBag;
import com.sm.mmo.moba.network.protobuf.EntityProtos.EntityDestroyed;
import com.sm.mmo.moba.qnfsm.FSMFeeder;
import com.sm.mmo.moba.qnfsm.FSMFeederController;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class GameWorldServerHandler extends ChannelInboundHandlerAdapter /*implements NetworkHandler*/ {
	
	private final ConnectedPlayersBag players = new ConnectedPlayersBag();
	private FSMFeederController fsmController;
	
	public GameWorldServerHandler() {
		super();
		//initializeFSM();
	}
	
	/*
	private void initializeFSM() {
		fsmController = new FSMFeederController();
		MovementFSM emFSM = new MovementFSM(fsmController);
		GameLogicFSM gmFSM = new GameLogicFSM(fsmController);
		NetworkFSM networkFSM = new NetworkFSM(fsmController, this);
		
		fsmController.register(new MovementFSMFeeder(UUID.randomUUID(), emFSM, fsmController));
		fsmController.register(new GameLogicFSMFeeder(UUID.randomUUID(), gmFSM, fsmController));
		fsmController.register(new NetworkFSMFeeder(UUID.randomUUID(), networkFSM, fsmController));
		
	}
	*/
	
	private void removeConnectedPlayer(ChannelHandlerContext ctx) {
		ConnectedPlayer<ChannelHandlerContext> player = players.getPlayerByContext(ctx);
		if (player != null) {
			players.remove(ctx);
			NetworkInput<ConnectedPlayer<ChannelHandlerContext>, EntityDestroyed> message = 
					new NetworkInput<ConnectedPlayer<ChannelHandlerContext>, EntityDestroyed>(
							player, EntityDestroyed.newBuilder().setId(player.getId()).build());
			fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, message);
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		removeConnectedPlayer(ctx);
	}
	
	@Override
	public synchronized void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Sombebody connected to me!");
		//super.channelActive(ctx);
		ConnectedPlayer<ChannelHandlerContext> player = new ConnectedPlayer<ChannelHandlerContext>(ctx);
		player.setId((int)(Math.random()*Integer.MAX_VALUE));
		players.add(ctx, player);
		
		//let the FSM know about the new player
		EntityConnected ec = new EntityConnected();
		ec.setEntity(player);
		fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, ec);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object objMsg) {
		//super.channelRead(ctx, objMsg);
		if (objMsg instanceof Message) {
			Message input = (Message) objMsg;
			ConnectedPlayer<ChannelHandlerContext> player = players.getPlayerByContext(ctx);
			if (player != null) {
				NetworkInput<ConnectedPlayer<ChannelHandlerContext>, Message> message = 
						new NetworkInput<ConnectedPlayer<ChannelHandlerContext>, Message>(player, input);
				fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, message);
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		removeConnectedPlayer(ctx);
	}
	
	public void writeToConnectedPlayers(List<NetworkOutput<ConnectedPlayer<ChannelHandlerContext>, Message>> commands) {
		for(NetworkOutput<ConnectedPlayer<ChannelHandlerContext>, Message> command:commands) {
			command.getDestination().getConnection().write(command.getRawMessage());
		}
		for(NetworkOutput<ConnectedPlayer<ChannelHandlerContext>, Message> command:commands) {
			command.getDestination().getConnection().flush();
		}
	}

	/*
	@Override
	public void sendMessage(Entity entity, Object out) {
		ChannelHandlerContext chc = players.getContextByPlayerId(entity.getId());
		if (chc != null) {
			chc.writeAndFlush(out);
		}
	}

	@Override
	public void disconnect(Entity entity) {
		ChannelHandlerContext chc = players.getContextByPlayerId(entity.getId());
		if (chc != null) {
			chc.disconnect();
		}
	}
	*/
}
