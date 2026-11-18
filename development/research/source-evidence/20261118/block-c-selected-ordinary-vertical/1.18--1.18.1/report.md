# 1.18 → 1.18.1 source review: ordinary vertical air integration

## Exact source identity

Mapping family: Mojmap (official Mojang mappings). The prescribed root pins all match:

| Release | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | `mojmap.sources.sha256` SHA-256 |
|---|---|---|---|
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |

Both ready manifests name the exact release, `mojmap`, and `ready`; their recorded provenance and source-manifest digests agree with the prescribed pins. For each Java file below, its actual SHA-256 matched its entry in that release's `mojmap.sources.sha256`. All four compared Java files are byte-identical across these two roots.

## Bounded comparison and verdict

**Verdict: no difference in the assigned slice.** For an ordinary local player in dry air, not riding, gliding, flying, under a movement-altering effect, or in an excluded block/collision case, the player travel override delegates to `LivingEntity.travel`. Its ordinary branch has identical gates, operation order, constants, casts, and state writes in both releases.

| Release | Exact source references (relative to `decompiled_minecraft/<release>/mojmap/`) | Java source SHA-256 |
|---|---|---|
| 1.18 | `net/minecraft/client/player/LocalPlayer.java`: class declaration line 82; `aiStep` delegation at line 808. `net/minecraft/world/entity/player/Player.java`: `travel`, lines 1405–1439; ordinary non-flying delegation at 1434–1435. `net/minecraft/world/entity/LivingEntity.java`: `aiStep` invokes `this.travel(...)` at 2548; `travel`, lines 2005–2141; ordinary branch at 2113–2136; movement helper at 2157–2167; `checkFallDamage`, lines 286–307. `net/minecraft/world/entity/Entity.java`: `move`, lines 541–660; vertical grounding/fall callback at 576–580; base `checkFallDamage`, lines 969–982. | `LocalPlayer` `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`; `Player` `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`; `LivingEntity` `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`; `Entity` `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.1 | Same relative files and member/line ranges as 1.18. | Same four corresponding hashes as 1.18. |

### Compared behavior

- `LivingEntity.travel` enters its general branch after the water, lava, and fall-flying branches. It obtains movement through `handleRelativeFrictionAndCalculateMovement` first; that helper calls `move` before returning the post-move delta movement. The branch then starts with `$$29 = $$28.y`, subtracts the default `0.08` gravity when gravity is enabled (the excluded slow-falling effect is the only earlier change to this local gravity value), and applies vertical `0.98F` drag after gravity. `shouldDiscardFriction()` skips the drag write. Both versions use the same double local, float literal, `F` drag literal, branch order, and expressions.
- `Entity.move` sets `onGround` from vertical collision and a negative requested Y displacement, then passes the actual resolved Y displacement to `checkFallDamage`. The base common fall-state path resets `fallDistance` on ground; otherwise, for negative actual Y displacement, it stores `(float)(fallDistance - actualY)`. The landing block callback is treated as the excluded block boundary. The corresponding call and casts are identical.
- `LivingEntity.checkFallDamage` adds server-side Soul Speed and landing-particle handling before the base call. Those branches are gated off for the local logical client; their source is byte-identical as well.

A collision-free sample from the shared ordinary branch boundary is identical on both sides: starting with `deltaMovement.y = -0.1`, the movement request uses `-0.1`; after that movement, the next vertical velocity expression is `(-0.1 - 0.08) * 0.98F`. The fall-distance accumulation for an actual resolved Y displacement of `-0.1` is `(float)(fallDistance - (-0.1))` in both versions.

## Coverage limits and exclusions

Compared and closed for this assignment: the LocalPlayer-to-Player-to-LivingEntity travel route relevant to the non-flying player; ordinary dry-air passive gravity and vertical drag; movement-before-gravity ordering; `Entity.move`'s vertical grounded flag and actual-displacement handoff; common fall-distance increment/reset; and the living-entity wrapper around that common update.

Excluded by assignment: collision and block/provider behavior (taken as boundaries); jump causation; effects and equipment, including Levitation and Slow Falling; water/lava, climbing, and other environmental movement; vehicle, elytra/gliding, and flying branches; angle and horizontal-velocity math; visual/network behavior; server-only landing effects, damage, and other non-local consequences. This is a bounded no-difference result, not full pair coverage.

No canonical difference finding is warranted for this slice.
