# Independent audit: 1.11.1 → 1.11.2 creative flight and glide flag

**Verdict: ACCEPT for the report's bounded conditional source claim.** The ordinary local-client travel trace is supported in both exact source trees. The verdict does not establish how the starting flag state is reached, pair coverage, or runtime parity.

## Immutable inputs and source identity

- Audited report: `c-early-1111-1112-ability-flying-glide-flag-writer/report.md`, SHA-256 `414dfb0fd7e7ae6cda1b074f5ce53c5d40bfdecf9fc6213139422396aac49590`.
- 1.11.1 Feather ready marker SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; source manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`; provenance sidecar SHA-256 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`. Marker names exact `1.11.1`, `ornithe-feather`, Feather Gen2 build.2 artifacts.
- 1.11.2 Feather ready marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; provenance sidecar SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; artifact manifest SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. Marker names exact `1.11.2`, `ornithe-feather`, Feather Gen2 build.2 artifacts. The accompanying verification record reports all 1,921 published source files and raw input artifacts matched; it separately discloses a regenerated intermediate remapped JAR hash difference, which does not undermine the published source-tree identity.

The audited Java-file hashes match their respective source-manifest entries. For both releases: `LivingEntity.java` is `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `LocalClientPlayerEntity.java` is `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; and `PlayerAbilities.java` is `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0`. `PlayerEntity.java` is `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620b3a59065c` in 1.11.1 and `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` in 1.11.2. A full-file comparison shows the only changed line is the unrelated `DataVersion` value (921 → 922); the `moveRelative` override lines are identical.

## Trace checked

- `LocalClientPlayerEntity.isLocallyControlled()` returns true (both: lines 468–470). `LocalClientPlayerEntity.mobTick()` calls `super.mobTick()` (both: lines 790–795); the inherited `LivingEntity.mobTick()` orders `flyingTick()` before virtual `moveRelative(...)` (both: lines 1798–1804).
- `LivingEntity.flyingTick()` reads flag 7, evaluates the fall-flying/ground/riding/elytra conditions, and writes flag 7 only inside `if (!world.isClient)` (both: lines 1810–1829). On the local client this routine therefore does not replace the initially true synchronized bit before travel.
- With `PlayerAbilities.flying == true`, not riding, and dry, `LivingEntity.moveRelative()` passes the liquid gates and sees `isFallFlying()` from flag 7 while running its inherited gliding branch (both: lines 1388–1439). Local control satisfies that method's movement-side gate.
- The `PlayerEntity.moveRelative()` override checks `abilities.flying && !isRiding()`, invokes `super.moveRelative(...)`, then performs its creative-flight adjustments and directly calls `setFlag(7, false)` (both: lines 1387–1405). Thus for the stated prestate the inherited glide simulation occurs before the local player's direct flag clear. `PlayerAbilities.flying` is the same public field in both versions (both: lines 5–13).

No difference was found in this bounded chain. The report correctly treats the true flag as a conditional prestate and leaves NBT/startup, fall-distance consequences, activation/network paths, and artificial state setup outside the claim. No server, runtime, game, build, or test behavior was used for this verdict.

## References

All source references below are relative to `decompiled_minecraft/<version>/ornithe-feather/`:

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `isLocallyControlled()` 468–470; `mobTick()` 632–795.
- `net/minecraft/entity/living/LivingEntity.java`: `moveRelative()` 1388–1439; `mobTick()` 1798–1804; `flyingTick()` 1810–1829.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `moveRelative()` 1387–1405.
- `net/minecraft/entity/living/player/PlayerAbilities.java`: field declaration 5–13.