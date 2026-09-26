# F002: Gravity suppression added for synchronized no-gravity state

- Older version A: 1.9.4
- Newer version B: 1.10.2
- Mechanic / coverage slice IDs: S3b, S7 (living movement; server-synchronized player state)
- Classification: changed behavior
- Confidence: candidate (client-side behavior is source-confirmed; vanilla player writer/source path unresolved)
- Applicability: unresolved (requires B `NoGravity=true` on the local PlayerEntity; server-supplied state)
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)` lines 1302–1470; SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. Its vertical acceleration statements at lines 1408, 1424 and 1452 are unconditional in the corresponding branches. A's generic `Entity.java` hash is `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; `Entity`, `LivingEntity` and `PlayerEntity` have no generic `isNoGravity`/`setNoGravity` state. A has a separate ArmorStand-specific no-gravity flag, which is not inherited by PlayerEntity.
- B: `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)` lines 1332–1506; SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`. B guards the corresponding `-0.08` and `-0.02` vertical acceleration updates with `!this.isNoGravity()` (lines 1432–1433, 1454–1455 and 1485–1486). B `Entity.java`, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, registers generic `NO_GRAVITY` default false (lines 139, 186–193), exposes `isNoGravity()`/`setNoGravity()` (796–801), and reads/writes `NoGravity` in entity NBT (1252–1253, 1354).
- B client receives entity synced data in `ClientPlayNetworkHandler.handleEntityData(EntityDataS2CPacket)` lines 447–453; it looks up the entity by ID and updates its `SyncedData`. File SHA-256 `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656`. This path is server-fed; no vanilla PlayerEntity write path has been established in the client artifact.

## Source-level difference

For the default-false state, the compared movement branches retain the same gravity terms and operation order. B adds a generic entity flag check so those terms are skipped when `isNoGravity()` is true. The ordinary no-gravity false path also retains the same water/lava vertical multipliers and the same 0.8 movement multiplier; B extracts the `0.8F` into `getBaseMovementSpeedMultiplier()`, whose only current implementation returns `0.8F`. Its climbing clamp replaces the local `w = 0.15F` use with the identical `-0.15F` / `0.15F` literals, so that is not a separate numeric behavior change.

## Reachability and dependencies

Local `PlayerEntity` inherits `LivingEntity.moveRelative` and generic `Entity` in B. B's client entity-data handler applies server-provided synced data to the entity resolved by packet ID; B movement then reads `NO_GRAVITY`. A PlayerEntity inherits no generic no-gravity flag in A. Source proves the client-side conditional effect and the packet/data path, but the 1.10.2 server source is required to determine whether vanilla can set `NoGravity=true` for an ordinary player. The expected default is false; no client-side local writer was identified. Do not infer server movement rules from this client evidence.

## Consequence and uncertainty

If B receives `NoGravity=true` on a player, its applicable vertical gravity additions are skipped; A has no corresponding player state and applies those additions. For the default false state, these branches do not differ. The source does not establish that vanilla 1.10.2 ever sends true for PlayerEntity, so player applicability remains unresolved and this slice is blocked pending a version-matched server writer/packet trace. No trajectory or runtime outcome is claimed.

## Handoff

Candidate delta only. Resolve D4 by checking the 1.10.2 server-side source path for setting PlayerEntity's generic `NoGravity` data and sending it; otherwise retain the boundary as an external mod/server-supplied flag. No implementation or runtime validation was performed.
