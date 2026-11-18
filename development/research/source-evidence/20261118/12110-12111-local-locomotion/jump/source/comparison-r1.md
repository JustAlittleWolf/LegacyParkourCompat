# 1.21.10 → 1.21.11 local dry ground jump source comparison (revision 1)

Revision 1 corrects the first report's branch analysis. It supersedes that report's claim that the dry path clears `noJumpDelay`; the original file is preserved and its raw SHA-256 is bound below.

## Verdict

**No in-scope jump behavior difference found** for an ordinary local player on dry ground, not riding, with jump held and `noJumpDelay == 0` at the start of the eligible jump step. Both releases set the jump input bit, pass it through `LocalPlayer.applyInput()` into `LivingEntity.jumping`, enter the grounded `jumpFromGround()` branch, and set `noJumpDelay` to 10. The jump-power arithmetic and velocity operations are identical, as is the subsequent dry in-air travel consumer. No distinguishing first-move witness exists for this bounded slice.

The original report's SHA-256, preserved unchanged: `ad98a8bf4ee292f638b76942bd54b4f3cfe9d18567dd1b42e75c6a7a72b95910`.

## Exact source identity and provenance

Both source trees have `status=ready`, exact version IDs 1.21.10/1.21.11 and `mapping=mojmap`. Ready-marker source-manifest hashes match the raw manifests; compared Java files match their manifest rows. Both success logs report successful decompilation, and movement diagnostics report the required movement source files.

| Release | Client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- |
| 1.21.10 Mojmap | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 1.21.11 Mojmap | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |

The following raw file hashes were checked against the path rows in each `mojmap.sources.sha256` manifest:

| Relative source path | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |

## Compared path and branch behavior

- **Keyboard source:** `KeyboardInput.tick()` creates `Input` from the same key state in both versions, with `options.keyJump.isDown()` as the fifth field (1.21.10 and 1.21.11 `net/minecraft/client/player/KeyboardInput.java`, lines 22–36). This is the runtime source of a held keyboard jump bit.
- **Input wrapper:** `ClientInput.tick()` is empty in both. Its `makeJump()` constructs a new `Input` with `jump=true` and preserves the other six fields (both `net/minecraft/client/player/ClientInput.java`, lines 6–31). In `LocalPlayer.aiStep()`, `input.tick()` runs before the later jump handling; the initial jump bit is captured and auto-jump may call `makeJump()` (1.21.10 `LocalPlayer.java` lines 697–713; 1.21.11 lines 738–754).
- **Local jump dispatch:** `LocalPlayer.applyInput()` has the same controlled-camera gate and assigns `this.jumping = this.input.keyPresses.jump()` in both (1.21.10 `LocalPlayer.java` lines 612–625; 1.21.11 lines 653–666). `LocalPlayer.tick()` transmits changed `keyPresses` via `ServerboundPlayerInputPacket` on both releases (1.21.10 lines 195–203; 1.21.11 lines 211–218). This confirms the client input handoff; server response behavior is outside this assignment.
- **Grounded jump and delay:** `LivingEntity.isAffectedByFluids()` returns `true` in both releases (1.21.10 line 547; 1.21.11 line 548). `LivingEntity.aiStep()` first decrements a positive `noJumpDelay` in both (1.21.10 lines 2751–2754; 1.21.11 lines 2877–2880). After `applyInput()`, for a held jump, the water/lava height conditions are false on dry ground, so control reaches the nested condition `(this.onGround() || $$7 && $$5 <= $$8) && this.noJumpDelay == 0`. With the assigned grounded and zero-delay precondition, it calls `jumpFromGround()` then writes `noJumpDelay = 10` (1.21.10 lines 2791–2831, especially 2805–2822; 1.21.11 lines 2917–2957, especially 2931–2948). The later `else { noJumpDelay = 0; }` runs only when the outer `jumping && isAffectedByFluids()` condition is false; it does not run for the held-jump scenario under review. Both releases therefore apply the same delay write to the same eligible jump path.
- **Jump power and effect:** `getJumpPower(float)` is identical: `(float)getAttributeValue(Attributes.JUMP_STRENGTH) * scale * getBlockJumpFactor() + getJumpBoostPower()`. `getJumpBoostPower()` retains the same `0.1F * (amplifier + 1.0F)` expression when Jump Boost is present (1.21.10 `LivingEntity.java` lines 2222–2232; 1.21.11 lines 2256–2266).
- **Velocity writer/order:** `jumpFromGround()` in both rejects power `<= 1.0E-5F`, reads the current velocity, writes `x, max(power, y), z`, then, if sprinting, adds the same yaw-derived horizontal vector using `0.2` (1.21.10 `LivingEntity.java` lines 2235–2247; 1.21.11 lines 2269–2281). Under equal sprint state, the resulting velocity is the same. The final bookkeeping write changed from `hasImpulse=true` to `needsSync=true`; this is a changed synchronization marker, not proof that all later entity state or network-visible behavior is equivalent. This comparison establishes only the in-scope jump velocity result and first dry travel input, not full state or network parity.
- **Dry travel consumption:** `travel(Vec3)` sends a dry non-fall-flying player to `travelInAir()` in both (1.21.10 `LivingEntity.java` lines 2275–2284; 1.21.11 lines 2309–2321). The dry vertical movement computation and subsequent delta-velocity write are identical (1.21.10 lines 2306–2329; 1.21.11 lines 2343–2366): it applies the same levitation/gravity branch and writes `$$5 * $$7`, where `$$7` is `0.98F` for a player. Thus the identical jump velocity is consumed by the same first dry travel operation.

## Bounded coverage and residuals

Examined the keyboard jump source, `ClientInput` behavior, local input handoff and flag assignment, grounded zero-delay eligibility and delay write, jump power/effect, velocity write order, and first dry in-air travel consumption. This is not complete player-tick or pair coverage. No positive difference was found, so no distinguishing witness is available.

Excluded by assignment: fluid jumping/travel behavior apart from the surrounding gates required to establish the dry grounded branch; mayfly/flying/spectator toggles; riding/jumpable vehicles; Elytra and climbing; sprint/sneak timers and unequal sprint flags; pose, collision/support providers, external server responses, and non-player paths. The synchronization marker difference is recorded above, but its full synchronization/network consequences were not traced. Intermediate patch introduction is unexamined; this endpoint comparison establishes no cutover within the interval.
