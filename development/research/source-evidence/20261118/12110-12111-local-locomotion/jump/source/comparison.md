# 1.21.10 → 1.21.11 local dry ground jump source comparison

## Verdict

**No in-scope jump behavior difference found** for ordinary local ground jumping with a dry, non-riding player and equal initial sprint state. The same jump input reaches the same jump flag; jump-power arithmetic, vertical velocity write and the dry in-air travel consumer are unchanged. No distinguishing first-move witness exists for this bounded slice.

## Exact source identity

Both source trees are ready Mojmap outputs for the requested version IDs. Their ready markers match their source and artifact manifest SHA-256 values; the LocalPlayer and LivingEntity file hashes below match each `mojmap.sources.sha256` entry. Both success logs report a successful decompile and the movement diagnostics confirm required sources are present.

| Release | Client SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | LocalPlayer SHA-256 | LivingEntity SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.21.10 Mojmap | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 Mojmap | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |

## Compared slices

- `net/minecraft/client/player/LocalPlayer.java`: `applyInput()` (1.21.10 lines 612–625; 1.21.11 lines 653–666) assigns `this.jumping = this.input.keyPresses.jump()` under the same controlled-camera gate. `aiStep()` captures the jump bit and handles ordinary input identically at 1.21.10 lines 697–713 and 1.21.11 lines 738–754. The nearby sprint timer/input conditions are treated as equal sprint-state boundaries, not as jump findings.
- `net/minecraft/world/entity/LivingEntity.java`: `getJumpPower(float)` is the same float expression in both releases: `(float)getAttributeValue(JUMP_STRENGTH) * scale * getBlockJumpFactor() + getJumpBoostPower()`. `getJumpBoostPower()` uses the same `0.1F * (amplifier + 1.0F)` term when Jump Boost is present (1.21.10 lines 2222–2232; 1.21.11 lines 2256–2266).
- `jumpFromGround()` has identical gate and velocity operation order in both releases: reject power `<= 1.0E-5F`; read current velocity; write `(x, max(power, y), z)`; if sprinting, add the same yaw-derived horizontal vector using `0.2`; then set an impulse/sync marker (1.21.10 lines 2235–2247; 1.21.11 lines 2269–2281). The marker changed from `hasImpulse` to `needsSync`, but the jump velocity is identical. This marker change supplies no in-scope first-move divergence under equal sprint state.
- `aiStep()` retains the same `noJumpDelay` decrement and jump branch, including the `jumping && isAffectedByFluids()` gate and the `onGround()` / delay checks (1.21.10 lines 2751–2754, 2791–2831; 1.21.11 lines 2877–2880, 2917–2957). On the assigned dry path, the fluid-jump branch is not taken and the same else branch clears the delay to zero; it does not impose a distinct dry-ground cooldown.
- `travel(Vec3)` routes dry non-fall-flying movement to `travelInAir()` in both releases (1.21.10 lines 2275–2284; 1.21.11 lines 2309–2321). The `travelInAir()` vertical movement consumption and write sequence is identical (1.21.10 lines 2306–2329; 1.21.11 lines 2343–2366): movement is calculated, gravity/effect adjustment is applied, then the Y velocity is multiplied by the same vertical factor (`FlyingAnimal ? friction*0.91F : 0.98F`) unless friction is discarded. The local player is not a FlyingAnimal.
- `ClientInput.java` is byte-for-byte equal in the reviewed jump-relevant surface: its `tick()` is empty, `makeJump()` reconstructs the input with `jump=true` and preserves the other fields, and no jump timing is implemented there (same source text in both releases).

## Bounded coverage and residuals

Examined: local jump-bit forwarding, ordinary local `aiStep` jump-related gates, Jump Boost and jump-strength power calculation, the ground jump velocity writer, jump-delay handling at the in-scope dry boundary, and dry in-air travel consumption. The result does not claim complete player-tick or pair coverage.

Excluded by assignment: fluid jumping and fluid travel; mayfly/fly toggles and spectator handling; riding and jumpable vehicles; Elytra and climbing; sprint/sneak timer changes and movement with differing sprint flags; pose, collision/support providers, external server responses, and non-player paths. Exact intermediate patch introduction is unexamined; this endpoint comparison does not identify a cutover within the interval.
