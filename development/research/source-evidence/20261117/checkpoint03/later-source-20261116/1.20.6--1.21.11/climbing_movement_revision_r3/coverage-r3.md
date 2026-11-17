# Player creative-flight climbable response (corrected r3)

## Bounded source difference and reachable movement witness

In 1.20.6, `Player` has no `onClimbable` override and inherits `LivingEntity.onClimbable`. For a non-spectator whose occupied block is an open EAST-facing trapdoor and whose block below is an EAST-facing ladder, `trapdoorUsableAsLadder` returns true. In 1.21.11, `Player.onClimbable()` returns false whenever `abilities.flying`, before the inherited predicate. This witness uses the explicit trapdoor fallback and requires no `CLIMBABLE` tag payload.

The following one-tick local-player state produces a surviving vertical-position difference during `move(SELF)`, before either flying `Player.travel` wrapper restores vertical velocity:

- Local player at `(0.5, 1.0, 0.5)`, non-spectator, not a passenger, not fall-flying; controlled camera; flying ability enabled; not sprinting; shift held; no jump or horizontal movement input; initial delta movement `(0, 0, 0)`; default ability flying speed `.05F`.
- Open EAST-facing trapdoor at `(0,1,0)` and EAST-facing ladder at `(0,0,0)`. All other blocks and fluids intersecting the player's box or this tick's vertical sweep are empty; no nearby world border. At each endpoint, no entity is returned by either `Entity.collide` entity-query path: the player's bounding box expanded towards the movement request, inflated by `1.0E-7`, with the `EntitySelector.NO_SPECTATORS` and `player.canCollideWith` predicate. This explicit empty entity-query precondition excludes any collidable entity shape that could shorten the one-tick vertical movement.

`LocalPlayer.aiStep` adds `-1 * .05F * 3.0F` to vertical delta movement for flying plus shift. The ordinary air movement helper then calls `handleOnClimbable` before `move(SELF)`. In 1.20.6, the inherited predicate is true for the stated block state. The helper first bounds Y to at least `-.15F`, then changes the negative value to zero because the block is not scaffolding and the held shift key makes this `Player` suppress ladder sliding. Thus the vertical request passed to `move(SELF)` is zero. In 1.21.11 the flying-player override makes the predicate false, so the helper does not clamp or suppress the same negative request; `move(SELF)` receives the downward input (approximately `-.15` blocks for this tick).

The collision-free geometry is source-derived. The old Player standing width is 0.6; the current `Avatar` standing width is also 0.6, so at the stated center the horizontal box is `[0.2,0.8]` on X and Z. The old EAST-facing trapdoor and ladder shapes are 3/16-thick edge strips. Current trapdoor and ladder shapes are formed from `Block.boxZ(16,13,16)` and rotated for facing; this is a 3/16-thick block-edge slab, also disjoint from the centered `[0.2,0.8]` horizontal bounds. A purely vertical sweep therefore intersects neither block's collision shape. With the stated empty surroundings, the entity collision resolver does not shorten this vertical movement: the old position remains at Y=1.0 for this operation, while the current position moves downward by the negative request.

Both flying `Player.travel` wrappers restore vertical velocity from the pre-super Y multiplied by `0.6` after `super.travel`; the old wrapper also resets fall distance after `super`. Neither post-super write reverses the position change already applied by `move`. This report does not claim that the helper's post-move Y=0.2 branch or its fall-distance write survives the wrapper; the demonstrated consequence is only the one-tick vertical-position update under the explicit preconditions above.

The endpoint predicate difference is established, but the first release introducing it and all intermediate forms remain unknown. This does not freeze pair coverage, establish runtime parity, or claim implementation readiness.

## Exact source identities

Canonical source root: read-only `decompiled_minecraft/`; both endpoints are ready exact Mojmap decompilations. Ready-marker and manifest digests are included to bind the source sets.

- 1.20.6 ready marker SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; client SHA `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; source manifest SHA `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; provenance SHA `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`.
- 1.21.11 ready marker SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; client SHA `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; source manifest SHA `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; provenance SHA `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.

## Compared source and hashes

Hashes below match the corresponding endpoint `mojmap.sources.sha256` entry and local source bytes.

- 1.20.6 `net/minecraft/world/entity/LivingEntity.java` — `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; `onClimbable` and trapdoor fallback 1541–1568; ordinary air response / `handleRelativeFrictionAndCalculateMovement` 2269–2280; `handleOnClimbable` 2297–2311.
- 1.20.6 `net/minecraft/world/entity/player/Player.java` — `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; standing dimensions 132–134; flying `travel` wrapper 1473–1495. No `onClimbable` override exists in the class.
- 1.20.6 `net/minecraft/client/player/LocalPlayer.java` — `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`; shift predicate 598–600; flight shift input 764–775; inherited AI call at 807.
- 1.20.6 `net/minecraft/world/entity/player/Abilities.java` — `a4f952ada7bc3b21406feaf13c171bfa22d36b01e265e3b987612f28b5609edc`; default flying speed `.05F` at 11; getter 44–46.
- 1.20.6 `net/minecraft/world/entity/Entity.java` — `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; movement/collision resolver from `move` at 597 onward, collision resolution 900 onward; block-position / occupied-state lookup 3165–3175.
- 1.20.6 `net/minecraft/world/level/block/TrapDoorBlock.java` — `13ab02b1ef41576b1ba2e0b228d4ecebf4a9c2308c2be6f2485dde4e0d6eaabc`; facing-selected shapes 43–48, 71–86.
- 1.20.6 `net/minecraft/world/level/block/LadderBlock.java` — `ab51bd41e6fc33e605177f7131de91ec7e383f80c11c279c72ae146e648d4167`; EAST collision shape 28–32, 47–61.
- 1.20.6 `net/minecraft/world/level/block/Block.java` — `0c96f526470944baa74d212ca8bfb8b579fd6a1d7045498b8e062b4868bb8e09`; box shape construction used by the historical facing shapes, lines 146–148.
- 1.20.6 `net/minecraft/world/level/LevelAccessor.java` — `4db0e09ed42b202117392da0e17e4ddea57ae5e8709320bb973bfd967b774879`; extends `CommonLevelAccessor`, making its entity-collision method available on `Level`, line 27.
- 1.20.6 `net/minecraft/world/level/CommonLevelAccessor.java` — `c0bdcf73ff5029f6e0da4c1019e45973cd543b992818b19bca843a737dd416b4`; delegates `getEntityCollisions` to `EntityGetter`, line 21.
- 1.20.6 `net/minecraft/world/level/EntityGetter.java` — `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f`; filters query entities by `NO_SPECTATORS` and `canCollideWith`, inflates the query by `1.0E-7`, and returns empty when none match, lines 57–74.
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java` — `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `onClimbable` / trapdoor fallback 1669–1696; ordinary air response and movement helper 2534–2544; `handleOnClimbable` 2561–2575.
- 1.21.11 `net/minecraft/world/entity/player/Player.java` — `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; flying wrapper 1360–1383; flying `onClimbable` override 2043–2045.
- 1.21.11 `net/minecraft/client/player/LocalPlayer.java` — `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; shift predicate 639–641; flight shift input 832–845; inherited AI call at 875.
- 1.21.11 `net/minecraft/world/entity/player/Abilities.java` — `d82af05310709658c7d324632b6bf92afa539322c185078bd649d97e11ffca39`; default flying speed `.05F` at 19; getter 22–24.
- 1.21.11 `net/minecraft/world/entity/Entity.java` — `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; movement/collision resolver from `move` at 685 onward, collision resolution 1096 onward; block-position / occupied-state lookup 3624–3632.
- 1.21.11 `net/minecraft/world/entity/Avatar.java` — `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9`; standing and crouching dimensions both use width 0.6, lines 20–33.
- 1.21.11 `net/minecraft/world/level/block/TrapDoorBlock.java` — `80fc4831ce3759894eaef67180d5f31b064449d0db4d61b05e348327c754ca8b`; open facing shape map 48; shape selection 71–73.
- 1.21.11 `net/minecraft/world/level/block/LadderBlock.java` — `2ecda32f482b2a4eb9293f7b289758ad75a2a464e77a46f41df23282aef2c574`; rotated facing shape map 27–43.
- 1.21.11 `net/minecraft/world/level/block/Block.java` — `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73`; `boxZ` shape construction 189–200.
- 1.21.11 `net/minecraft/world/level/LevelAccessor.java` — `138ba1c4247cb36945849fbaf7b2d9b710fdf1ae5564668e12f3f3ca5d50d4d1`; extends `CommonLevelAccessor`, making its entity-collision method available on `Level`, line 26.
- 1.21.11 `net/minecraft/world/level/CommonLevelAccessor.java` — `3c973efa29342b3896c0b5cf6abe04b6706e3cecd2a1df4641d9117acf18343d`; delegates `getEntityCollisions` to `EntityGetter`, line 21.
- 1.21.11 `net/minecraft/world/level/EntityGetter.java` — `1c370bf56b66774d6f5161a1844341be93399a8a61862e71a7106a96262b813d`; filters query entities by `NO_SPECTATORS` and `canCollideWith`, inflates the query by `1.0E-7`, and returns empty when none match, lines 54–71.
- 1.21.11 `net/minecraft/world/phys/shapes/Shapes.java` — `fbe1aa9c92b4806128325ed82a5255aba5158de68ba62eb1afd53e7811b6b40b`; horizontal/all-direction rotations 372–393.

## Remaining gates

- The exact release that introduced the `Player.onClimbable` flying override is unknown; intermediate releases are unexamined.
- This establishes one source-level one-tick vertical-position witness under the explicit input and collision preconditions. It does not establish other trajectories or later fall-distance consequences.
- Full-pair coverage, independent audit of this corrected report, runtime parity, and implementation readiness remain open.
