package com.sm.mmo.moba.gameserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.com.sm.mmo.moba.domain.message.network.NetworkOutput;

import com.sm.mmo.moba.gameserver.domain.ConnectedPlayer;

public class GameWorldServerBroadcaster implements Runnable {

	private static class EnqueuedCommand {
		Set<ConnectedPlayer> target;
		List<NetworkOutput> commands;
		EnqueuedCommand(Set<ConnectedPlayer> target, List<NetworkOutput> commands) {
			this.target = target;
			this.commands = commands;
		}
		EnqueuedCommand(ConnectedPlayer target, List<NetworkOutput> commands) {
			this.target = new HashSet<ConnectedPlayer>();
			this.target.add(target);
			this.commands = commands;
		}
		EnqueuedCommand(Set<ConnectedPlayer> target, NetworkOutput command) {
			this.target = target;
			this.commands = new ArrayList<NetworkOutput>();
			this.commands.add(command);
		}
		EnqueuedCommand(ConnectedPlayer target, NetworkOutput command) {
			this.target = new HashSet<ConnectedPlayer>();
			this.target.add(target);
			this.commands = new ArrayList<NetworkOutput>();
			this.commands.add(command);
		}
	}
	
	private GameWorldServerHandler serverHandler;
	
	private BlockingQueue<EnqueuedCommand> queue = new LinkedBlockingQueue<GameWorldServerBroadcaster.EnqueuedCommand>();
	
	public GameWorldServerBroadcaster(GameWorldServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
	
	public void run() {
		while(true) {
			EnqueuedCommand command = null;
			
			try {
				command = queue.take();
			} catch (InterruptedException e) {
			}
			
			if (command != null) {
				serverHandler.writeToConnectedPlayers(command.target, command.commands);
			}
		}
	}

	public void addCommandToQueue(Set<ConnectedPlayer> target, List<NetworkOutput> command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(ConnectedPlayer target, List<NetworkOutput> command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(Set<ConnectedPlayer> target, NetworkOutput command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(ConnectedPlayer target, NetworkOutput command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
}
