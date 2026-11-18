# Independent source audit verdict

## Verdict

Accepted for the bounded 1.16 → 1.16.1 slice: an already-active, ordinary local player in dry conditions, on foot, with an enabled chest Elytra while standing in a ladder/vine climbable feet block. No source difference is present in the audited path. This verdict does not close the pair inventory or establish runtime parity.

## Immutable input

Frozen draft: `c-glide-climb-116-1161-alias-gate/report.md`.
Input SHA-256: `8c340c2b99feb18063eb7b0b9c32acff1f6a9af599244d14f427b524b074a33f` (verified against the supplied digest).

## Source identity and integrity

Canonical inputs: `decompiled_minecraft/1.16/mojmap/` and `decompiled_minecraft/1.16.1/mojmap/`.
Both `mojmap.ready.json` markers identify their exact release, `mojmap`, `ready`, and 3,572 source files. Recomputed source-manifest SHA-256 values match the markers and the draft:

- 1.16 `mojmap.sources.sha256`: `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; client SHA-256: `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`.
- 1.16.1 `mojmap.sources.sha256`: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; client SHA-256: `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.

The 1.16 provenance records an exact 1.16 Mojmap run. The 1.16.1 provenance records `mappingOption: mojmap`, `versionId: 1.16.1`, and batch `1.16.1,1.16.2`; this audit used only the 1.16.1 canonical export. Source-manifest entries were recomputed against files on both sides: 3,572 entries per side, with no hash mismatch. Relevant source files are byte-identical across the pair:

| Source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `7962d9207b139f8f794061c79bf06454e44f2fe7be3549e513500ef3bccde6e6` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |

## Trace and claim review

All following references apply to both `1.16/mojmap/` and `1.16.1/mojmap/`; the cited files have identical full-file hashes above.

- **Climbable and startup boundary:** `LivingEntity.java:1361–1385` defines `onClimbable()` using the block at `blockPosition()`/`getFeetBlockState()` and the `CLIMBABLE` tag (which covers the ordinary ladder/vine case). `LocalPlayer.java:750–757` prevents the jump-start request while `onClimbable()` is true, checks enabled Elytra in the chest slot for eligible starts, then samples `wasFallFlying` from `isFallFlying()`. This is a start gate; it does not reject an already-active flyer.
- **Flag authority and ordered update:** `Player.java:1513–1527` sets shared flag 7 when `tryToStartFallFlying()` succeeds. `LocalPlayer.java:752–754` sends `START_FALL_FLYING`; `ServerGamePacketListenerImpl.java:1194–1198` handles that request through the server player's `tryToStartFallFlying()`. `Entity.java:1905–1915` reads/writes the shared flag. In `LivingEntity.java:2452–2457`, the tick applies input scaling, calls `updateFallFlying()`, snapshots the box, and then calls `travel(...)`. At `LivingEntity.java:2476–2494`, an active flag remains valid only while airborne, not a passenger, not levitating, and wearing enabled chest Elytra; the recalculated boolean is written back only when `!level.isClientSide`. Thus client-side validation does not locally clear the active shared flag.
- **Branch and pre-move predicate:** `LivingEntity.java:1912–1922` enters movement only for effective AI or the locally controlled instance and tests water first; lava is the next branch at `1957–1960`. Under the requested dry precondition, `LivingEntity.java:1977–2006` selects `else if (isFallFlying())` before the normal `else`. It reads the signed `double` vertical component from `Vec3`; the strict `vec36.y > -0.5` test assigns `fallDistance = 1.0F` before glide velocity updates and `move(MoverType.SELF, ...)`. Equality at `-0.5` does not enter the assignment.
- **Ordinary climb path and after-move/final gates:** Only the normal branch calls `handleRelativeFrictionAndCalculateMovement` (`LivingEntity.java:2020–2025`), whose `handleOnClimbable` call precedes `move` (`2060–2069`). `handleOnClimbable` resets fall distance to `0.0F` on a climbable block and clamps velocity (`2087–2101`). The glide branch bypasses both. After its move, wall-impact damage and landing flag clearing are guarded by `!level.isClientSide` at `2007–2019`; they do not undo the client-side pre-move fall-distance seed.

## Scope

The closed claim is only the specified already-active dry local-player path on a ladder/vine feet block. The jump-start exclusion explains why this must be a pre-existing active glide state; it does not turn startup into the behavior under review. No differences appear in the exact paired files, so the report’s no-difference result is accepted for this bounded operation.
