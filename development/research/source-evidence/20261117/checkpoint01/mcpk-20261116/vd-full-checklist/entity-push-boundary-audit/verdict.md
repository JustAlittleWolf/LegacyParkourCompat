# Independent boundary audit: entity-to-local-player push

Verdict: **CONFIRMED** — the first examined stable release with the client-side living-entity push path is Mojang version ID `1.9` (often normalized in prose as `1.9.0`). The cutover is between exact `1.8.9` and `1.9`; it is not a claim that Mojang's exact ID is `1.9.0`.

## Immutable inputs

- Candidate finding: `build/mcpk-20261116/vd-full-checklist/remaining-117/findings-1.8.9--1.9.4/entity-collision-push.md`, SHA-256 `1841726edfbd2880d436ccd8c3772f7833266279828d866937b47eaf396ca304`.
- Boundary input: `build/mcpk-20261116/vd-full-checklist/entity-push-boundary/cutover-evidence.md`, SHA-256 `da3b91c7f027811d9d0ffc64aa2f255b12a2e22e51173db21ed6d8f68762bdc7`.
- All mapping sources read from canonical `decompiled_minecraft/<version>/ornithe-feather/`; no source regeneration was performed.

## Exact identities and source-integrity checks

Intermediate mapping-local provenance manifest SHA-256 values and their pinned Mojang metadata inputs:

| Exact version ID | Provenance SHA-256 | Mojang metadata input SHA-256 | Entity mapping artifact |
|---|---|---|---|
| `1.9` | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688` (`.../1.9.json`) | Feather `1.9+build.2`, `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| `1.9.1` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56` (`.../1.9.1.json`) | Feather `1.9.1+build.2`, `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| `1.9.2` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `a0b96ff1dabf71650c40957b615188067e6ee338e3a30522b4c3fd8613a25c0f` (`.../1.9.2.json`) | Feather `1.9.2+build.2`, `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| `1.9.3` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `2ec3b7abc12f0ad4d4e3c777dc0c429fa948ffd4d1bdbb89a81aecbd30021be5` (`.../1.9.3.json`) | Feather `1.9.3+build.2`, `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` |

For `1.8.9` and `1.9.4`, the exact-run ready markers identify the release ID, Ornithe Feather mapping, build.2 mapping archives, client input hash and source-manifest SHA. The ready markers' `sourceManifestSha256` values match the actual source-manifest files: `1.8.9` `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; `1.9.4` `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The source files inspected in those releases match their source-manifest entries. For all four intermediate releases, inspected source hashes match the corresponding mapping-local provenance `sources` entries. The intermediate manifests pin exact Mojang metadata and client inputs and Feather build.2 artifacts; see the boundary input for their full hashes.

## Trace and boundary

- In `1.8.9` `LivingEntity.mobTick`, after `moveRelative`, the push call is guarded by `if (!this.world.isClient)`. The query itself grows the querying entity's shape by `(0.2F, 0.0, 0.2F)`, filters non-spectator pushable candidates, and calls `entity.push(this)`. `PlayerEntity.mobTick` delegates to `super.mobTick`. Thus the living-entity query cannot push a player on the logical client in this source.
- In `1.9`, `LivingEntity.mobTick` invokes `pushAwayCollidingEntities()` unconditionally after `moveRelative`; it queries `getEntities(this, this.getShape(), EntityFilter.canBePushedBy(this))`, then delegates to `entity.push(this)`. `MobEntity` extends `LivingEntity` and calls `super.mobTick()`, and the ordinary entity tick path is reached from client `Minecraft` through `World.tickEntities`/`tickEntity`.
- `1.9` `EntityFilter.canBePushedBy` admits a client-side candidate only when it is a local `PlayerEntity` (in addition to pushability, spectator and collision-rule checks). `LocalClientPlayerEntity.isLocal()` returns true. `WorldChunk.getEntities` excludes the supplied query entity. Therefore a nearby mob's query can select the local player; the player's own query cannot select itself. The dispatch calls `localPlayer.push(mob)`. `PlayerEntity.push` suppresses the call only while sleeping and otherwise delegates to `Entity.push`, whose horizontal response can add velocity to the receiving player.
- The same unguarded living-entity query, local-player eligibility and response are present in the exact `1.9.1`, `1.9.2`, `1.9.3` and `1.9.4` sources. In `1.9.3` the self-exclusion implementation and source hashes change, but its `exclude` behavior remains; it does not move the cutover.

Key hash-verified source files include `LivingEntity.java`, `Entity.java`, `PlayerEntity.java`, and `EntityFilter.java` for `1.8.9` and `1.9.4`; and those same files plus `WorldChunk.java`, `LocalClientPlayerEntity.java`, `MobEntity.java`, `World.java`, and `Minecraft.java` for the intermediate path. For `1.8.9` and `1.9.4` each selected file matched `ornithe-feather.sources.sha256`; for intermediate versions each selected file matched its `.decompile-provenance.json` source map. The `1.9` `WorldChunk.java` hash is `b98bccf16277a5a62b09ac2e77db7d77e098a62d8029c03a44fbed62e01ff267`; `1.9.3` is `eb5c4b9ecefdcc17cee106fbfc4c3eee0768e88e4bb36e503fccae852cb276e6`.

## Bounded conclusion

The user-supplied clause, “Entities now push the player when colliding,” is source-supported at stable version ID `1.9`. The candidate's endpoint contrast remains accurate: `1.8.9` client push-query disabled; `1.9.4` client query enabled with the local player eligible as an inbound candidate. The candidate also accurately describes the query-shape change (grown in `1.8.9`, exact shape in `1.9.4`) and player sleeping response gate. The examined exact stable sequence establishes no later first-enable point; it does not establish pre-release/snapshot introduction timing.

## Checked source locations

- `1.8.9`: `LivingEntity.java` 1452–1480; `PlayerEntity.java` 431–448; `Entity.java` `push` at 947–978.
- `1.9`: `LivingEntity.java` 1693–1741; `EntityFilter.java` 55–90; `LocalClientPlayerEntity.java` 302–304; `PlayerEntity.java` 1879–1883; `Entity.java` 1057–1088; `World.java` 2139–2154; `WorldChunk.java` `getEntities` at 663 onward; `MobEntity.java` 60 and 476–479; client tick `Minecraft.java` 1446 and `World.java` `tickEntities`.
- `1.9.1` and `1.9.2`: `LivingEntity.java` 1697–1745; `EntityFilter.java` 55–90; `PlayerEntity.java` `push` at 1879–1883; `Entity.java` `push` at 1057–1088.
- `1.9.3` and `1.9.4`: `LivingEntity.java` 1706–1754; `EntityFilter.java` 56–91; `LocalClientPlayerEntity.java` `isLocal`; `PlayerEntity.java` `push` at 1886–1890; `Entity.java` `push` at 1061–1092.
