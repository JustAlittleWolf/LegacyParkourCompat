# Independent source coverage roster — 1.18.2 to 1.19.4

**Roster status:** frozen for blind lane comparison. This is an inventory freeze only; source verdicts and pair freeze are still open.

## Inputs and source identity

Read-only Mojmap/Vineflower roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. For each version, the `mojmap.sources.sha256` digest equaled both the task-supplied digest and `mojmap.ready.json.sourceManifestSha256`. Manifest rows equaled `sourceFileCount`; every listed source existed and its content SHA-256 matched; malformed, missing, and mismatched rows were all zero.

| Version | Manifest SHA-256 | Java rows |
|---|---|---:|
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | 4,236 |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | 4,430 |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | 4,480 |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | 4,480 |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4,618 |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | 4,740 |

`changed-path-roster.tsv` records every added, removed, or content-modified Java path for each adjacent edge, with old/new file digests and an initial role bucket. Counts by edge: 1.18.2→1.19: 1,946; 1.19→1.19.1: 321; 1.19.1→1.19.2: 12; 1.19.2→1.19.3: 1,700; 1.19.3→1.19.4: 1,095 (5,074 edge/path rows). Bucket labels are routing hints, not movement verdicts; the geometry and block buckets deliberately include candidates needing member-level disposition.

## Frozen ordinary local-player tick and movement closure

The roster follows the ordinary client path and its dependencies:

1. `net/minecraft/client/Minecraft.java`: client loop `runTick` → `tick` → `handleKeybinds`; key mappings/input configuration and the active player/level selection. The client calls `ClientLevel.tick`.
2. `net/minecraft/client/multiplayer/ClientLevel.java`: `tick` → `tickEntities` → `tickNonPassenger`; local-player entity tick reaches the player hierarchy. This is the client-side ordinary entity tick route; server ticking is excluded.
3. `net/minecraft/client/player/LocalPlayer.java`: `tick` → `Player/LivingEntity/Entity.tick`; `aiStep` samples `Input.tick` and derives jump, sprint, flying and movement state; `move` dispatches the local move and auto-jump checks. `Input`/`KeyboardInput` implement the input-vector provider.
4. `net/minecraft/world/entity/player/Player.java`: `tick`, `updatePlayerPose`, `aiStep`, `jumpFromGround`, `travel`, `maybeBackOffFromEdge`, sprint/flight speed providers, and direct player responses. Preserve fixed-profile player scope; exclude vehicle physics and the explicitly out-of-scope food production/damage work.
5. `net/minecraft/world/entity/LivingEntity.java`: `tick`, `baseTick`, `aiStep`, `travel`, jump, climb, friction, swimming/fluid, fall-flight, effect, block-speed, and dimension/pose providers. `MobEffect`/`MobEffectInstance`, attributes, enchantment and equipment providers are dependencies where a player path reads them.
6. `net/minecraft/world/entity/Entity.java`: `tick`, `baseTick`, `move`, `maybeBackOffFromEdge`, `collide`, `collideBoundingBox`, `checkFallDamage`, `updateInWaterStateAndDoFluidPushing`, `updateFluidHeightAndDoFluidPushing`, collision flags, position/velocity/box/pose and direct player movement responses.
7. Collision/provider closure: `CollisionGetter`, `BlockGetter`, `Level`, `BlockBehaviour.BlockStateBase`, block state collision/outline/support shapes, `VoxelShape`, `Shapes`, `AABB`, `Vec3`, old-era block subclasses, `Fluid`/`FluidState`/water and lava providers, and movement-relevant block contact callbacks (`entityInside`, `fallOn`, `stepOn`, `updateEntityAfterFallOn`). `LocalPlayer.updateAutoJump` and `moveTowardsClosestSpace` are additional direct block-shape consumers.
8. Callback/direct-response closure: client packet/listener and client-level paths that change local-player position, velocity, pose or movement state, including teleport/correction, client event callbacks and piston application. These are routed in the changed-path TSV; member comparison remains partial pending the lane cross-check.

The working member roster is the above graph plus the candidate members in the TSV. Exact starting lines confirmed in source include: 1.18.2 `Minecraft.runTick` 973 / `tick` 1572 / `handleKeybinds` 1701; `ClientLevel.tick` 161 / `tickEntities` 195 / `tickNonPassenger` 212; `LocalPlayer.tick` 183 / `aiStep` 642 / `move` 889 / `updateAutoJump` 900; `Player.tick` 222 / `updatePlayerPose` 366 / `aiStep` 491 / `maybeBackOffFromEdge` 1032 / `travel` 1422; `LivingEntity.tick` 2224 / `aiStep` 2461 / `travel` 2009 / `jumpFromGround` 1981; `Entity.tick` 399 / `baseTick` 403 / `move` 543 / `maybeBackOffFromEdge` 740 / `collide` 777 / `collideBoundingBox` 803 / `checkFallDamage` 979 / fluid update 2769. The corresponding methods persist or move in later versions; source line/member locations are enumerated during the terminal review.

## Roster-level partial/unexamined areas

- The ordinary client tick and core method graph is identified, but adjacent-edge semantic dispositions for every changed core/provider member have not been closed yet.
- The TSV path-role assignment is an exhaustive file-level roster; block providers, client callbacks and movement modifiers still require member/body-level paired review. New paths must be checked as additions and old existing paths as modifications/removals; a new 1.19+ feature is not a historical behavior delta by itself.
- The supplied exact roots contain decompiled Java only. Data resources/tags (including block/fluid/entity tag membership, item/component data and block-state definitions) are not present in the audited roots, so their versioned contents and resource-to-code registration edges remain unexamined. No assumption of tag/resource stability is frozen.
- No terminal no-difference verdict has yet been entered for all relevant route members. No pair freeze is asserted.

This freezes the independent path/member inventory before comparison with the three sibling lane outputs. Any newly revealed route will be recorded as a roster reopening rather than silently added to this frozen baseline.
