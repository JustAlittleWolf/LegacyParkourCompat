# Independent source audit: 1.10.2 → 1.11

**Verdict: REQUEST CHANGES for the two candidate revisions.** The coverage report is accepted only as an explicitly partial source inventory. The exact pair is not frozen.

## Bound inputs

- Coverage: `build/source-early-patches-20261117/1.10.2--1.11/coverage.md`, SHA-256 `b6789f01de090321f9f05e79a99b35e9ee6739a141a26ded0b510d643fb24069`.
- Sneak-edge candidate: `build/source-early-patches-20261117/1.10.2--1.11/findings/sneak-edge-piston-mover-type.md`, SHA-256 `2a6a07709aa63ddfb62febb5b31170f987bf2fe035ac45315267983a477c1eaf`.
- Moving-piston candidate: `build/source-early-patches-20261117/1.10.2--1.11/findings/moving-piston-client-response.md`, SHA-256 `6634a107d75460d8bab21d9d6a14ceede2d5e8b8f6e5c88d2be42941d3bb8694`.
- Exact inputs: 1.10.2 root marker `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`, manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11 root marker `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`, manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.

## Verified claims

Both manifests match the exact source trees: 1,845 and 1,918 entries, with no missing, extra, or hash-mismatched files. Independent path comparison confirms 1,842 shared paths, 762 changed shared files, 76 additions, and 3 removals. All cited movement files in the report match their manifest rows.

The `Entity.move` comparison supports the stated source change: 1.10.2 applies the grounded/sneaking/player edge-reduction loop to every non-noClip move; 1.11 limits it to `MoverType.SELF` and `PLAYER` and changes the support-query offset from `-1.0` to `-stepHeight`. Both retain sequential Y/X/Z clipping. `MovingBlockEntity` calls untyped `move` in 1.10.2 and `move(MoverType.PISTON, …)` in 1.11. `Minecraft.tick` calls each version's `World.tickEntities`; that loop ticks loaded, in-border block entities without a logical-side guard. These observations support a client-call route, conditional on the relevant block entity and entity being present.

The second candidate correctly identifies source-level differences in moving-piston timing, selected shape, overlap calculation, mover type, and the retracting-source follow-up. Its state-shape and final clipping dependencies remain open as reported; no net trajectory is established.

The report's `World.getCollisions(Entity, Box)` comparison is supported for the listed method: the scanned bounds, loaded-chunk gate, border/default-stone selection, block collision dispatch, and entity-collision collection match. Provider state domains and full provider closure remain open.

## Required corrections

The sneak-edge candidate omits two gates needed for its stated movement consequence:

1. `Entity.move` enters its `noClip` branch before the sneak-edge guard. State the candidate's precondition as `noClip == false`.
2. Both piston callers use `World.getEntities(null, bounds)`, whose default filter is `EntityFilter.NOT_SPECTATOR`; a spectator player is not selected. State the player-selection precondition as non-spectator.

The phrase “proceeds through normal block clipping” also needs qualification. The 1.11 piston caller brackets `Entity.move(MoverType.PISTON, …)` with the `NOCLIP_DIR` thread-local. `MovingBlockEntity.addCollisions` then suppresses its own moving-block collision shape while the push is active and progress is below 1.0; other world collision boxes still participate in `Entity.move`. Keep the consequence conditional and do not imply the moving-piston collision shape clips that same push normally.

These gates are visible in 1.10.2/1.11 `Entity.java` (`move`), `World.java` (`getEntities`), `EntityFilter.java` (`NOT_SPECTATOR`), and 1.11 `MovingBlockEntity.java` (`moveEntities` and `addCollisions`). Verified source SHA-256 values: `Entity.java` 1.10.2 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, 1.11 `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97`; `MovingBlockEntity.java` 1.10.2 `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`, 1.11 `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a`; `World.java` 1.10.2 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`, 1.11 `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47`; `EntityFilter.java` 1.10.2 `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`, 1.11 `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629`.

The moving-piston candidate should state the same non-spectator selection gate. Its source-level formula differences are supported, but its player movement consequence stays conditional on an intersecting shape and the unresolved collision dependencies.

## Coverage status

The coverage report correctly leaves the pair partial: full input/tick order, changed entity responses, provider registry/state/callback closure, fluids/climbing, effects/attributes/equipment, and other client responses are unexamined or partial. The 762-path inventory is a path/hash inventory, not a semantic disposition of all changed sources. Do not transfer the frozen `1.10.2→1.11.2` endpoint verdict to this edge. After immutable candidate corrections, audit only the changed claims and bindings; full-pair freeze still requires a separate complete inventory and dependency audit.
