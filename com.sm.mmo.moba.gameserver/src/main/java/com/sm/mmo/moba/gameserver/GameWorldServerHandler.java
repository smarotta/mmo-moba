package com.sm.mmo.moba.gameserver;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.com.sm.mmo.moba.domain.Entity;
import org.com.sm.mmo.moba.domain.message.EntityConnected;
import org.com.sm.mmo.moba.domain.message.EntityPosition;
import org.com.sm.mmo.moba.domain.message.EntityUpdate;
import org.com.sm.mmo.moba.domain.message.network.EntityConnectedNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.EntityPositionNetworkOutput;
import org.com.sm.mmo.moba.domain.message.network.NetworkInput;
import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder;
import org.com.sm.mmo.moba.qnfsm.FSMFeederController;
import org.com.sm.mmo.moba.qnfsm.FSMFeeder.Type;
import org.com.sm.mmo.moba.qnfsm.feeder.EntityMovementFSMFeeder;
import org.com.sm.mmo.moba.qnfsm.feeder.GameLogicFSMFeeder;
import org.com.sm.mmo.moba.qnfsm.feeder.NetworkFSMFeeder;
import org.com.sm.mmo.moba.qnfsm.fsm.EntityMovementFSM;
import org.com.sm.mmo.moba.qnfsm.fsm.GameLogicFSM;
import org.com.sm.mmo.moba.qnfsm.fsm.NetworkFSM;
import org.com.sm.mmo.moba.qnfsm.fsm.NetworkFSM.NetworkHandler;

import com.sm.mmo.moba.gameserver.domain.ConnectedPlayer;

@Sharable
public class GameWorldServerHandler extends ChannelInboundHandlerAdapter implements NetworkHandler {
	
	private static final Map<ChannelHandlerContext, ConnectedPlayer> contextClientMap = new ConcurrentHashMap<ChannelHandlerContext, ConnectedPlayer>();
	private static final Map<UUID, ConnectedPlayer> connectedPlayerIdMap = new ConcurrentHashMap<UUID, ConnectedPlayer>();
	private static final Map<UUID, ChannelHandlerContext> connectedPlayerContextMap = new ConcurrentHashMap<UUID, ChannelHandlerContext>();
	
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
		EntityMovementFSM emFSM = new EntityMovementFSM(fsmController);
		GameLogicFSM gmFSM = new GameLogicFSM(fsmController);
		NetworkFSM networkFSM = new NetworkFSM(fsmController, this);
		
		fsmController.register(new EntityMovementFSMFeeder(UUID.randomUUID(), emFSM, fsmController));
		fsmController.register(new GameLogicFSMFeeder(UUID.randomUUID(), gmFSM, fsmController));
		fsmController.register(new NetworkFSMFeeder(UUID.randomUUID(), networkFSM, fsmController));
		
	}
	
	private void removeConnectedPlayer(ChannelHandlerContext ctx) {
		ConnectedPlayer player = contextClientMap.get(ctx);
		if (player != null) {
			contextClientMap.remove(ctx);
			connectedPlayerIdMap.remove(player.getId());
			connectedPlayerContextMap.remove(player.getId());
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		removeConnectedPlayer(ctx);
		//call FSM
	}
	
	@Override
	public synchronized void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Sombebody connected to me!");
		super.channelActive(ctx);
		ConnectedPlayer player = new ConnectedPlayer(ctx);
		player.setId(UUID.randomUUID());
		contextClientMap.put(ctx, player);
		connectedPlayerIdMap.put(player.getId(), player);
		connectedPlayerContextMap.put(player.getId(), ctx);
		
		for(ConnectedPlayer target:connectedPlayerIdMap.values()) {
			EntityConnectedNetworkOutput ecno = new EntityConnectedNetworkOutput(new EntityConnected());
			ecno.getEntityConnected().setEntity(player);
			ecno.setDestination(target);
			fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, ecno);
		}
		/*
		for(ConnectedPlayer target:connectedPlayerIdMap.values()) {
			if (target != player){
				EntityPositionNetworkOutput epno = new EntityPositionNetworkOutput(new EntityPosition());
				epno.getEntityPosition().setEntity(target);
				epno.getEntityPosition().setAngle(target.getAngle());
				epno.getEntityPosition().setX(target.getX());
				epno.getEntityPosition().setY(target.getY());
				epno.setDestination(player);
				fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, epno);
			}
		}
		*/
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object objMsg) {
		System.out.println("Recieved msg!");
		if (objMsg instanceof NetworkInput) {
			System.out.println("Recieved NetworkInput");
			NetworkInput input = (NetworkInput) objMsg;
			ConnectedPlayer player = contextClientMap.get(ctx);
			System.out.println("Connected player is:" + player);
			if (player != null) {
				input.setSource(player);
				fsmController.sendMessage(FSMFeeder.Type.FSM_NETWORK, input);
			}
			
		}
		/*
		if (objMsg instanceof SketchCommandInput) {
			ConnectedPlayer player = contextClientMap.get(ctx);
			if (player != null) {
				Set<ConnectedPlayer> players = new HashSet<ConnectedPlayer>();
				players.addAll(contextClientMap.values());
				players.remove(player);
								
				SketchCommandInput in = (SketchCommandInput) objMsg;
				SketchCommandOutput out = new SketchCommandOutput();
				out.setPlayerId(player.getId());
				out.setSketch(in.getSketch());
				broadcaster.addCommandToQueue(players, out);
			} else {
				System.err.println("command recieved on an unknown player, ignored!");
			}
		}
		*/
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		removeConnectedPlayer(ctx);
		//call FSM
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
		//System.out.println("FSM asked to send something! " + out.getType());
		ChannelHandlerContext chc = connectedPlayerContextMap.get(entity.getId());
		if (chc != null) {
			chc.writeAndFlush(out);
		}
	}

	@Override
	public void disconnect(Entity entity) {
		ChannelHandlerContext chc = connectedPlayerContextMap.get(entity.getId());
		if (chc != null) {
			chc.disconnect();
		}
	}
}
