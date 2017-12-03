MOBA Server
===================


Network Protocol
-------------

#### Server -> Client

**Entity Position Setup (currently sent to players)**

Size Descriptor | Size Value | Message ID | Entity UUID                                     | Map ID | Facing Direction  | X             | Y
--------------- | ---------- | ---------- | ----------------------------------------------- | ------ | ----------------- | ------------- | ------------- 
C1              | XX XX      | A3         | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | ID ID  | AG AG             | XX XX XX XX   | YY YY YY YY   

**Entity movement** 

Size Descriptor | Size Value | Message ID | Entity UUID                                     | Facing Direction  | Moving from X | Moving from Y | Moving to X | Moving to Y
--------------- | ---------- | ---------- | ----------------------------------------------- | ----------------- | ------------- | ------------- | ----------- | -----------
C1              | XX XX      | A1         | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | AG AG             | XX XX XX XX   | YY YY YY YY   | DX DX DX DX | DY DY DY DY

**Entity damage** 

Size Descriptor | Size Value | Message ID | Entity UUID                                     | Damage Type
--------------- | ---------- | ---------- | ----------------------------------------------- | -----------
C1              | XX XX      | B0         | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | DG

After the Damage integer you will get a combination byte to determine the damage type:
b10000000 = health
b01000000 = mana
b00100000 = stamina
b00010000 = N/A (maybe wearable damage: clothing, armor, boots, etc)
b00001000 = N/A (maybe equipable damage: staff, sword, etc)
b00000100 = N/A
b00000010 = N/A
b00000001 = N/A

Each binary mask hit will have it's own preset of packet sizes (always in the order described above from top to bottom)

Damage type is b11100000, meaning the entity got 3 damages, HP, Mana and Stamina. Example:
 
Damage Type | HP Damage   | HP Crit  | Mana Damage | Mana Crit | Stamina Damage | Something else about stamina  
----------- | ----------- | -------- | ----------- | --------- | -------------- | ----------------------------
b11100000   | HD HD HD HD | CR       | MD MD MD MD | CR        | SD SD SD SD    | SE SE SE SE SE SE SE SE

**Entity death** 

Size Descriptor | Size Value | Message ID | Entity UUID                                     | Killer UUID                                     | Items dropped (count)
--------------- | ---------- | ---------- | ----------------------------------------------- | ----------------------------------------------- | ---------------------
C1              | XX XX      | B1         | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | IC         

For each item dropped:
Item Package Size | Position X  | Position Y  | Item details (size vary) 
----------------- | ----------- | ----------- | -------------------------
IS IS             | XX XX XX XX | YY YY YY YY | ID ID ID ID ID ID ID ID         

**Entity meet** 

Entity type describes if its a player, a npc, a monster etc..

Size Descriptor | Size Value | Message ID | Entity UUID                                     | Entity Type 
--------------- | ---------- | ---------- | ----------------------------------------------- | -----------
C1              | XX XX      | A4         | ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID ID | ET         

This will only be present for non monster entities (players, npc sellers, etc)

Entity Name                                                  |
------------------------------------------------------------ |
EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN EN  |


This will be present for all entities (even monsters might be able to wear an item)

Facing Direction | X            | Y           | Race | Pose | Items count
---------------- | ------------ | ----------- | ---- | ---- | -----------
AG AG            | XX XX XX XX  | YY YY YY YY | RC   | PS   | IC
  
Position might be LEFT_HAND, RIGHT_HAND, HELMET, ARMOR, PANTS, GLOVES, BOOTS, WINGS, etc

Item Package Size | Position | Item details (size vary) 
----------------- | -------- | -------------------------
IS IS             | XX       | ID ID ID ID ID ID ID ID         


 
#### Client -> Server


----------

