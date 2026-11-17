# S-PLAYER-AISTEP-PRE — bounded source comparison

**Pair:** 26.1.2 unobfuscated → 26.2 unobfuscated. **Verdict:** partial slice, no difference found in owned operations; no pair acceptance, onset, full-freeze, implementation, or runtime-parity claim.

## Evidence identity

Both trees are rooted at `decompiled_minecraft/<version>/unobfuscated/`. Their `unobfuscated.ready.json` and `unobfuscated.provenance.json` agree on the exact version and `unobfuscated` namespace. Each ready marker names `unobfuscated.sources.sha256` and `artifacts.sha256`; the SHA-256 values of both files match the marker declarations, and source-manifest line counts match `sourceFileCount`.

| Version | ready marker SHA-256 | provenance SHA-256 | source manifest SHA-256 (declared = actual; entries) | artifact manifest SHA-256 (declared = actual) | client.jar entry in artifact manifest |
|---|---|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (6,882) | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 *26.1.2/client.jar` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (7,055) | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 *26.2/client.jar` |

**Artifact-payload availability limitation:** The 26.1.2 artifact manifest has 78 entries and the 26.2 manifest has 91. Their manifest identities and listed `client.jar` entries are verifiable, but the paths for the listed payloads are absent from the recorded campaign output/cache directories: 26.1.2 `build/movement-campaign-2026-10-07/ready/26.1.2/unobfuscated` and `staging/unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18/{output,cache}`; 26.2 `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated`, `staging/26.2-unobfuscated-3f17fe9da1bd4a17918a5b39990fcad5`, and `build/movement-campaign-2026-10-07/artifacts`. Therefore the artifact payload bytes cannot be independently verified in this workspace. This limitation concerns the underlying artifact payloads only: the ready/provenance identities, source-manifest identities, and cited Java file hashes remain verified as recorded here.

Relevant source-file hashes were matched to each root source manifest and actual files:

| Source member | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |

## Ordered owned scope and evidence

1. Ordinary reachability: `LocalPlayer.tick()` (26.1.2 and 26.2 `LocalPlayer.java:227-230`) enters `Player.tick()` through `super.tick()`; that enters `LivingEntity.tick()`, whose non-removed branch invokes virtual `this.aiStep()` (26.1.2 `LivingEntity.java:2738-2740`; 26.2 `2804-2806`). `LocalPlayer.aiStep()` is therefore the ordinary local-player override. It calls `super.aiStep()` at `LocalPlayer.java:914` in both versions, reaching `Player.aiStep()`.
2. Jump-trigger countdown and flight reset, including order relative to `super.aiStep()`: `Player.java:442-454` in both versions. Both perform, in order: decrement `jumpTriggerTime` only when `> 0`; `tickRegeneration()`; `inventory.tick()`; reset fall distance only when `abilities.flying && !isPassenger()`; then `super.aiStep()`. Text and gates match exactly. `Entity.resetFallDistance()` assigns `fallDistance = 0.0` in both (26.1.2 `Entity.java:2841-2843`; 26.2 `2908-2910`).
3. Local creative-flight trigger writer: `LocalPlayer.java:777-854` in both versions. Input snapshots and `input.tick()` precede `mayfly` handling. The first fresh jump edge sets `jumpTriggerTime = 7` only when its post-Player.aiStep countdown value is zero; otherwise, when not swimming and either unmounted or on a jumpable vehicle, it toggles `abilities.flying`, optionally jumps from ground, updates abilities, and clears the trigger to zero. All reviewed expressions, order and gates match. Spectator flight remains a separate earlier `mayfly` branch.
4. Direct movement predicate consuming the reset state: `Player.java:931-933` in both versions, `isAboveGround(maxDownStep)`, is reached from `Entity.move()` via its virtual `maybeBackOffFromEdge(...)` call (`Entity.java:730` in 26.1.2; `737` in 26.2), dispatched to `Player.maybeBackOffFromEdge()` (`Player.java:880-886` both versions), which calls `isAboveGround(maxDownStep)`. It returns `onGround() || fallDistance < maxDownStep && !canFallAtLeast(0.0, 0.0, maxDownStep - fallDistance)` identically. Although this is a direct player-movement consumer of `fallDistance`, its `!abilities.flying` guard is mutually exclusive with the `abilities.flying` guard on the reset in the ordinary tick path reviewed. No intervening flight-state change is shown, so this path does not establish a same-tick movement consequence of the reset; that dependency remains open. `canCriticalAttack` also reads `fallDistance` (`Player.java:1031-1038` in 26.1.2; `1033-1040` in 26.2), but is attack/damage eligibility and excluded by scope.

## Bounded diff and dependencies

No difference found in the owned countdown, creative-flight trigger writer, conditional fall-distance reset, reset assignment, ordinary local-player reachability, or `isAboveGround` consumer. Checked as partial coverage only. Other tick/update writers and readers of `fallDistance` (movement, fluids, block effects, and other callbacks), remaining player tick behavior, and the rest of the pair inventory remain open. No onset boundary is inferred from these endpoints.
