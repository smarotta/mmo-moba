// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: entities.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Messages {

  /// <summary>Holder for reflection information generated from entities.proto</summary>
  public static partial class EntitiesReflection {

    #region Descriptor
    /// <summary>File descriptor for entities.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static EntitiesReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "Cg5lbnRpdGllcy5wcm90bxIIbWVzc2FnZXMaD3Bvc2l0aW9ucy5wcm90bxoL",
            "aXRlbXMucHJvdG8igAEKBkVudGl0eRIMCgR0eXBlGAEgASgFEgwKBHJhY2UY",
            "AiABKAUSCgoCaHAYAyABKAUSDAoEbWFuYRgEIAEoBRIMCgRuYW1lGAUgASgJ",
            "Eg0KBWxldmVsGAYgASgFEiMKBWl0ZW1zGAcgAygLMhQubWVzc2FnZXMuRW50",
            "aXR5SXRlbSI4CgpFbnRpdHlJdGVtEgwKBHNsb3QYASABKAUSHAoEaXRlbRgC",
            "IAEoCzIOLm1lc3NhZ2VzLkl0ZW0iYwoNRW50aXR5U3Bhd25lZBIKCgJpZBgB",
            "IAEoBRIkCghwb3NpdGlvbhgCIAEoCzISLm1lc3NhZ2VzLlBvc2l0aW9uEiAK",
            "BmVudGl0eRgDIAEoCzIQLm1lc3NhZ2VzLkVudGl0eSIdCg9FbnRpdHlEZXN0",
            "cm95ZWQSCgoCaWQYASABKAUidQoLRW50aXR5TW92ZWQSCgoCaWQYASABKAUS",
            "DQoFZGVsYXkYAiABKAUSIgoGc291cmNlGAMgASgLMhIubWVzc2FnZXMuUG9z",
            "aXRpb24SJwoLZGVzdGluYXRpb24YBCABKAsyEi5tZXNzYWdlcy5Qb3NpdGlv",
            "bkIwCiBjb20uc20ubW1vLm1vYmEubmV0d29yay5wcm90b2J1ZkIMRW50aXR5",
            "UHJvdG9zYgZwcm90bzM="));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { global::Messages.PositionsReflection.Descriptor, global::Messages.ItemsReflection.Descriptor, },
          new pbr::GeneratedClrTypeInfo(null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.Entity), global::Messages.Entity.Parser, new[]{ "Type", "Race", "Hp", "Mana", "Name", "Level", "Items" }, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.EntityItem), global::Messages.EntityItem.Parser, new[]{ "Slot", "Item" }, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.EntitySpawned), global::Messages.EntitySpawned.Parser, new[]{ "Id", "Position", "Entity" }, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.EntityDestroyed), global::Messages.EntityDestroyed.Parser, new[]{ "Id" }, null, null, null),
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.EntityMoved), global::Messages.EntityMoved.Parser, new[]{ "Id", "Delay", "Source", "Destination" }, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  public sealed partial class Entity : pb::IMessage<Entity> {
    private static readonly pb::MessageParser<Entity> _parser = new pb::MessageParser<Entity>(() => new Entity());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<Entity> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.EntitiesReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Entity() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Entity(Entity other) : this() {
      type_ = other.type_;
      race_ = other.race_;
      hp_ = other.hp_;
      mana_ = other.mana_;
      name_ = other.name_;
      level_ = other.level_;
      items_ = other.items_.Clone();
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Entity Clone() {
      return new Entity(this);
    }

    /// <summary>Field number for the "type" field.</summary>
    public const int TypeFieldNumber = 1;
    private int type_;
    /// <summary>
    ///mob, player, npc
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Type {
      get { return type_; }
      set {
        type_ = value;
      }
    }

    /// <summary>Field number for the "race" field.</summary>
    public const int RaceFieldNumber = 2;
    private int race_;
    /// <summary>
    ///mob: horse, dragon, plant; player: elf, human, orc; npc: vendor, blacksmith, etc
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Race {
      get { return race_; }
      set {
        race_ = value;
      }
    }

    /// <summary>Field number for the "hp" field.</summary>
    public const int HpFieldNumber = 3;
    private int hp_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Hp {
      get { return hp_; }
      set {
        hp_ = value;
      }
    }

    /// <summary>Field number for the "mana" field.</summary>
    public const int ManaFieldNumber = 4;
    private int mana_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Mana {
      get { return mana_; }
      set {
        mana_ = value;
      }
    }

    /// <summary>Field number for the "name" field.</summary>
    public const int NameFieldNumber = 5;
    private string name_ = "";
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public string Name {
      get { return name_; }
      set {
        name_ = pb::ProtoPreconditions.CheckNotNull(value, "value");
      }
    }

    /// <summary>Field number for the "level" field.</summary>
    public const int LevelFieldNumber = 6;
    private int level_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Level {
      get { return level_; }
      set {
        level_ = value;
      }
    }

    /// <summary>Field number for the "items" field.</summary>
    public const int ItemsFieldNumber = 7;
    private static readonly pb::FieldCodec<global::Messages.EntityItem> _repeated_items_codec
        = pb::FieldCodec.ForMessage(58, global::Messages.EntityItem.Parser);
    private readonly pbc::RepeatedField<global::Messages.EntityItem> items_ = new pbc::RepeatedField<global::Messages.EntityItem>();
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public pbc::RepeatedField<global::Messages.EntityItem> Items {
      get { return items_; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as Entity);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(Entity other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Type != other.Type) return false;
      if (Race != other.Race) return false;
      if (Hp != other.Hp) return false;
      if (Mana != other.Mana) return false;
      if (Name != other.Name) return false;
      if (Level != other.Level) return false;
      if(!items_.Equals(other.items_)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Type != 0) hash ^= Type.GetHashCode();
      if (Race != 0) hash ^= Race.GetHashCode();
      if (Hp != 0) hash ^= Hp.GetHashCode();
      if (Mana != 0) hash ^= Mana.GetHashCode();
      if (Name.Length != 0) hash ^= Name.GetHashCode();
      if (Level != 0) hash ^= Level.GetHashCode();
      hash ^= items_.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Type != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Type);
      }
      if (Race != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Race);
      }
      if (Hp != 0) {
        output.WriteRawTag(24);
        output.WriteInt32(Hp);
      }
      if (Mana != 0) {
        output.WriteRawTag(32);
        output.WriteInt32(Mana);
      }
      if (Name.Length != 0) {
        output.WriteRawTag(42);
        output.WriteString(Name);
      }
      if (Level != 0) {
        output.WriteRawTag(48);
        output.WriteInt32(Level);
      }
      items_.WriteTo(output, _repeated_items_codec);
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Type != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Type);
      }
      if (Race != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Race);
      }
      if (Hp != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Hp);
      }
      if (Mana != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Mana);
      }
      if (Name.Length != 0) {
        size += 1 + pb::CodedOutputStream.ComputeStringSize(Name);
      }
      if (Level != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Level);
      }
      size += items_.CalculateSize(_repeated_items_codec);
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(Entity other) {
      if (other == null) {
        return;
      }
      if (other.Type != 0) {
        Type = other.Type;
      }
      if (other.Race != 0) {
        Race = other.Race;
      }
      if (other.Hp != 0) {
        Hp = other.Hp;
      }
      if (other.Mana != 0) {
        Mana = other.Mana;
      }
      if (other.Name.Length != 0) {
        Name = other.Name;
      }
      if (other.Level != 0) {
        Level = other.Level;
      }
      items_.Add(other.items_);
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Type = input.ReadInt32();
            break;
          }
          case 16: {
            Race = input.ReadInt32();
            break;
          }
          case 24: {
            Hp = input.ReadInt32();
            break;
          }
          case 32: {
            Mana = input.ReadInt32();
            break;
          }
          case 42: {
            Name = input.ReadString();
            break;
          }
          case 48: {
            Level = input.ReadInt32();
            break;
          }
          case 58: {
            items_.AddEntriesFrom(input, _repeated_items_codec);
            break;
          }
        }
      }
    }

  }

  public sealed partial class EntityItem : pb::IMessage<EntityItem> {
    private static readonly pb::MessageParser<EntityItem> _parser = new pb::MessageParser<EntityItem>(() => new EntityItem());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<EntityItem> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.EntitiesReflection.Descriptor.MessageTypes[1]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityItem() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityItem(EntityItem other) : this() {
      slot_ = other.slot_;
      item_ = other.item_ != null ? other.item_.Clone() : null;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityItem Clone() {
      return new EntityItem(this);
    }

    /// <summary>Field number for the "slot" field.</summary>
    public const int SlotFieldNumber = 1;
    private int slot_;
    /// <summary>
    ///hand, head, feet, inventory, etc
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Slot {
      get { return slot_; }
      set {
        slot_ = value;
      }
    }

    /// <summary>Field number for the "item" field.</summary>
    public const int ItemFieldNumber = 2;
    private global::Messages.Item item_;
    /// <summary>
    ///katana, shotgun, pants
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Messages.Item Item {
      get { return item_; }
      set {
        item_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as EntityItem);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(EntityItem other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Slot != other.Slot) return false;
      if (!object.Equals(Item, other.Item)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Slot != 0) hash ^= Slot.GetHashCode();
      if (item_ != null) hash ^= Item.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Slot != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Slot);
      }
      if (item_ != null) {
        output.WriteRawTag(18);
        output.WriteMessage(Item);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Slot != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Slot);
      }
      if (item_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Item);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(EntityItem other) {
      if (other == null) {
        return;
      }
      if (other.Slot != 0) {
        Slot = other.Slot;
      }
      if (other.item_ != null) {
        if (item_ == null) {
          item_ = new global::Messages.Item();
        }
        Item.MergeFrom(other.Item);
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Slot = input.ReadInt32();
            break;
          }
          case 18: {
            if (item_ == null) {
              item_ = new global::Messages.Item();
            }
            input.ReadMessage(item_);
            break;
          }
        }
      }
    }

  }

  public sealed partial class EntitySpawned : pb::IMessage<EntitySpawned> {
    private static readonly pb::MessageParser<EntitySpawned> _parser = new pb::MessageParser<EntitySpawned>(() => new EntitySpawned());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<EntitySpawned> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.EntitiesReflection.Descriptor.MessageTypes[2]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntitySpawned() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntitySpawned(EntitySpawned other) : this() {
      id_ = other.id_;
      position_ = other.position_ != null ? other.position_.Clone() : null;
      entity_ = other.entity_ != null ? other.entity_.Clone() : null;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntitySpawned Clone() {
      return new EntitySpawned(this);
    }

    /// <summary>Field number for the "id" field.</summary>
    public const int IdFieldNumber = 1;
    private int id_;
    /// <summary>
    ///unique id of this entity in the current world connected (room, map section, lobby)
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Id {
      get { return id_; }
      set {
        id_ = value;
      }
    }

    /// <summary>Field number for the "position" field.</summary>
    public const int PositionFieldNumber = 2;
    private global::Messages.Position position_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Messages.Position Position {
      get { return position_; }
      set {
        position_ = value;
      }
    }

    /// <summary>Field number for the "entity" field.</summary>
    public const int EntityFieldNumber = 3;
    private global::Messages.Entity entity_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Messages.Entity Entity {
      get { return entity_; }
      set {
        entity_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as EntitySpawned);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(EntitySpawned other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Id != other.Id) return false;
      if (!object.Equals(Position, other.Position)) return false;
      if (!object.Equals(Entity, other.Entity)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Id != 0) hash ^= Id.GetHashCode();
      if (position_ != null) hash ^= Position.GetHashCode();
      if (entity_ != null) hash ^= Entity.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Id != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Id);
      }
      if (position_ != null) {
        output.WriteRawTag(18);
        output.WriteMessage(Position);
      }
      if (entity_ != null) {
        output.WriteRawTag(26);
        output.WriteMessage(Entity);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Id != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Id);
      }
      if (position_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Position);
      }
      if (entity_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Entity);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(EntitySpawned other) {
      if (other == null) {
        return;
      }
      if (other.Id != 0) {
        Id = other.Id;
      }
      if (other.position_ != null) {
        if (position_ == null) {
          position_ = new global::Messages.Position();
        }
        Position.MergeFrom(other.Position);
      }
      if (other.entity_ != null) {
        if (entity_ == null) {
          entity_ = new global::Messages.Entity();
        }
        Entity.MergeFrom(other.Entity);
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Id = input.ReadInt32();
            break;
          }
          case 18: {
            if (position_ == null) {
              position_ = new global::Messages.Position();
            }
            input.ReadMessage(position_);
            break;
          }
          case 26: {
            if (entity_ == null) {
              entity_ = new global::Messages.Entity();
            }
            input.ReadMessage(entity_);
            break;
          }
        }
      }
    }

  }

  public sealed partial class EntityDestroyed : pb::IMessage<EntityDestroyed> {
    private static readonly pb::MessageParser<EntityDestroyed> _parser = new pb::MessageParser<EntityDestroyed>(() => new EntityDestroyed());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<EntityDestroyed> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.EntitiesReflection.Descriptor.MessageTypes[3]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityDestroyed() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityDestroyed(EntityDestroyed other) : this() {
      id_ = other.id_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityDestroyed Clone() {
      return new EntityDestroyed(this);
    }

    /// <summary>Field number for the "id" field.</summary>
    public const int IdFieldNumber = 1;
    private int id_;
    /// <summary>
    ///unique id of this entity in the current world connected (room, map section, lobby)
    /// </summary>
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Id {
      get { return id_; }
      set {
        id_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as EntityDestroyed);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(EntityDestroyed other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Id != other.Id) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Id != 0) hash ^= Id.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Id != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Id);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Id != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Id);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(EntityDestroyed other) {
      if (other == null) {
        return;
      }
      if (other.Id != 0) {
        Id = other.Id;
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Id = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  public sealed partial class EntityMoved : pb::IMessage<EntityMoved> {
    private static readonly pb::MessageParser<EntityMoved> _parser = new pb::MessageParser<EntityMoved>(() => new EntityMoved());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<EntityMoved> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.EntitiesReflection.Descriptor.MessageTypes[4]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityMoved() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityMoved(EntityMoved other) : this() {
      id_ = other.id_;
      delay_ = other.delay_;
      source_ = other.source_ != null ? other.source_.Clone() : null;
      destination_ = other.destination_ != null ? other.destination_.Clone() : null;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public EntityMoved Clone() {
      return new EntityMoved(this);
    }

    /// <summary>Field number for the "id" field.</summary>
    public const int IdFieldNumber = 1;
    private int id_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Id {
      get { return id_; }
      set {
        id_ = value;
      }
    }

    /// <summary>Field number for the "delay" field.</summary>
    public const int DelayFieldNumber = 2;
    private int delay_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Delay {
      get { return delay_; }
      set {
        delay_ = value;
      }
    }

    /// <summary>Field number for the "source" field.</summary>
    public const int SourceFieldNumber = 3;
    private global::Messages.Position source_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Messages.Position Source {
      get { return source_; }
      set {
        source_ = value;
      }
    }

    /// <summary>Field number for the "destination" field.</summary>
    public const int DestinationFieldNumber = 4;
    private global::Messages.Position destination_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public global::Messages.Position Destination {
      get { return destination_; }
      set {
        destination_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as EntityMoved);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(EntityMoved other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (Id != other.Id) return false;
      if (Delay != other.Delay) return false;
      if (!object.Equals(Source, other.Source)) return false;
      if (!object.Equals(Destination, other.Destination)) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (Id != 0) hash ^= Id.GetHashCode();
      if (Delay != 0) hash ^= Delay.GetHashCode();
      if (source_ != null) hash ^= Source.GetHashCode();
      if (destination_ != null) hash ^= Destination.GetHashCode();
      if (_unknownFields != null) {
        hash ^= _unknownFields.GetHashCode();
      }
      return hash;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override string ToString() {
      return pb::JsonFormatter.ToDiagnosticString(this);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void WriteTo(pb::CodedOutputStream output) {
      if (Id != 0) {
        output.WriteRawTag(8);
        output.WriteInt32(Id);
      }
      if (Delay != 0) {
        output.WriteRawTag(16);
        output.WriteInt32(Delay);
      }
      if (source_ != null) {
        output.WriteRawTag(26);
        output.WriteMessage(Source);
      }
      if (destination_ != null) {
        output.WriteRawTag(34);
        output.WriteMessage(Destination);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (Id != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Id);
      }
      if (Delay != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Delay);
      }
      if (source_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Source);
      }
      if (destination_ != null) {
        size += 1 + pb::CodedOutputStream.ComputeMessageSize(Destination);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(EntityMoved other) {
      if (other == null) {
        return;
      }
      if (other.Id != 0) {
        Id = other.Id;
      }
      if (other.Delay != 0) {
        Delay = other.Delay;
      }
      if (other.source_ != null) {
        if (source_ == null) {
          source_ = new global::Messages.Position();
        }
        Source.MergeFrom(other.Source);
      }
      if (other.destination_ != null) {
        if (destination_ == null) {
          destination_ = new global::Messages.Position();
        }
        Destination.MergeFrom(other.Destination);
      }
      _unknownFields = pb::UnknownFieldSet.MergeFrom(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(pb::CodedInputStream input) {
      uint tag;
      while ((tag = input.ReadTag()) != 0) {
        switch(tag) {
          default:
            _unknownFields = pb::UnknownFieldSet.MergeFieldFrom(_unknownFields, input);
            break;
          case 8: {
            Id = input.ReadInt32();
            break;
          }
          case 16: {
            Delay = input.ReadInt32();
            break;
          }
          case 26: {
            if (source_ == null) {
              source_ = new global::Messages.Position();
            }
            input.ReadMessage(source_);
            break;
          }
          case 34: {
            if (destination_ == null) {
              destination_ = new global::Messages.Position();
            }
            input.ReadMessage(destination_);
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code
