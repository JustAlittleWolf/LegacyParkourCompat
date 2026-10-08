# F-S7.4-BOAT-RIDER-ROTATION: boat yaw is conditionally applied to passengers

- Older version A: Minecraft Java 1.20.2
- Newer version B: Minecraft Java 1.20.4
- Mechanic / coverage slice IDs: boat rider rotation; S7.4
- Classification: changed behavior
- Confidence: candidate (the exact 1.20.4 default membership of `minecraft:can_turn_in_boats` is not present in the permitted Java source trees)
- Applicability: unresolved until Player membership in that tag is established
- First changed release: unknown within (1.20.2, 1.20.4]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.20.2/mojmap`; source path `net/minecraft/world/entity/vehicle/Boat.java`; `net.minecraft.world.entity.vehicle.Boat.positionRider(Entity, Entity.MoveFunction)` (`(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V`), lines 700-709; file SHA-256 `8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686`; brace-bounded method SHA-256 `0bc9c83192a16e8424d68c2f209182e9733ecd97223ba491d2c958ed8362a15d`.
- B manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.20.4/mojmap`; source path `net/minecraft/world/entity/vehicle/Boat.java`; same member and descriptor, lines 665-676; file SHA-256 `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`; brace-bounded method SHA-256 `9c1761bd74fe38c0e76ad5f93ee702b7a100080179a1adea19233e82c7b89976`.

```java
// A: after the shared positional writer
$$0.setYRot($$0.getYRot() + this.deltaRotation);
$$0.setYHeadRot($$0.getYHeadRot() + this.deltaRotation);
this.clampRotation($$0);
```

```java
// B: the same rotation writes are guarded by the passenger type tag
if (!$$0.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS)) {
    $$0.setYRot($$0.getYRot() + this.deltaRotation);
    $$0.setYHeadRot($$0.getYHeadRot() + this.deltaRotation);
    this.clampRotation($$0);
}
```

The preceding `super.positionRider($$0, $$1)` call is present on both sides. The shared writer applies the same positional attachment calculation; this delta changes the subsequent rotation writes.

## Source-level difference

In 1.20.2, each Boat rider receives the boat's `deltaRotation` in both body yaw (`YRot`) and head yaw, followed by `clampRotation`. In 1.20.4, those writes occur only when the rider entity type is not in `EntityTypeTags.CAN_TURN_IN_BOATS`. The additional animal passenger rotation branch is inside the same new guard. The direct x/y/z passenger placement call remains the unchanged shared superclass call.

## Reachability and dependencies

Client packet handling can attach the local player from a `ClientboundSetPassengersPacket`: A `net.minecraft.client.multiplayer.ClientPacketListener.handleSetEntityPassengersPacket(ClientboundSetEntityPassengersPacket)` lines 923-950, source SHA-256 `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264`; B same member lines 953-980, source SHA-256 `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f`; its brace-bounded method hash is identical, `3db92e21b347f1b7dd368031ffebc72755cb94757591a41adbc172d68e3ecf5a`. The player then follows `net.minecraft.world.entity.Entity.rideTick()` (A lines 1827-1833, source SHA-256 `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8d8c5d23b01b`; B lines 1828-1834, source SHA-256 `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`), with the same body hash `710a3f9829d5af247362d0fb87a1b87556ac1f78651ff0a73f3a6a833ca7d14e`; that method dispatches to its vehicle's `positionRider`, so Boat's override receives the rider entity. This callback directly writes the player's yaw/head yaw when its condition passes.

The B source declares `EntityTypeTags.CAN_TURN_IN_BOATS`, but the supplied exact-version source roots do not establish the default tag members. Therefore the Java evidence proves the new conditional rotation behavior, but does not prove whether a vanilla Player rider takes the guarded branch. Resolving the candidate requires exact 1.20.4 default tag membership; do not infer it from the tag name or the Java declaration.

## Consequence and uncertainty

Source-proven: B adds a type-tag condition around Boat rider yaw and head-yaw writes; the shared rider-position writer is called before that condition and is unchanged. If a Player is absent from the B tag, those rotation writes remain reachable each Boat rider tick. If Player is present, the writes are skipped. Because player travel reads yaw to orient movement input, a changed yaw can change subsequent movement direction; this is a predicted consequence, not an observed trajectory. The candidate remains unresolved without the default membership evidence.

## Handoff

Independent reviewer: verify this immutable source delta and its player applicability. Specifically resolve Player membership in the exact 1.20.4 `minecraft:can_turn_in_boats` default tag using an allowed, version-matched source input; then accept, reject, or return the candidate with the supporting evidence. Related slice: S7.4. Release boundary remains unknown within (1.20.2, 1.20.4]. Implementation and runtime decisions are deferred.
