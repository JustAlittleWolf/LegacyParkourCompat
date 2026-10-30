# Pose candidate reachability closure: 1.13.2 → 1.14.4

## Scope and immutable inputs

This bounded source-only closure checks the two ordinary player-pose candidates in the exact Feather Gen2 build 2 pair. It establishes one static ordinary-movement witness for the sneaking-height delta and one vanilla-command setup witness for constrained fallback. The latter is not an ordinary movement entry path. No product source/history, wiki, backlog, game/runtime, test, mock movement, build, or decompilation was used. No source or prior report was edited.

Read-only exact source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.13.2,1.14.4}/ornithe-feather`.

The input claim/review bindings are:

| Input | SHA-256 |
| --- | --- |
| `ordinary-tick/source-review.md` | `f9daef67079078add9ad621c6ef279e8275ae687b00a11184873fc6e00c1d128` |
| `ordinary-tick/player-sneaking-pose-height.draft.md` | `2774b0e7e45d042cc2d2a236df179807325c40bba0fb13082b580b14ecf43d16` |
| `ordinary-tick/constrained-player-pose-fallback.draft.md` | `9e857b97768b5dba8b03851f878d8e0f4c99ad05761e390eeba88345ed90eaf0` |
| `ordinary-tick/correction-supplement.md` | `679a5605972826dc3203717200f9c125e5834b83db8e9357177da0b8e1825481` |
| `ordinary-correction-audit/independent-audit.md` | `e07e0d91b8a27eecdc399cc54202fd0d507cfcb4551975ffb04fecff7a5f3d40` |

Selected source files rehashed for this closure:

| Release and file | SHA-256 |
| --- | --- |
| 1.13.2 `entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 `entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 `world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 `block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| 1.14.4 `entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 `entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 `world/WorldView.java` | `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909` |
| 1.14.4 `util/math/Box.java` | `b68667118ba729fc18fefa3b9de9060184f9d71f53a4bf10a8b7215863ff8b6e` |
| 1.14.4 `block/SlabBlock.java` | `91f2ebc592e5cb07d5ce8a6faa123ec0d4b7bf10aa6323d6aceb343bc0f73a53` |
| 1.14.4 `block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 `block/shape/VoxelShapes.java` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |
| 1.14.4 `server/command/TeleportCommand.java` | `09d6cd387f01e5caf37958c95535eb9c19a5ebb2df42edeb78c62380144ba495` |
| 1.14.4 `server/network/handler/ServerPlayNetworkHandler.java` | `98ac0eb8bd6d3d8264a787ce03d5bc6b1b580f4d990bd3e1a86f5e2cde3945e8` |
| 1.14.4 `client/network/handler/ClientPlayNetworkHandler.java` | `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef` |

## Shared pose and collision gates

In 1.14.4, `PlayerEntity.tick()` invokes `updatePlayerPose()` at `PlayerEntity.java:281`. The whole ordinary pose choice is inside the swimming-box gate at `PlayerEntity.java:343–370`: `m_87715359(Pose.SWIMMING)` must pass first. It asks `world.hasNoCollisions(this, m_16672198(pose))` (`Entity.java:1628–1629`); `m_16672198` builds the requested dimensions box at current x/y/z (`Entity.java:2300–2305`). Desired-pose order is fall-flying, sleeping, swimming, spin attack, sneaking when not flying, then standing (`PlayerEntity.java:346–358`). When not spectator/riding, a failing desired box tries sneaking and then swimming (`:360–367`). Pose is synced data (`Entity.java:295–300`); applying the changed pose invokes `m_84943753()` from the data callback (`:2239–2249`), which installs the selected dimensions/shape (`:2245–2266`). Player dimensions are standing `0.6 × 1.8`, sneaking `0.6 × 1.5`, swimming `0.6 × 0.6` (`PlayerEntity.java:109–117`). Player swimming state is not implied just by the `Pose.SWIMMING` fallback: for a player, `isSwimming()` requires not flying/spectator and the inherited swimming flag (`PlayerEntity.java:1762–1764`; `Entity.java:948–954` shows its sprint/water producer). Thus a no-input, nonflying, nonspectator, nonriding, non-sleeping player has desired standing even while the stored pose currently falls back to swimming.

The 1.14.4 query reduces to block and entity collision shapes; `hasNoCollisions` requires every returned shape empty (`WorldView.java:121–130`). Block collision candidates are queried with the entity collision context and accepted only when voxel overlap is true (`:133–175`). No other entity is included in the witnesses. `Box.intersects` uses strict inequalities on all axes (`Box.java:198–204`), and voxel-shape overlap only reports a shared positive-volume cell; face contact does not obstruct. A bottom slab's collision shape is `[0,1] × [0,0.5] × [0,1]` (`SlabBlock.java:27,45–55`; `Block.java:376–378`); 1.13.2 uses the same bottom slab shape (`SlabBlock.java:27,55–60`). A normal full block uses the unit cube collision shape (`Block.java:370–378`).

1.13.2 calls `updatePlayerPose()` at end of `PlayerEntity.tick()` (`PlayerEntity.java:254`). It directly selects dimensions, with sneaking `0.6 × 1.65` (`:334–352`), constructs the target box, and calls `setSize` only if that target passes `hasNoCollisions(null, box)` (`:354–359`). Its `setSize` keeps the feet at the same y and rebuilds the box when width shrinks (`Entity.java:285–300`). The old query tests block-shape overlap and returns no collision when the proposed box only touches a surface (`WorldView.java:126–150, 201–206`). Consequently old crouch-fit failure retains the previous size; there is no fallback pose attempt.

## Candidate A — sneaking 1.65F → 1.5F: reachable by ordinary movement

**Verdict: REACHABLE (static ordinary movement).** Use an unobstructed, level bottom-slab walkway as the floor, with a full-block roof whose underside is y=2 over a tunnel; place the slab blocks at y=0 so their top is y=0.5. Keep the approach open. The player walks on the slab, holds sneak before the roof edge, and moves forward normally. This uses bottom slabs and full blocks, both existing in 1.13.2.

At a player feet y of 0.5, the 1.14.4 sneaking box top is `0.5 + 1.5 = 2.0`, exactly the roof underside. Its swimming outer-gate box top is `0.5 + 0.6 = 1.1`. Both are clear: the slab ends at y=.5 and the roof begins at y=2, with only face contact for the sneaking target. A 1.13.2 sneaking target top is `0.5 + 1.65 = 2.15`; it overlaps the roof by 0.15, so `setSize(0.6,1.65)` is rejected and old dimensions remain. With a crouch chosen in the open approach, 1.13.2 `Entity.move` collision resolution clips horizontal movement at the roof edge for the 1.65-high body; the 1.14.4 1.5-high body passes under it. For the new path, standing may fail at the roof but desired sneaking passes after the outer swimming test, so the end-of-tick pose is sneaking before or while advancing into the tunnel.

This witness closes the previously open shape/query and ordinary reachability dependency for the dimension delta. It is narrower than the draft's unsupported universal “can fit through” wording: this concrete arrangement is the witness, and unrelated entity collisions or world borders are excluded.

## Candidate B — constrained fallback to swimming: external setup only

**Verdict: ONLY EXTERNAL SETUP; no static ordinary-movement entry witness.** For a concrete legal vanilla setup, keep a full-block floor ending at y=1 and a full-block ceiling beginning at y=2 over an open region. An operator-authorized vanilla `/teleport` command can place the player at feet y=1 under that roof while their standing shape intersects it. The command is permission-gated (`TeleportCommand.java:35–45`), resolves player targets to `ServerPlayNetworkHandler.teleport(...)` (`TeleportCommand.java:247–259`), and that handler directly calls `player.updatePositionAndAngles(...)` then sends an absolute `PlayerMoveS2CPacket` without a collision-fit check (`ServerPlayNetworkHandler.java:825–839`). The client applies that position packet on its main thread (`ClientPlayNetworkHandler.java:652–705`); its next ordinary player tick reaches `updatePlayerPose()` at the end (`PlayerEntity.java:281`).

At feet y=1, standing is `[1,2.8]` vertically and sneaking is `[1,2.5]`, so both intersect the ceiling cube `[2,3]`. The swimming box is `[1,1.6]`, clears ceiling and touches the floor cube `[0,1]` only at its bottom face. With no neighboring entities and the player non-spectating, nonriding, nonsneaking, not swimming/flying/sleeping/spin-attacking, the swimming outer gate passes, desired standing fails, sneaking fails, and `Pose.SWIMMING` is selected at `PlayerEntity.java:343–370`. Since the pose width stays 0.6, dimension application rebuilds the shape at the same feet coordinate and reduces only height (`Entity.java:2245–2265`). A subsequent local movement tick can move a 0.6-high body along the one-block air band.

For a **static ordinary movement** path, the player cannot first walk into this one-block band with standing or sneaking dimensions: those boxes collide with the roof during movement, and `Entity.move` resolves horizontal motion against collision shapes (`Entity.java:444–468, 573–620`). Therefore the command/position-correction setup is a prerequisite, not an ordinary movement witness. This proves the fallback is a source- and vanilla-path-reachable conditional difference, but it does not establish its frequency in a normal parkour traversal. 1.13.2's updater has no smaller fallback; a blocked target size fails the direct collision test and leaves current dimensions (`PlayerEntity.java:334–360`). If no command/teleport setup is in accepted scope, classify this candidate as **withdraw from ordinary-movement findings**, not as an ordinary witness.

## Candidate dispositions and next dependency

| Candidate | Closure verdict | Disposition |
| --- | --- | --- |
| Sneaking height | Reachable by static ordinary movement | retain as a concrete ordinary behavioral candidate; collision/query and standard blocks are closed for this witness |
| Constrained fallback | Only external setup in this source closure | retain only if legal vanilla teleport/position correction is an in-scope trigger; otherwise withdraw from ordinary-movement findings |

This does not accept either candidate as an implementation requirement, prove full tick trajectory parity, or freeze the exact pair. Next dependency is scope/acceptance of external teleport as a relevant setup for Candidate B; the collision-query and static ordinary path dependencies for Candidate A are closed by the stated geometry and source gates.
