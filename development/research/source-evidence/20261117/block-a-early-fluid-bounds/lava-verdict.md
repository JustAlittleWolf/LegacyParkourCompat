# Independent source verdict: early lava material scan

## Frozen inputs

- Source roster: `source-roster.tsv` (88 source-file digests), SHA-256 `1a43afe74b7df914bc7d55b24fb03c1fd53dba7d86e54ebbaa76c6a901d25c29`.
- Source identity index: `source-identities.tsv`, SHA-256 `59dc93d840b336266836b96c24af082d1aa4fafb3c47b4ebcb59e174169570fb`.
- Immutable claim `comparison.md`, SHA-256 `172244792621906f5b8ca1ecb5e896310df6ab079c933620a1e4a0344d13418b`.
- Immutable claim `lava-material-scan-exact-upper-bound.md`, SHA-256 `2d021565e3ec3ac778dcfd766307c8755c217671063f01ccc5c2db9d1988867d`.
- All 88 selected Java files matched the source digest in their mapping-root provenance source map or version-root `ornithe-feather.sources.sha256` manifest. The exact version/mapping input identities and root-sidecar digests are recorded in `source-identities.tsv`.

## Claim 1 — `comparison.md`: REJECT AS SUBMITTED

The scan difference and reachable-position witness in this file are supported. Its release-attribution assessment is incorrect: it labels the 1.8.9, 1.9.4, and 1.10.2 trees as having no provenance and therefore unverified release attribution. The relevant provenance artifacts are version-root siblings, as allowed by the review assignment, and their chains are internally verified:

- `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.verification.json`, and `ornithe-feather.sources.sha256` identify version 1.8.9 / Feather Gen2 build.2. The ready marker reports 1,612 sources; verification reports `verified-identical-source-and-input-artifacts`, 1,612 verified sources, zero source differences, and zero missing sources. The ready marker's source-manifest digest matches the version-root manifest file.
- The same four sidecars for 1.9.4 identify Feather Gen2 build.2; the ready marker reports 1,819 sources and verification reports 1,819 verified, zero differences, and zero missing sources.
- The same four sidecars for 1.10.2 identify Feather Gen2 build.2; the ready marker reports 1,845 sources and verification reports 1,845 verified, zero differences, and zero missing sources.

Thus the version sweep is authenticated through 1.8.9 (old form) and 1.9 (new form), with the subsequent listed releases retaining the new form. The first stable release with the new upper-bound rule in this inspected sequence is 1.9; the last 1.8 release, 1.8.9, has the old rule. The false “attribution unverified” label prevents accepting the comparison document as written, while its underlying code comparison remains sound.

Exact evidence for the rule: in `1.8/ornithe-feather/net/minecraft/world/World.java:1504-1523` and `1.8.9/.../World.java:1529-1549`, each upper limit is `floor(bounds.max* + 1.0)` and each loop is `< upper`. In `1.9/.../World.java:1598-1620` and the remaining listed 1.9.x/1.10.2 `World.java` methods, each upper limit is `ceil(bounds.max*)`, also with `< upper`. For nonintegral maxima both expressions produce the same integer; for `max* = n`, they yield `n + 1` and `n`, respectively.

## Claim 2 — `lava-material-scan-exact-upper-bound.md`: ACCEPT

The exact upper-bound change and the stated native jump-predicate consequence are supported.

- `1.8/ornithe-feather/net/minecraft/entity/Entity.java:839-841` and `1.9/.../Entity.java:939-941` both call `containsMaterial(getShape().grown(-0.1F, -0.4F, -0.1F), Material.LAVA)`.
- `1.8/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:411` sets the ordinary player size to `0.6F × 1.8F`. In `1.8/.../Entity.java:225-232`, `setPosition` computes `width / 2.0F` in `float` before using it in the double box coordinates. `1.8/.../util/math/Box.java:59-67` adds the widened `dx` to maxima, and the lava call supplies the widened value of `-0.1F`.
- With `h = (double)(0.6F / 2.0F) = 0.30000001192092896` and `e = (double)0.1F = 0.10000000149011612`, the proposed center `x = 1.0 - h + e = 0.7999999895691872` is a representable double. `setPosition` makes the original maximum `x + h = 1.1000000014901161`; `Box.grown` then evaluates `(x + h) + (-e)`, which is exactly `1.0` in double. The grown minimum is `x - h + e = 0.5999999791383743`. Consequently `floor(minX) = 0`; the 1.8 exclusive upper is `floor(1.0 + 1.0) = 2`, while the 1.9 exclusive upper is `ceil(1.0) = 1`.
- At `y = 0`, `z = 0.5`, and with lava at `(1,0,0)`, the y and z intervals include zero on both versions. The old x interval visits x=1 and the new interval does not. The witness block is in the same ordinary client chunk as the player.
- `LivingEntity.tick` checks water, then lava, then the grounded jump in both `1.8/.../entity/living/LivingEntity.java:1427-1439` and `1.9/.../LivingEntity.java:1678-1690`. With water absent and jump held, the old scan selects `jumpInLava`; `LivingEntity.java:1111-1113` in 1.8 and `:1285-1287` in 1.9 add `0.04F` to Y velocity. The new scan falls through to the grounded `jump()` and cooldown 10; `LivingEntity.java:1092-1105` in 1.8 and `:1266-1279` in 1.9 show that the ordinary jump writes jump strength to Y velocity (and applies its sprint additions when applicable). This is a direct branch and velocity-write consequence, not a predicted later trajectory.

### Client scope and loaded-area gate

The lava scan itself does not call `isAreaLoaded`; its `World.containsMaterial` loop reads `getBlockState` directly. `World.getBlockState` obtains the chunk (`1.8/.../world/World.java:593-600`; `1.9/.../World.java:614-621`). The queried witness block is at x=1 and the local player is in chunk x=0, so the witness requires no neighboring or unloaded chunk. The local client player returns true from `isLocallyControlled()` (`1.8/.../client/entity/living/player/LocalClientPlayerEntity.java:389-391`; `1.9/.../LocalClientPlayerEntity.java:443-445`). The 1.9 client world's `isChunkLoadedAt` implementation is `allowEmpty || !getChunkSource().getChunk(...).isEmpty()` (`1.9/.../client/world/ClientWorld.java:103-105`); it is used by area-loaded queries but does not gate this scan. In `moveRelative`, the added logical-side disjunct in 1.9 does not remove the local player's existing true local-control path (`LivingEntity.java:1115-1118` in 1.8; `:1289-1292` in 1.9).

## Terminal status

COMPLETED — source-only independent verdicts recorded. No implementation, build, test, runtime launch, decompilation, network access, or writes outside the assigned visualization directory were performed.
