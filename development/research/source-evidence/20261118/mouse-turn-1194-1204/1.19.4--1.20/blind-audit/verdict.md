# Independent verdict: 1.19.4 to 1.20 callback and flight claims

## Bound revisions

- Author draft `source/comparison.md`, raw SHA-256: `ad94e58bc818d4f6ce63e8f4f1dc40ce2a8ebc87ced76a94811e097ea400b47c`.
- Prior blind freeze `blind-audit/frozen.md`, raw SHA-256: `a3b09ac8ebdbe3a77bd64d584c4f23e413eef634ad952a3ddb9db5c4841f67e2`.
- Verdict is limited to the claims and source members named below. It does not certify whole-pair movement coverage or runtime parity.

## Evidence identity audit

The draft's listed SHA-256 identities for the 1.19.4 ready marker, provenance, source manifest, and consumed Java files match the local bytes / manifests. Its 1.19.4 mapping artifact claim is supported by the ready pair's `artifacts.sha256`: `client_mappings.txt` is pinned to `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`.

The 1.20 `.decompile-provenance.json` has SHA-256 `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Its `inputs` map explicitly includes `https://piston-data.mojang.com/v1/objects/a4cd9a97400f7ecfe4dba23e427549ebc5815d66/client.txt` with SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, plus the stated exact client JAR and `1.20.json` metadata pins. Thus the draft's 1.20 mapping artifact pin is verified. The prior blind freeze incorrectly said that this manifest did not separately record the mapping input. Preserve that frozen file unchanged, but do not reuse that one provenance limitation; the draft has the better evidence account.

All five source file hashes in the draft match the source manifest (1.19.4) or embedded per-file hashes (1.20), and actual source bytes. The audit also checked `Minecraft.java`, SHA-256 `7ea6ae69423f3658e4084c5371c13c29acec1c442166ff6f93144e748d6362b3` (1.19.4) and `fd55374b9a1d51e4b09063a2803a999823668a6bbd49575ed7cb7b61aeb9b7a4` (1.20), for scheduler placement.

## Claim verdicts

### Callback arithmetic, gates, and scheduler — ACCEPT

`MouseHandler.turnPlayer()` is identical at 1.19.4 lines 212-253 and 1.20 lines 212-253 for the scoped ordinary path. Under mouse-grabbed + active-window, smooth-camera-off, not-scoping, local-player-present, both reset smooth accumulators, evaluate the sensitivity curve and normal `* 8.0` scale in the same order, scale accumulated X/Y, clear accumulators, invert only Y when configured, notify the tutorial, and invoke `player.turn(x, y * invert)`. The unscoped precondition excludes the alternate first-person scoping multiplier; finite ordinary deltas reach the same call. Device event acquisition/accumulation remains outside the claim.

The scheduler was checked: each `Minecraft.runTick(boolean)` runs its timer-selected `tick()` loop (bounded to at most ten iterations) and then calls `mouseHandler.turnPlayer()` once before sound/render. The call is at 1.19.4 line 1085 and 1.20 line 1075. Placement and call count in the method are unchanged. This places the callback after the current frame's client tick loop; the reviewed source does not imply that it runs once per simulation tick.

### Entity.turn and virtual pitch setter/clamp — ACCEPT

`Entity.turn(double,double)` matches in operation order and expressions: cast input Y to float and multiply by `0.15F`, cast X and multiply by `0.15F`, invoke virtual `setXRot` with the current pitch plus delta, invoke `setYRot`, clamp the resulting current pitch through `setXRot`, increment old rotations, clamp old pitch, then notify an attached vehicle. References: 1.19.4 `Entity.java:391-403`; 1.20 `Entity.java:395-407`.

The effective player setter is inherited `Entity.setXRot(float)` in both versions. It checks `Float.isFinite` before assigning `xRot`; the setter has no additional clamp. `LivingEntity`, `Player`, and `LocalPlayer` do not override it. References: 1.19.4 `Entity.java:3155-3161`; 1.20 `Entity.java:3273-3279`. This validates the virtual dispatch and explicit post-write clamp described by the draft.

### Active flight — ACCEPT ONLY FOR NAMED MOVEMENT MATH; STATE EQUIVALENCE REJECTED

The draft correctly separates the abilities-flying (creative flight) branch in `Player.travel` from Elytra `isFallFlying()` travel. Checked creative vertical input in `LocalPlayer.aiStep()` has the same `flying && isControlledCamera()` gate and same shift/jump integer accumulation and `flyingSpeed * 3.0F` Y-velocity addition (1.19.4 lines 752-765; 1.20 lines 758-771). In `Player.travel`, both active abilities-flying branches require `abilities.flying && !isPassenger()`, save prior Y, delegate, restore Y as saved-Y times `0.6`, reset fall distance, and clear shared flag 7 (1.19.4 lines 1455-1464; 1.20 lines 1461-1470). These are separate from the Elytra branch and show no checked creative-flight movement difference.

For Elytra, the local-control gate and ordered water/lava priority precede `isFallFlying()`. A reachable state witness is a locally controlled, airborne player with the fall-flying state set and not taking either higher-priority fluid branch; `LocalPlayer.aiStep()` can request fall flight under its jump / not-already-flying / not-passenger / not-climbable / enabled-Elytra checks, while `Player.tryToStartFallFlying()` additionally requires not on ground, not already fall-flying, not in water, no levitation, and a usable Elytra. The active `LivingEntity.travel` Elytra formulas, velocity write, `move(SELF, velocity)`, collision response and ground flag match for position/velocity math (1.19.4 lines 2048-2087; 1.20 lines 2068-2107). The current pitch and look direction feed that calculation; no interpolation is inserted by this branch.

Do not accept equivalence for the entire Elytra travel state. Immediately before branch selection, both versions calculate gravity and test nonpositive Y plus slow falling; 1.19.4 sets gravity to `0.01` and resets `fallDistance`, while 1.20 sets the same gravity without resetting it (`LivingEntity.java:1983-1990` vs `:2004-2010`). On a tick that then takes Elytra travel, this is a real player movement-state difference. `checkSlowFallDistance()` later in the branch can clamp `fallDistance` to `1.0F` when Y velocity is greater than `-0.5` and the counter exceeds `1.0F`; it does not alter the Elytra velocity formulas. The actual counter state therefore may differ at the end of the move, although downstream fall-damage effects are not reviewed. The draft correctly discloses this residual, but its blanket sentence “No source difference was found in the requested ... first active Elytra travel path” should be narrowed to “no difference in the checked Elytra position/velocity movement calculation”; state parity for the full travel call fails.

## Draft corrections and unresolved gates

- Minor source-reference typo: the `MouseHandler` citation appears as `\net/minecraft/client/MouseHandler.java`; correct it to `net/minecraft/client/MouseHandler.java`.
- Narrow the no-difference conclusion to the checked velocity/position math, as above, and preserve the slow-fall `fallDistance` residual as a state difference.
- The mapping provenance claim is supported; no mapping-input evidence gate remains for these exact endpoints.
- Intermediate patch introduction and broader tick/inventory closure remain unexamined. No runtime parity claim is established.

## Verdict summary

Accept the callback delta/gate/order equivalence and `Entity.turn` virtual pitch setter/clamp equivalence. Accept creative and Elytra movement position/velocity math equivalence only under the stated gates. Reject whole-call Elytra movement-state equivalence because of the slow-fall reset removal. The frozen record remains immutable; its mapping-provenance caveat is superseded by this exact draft audit evidence.