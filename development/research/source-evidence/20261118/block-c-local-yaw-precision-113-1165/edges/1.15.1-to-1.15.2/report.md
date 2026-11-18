# 1.15.1 → 1.15.2: direct yaw and first dry relative-velocity contribution

## Exact source identities and byte checks

Compared the read-only generated trees `decompiled_minecraft/1.15.1/mojmap` and `decompiled_minecraft/1.15.2/mojmap` (Vineflower; Mojmap names). Each `mojmap.ready.json` identifies its exact `versionId`, `versionMetadataId`, and mapping (`mojmap`). The 1.15.1 provenance also records the exact metadata ID and official client-mapping input hash. The 1.15.2 ready marker records `versionMetadataId=1.15.2` and client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`.

The ready markers' source-manifest SHA-256 values were recomputed and match: 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. Each cited Java file's SHA-256 also matches its entry in that version's source manifest. `Entity.java` is identical across versions (SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`); `MouseHandler.java`, `Mth.java`, and `Vec3.java` likewise have identical whole-file bytes. Relevant `LivingEntity` and `Player` methods were extracted from the exact source files and compared line-for-line; body hashes are listed below.

Provenance limitation: the 1.15.2 `mojmap.provenance.json` reports `mappingArtifacts: null`; its ready marker and source manifest still pin the exact version label, client hash, and current decompiled bytes, but this provenance record does not provide a raw mapping-artifact digest. This is a source-input traceability gap, not a mismatch in the compared source bytes.

## Ordinary local yaw path

Both versions have the same `MouseHandler.turnPlayer()` (`MouseHandler.java:192–228`). With mouse captured, window active, and a non-null local player, unsmoothed motion computes `j = accumulatedDX * g` and calls `player.turn(j, k * n)`. There is no finite-value guard in that path. The inherited `Entity.turn(double d, double e)` is identical (`Entity.java:326–338`): it computes `g = d * 0.15` as `double`, adds that double to the float current `yRot` and previous `yRotO` separately, casts each result to `float`, does not wrap yaw, and contains no finite-value gate. Only pitch is clamped; the vehicle callback is unchanged.

A concrete finite ordinary-input witness is sensitivity `0.5F`, one accumulated horizontal mouse pixel, smoothing off, and the mouse/window/player gates true. The source arithmetic gives `f=0.5F`, `g=1.0`, `j=1.0`; for `yRot=yRotO=0.0F`, each release writes `(float)(0.0F + 1.0 * 0.15)`, i.e. `0.15F`, to both yaw fields. This witness is lawful and finite and follows the actual mouse-to-player call. It does not distinguish the releases: their method bytes are identical.

## First grounded/air dry relative-velocity contribution

`LocalPlayer.isEffectiveAi()` returns `true` in both versions (`LocalPlayer.java:490–492`), so the top `LivingEntity.travel` gate (`LivingEntity.java:1831–1832`) admits the local player on the logical client. A non-water, non-lava, non-fall-flying local player reaches the dry branch; it reads the friction block below, computes the grounded/air damping factor, then makes its first relative-input contribution at `LivingEntity.java:1886–1890` with `moveRelative(getFrictionInfluencedSpeed(v), vec3)`. `Player.travel` delegates to this same superclass path for a non-flying player (`Player.java:1481–1483` in 1.15.1; `1473–1475` in 1.15.2), and its complete method body is otherwise equal.

The first contribution is byte-identical across the pair:

- `LivingEntity.travel(Vec3)` lines 1831–1987: equal body, SHA-256 `fcad2aeb74014e783c0c9510c1b5f434e6f07854a6cb9db54cb8469aca0917d6`.
- `getFrictionInfluencedSpeed(float)` lines 2006–2008: equal body, SHA-256 `97dc0a035607e2153384a5a55bff437f47f7fbda38d06df2010ef7514e5bb868`. Ground uses `getSpeed() * (0.21600002F / (friction * friction * friction))`; air uses `flyingSpeed`.
- `Entity.moveRelative` lines 1063–1066 and `getInputVector` lines 1068–1078: equal bodies (SHA-256 `ca44b22bf1203df8d36c6ba044120bb4884190168cbb164a0d953ce80c4ee11e` and `8da15894a9a4eb89cbdfac7cd31da3d60bb31591975bb2a5a5707225b5d6dbee`). Both preserve the `lengthSqr() < 1.0E-7` zero-input gate, `> 1.0` normalization gate, float scaling/trig, and double-vector addition into current delta movement.
- `Mth.sin`, `Mth.cos`, and the whole `Vec3.java` are byte-identical; there is no changed trig lookup or relative-input rotation in this slice.

For a finite movement witness, take nonzero forward input `(0.0F, 0.0F, 1.0F)`, finite yaw `0.15F` from the yaw example, and an ordinary dry local-player state. The local-player AI override opens the actual movement gate. On ground the finite friction-influenced speed is passed as the relative contribution; in air the finite `flyingSpeed` is passed. The same equal `getInputVector` body rotates either contribution in both versions, and `travel` applies the same later damping/gravity statements. This is source reachability evidence only; no game or runtime test was run.

## Result

No difference found in this assigned slice: direct yaw numeric widths, casts, current/previous updates and finite gates are unchanged; the first grounded/air dry relative-velocity contribution, its actual local-player gate, and its trigonometric dependency are unchanged. This closes only the assigned C slice. It does not claim full-pair inventory coverage or an independent freeze. No separate acceleration/trig delta is established by these methods.