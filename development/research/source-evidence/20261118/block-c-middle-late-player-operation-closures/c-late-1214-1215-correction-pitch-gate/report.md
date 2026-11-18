# 1.21.4 to 1.21.5 correction pitch gate

## Result

The exact source comparison confirms a change in the shared relative/absolute rotation composition helper. In 1.21.4, `PositionMoveRotation.calculateAbsolute` adds the selected base pitch and correction pitch as `float`s and returns that sum without clamping (lines 39–43, 57). In 1.21.5 it applies `Mth.clamp(sum, -90.0F, 90.0F)` immediately after the addition (lines 38–42, 56).

That placement matters only to the same helper's `Relative.ROTATE_DELTA` branch: it computes the pitch difference from the composed pitch and rotates the existing delta movement around X before rotating it around Y (1.21.4 lines 44–50; 1.21.5 lines 43–49). The 1.21.4 result is not clamped until `Entity.setXRot`, which first applies `% 360.0F` and then clamps; the 1.21.5 helper clamps before delta rotation, and the same setter still runs later. Thus, with `ROTATE_DELTA` set and an out-of-range pitch sum, the velocity rotation can differ even though the final stored pitch is bounded in both versions. With no `ROTATE_DELTA`, or a composed pitch already in [-90, 90], this clamp has no effect on correction movement state.

For the ordinary client movement-correction path, the source does not reach that condition. The server's movement rejection/correction calls `teleport(x, y, z, yaw, pitch)` with the movement packet's wrapped rotation (1.21.4 `ServerGamePacketListenerImpl` lines 902–903, 1009–1011; 1.21.5 lines 970–971, 1084–1087). That overload constructs a `PositionMoveRotation` and supplies `Collections.emptySet()` to the teleport overload (1.21.4 lines 1067–1079; 1.21.5 lines 1145–1157). The empty relative set makes the correction rotation absolute and leaves `ROTATE_DELTA` false. The normal client writer then sets position, delta movement, yaw and pitch; its pitch setter clamps pitch in both releases (1.21.4 `ClientPacketListener` lines 718–735 and `Entity` lines 3513–3518; 1.21.5 lines 731–749 and the same `Entity` setter lines 3513–3518). In this path, source-reachable valid pitch is already within the setter's normal bounds, so the new helper clamp is the identity and correction movement state has no observed pitch-related difference.

The later ordinary movement input rotation uses yaw: `Entity.moveRelative` passes `getYRot()` into `getInputVector` in both releases (1.21.4 `Entity` lines 1424–1438; 1.21.5 lines 1453–1468). The direct pitch-dependent movement consumer located is `LivingEntity.updateFallFlyingMovement`, which reads `getLookAngle()` and `getXRot()` (1.21.4 lines 2290–2312; 1.21.5 lines 2322–2345); its dispatcher is the fall-flying branch of `travel` (1.21.4 lines 2189–2199; 1.21.5 lines 2213–2221). Glide is outside this assignment's scope, so this does not establish an in-scope later pitch consumer for the ordinary correction case.

## Exact-source evidence and provenance

Both inputs are the canonical local `decompiled_minecraft/<version>/mojmap/` trees, ready markers status `ready`, exact IDs 1.21.4 and 1.21.5, Mojmap, Vineflower, Java 25. Provenance records the exact run `decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G`, with pinned source/artifact manifests. Actual manifest SHA-256 values matched the corresponding ready-marker claims. Relevant Java file bytes matched the source-manifest entries on each side.

| Version | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- |
| 1.21.4 | `1c6617b7ac8fbb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` |
| 1.21.5 | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` |

Relevant source-file SHA-256 (each verified against its version's source manifest):

| Source | 1.21.4 | 1.21.5 |
| --- | --- | --- |
| `net/minecraft/world/entity/PositionMoveRotation.java` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |
| `net/minecraft/world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b` | `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69` |
| `net/minecraft/world/entity/LivingEntity.java` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |

## Boundaries

This is a bounded source gate for ordinary client movement corrections, not a claim about every custom teleport or every arbitrary relative-rotation set. The helper-level `ROTATE_DELTA` case remains a real mathematical distinction if an independently established ordinary path supplies a relative pitch sum outside [-90, 90] together with that flag. No crafted or malformed packet path was used. This report does not assess runtime parity, glide, startup/cap behavior, position axes, or geometry.
