using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Google.Protobuf;
using Messages;
using System.IO;
using System;

public class Hello : MonoBehaviour {

	// Use this for initialization
	void Start () {

        Entity entity = new Entity() {
            Hp = 1,
            Level = 11,
            Mana = 95,
            Name = "Manolim 1",
            Race = 1,
            Type = 1,
        };

        string output = BitConverter.ToString(entity.ToByteArray());
        Debug.Log("OUTPUT: " + output);

        //MemoryStream ms = new MemoryStream();
        //entity.WriteTo(new CodedOutputStream(ms));
        //Debug.Log(ms.ToString());
    }

    // Update is called once per frame
    void Update () {
		
	}
}
