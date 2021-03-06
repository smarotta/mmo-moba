// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: positions.proto
// </auto-generated>
#pragma warning disable 1591, 0612, 3021
#region Designer generated code

using pb = global::Google.Protobuf;
using pbc = global::Google.Protobuf.Collections;
using pbr = global::Google.Protobuf.Reflection;
using scg = global::System.Collections.Generic;
namespace Messages {

  /// <summary>Holder for reflection information generated from positions.proto</summary>
  public static partial class PositionsReflection {

    #region Descriptor
    /// <summary>File descriptor for positions.proto</summary>
    public static pbr::FileDescriptor Descriptor {
      get { return descriptor; }
    }
    private static pbr::FileDescriptor descriptor;

    static PositionsReflection() {
      byte[] descriptorData = global::System.Convert.FromBase64String(
          string.Concat(
            "Cg9wb3NpdGlvbnMucHJvdG8SCG1lc3NhZ2VzIjoKCFBvc2l0aW9uEgkKAXgY",
            "AyABKBESCQoBeRgEIAEoERIJCgF6GAUgASgREg0KBWFuZ2xlGAYgASgFQjIK",
            "IGNvbS5zbS5tbW8ubW9iYS5uZXR3b3JrLnByb3RvYnVmQg5Qb3NpdGlvblBy",
            "b3Rvc2IGcHJvdG8z"));
      descriptor = pbr::FileDescriptor.FromGeneratedCode(descriptorData,
          new pbr::FileDescriptor[] { },
          new pbr::GeneratedClrTypeInfo(null, new pbr::GeneratedClrTypeInfo[] {
            new pbr::GeneratedClrTypeInfo(typeof(global::Messages.Position), global::Messages.Position.Parser, new[]{ "X", "Y", "Z", "Angle" }, null, null, null)
          }));
    }
    #endregion

  }
  #region Messages
  public sealed partial class Position : pb::IMessage<Position> {
    private static readonly pb::MessageParser<Position> _parser = new pb::MessageParser<Position>(() => new Position());
    private pb::UnknownFieldSet _unknownFields;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pb::MessageParser<Position> Parser { get { return _parser; } }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public static pbr::MessageDescriptor Descriptor {
      get { return global::Messages.PositionsReflection.Descriptor.MessageTypes[0]; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    pbr::MessageDescriptor pb::IMessage.Descriptor {
      get { return Descriptor; }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Position() {
      OnConstruction();
    }

    partial void OnConstruction();

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Position(Position other) : this() {
      x_ = other.x_;
      y_ = other.y_;
      z_ = other.z_;
      angle_ = other.angle_;
      _unknownFields = pb::UnknownFieldSet.Clone(other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public Position Clone() {
      return new Position(this);
    }

    /// <summary>Field number for the "x" field.</summary>
    public const int XFieldNumber = 3;
    private int x_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int X {
      get { return x_; }
      set {
        x_ = value;
      }
    }

    /// <summary>Field number for the "y" field.</summary>
    public const int YFieldNumber = 4;
    private int y_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Y {
      get { return y_; }
      set {
        y_ = value;
      }
    }

    /// <summary>Field number for the "z" field.</summary>
    public const int ZFieldNumber = 5;
    private int z_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Z {
      get { return z_; }
      set {
        z_ = value;
      }
    }

    /// <summary>Field number for the "angle" field.</summary>
    public const int AngleFieldNumber = 6;
    private int angle_;
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int Angle {
      get { return angle_; }
      set {
        angle_ = value;
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override bool Equals(object other) {
      return Equals(other as Position);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public bool Equals(Position other) {
      if (ReferenceEquals(other, null)) {
        return false;
      }
      if (ReferenceEquals(other, this)) {
        return true;
      }
      if (X != other.X) return false;
      if (Y != other.Y) return false;
      if (Z != other.Z) return false;
      if (Angle != other.Angle) return false;
      return Equals(_unknownFields, other._unknownFields);
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public override int GetHashCode() {
      int hash = 1;
      if (X != 0) hash ^= X.GetHashCode();
      if (Y != 0) hash ^= Y.GetHashCode();
      if (Z != 0) hash ^= Z.GetHashCode();
      if (Angle != 0) hash ^= Angle.GetHashCode();
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
      if (X != 0) {
        output.WriteRawTag(24);
        output.WriteSInt32(X);
      }
      if (Y != 0) {
        output.WriteRawTag(32);
        output.WriteSInt32(Y);
      }
      if (Z != 0) {
        output.WriteRawTag(40);
        output.WriteSInt32(Z);
      }
      if (Angle != 0) {
        output.WriteRawTag(48);
        output.WriteInt32(Angle);
      }
      if (_unknownFields != null) {
        _unknownFields.WriteTo(output);
      }
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public int CalculateSize() {
      int size = 0;
      if (X != 0) {
        size += 1 + pb::CodedOutputStream.ComputeSInt32Size(X);
      }
      if (Y != 0) {
        size += 1 + pb::CodedOutputStream.ComputeSInt32Size(Y);
      }
      if (Z != 0) {
        size += 1 + pb::CodedOutputStream.ComputeSInt32Size(Z);
      }
      if (Angle != 0) {
        size += 1 + pb::CodedOutputStream.ComputeInt32Size(Angle);
      }
      if (_unknownFields != null) {
        size += _unknownFields.CalculateSize();
      }
      return size;
    }

    [global::System.Diagnostics.DebuggerNonUserCodeAttribute]
    public void MergeFrom(Position other) {
      if (other == null) {
        return;
      }
      if (other.X != 0) {
        X = other.X;
      }
      if (other.Y != 0) {
        Y = other.Y;
      }
      if (other.Z != 0) {
        Z = other.Z;
      }
      if (other.Angle != 0) {
        Angle = other.Angle;
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
          case 24: {
            X = input.ReadSInt32();
            break;
          }
          case 32: {
            Y = input.ReadSInt32();
            break;
          }
          case 40: {
            Z = input.ReadSInt32();
            break;
          }
          case 48: {
            Angle = input.ReadInt32();
            break;
          }
        }
      }
    }

  }

  #endregion

}

#endregion Designer generated code
