package com.sm.mmo.moba.gameserver;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.domain.message.EntityConnected;
import com.sm.mmo.moba.domain.message.EntityDisconnected;
import com.sm.mmo.moba.domain.message.EntityPosition;
import com.sm.mmo.moba.domain.message.EntityUpdate;
import com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import com.sm.mmo.moba.domain.message.network.NetworkInput;
import com.sm.mmo.moba.domain.message.network.NetworkOutput;
import com.sm.mmo.moba.gameserver.domain.ConnectedPlayer;
import com.sm.mmo.moba.gameserver.domain.ConnectedPlayersBag;
import com.sm.mmo.moba.qnfsm.FSMFeeder;
import com.sm.mmo.moba.qnfsm.FSMFeederController;
import com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import com.sm.mmo.moba.qnfsm.feeder.MovementFSMFeeder;
import com.sm.mmo.moba.qnfsm.feeder.GameLogicFSMFeeder;
import com.sm.mmo.moba.qnfsm.feeder.NetworkFSMFeeder;
import com.sm.mmo.moba.qnfsm.fsm.MovementFSM;
import com.sm.mmo.moba.qnfsm.fsm.GameLogicFSM;
import com.sm.mmo.moba.qnfsm.fsm.NetworkFSM;
import com.sm.mmo.moba.qnfsm.fsm.NetworkFSM.NetworkHandler;

@Sharable
public class GameWorldServerHandler extends ChannelInboundHandlerAdapter implements NetworkHandler {
	
	private final ConnectedPlayersBag players = new ConnectedPlayersBag();
	private GameWorldServerBroadcaster broadcaster;
	private FSMFeederController fsmController;
	
	public GameWorldServerHandler() {
		super();
		this.broadcaster = new GameWorldServerBroadcaster(this);
		new Thread(this.broadcaster).start();
		initializeFSM();
	}
	
	private void initializeFSM() {
		fsmController = new FSMFeederController();
		MovementFSM emFSM = new MovementFSM(fsmController);
		GameLogicFSM gmFSM = new GameLogicFSM(fsmController);
		NetworkFSM networkFSM = new NetworkFSM(fsmController, this);
		
		fsmController.register(new MovementFSMFeeder(UUID.randomUUID(), emFSM, fsmController));
		fsmController.register(new GameLogicFSMFeeder(UUID.randomUUID(), gmFSM, fsmController));
		fsmController.register(new NetworkFSMFeeder(UUID.randomUUID(), networkFSM, fsmController));
		
	}
	
	private void removeConnectedPlayer(ChannelHandlerContext ctx) {
		ConnectedPlayer player = players.getPlayerByContext(ctx);
		if (player != null) {
			players.remove(ctx);
			
			//let the FSM know about the disconnected player
			EntityDisconnected ed = new EntityDisconnected();
			ed.setEntity(player);
			fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, ed);
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
		ConnectedPlayer player = new ConnectedPlayer(ctx);
		player.setId(UUID.randomUUID());
		players.add(ctx, player);
		
		//let the FSM know about the new player
		EntityConnected ec = new EntityConnected();
		ec.setEntity(player);
		fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, ec);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object objMsg) {
		//super.channelRead(ctx, objMsg);
		if (objMsg instanceof NetworkInput) {
			NetworkInput input = (NetworkInput) objMsg;
			ConnectedPlayer player = players.getPlayerByContext(ctx);
			if (player != null) {
				input.setSource(player);
				fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, input);
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		removeConnectedPlayer(ctx);
	}
	
	public void writeToConnectedPlayers(Set<ConnectedPlayer> players, List<NetworkOutput> commands) {
		for(ConnectedPlayer player:players) {
			for(NetworkOutput command:commands) {
				player.getContext().write(command);
			}
			player.getContext().flush();
		}
	}

	@Override
	public void sendMessage(Entity entity, NetworkOutput out) {
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
}
