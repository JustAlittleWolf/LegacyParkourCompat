# 1.21.0 → 1.21.1 local-player movement source comparison

## Scope and verdict

This is a bounded source-only comparison of the ordinary local-player tick, keyboard input, pose/dimension, travel, and direct received player-state paths. The hash-pruned movement roots checked are unchanged between the two Mojmap trees. No local-player tick, input, travel, pose, movement-predicate, or direct client response source delta was found in the reviewed paths. This does not establish runtime parity, a product change, or a full-pair source freeze.

## Exact source identity

Canonical sources are read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release | Identity evidence | Verified counts/pins |
| --- | --- | --- |
| 1.21.0 (`1.21/mojmap`) | Embedded `.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c` | 73 input pins; 5,363 source-map paths; 5,363 Java files. Provenance pins `1.21.json` SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`, client JAR SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, and `client.txt` SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. The complete source-path set matches the embedded map. |
| 1.21.1 (`1.21.1/mojmap`) | Release-parent `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | Ready marker says version 1.21.1, Mojmap, ready, 5,363 sources, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, metadata ID 1.21.1. `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` and `artifacts.sha256` SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` match the ready marker. Actual Java path set matches the source map. |

The 1.21.1 mapping directory has no embedded `.decompile-provenance.json`; the release-parent provenance/ready/manifests were checked. No raw JAR or mapping download was re-fetched. The 1.21 embedded manifest and 1.21.1 sidecar contain exactly the same 5,363 Java paths: 17 same-path hash changes, no additions, and no removals. Both actual Java path sets match their maps. Every changed path was rehashed against both map entries; each matched. See [changed-source-paths.tsv](changed-source-paths.tsv).

## Movement roots and consumers checked

The exact file SHA-256 values in [selected-source-hashes.tsv](selected-source-hashes.tsv) match both source maps. The whole files are byte-identical across the releases for:

- `LocalPlayer`, `Player`, `LivingEntity`, and `Entity`, covering the local tick, `aiStep`, jumping/sprinting state, pose selection, travel, movement writes, and entity movement predicates.
- `Input` and `KeyboardInput`, covering sampled keys and movement input construction.
- `ClientPacketListener`, covering direct player correction and received-state dispatch.
- `EntityDimensions`, `Level`, `BlockGetter`, `BlockState`, `BlockBehaviour`, `Blocks`, `Block`, and `AABB`, covering selected player dimension and the state/query entry points at this scope boundary.
- The selected clientbound position, entity-motion, entity-relative-move, effect, attribute, and ability packet classes.

Because the complete source files for these roots are identical, their method bodies, call order, constants, and gates are identical for this pair. The checked direct client response path therefore shows no difference for equivalent received packets/state. Server packet production is not compared.

## Changed Java source paths

The 17 changed paths from the complete source-map comparison are all listed with their old/new hashes in the TSV. Their changes fall outside the local-player movement roots:

- `ReportEnvironment`, `DetectedVersion`, and `SharedConstants` update version/report metadata.
- Command argument and chat-content classes change selector parsing, permissions, or chat construction.
- `ChiseledBookShelfBlock`, `LecternBlock`, `SculkSensorBlock`, and `ShulkerBoxBlock` change block-entity removal/neighbor-signal callback ordering.
- `BlockEntity` adds block-state validation; `LevelChunk` removes an invalid block entity when its state no longer matches.

The four changed block classes do not change their collision-shape methods in this diff. The block-entity changes concern state validation/removal rather than local-player movement math or client movement-packet handling. Their possible indirect effects through future block-state or neighbor-update inputs were not traced here; server-produced state changes and collision-provider internals remain outside this bounded sweep.

## Boundaries

This comparison does not close packaged resources/tags, all block callbacks or collision providers, every external movement input, server-side producers, or runtime trajectory behavior. It only establishes that the checked local-player movement and direct client-response source files are equal at the exact 1.21.0 and 1.21.1 endpoints.
