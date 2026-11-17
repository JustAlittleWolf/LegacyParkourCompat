# Early boat rider placement transform

## Bounded source result

Compared exact Ornithe Feather decompilations of 1.8.9 against 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4. Scope is the local client's ordinary player as the sole boat passenger: `World` routes riding entities through `rideTick`; `Entity.rideTick` invokes the boat placement hook after the player tick. Vessel movement, admission, additional passengers and the later boat-rotation tag are outside this comparison.

The placement change is already present in 1.9 and the examined hook body stays byte-identical through 1.9.4. Thus this source set bounds the cut to after 1.8.9 and at or before 1.9; no intermediate patch is needed to establish the cut. It does not identify an exact earlier point inside that interval.

## Source locations and behavior

- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/world/World.java:1342-1345` calls `entity.rideTick()` when `entity.vehicle != null`. `Entity.java:1275-1327` zeros rider velocity, ticks the rider, calls `vehicle.updateRiderPositon()` if still mounted, then accumulates/wraps/clamps vehicle yaw/pitch deltas. The delta bookkeeping does not directly assign rider yaw or pitch.
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java:34-37,79-81,366-372`: boat default size is `1.5F x 0.6F`, `getMountHeight()` returns `-0.3`, and the single rider slot is guarded by `rider != null`. The hook computes `d = cos(yaw * PI / 180.0) * 0.4`, then `e = sin(yaw * PI / 180.0) * 0.4`, then writes `(boat.x + d, boat.y + mountHeight + rider.getRideHeight(), boat.z + e)` with `setPosition`.
- `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/world/World.java:1403-1406` dispatches `rideTick()` when `entity.isRiding()`. `Entity.java:1423-1436` zeros rider velocity, ticks it, and calls `mount.updateRiderPositon(this)` if it remains riding. For a boat, `BoatEntity.java:554-591` first requires `hasPassenger(rider)`. With exactly one passenger, `f` remains `0.0F`; the multi-passenger branch is skipped. The code creates `Vec3d(f, 0.0, 0.0)`, rotates it by `-yaw * (float)(PI / 180.0F) - (float)(PI / 2)`, then writes position. Since the vector is zero, the rotated horizontal offset is exactly zero: the supported sole rider is placed at the boat's x/z center.
- In 1.9, the active-boat vertical value is `g = (float)((this.removed ? 0.01F : this.getMountHeight()) + rider.getRideHeight())`. For an ordinary active boat, this is the float cast of `-0.1 + -0.35`, or `-0.45F`; a removed boat takes the other branch. The 1.8.9 expression is double arithmetic, `-0.3 + -0.35 = -0.65`. So the ordinary sole passenger's position moves from `(boat.x + cos(yaw*PI/180)*0.4, boat.y - 0.65, boat.z + sin(yaw*PI/180)*0.4)` to `(boat.x, boat.y - 0.45F, boat.z)`. This is a `+0.2` vertical seat shift plus removal of the 0.4 horizontal offset.
- The ordinary player contributes `getRideHeight() == -0.35` in `PlayerEntity.java:931-933` (1.8.9) and `911-913` (1.9). The normal player size remains `0.6F x 1.8F` (`PlayerEntity.java:417` / `387`); boat size changes to `1.375F x 0.5625F` (`BoatEntity.java:66` from 1.9). Those sizes describe reachable default entities; the rider placement formula itself uses the fixed mount and ride heights above.
- The common `Entity.setPosition` writer (`Entity.java:226-233` in 1.8.9, `274-281` in 1.9) stores x/y/z and rebuilds the entity box from width and height. It performs no collision movement or yaw update.

### Rider yaw writes

The 1.8.9 boat hook writes no rider yaw fields. Its enclosing `Entity.rideTick` only tracks and drains wrapped vehicle yaw/pitch deltas. Starting in 1.9, after the position write the boat does `rider.yaw += yawVelocity`, then adds the same value to head yaw, then calls `copyEntityData`. That helper sets body yaw to boat yaw, wraps `rider.yaw - boat.yaw`, clamps the difference to `[-105.0F, 105.0F]`, shifts both `lastYaw` and `yaw` by `clamped - wrapped`, and sets head yaw to the corrected rider yaw. Thus the newer direct yaw baseline is player yaw plus boat yaw velocity, bounded relative to boat yaw; the old placement hook had no such direct yaw writes.

## Examined patch consistency

The whole `BoatEntity.java` SHA-256 is identical for 1.9, 1.9.1 and 1.9.2. It is identical for 1.9.3 and 1.9.4. The latter pair's changed line numbers reflect other class edits; the hook and helper bodies remain the same. Exact source bounds: 1.8.9 hook `366-372`; 1.9-1.9.2 hook/helper `554-591`; 1.9.3-1.9.4 hook/helper `558-595`.

## Evidence identity

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Every version's checked `.decompile-provenance.json` (inside the source directory for 1.9-1.9.3; sibling `ornithe-feather.provenance.json` for 1.8.9 and 1.9.4) identifies the exact version and `ornithe-feather` namespace. SHA-256 of those provenance files:

| Version | Provenance SHA-256 | `BoatEntity.java` SHA-256 |
|---|---|---|
| 1.8.9 | `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185` | `77EF72C28024B754FF76F2C67E14DD61832394BAC2BD7B62B29708560E588DA2` |
| 1.9 | `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A` | `950CC19CE2627F02D619658EAB6560B40758149EACD2E62534653666AD7EB758` |
| 1.9.1 | `E9FB0DA6511597E2DE953A143F9570B82243E5B9504603686E557F06271DA64A` | `950CC19CE2627F02D619658EAB6560B40758149EACD2E62534653666AD7EB758` |
| 1.9.2 | `9928B467DEB35C7E7F891B4FEE1627A68FEECDF233C2076909FD4834B0380510` | `950CC19CE2627F02D619658EAB6560B40758149EACD2E62534653666AD7EB758` |
| 1.9.3 | `43325CDD53CA34B88EE9AF8464782B323A948B98C8A556C4761F1E5737B84C3A` | `3D41B9DF0EF0D36158105235C92B35DAC4B51070476E7EF688520961D1D56E57` |
| 1.9.4 | `5C913715BED763369CEB5FD171CAB3E63C43298023078C3683F96C7EA8FED3CD` | `3D41B9DF0EF0D36158105235C92B35DAC4B51070476E7EF688520961D1D56E57` |

The 1.8.9 / 1.9.4 batch provenance sidecars also include `ornithe-feather.sources.sha256`; the per-file boat source hashes above independently bind the quoted class bytes. No source tree was modified or regenerated.

## Terminal

**COMPLETED** — bounded local player / sole boat passenger placement comparison for 1.8.9 vs 1.9 through 1.9.4. Exact bounds are listed above. Difference confirmed in position and direct yaw writes; excluded boundaries remain unexamined by design.