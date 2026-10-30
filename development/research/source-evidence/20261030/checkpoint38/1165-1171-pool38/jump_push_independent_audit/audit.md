# Independent source audit: Jump Boost and Entity.push precision, 1.16.5 → 1.17.1

## Verdicts

| Candidate | Verdict | Boundary and scope |
|---|---|---|
| Jump-effect addition in `LivingEntity.jumpFromGround` | **ACCEPT: endpoint source delta and local-player caller path confirmed; conditional arithmetic witness confirmed.** | 1.16.5 uses float compound addition; 1.17.1 adds a float-valued boost term to float jump power as `double`. The exact intermediate introduction release is unknown from this endpoint-only audit. |
| Square-root precision in `Entity.push(Entity)` | **ACCEPT: endpoint source delta and a local-player velocity-writer path confirmed for eligible non-player pushables; conditional arithmetic witness confirmed.** | 1.16.5 rounds the square root to float via `Mth.sqrt(double)`; 1.17.1 retains `Math.sqrt(double)` as double. The exact intermediate introduction release is unknown from this endpoint-only audit. Remote-player collisions do not establish this player path. |

These verdicts accept source-level operation changes and source reachability under the stated conditions. They do not establish a recorded normal input/state trace, a resulting multi-tick trajectory, runtime parity, or full-pair coverage.

## Evidence identity and integrity

The audited report was `.native-source/20261028/frontier-c/1165-1171-ordinary-tick-closure/report.md`, SHA-256 `dbab3ca3cec3fc263190cd106dc7596de07a46a268e25d4c1033c0a6e2dcfc42`. Its source list is `.native-source/20261028/frontier-c/1165-1171-ordinary-tick-closure/source-manifest.csv`, SHA-256 `1bc0019d3d5c45281841b319f5a534fa696987cf4ccfcfcc2d3e4d124b0ad6e3`. I also read the prior bounded audit `.native-source/20261028/frontier-c/1165-1171-jump-push-audit/audit.md` (SHA-256 `a7f4e3e2a9c30084484eb01fbe98d40a517d4aa9094629c279c00ca8bded9e77`) and adjacent source audit `.native-source/20261028/frontier-c/AD1165-117-audit/audit.md` (SHA-256 `bc54f3e1035abc55813e2c73b8ee36316a9eed1bf29da531974869ce70341c50`) as context, not as a substitute for endpoint source inspection.

Canonical source trees were read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap` and `...\1.17.1\mojmap`. I recomputed every Java entry in each full Mojmap source manifest: 1.16.5 manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, **3,523/3,523** files present and matching; 1.17.1 manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`, **4,142/4,142** files present and matching. Both values match the expected full source-manifest digests in the supplied record. The exact source files used for expressions and call routes are included in those full manifests. Selected file digests:

| Mojmap source file | 1.16.5 SHA-256 | 1.17.1 SHA-256 |
|---|---|---|
| `world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| `client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3e3a60fd066e848f6a8c2b` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `client/player/RemotePlayer.java` | `719d6af7a22891f0090493d6a784de84547544bcd10289091fdeeda7931746fe` | `6a3773ecdc977aa78325cee555613bdcbdca462bb2d489854f5796eb0e3cfd88` |
| `util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| `world/entity/EntitySelector.java` | `1fd642a27e3773d2c8bb79a20db965b2ea4876fea232490fbbbdc7efbf78fd9a` | `63f72ac3907b18f09716e9e64cfd3b7e8767f43792430ef79f15c11b59845c51` |

## Candidate 1: Jump Boost addition precision

### Compared expressions

- 1.16.5 Mojmap `net/minecraft/world/entity/LivingEntity.java:1878-1895`: `getJumpPower()` returns `float` (`0.42F * getBlockJumpFactor()`). `jumpFromGround()` assigns it to `float f`; when `MobEffects.JUMP` is present, `f += 0.1F * (amplifier + 1)` performs a float multiplication followed by float compound-add rounding. `setDeltaMovement` receives `f` as Y velocity at line 1889.
- 1.17.1 Mojmap `net/minecraft/world/entity/LivingEntity.java:1967-1985`: `getJumpPower()` still returns `float`; new `getJumpBoostPower()` returns `double` at lines 1971-1973. Its `0.1F * (amplifier + 1)` term is evaluated in float and widened for the `double` return. `double d = getJumpPower() + getJumpBoostPower()` at line 1976 therefore adds the float base (widened) and float effect term (widened) as double; line 1978 writes that value to Y velocity. Sprinting later changes only horizontal velocity here.

### Local-player caller route and gates

In both endpoint sources, `LocalPlayer` extends `AbstractClientPlayer`; `LocalPlayer.aiStep()` calls `super.aiStep()` (1.16.5 `LocalPlayer.java:627,788`; 1.17.1 `LocalPlayer.java:649,810`). The superclass chain reaches `Player.aiStep()` and then `LivingEntity.aiStep()` (1.16.5 `Player.java:483-500`; 1.17.1 `Player.java:489-506`). Local input is copied into the entity jump state in `LocalPlayer.java:614` / `:618`; the method also temporarily sets jump input in `:653` / `:675` for the auto-jump path. `Player.jumpFromGround()` delegates to `super.jumpFromGround()` (1.16.5 `Player.java:1376-1377`; 1.17.1 `Player.java:1398-1399`), reaching the changed living-entity operation.

The relevant `LivingEntity.aiStep()` branch is present in both versions (1.16.5 `LivingEntity.java:2426-2450`; 1.17.1 `:2513-2538`): it requires `jumping` and `isAffectedByFluids()`. It selects the ordinary ground-jump path only when the nested water/lava checks allow it and `(onGround || inWater && fluidHeight <= threshold)` with `noJumpDelay == 0`; otherwise it jumps in a liquid or does not jump. The caller then sets `noJumpDelay = 10`. Thus a grounded, ordinary on-foot local jump with a Jump effect reaches this operation; deep-liquid and delayed-jump cases do not.

### Conditional numeric witness

Condition on `getJumpPower() == 0.42F` (for example, a movement state whose effective block jump factor is exactly 1.0) and Jump amplifier `0`. Java float arithmetic gives `base = 0.41999998688697815` and `effect = 0.10000000149011612`. The 1.16.5 float sum is `0.5199999809265137`; the 1.17.1 double sum is `0.5199999883770943`, a difference of `7.450580596923828e-9`. This is a conditional source-expression witness. The source audit did not prove a particular block/effect setup or sample a normal input trace, and it does not show a multi-tick result.

## Candidate 2: Entity-push square-root precision

### Compared expressions and order

- 1.16.5 Mojmap `net/minecraft/world/entity/Entity.java:1180-1215`: after same-root-vehicle and both-`noPhysics` guards, it computes horizontal offsets `d = other.x - this.x`, `e = other.z - this.z`, `f = Mth.absMax(d,e)`, and requires `f >= 0.01F`. At line 1187, `f = Mth.sqrt(f)`. `Mth.sqrt(double)` at `net/minecraft/util/Mth.java:37-39` returns `(float)Math.sqrt(d)`, so the root rounds to float before widening back to the `double f`. The code divides `d,e` by `f`, computes/clamps `g = 1.0/f`, scales each offset by `g`, multiplies by `0.05F`, then multiplies by `1.0F - this.pushthrough`. It calls `this.push(-d,0,-e)` when this entity is not a vehicle and `other.push(d,0,e)` when the argument entity is not a vehicle.
- 1.17.1 `Entity.java:1278-1312` has the same guards, threshold, divisions, reciprocal clamp, scaling and call order, but line 1285 uses `f = Math.sqrt(f)`, keeping the result double. The `pushthrough` field and its factors are gone. A scan of both full Mojmap source trees found no vanilla writes to `pushthrough`; in 1.16.5 the only source uses are the field declaration and reads in `Entity.java` and `AbstractMinecart.java`, so its vanilla default is zero. This removed factor supplies no demonstrated vanilla impulse difference.

### Local-player caller route, target direction, and gates

In both versions `LivingEntity.aiStep()` calls `pushEntities()` after `travel` (`1.16.5 LivingEntity.java:2459-2467`; `1.17.1 :2565-2573`). `pushEntities()` obtains a list from `level.getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this))` and calls `this.doPush(entity)` for each result (`1.16.5 LivingEntity.java:2502-2523`; `1.17.1 :2614-2635`). `doPush(entity)` is `entity.push(this)` (`1.16.5 :2549-2550`; `1.17.1 :2661-2662`). With `this` as the local player, the receiver of `Entity.push(Entity)` is the nearby entity and the argument is the local player. Once the shared guards and threshold pass, the `other.push(d,0,e)` branch writes the impulse to the player's inherited `Entity.push(double,double,double)`, which adds to delta movement and sets `hasImpulse` (1.16.5 `Entity.java:1213-1215`; 1.17.1 `:1309-1311`). This executes after the player's travel call and changes velocity for subsequent movement.

Source gates for this incoming impulse are: the nearby entity must be returned by the bounding-box query and pass `EntitySelector.pushableBy` (including pushability, non-spectator and collision-team rules); neither participant may have `noPhysics`; they must not share a root vehicle; `absMax(dx,dz) >= 0.01F`; the nearby receiver must not be sleeping if it is a `LivingEntity`; and the local player, as the argument entity, must not be a vehicle for the incoming `entity.push(d,0,e)` branch. The root vehicle, block-box overlap, team state, entity presence, local player's movement mode and coordinate offsets are environment/state inputs, not established by this audit.

**Remote-player case:** `RemotePlayer` has `noPhysics = true` in both constructors (`client/player/RemotePlayer.java:11-15`). Also, `EntitySelector.pushableBy` on a client rejects nonlocal player entities (`EntitySelector.java:39-51`). Therefore `LocalPlayer.pushEntities()` does not acquire an ordinary remote player as a push candidate. In the reverse route, a remote player can call `doPush(localPlayer)`, but `Entity.push(Entity)` sees the remote receiver's `noPhysics` and returns before the impulse. Remote-player overlap is not a valid witness for this candidate.

### Conditional numeric witness

Condition on the local-player `pushEntities()` loop receiving a nonvehicle, awake, non-`noPhysics` nearby entity with `dx = +0.03`, `dz = 0`, and ordinary vanilla-default `pushthrough = 0`; assume local player's starting X velocity is zero. The threshold passes and `g = 1/sqrt(0.03) > 1`, so it clamps to 1. Before the final `0.05F` scale, the normalized X terms differ: old root `0.17320507764816284` (float-rounded), new root `0.17320508075688773` (double). Following the stated source operation order, the local-player X impulse is `+0.008660254322328477` in 1.16.5 and `+0.008660254166892228` in 1.17.1; difference `-1.5543624894143182e-10`. These are conditional arithmetic outputs for the supplied offsets and gates, not proof that a normal game trace produces those exact positions. Starting velocity other than zero, alternate team/vehicle/noPhysics/sleep state, or another source of candidate list entries is outside this arithmetic witness.

## Examined and unexamined scope

**Examined:** exact endpoint method bodies for both candidates; the Jump Boost accessor and old `Mth.sqrt(double)` implementation; `LocalPlayer` → `Player` → `LivingEntity` jump caller chain and ground/fluid/delay gates; post-travel local-player push list, selector, `doPush` direction, receiver/argument writes, `noPhysics`/vehicle/sleep/threshold/reciprocal gates; both RemotePlayer constructors and selector behavior; full-tree `pushthrough` references; complete Java file hashes in both source manifests; conditional arithmetic for each expression.

**Not examined:** exact 1.17 source tree or any intermediate patch release; all other movement candidates or `Entity.move`; full player-tick inventory, block collision shapes/effects, fluids/resources/tags, other direct external movement responses; a concrete loaded world/entity/effect setup; input playback or runtime trajectories; product implementation, Git, wiki, backlog, tests, builds, or launches. The input ordinary-tick report separately records broader open coverage; this audit does not close it.

## Source-only outcome

Both named endpoint source deltas are confirmed, with conditional arithmetic differences and ordinary local-player source paths as stated. The Jump Boost source path is a grounded local jump with active effect and applicable state. The Entity.push path applies to eligible non-player pushables; the previously generalized “nearby entity/player push” statement must exclude client RemotePlayer collisions. Intermediate introduction dates and runtime parity remain open.
