# Independent source audit: sleeping pitch

## Frozen input binding

- `comparison.md` SHA-256: `DC0A620F7536B88922E329173C921769A7FDF5F852795337E54472E9C8D5FE77`.
- `coverage.json` SHA-256: `D12864E5376E92C29111D3E7903FCFA65A1633297EBC155B271B21D97192AAAB`.
- Both hashes match the assigned immutable specimen. All 24 preserved `LivingEntity.java` copies match their coverage digests and canonical decompiled source bytes. Each canonical class hash also matches its version's exact provenance source registry (detailed `.decompile-provenance.json` or the exact `*.sources.sha256` manifest); the specimen's manifest hashes match the supplied values.

## Independent source checks

- For every available stable Feather alias from 1.8 through 1.12.2 (24 IDs), I read the complete `LivingEntity.tick()` body and checked the whole class. No tick body invokes `isSleeping()` or assigns zero to pitch. In each class the only `isSleeping()` occurrence is its predicate declaration, and the only `pitch =` assignment is the `lerpPitch` interpolation outside `tick()`.
- I also checked the complete `PlayerEntity.java` and `PlayerEntity.tick()` for each of those 24 aliases. Every player class matched its canonical provenance source registry; none assigns `pitch = 0.0F` anywhere or in its tick body. This closes the alternate subclass write path for the bounded claim.
- The 1.13, 1.13.1 and 1.13.2 Feather `LivingEntity.tick()` bodies have no `isSleeping()` invocation or zero-pitch assignment. Their corresponding complete `PlayerEntity` classes have no zero-pitch assignment. The relevant `LivingEntity.java` hashes are respectively `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`, `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, and `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; all match their exact source registries.
- In 1.14.0 Feather build 2, `LivingEntity.tick()` calls `mobTick()` before its sleeping-conditioned `pitch = 0.0F` at lines 2113–2114. `LivingEntity.mobTick()` performs travel work at lines 2214–2220 (`moveRelative`, whose movement branches call `move`) before returning. The reset itself has no logical-side condition. This verifies the absent 1.13.x / present 1.14.0 endpoint boundary and that the write follows the current tick's movement work.

## Local packet-visible look scope

The 1.14.1 Yarn build 10 provenance file is present at `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json` (SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`); its pinned mapping input is `yarn-1.14.1+build.10-v2.jar` (SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`). Provenance source hashes match `LivingEntity.java` (`331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`), `PlayerEntity.java` (`ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`) and `ClientPlayerEntity.java` (`6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`).

In that local-client path, `ClientPlayerEntity.tick()` calls `super.tick()` before sending movement packets (lines 172–184). `LivingEntity.tick()` calls `tickMovement()` at line 2023 and applies the sleep pitch reset at lines 2097–2098. `PlayerEntity.cannotMove()` includes `isSleeping()` (lines 963–964). After `super.tick()`, `sendMovementPackets()` compares `pitch` with `lastPitch` and can send `Both` or `LookOnly` packets containing `this.pitch` (lines 213, 219–226); the vehicle branch sends a look packet with pitch at line 176. The reset is therefore packet-visible player look state. Since it follows the current tick's movement routine, it cannot alter that tick's already-completed locomotion. These sources do not establish a concrete sleeping-to-fall-flying continuation or a later-tick trajectory effect.

## Exact-symbol correction and verdict

The frozen comparison's 1.14.0 phrase “after `tickMovement()`” is not the exact Feather 1.14.0 member name. That source calls `mobTick()`, whose travel section performs movement before the reset. `tickMovement()` is the exact method name in the separately checked 1.14.1 Yarn source. This is a citation/member-name correction; the bounded post-movement ordering and player-state/network conclusion remain supported. No scoped source call or provenance entry was missing.

**COMPLETED — ACCEPT** for the bounded source claim: no sleeping-conditioned post-travel pitch-zero write in 1.8–1.12.2, absent through 1.13.2 and present by 1.14.0; local packet-visible look state is established, while a current-tick locomotion delta is not.