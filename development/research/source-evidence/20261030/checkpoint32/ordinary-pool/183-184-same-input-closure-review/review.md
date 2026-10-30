# Independent source review: 1.8.3 → 1.8.4 same-input closure

## Review identity and scope

Reviewed `.native-source/20261030/ordinary-pool/183-184-same-input-closure/report.md`.
Its SHA-256 is `c0affec7df11b514b36c12f96322f756437713d656365b32946ef5d971b5910d`, matching the assigned expected identity.

This review checked the report's exact source claims against the canonical read-only `ornithe-feather` trees for 1.8.3 and 1.8.4; it did not review product code, wiki state, prior full-pair coverage, or runtime parity.

## Findings

**The bounded local movement conclusion is supported.** The listed hashes for `Block.java`, `Blocks.java`, `PortalBlock.java`, `VineBlock.java`, `LivingEntity.java`, `PortalForcer.java`, `Entity.java`, `PlayerManager.java`, `ServerPlayNetworkHandler.java`, `PlayerMoveS2CPacket.java`, and `ClientPlayNetworkHandler.java` match the canonical files on disk. `Block.java` registers `portal` and `vine` with `PortalBlock` and `VineBlock`; `Blocks.java` resolves `NETHER_PORTAL` and `VINE` from those registry keys. Both files are byte-identical across the pair.

`VineBlock.getCollisionShape` returns `null` on both sides, and the identical `LivingEntity.isClimbing` checks the vine block identity at the player's feet, with the spectator gate. The changed `VineBlock.tick` growth writes are inside `if (!world.isClient)`. The changes can affect vine existence or property state subsequently received from the server, but do not create a vine shape or horizontal-property-dependent climb predicate under equal received block state.

The 1.8.3 `PortalForcer.findNetherPortal` found-portal branch adds the forward-axis offset to `f` or `h`; 1.8.4 omits those two additions. The changed result is set on the entity by `setPositionAndAngles`. `Entity.changeDimension` is guarded by `!world.isClient`; `PlayerManager` invokes the destination portal forcer and then calls the server player's network-handler `teleport`; that handler sends `PlayerMoveS2CPacket`; the unchanged client `handlePlayerMove` applies its position and angles to `minecraft.player`. This is a server-authoritative position response, not a local client movement rule. The same-input caveat in the report is therefore appropriate.

## Correction: client JARs were present and were inspected

The report says that no cached 1.8.3 or 1.8.4 client JAR was present and that no archive inspection was performed. That statement is incorrect for this review environment. The local cached archives are:

| Release | Local archive | SHA-256 | Embedded provenance match |
|---|---|---|---|
| 1.8.3 | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.3/client.jar` | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` | Yes; the 1.8.3 `.decompile-provenance.json` names this digest as its client input. |
| 1.8.4 | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.4/client.jar` | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | Yes; the 1.8.4 `.decompile-provenance.json` names this digest as its client input. |

Each JAR was hashed before it was opened. Its archive entries were then inventoried read-only. Both contain the same asset namespace and path set: `assets/minecraft/{blockstates,font,lang,models,shaders,texts,textures}`. There are no `data/` entries. Every packaged `assets/` entry has the same path and content across the pair except `assets/minecraft/lang/en_US.lang`. These assets provide presentation, language, or shader content; they do not define block registry identities, collision shapes, or the climbing predicate. The report's default-registration conclusion remains supported by the Java source. The archive check replaces the absent-JAR/no-inspection caveat; it does not establish compatibility with custom/modded resources or registries.

## Disposition and remaining gates

The recommendation to freeze this bounded pair for equal effective movement-relevant inputs remains justified. Amend the report's client-JAR availability and archive-inspection statements to reflect the matching provenance-bound archives and this asset inventory. Broaden the vine caveat from possible block-presence changes to possible existence/property-state changes; either is an unequal received block-state input, and the inspected movement consumers do not read the changed vine properties.

This review does not independently re-audit the reused 1.8.2 → 1.8.3 coverage, every changed path for the full pair, other custom registries/resources, or runtime parity. Those remain outside this bounded review and should stay explicit prerequisites for any broader pair acceptance. No residual local movement candidate was found in the two reviewed endpoints.
