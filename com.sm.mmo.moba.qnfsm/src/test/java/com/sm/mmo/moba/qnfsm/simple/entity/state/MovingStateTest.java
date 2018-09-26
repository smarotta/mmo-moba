package com.sm.mmo.moba.qnfsm.simple.entity.state;

import org.junit.jupiter.api.Test;

import com.sm.mmo.moba.domain.Entity;
import com.sm.mmo.moba.qnfsm.simple.EventPublisher;
import com.sm.mmo.moba.qnfsm.simple.base.BaseEventPublisher;
import com.sm.mmo.moba.qnfsm.simple.entity.EntityMachine;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementCancelEvent;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementStartEvent;
import com.sm.mmo.moba.qnfsm.simple.entity.event.MovementUpdateEvent;

public class MovingStateTest {


	@Test
	public void test() throws InterruptedException {
		
		double speed = 5.00d;
		
		Entity entity = new Entity() {{
			setX(-10);
			setY(0);
			setZ(10);
		}};
		EventPublisher publisher = new BaseEventPublisher();
		EntityMachine machine = new EntityMachine(publisher, new IdleState() , entity);
		
		publisher.publish(new MovementStartEvent() {{
			setConsumerId(machine.getConsumerId());
			setPublisherId(publisher.getPublisherId());
			setDestX(10);
			setDestY(10);
			setDestZ(10);
			setOriginX(entity.getX());
			setOriginY(entity.getY());
			setOriginZ(entity.getZ());
			setSpeed(speed);
		}});
		
		Thread.sleep(500);
		
		for(int x=0; !(machine.getState() instanceof IdleState); x++) {
			System.out.println("[" + entity.getX() + ", " + entity.getY() + ", " + entity.getZ() + "]");
			Thread.sleep(200);
			publisher.publish(new MovementUpdateEvent(publisher.getPublisherId()));
		}
		
		publisher.publish(new MovementStartEvent() {{
			setConsumerId(machine.getConsumerId());
			setPublisherId(publisher.getPublisherId());
			setDestX(5);
			setDestY(-5);
			setDestZ(0);
			setOriginX(entity.getX());
			setOriginY(entity.getY());
			setOriginZ(entity.getZ());
			setSpeed(speed);
		}});
		
		Thread.sleep(500);
		
		for(int x=0; !(machine.getState() instanceof IdleState); x++) {
			System.out.println("[" + entity.getX() + ", " + entity.getY() + ", " + entity.getZ() + "]");
			Thread.sleep(200);
			publisher.publish(new MovementUpdateEvent(publisher.getPublisherId()));
			
			if (x == 5) {
				publisher.publish(new MovementCancelEvent(publisher.getPublisherId()));
				publisher.publish(new MovementStartEvent() {{
					setConsumerId(machine.getConsumerId());
					setPublisherId(publisher.getPublisherId());
					setDestX(-10);
					setDestY(-10);
					setDestZ(-10);
					setOriginX(entity.getX());
					setOriginY(entity.getY());
					setOriginZ(entity.getZ());
					setSpeed(speed);
				}});
			}
		}
		
		System.out.println("DONE! [" + entity.getX() + ", " + entity.getY() + ", " + entity.getZ() + "]");
	}
	
	
}