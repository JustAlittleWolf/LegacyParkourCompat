# Source coverage: LocalPlayer auto-jump

Verdict: NO_DIFF within the assigned ordinary player auto-jump decision and step-response slice, 1.21.11 → 26.1.2.

## Immutable source identities

- 1.21.11 Mojmap: `decompiled_minecraft/1.21.11/mojmap.ready.json`, ready status; source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; client artifact SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`. Provenance `decompiled_minecraft/1.21.11/mojmap.provenance.json` binds an exact Mojmap run for 1.21.8, 1.21.10, and 1.21.11.
- 26.1.2 unobfuscated: `decompiled_minecraft/26.1.2/unobfuscated.ready.json`, ready status; source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact manifest SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`; client artifact SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`. Provenance `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` binds an exact unobfuscated 26.1.2 run.
- Both source-manifest and artifact-manifest file hashes match the ready markers. No archive re-decompilation was performed.

## Source files and hashes

- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java` — SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`, matching its entry in `mojmap.sources.sha256`.
- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` — SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`, matching its entry in `unobfuscated.sources.sha256`.

## Ordinary path and gates

At 1.21.11 `LocalPlayer.move` lines 947–955 and 26.1.2 lines 986–994 both snapshot X/Z, call `super.move`, cast actual X/Z displacement to `float`, pass those deltas to `updateAutoJump`, then add horizontal walked distance. Auto-jump detection therefore follows the regular collision-resolved player move; it does not decide or alter that move's position response.

`canAutoJump` is equivalent at 1.21.11 lines 1082–1090 and 26.1.2 lines 1121–1129: enabled option, timer <= 0, on ground, not staying on a ground surface, not riding, nonzero input move-vector length squared, and block jump factor >= 1.0. `isMoving` checks the same `input.getMoveVector().lengthSquared() > 0.0F` at 1092–1094 and 1131–1133.

`updateAutoJump` is structurally and numerically equivalent at 1.21.11 lines 966–1062 and 26.1.2 lines 1005–1101. Both use actual horizontal displacement, falling back to rotated input × current speed when squared displacement <= 0.001F; reject movement facing dot below -0.15F; require two empty collision shapes above the player's bounding box; set base jump height to 1.2F plus 0.75F per Jump Boost amplifier level; look ahead by max(speed × 7, inverse movement length); build the same player-height, width-inflated test AABB; probe both side segments offset by half player width at +0.51F; traverse collision AABBs and above-obstacle block collision shapes; reject excess height/blocked overhead; and schedule `autoJumpTime = 1` only for obstacle rise > 0.5F and <= jump height. The iteration bounds, shape queries, return/break positions, floating-point thresholds and assignment are unchanged. Names differ due to mappings/decompiler presentation.

## Timer and observable response

At 1.21.11 `aiStep` lines 749–754 and 26.1.2 lines 788–793 both decrement a positive `autoJumpTime`, mark the tick as auto-jump-originated, and call `input.makeJump()`. The flag gates creative-flight double-jump at 1.21.11 line 799 and 26.1.2 line 838. Neither version adds the auto-jump-origin flag to the adjacent `tryToStartFallFlying` condition (1.21.11 lines 811–813; 26.1.2 lines 850–852). These are equivalent gates in this slice. The scheduled jump is conveyed through input on the subsequent tick; `updateAutoJump` itself writes only the timer and does not change position, velocity, or the bounding box.

## Coverage boundary

This packet covers the LocalPlayer auto-jump trigger, obstacle/ceiling probes, timer and synthetic jump input handoff. The inherited `super.move` collision/step kernel, ordinary input-vector/travel math, all other Player movement behavior, world state resources, and non-LocalPlayer routes were outside this assignment. No pair-wide completeness or release-pair freeze is claimed. Exact source semantics were compared statically; runtime behavior was not exercised.