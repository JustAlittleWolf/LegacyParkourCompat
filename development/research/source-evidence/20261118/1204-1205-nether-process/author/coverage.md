# 1.20.4–1.20.5 ordinary Nether portal process coverage

## Bounded result

Compared exact stable 1.20.4 and 1.20.5 Mojmap client source for an unmounted local player contacting an ordinary Nether portal, carrying the pending contact into the local tick, client-side portal feedback/cooldown processing, and any resulting first movement-tick effect. No independent movement behavior difference was found in this slice, so there is no difference draft. This is a bounded result, not a full-pair completion claim.

## Inputs and integrity

Both trees are `decompiled_minecraft/<version>/mojmap`, prepared with Vineflower and official Mojang client mappings. `mojmap.ready.json` reports exact version and Mojmap namespace; the per-version `mojmap.sources.sha256` digest matches its `sourceManifestSha256` field. Every cited Java file's SHA256 below matches its entry in that manifest, so the cited source files are intact relative to the pinned source manifests.

| Version | Client jar SHA256 | Official client mappings SHA256 | Source manifest SHA256 | Ready marker SHA256 | Provenance SHA256 |
|---|---|---|---|---|---|
| 1.20.4 | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` |
| 1.20.5 | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` |

Artifact checksums come from each `artifacts.sha256`; the ready and provenance identities are the version-root files shown. 1.20.4 provenance identifies `mojmap`; 1.20.5 provenance explicitly identifies the mapping family as official Mojang client mappings. The official mappings input hashes above are the `client_mappings.txt` entries in the artifact manifests.

## Ordered trace and paired evidence

1. **Contact callback and eligibility.** `NetherPortalBlock.entityInside` calls `Entity.handleInsidePortal(pos)` only when `Entity.canChangeDimensions()` is true. The body is equivalent in both versions (`NetherPortalBlock.java:84–87`); 1.20.4 declares the override `public`, while 1.20.5 declares it `protected`. `Entity.handleInsidePortal` first checks `isOnPortalCooldown()`: if true it resets cooldown using `setPortalCooldown()`; otherwise it records the pending `isInsidePortal` flag. On the server only, it also records a changed entrance position. Paired method: `Entity.java:2027–2036` (1.20.4), `2049–2058` (1.20.5). No difference in predicates, writes, or order.
2. **Entity tick and server dwell boundary.** `Entity.baseTick` invokes `handleNetherPortal()` before subsequent base-tick work (`Entity.java:416–435` / `418–437`). That method's entire dwell counter and dimension-change path is guarded by `level() instanceof ServerLevel`; on server it increments `portalTime` while `isInsidePortal`, requires a destination level, enabled Nether, and a non-passenger, then compares `portalTime++ >= getPortalWaitTime()`, sets the configured cooldown and changes dimension. When no portal contact is pending it decays `portalTime` by four and clamps at zero. The method resets the pending flag and processes cooldown in the same order in both versions (`Entity.java:2039–2068` / `2061–2090`). This is server authority; it does not run local historical client movement.
3. **Local-player callback timing and client feedback.** `LocalPlayer.aiStep` calls `handleNetherPortalClient()` before its input work and `super.aiStep()`, except while the screen is a `ReceivingLevelScreen` (`LocalPlayer.java:646–654`, 1.20.4; `648–656`, 1.20.5). Portal block contact produced during a movement callback later in that tick therefore remains pending until the next eligible local `aiStep`. The client handler copies current spinning intensity to its old value, handles pending contact by eligible screen closure and trigger sound, selects `0.0125F` spin intensity, clears `isInsidePortal`, clamps the updated effect, then calls `processPortalCooldown()` (`LocalPlayer.java:815–843` / `822–850`). These gates, arithmetic, state writes, and ordering match. This handler does not write position or velocity or otherwise alter movement physics; first post-contact client movement receives no movement change from this portal-processing slice.
4. **Cooldown policy and wait-time input.** `Entity.isOnPortalCooldown` is `portalCooldown > 0`, `processPortalCooldown` decrements exactly one when positive, and default `getDimensionChangingDelay()` is 300 in both versions (`Entity.java:498–509, 2070–2072` / `500–511, 2092–2094`). `Player.getPortalWaitTime()` uses `Math.max(1, game-rule integer)` and selects creative/default Nether portal delay from invulnerability in both versions (`Player.java:408–415` / `428–435`). This is the upstream server dwell dependency, read-only here; the client-side local handler only decrements an already-present cooldown.

## Source pins

Paths are relative to `decompiled_minecraft/<version>/mojmap/`. Full-file SHA256 values match each version's source manifest.

| Source | 1.20.4 SHA256 | 1.20.5 SHA256 |
|---|---|---|
| `net/minecraft/world/level/block/NetherPortalBlock.java` | `d349c04fcd934a9dfd9e2ba9d6fb53d58944bddaf750d77628ce8e0970cbf580` | `ba6e3ce5cb9c24eb4e3dd30e3193e9e114db8acd36aa47246dc4793605f12023` |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |

## Comparison limits

Covered: portal block callback and `canChangeDimensions` gate; pending-contact flag and cooldown gate; `baseTick` caller ordering; server-only dwell/cooldown authority as a dependency; local-player handler gate, order, state updates and first subsequent client movement effect; player game-rule wait-time input. Ordinary unmounted Nether contact only.

Excluded by assignment: End portal/gateway paths, portal shape/geometry providers except the callback dependency, rendering/UI behavior beyond noting its handler gate and closure side effect, general movement work outside this portal-triggered slice, and all other pair movement areas. The `public` to `protected` visibility change on the block callback is an API visibility change only and does not create a player movement finding.
