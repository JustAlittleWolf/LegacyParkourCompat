# Independent resource gate audit — 1.16 → 1.16.1 r2

Verdict: ACCEPT for the bounded source-derived movement tag allowlist and its pinned archive entries.

## Immutable inputs

- Packet: `build/source-mid-patches-20261116/1.16--1.16.1/resource_gate/input-reference-r2.json`
- Packet SHA-256: `0b26d71d485973a8ddd254edce88ce6ebac8fe84db961887927274c0b79154ca` (recomputed from exact bytes).
- Bound source coverage: `build/source-mid-patches-20261117/1.16--1.16.1/ordinary_player_revision_r4/coverage-r4.md`, SHA-256 `4cc8599cb3ab2155923cc35a1e1bf62e1d18e36834cd8bcf71ab5a4bdd269440`.
- Coverage audit: `build/source-mid-patches-20261117/1.16--1.16.1/ordinary_player_r4_audit/verdict.md`, SHA-256 `455d9787d7766e226a24f356a58b162bebf310923f031f02fd3039d51298a9f4`, ACCEPT for the bounded source correction.
- Prior scope audit: `build/source-mid-patches-20261116/1.16--1.16.1/resource_gate/audit/verdict.md`, SHA-256 `64b29efb4ce87aa1387fe38fe76df16cbd14e21f8c863364c49efd231d36f30f`, whose omissions and unsupported entries are specifically corrected by r2.

## Source identity and allowlist

The packet's exact 1.16 and 1.16.1 Mojmap source-manifest, ready-marker, artifact-manifest and provenance hashes match the local sidecars. The packet's seven cited Java-file hashes per endpoint (BlockTags, FluidTags, StaticTagHelper, Entity, LivingEntity, LocalPlayer, ResourceLocation) each match local source bytes. Each declared source manifest digest matches `mojmap.sources.sha256` for its endpoint.

The relevant source uses exactly these eight tags: block `climbable`, `fences`, `walls`, `fire`, `soul_speed_blocks`, and `portals`; fluid `water` and `lava`. Exact Entity/LivingEntity/LocalPlayer references at both endpoints support the movement-relevant list, including `LivingEntity`'s previously omitted `BlockTags.PORTALS` use at line 1862. `StaticTagHelper.bind` passes the symbolic string into the one-string `ResourceLocation` constructor; its decomposition defaults an omitted namespace to `minecraft`, so the packet's `data/minecraft/tags/{blocks,fluids}/...` entry paths are consistent. The prior audit's `leaves` and `shulker_boxes` entries are removed from the corrected allowlist.

## Pinned archive and payload verification

Both cached client jars match the exact packet pins and sizes: 1.16 is 17,492,200 bytes, SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`; 1.16.1 is 17,492,432 bytes, SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`. For all 16 endpoint entries, the archive contains the exact allowlisted path; archive entry size and SHA-256 match the packet and extracted file. The raw entry bytes parse as JSON and their `replace`/ordered `values` payloads match the packet declarations. No unlisted entry was used. The 1.16.1 water/lava resource paths also retain the exact previously accepted packet and audit bindings specified in r2.

## Limits

This ACCEPT is limited to the corrected eight-tag resource allowlist, source identity bindings, and exact pinned archive membership/payloads. It does not accept a movement-effect or candidate applicability claim, close other source dependencies, establish datapack behavior or runtime parity, or freeze the release pair. Provenance remains as explicitly stated in the packet: 1.16.1's source provenance sidecar does not itself establish a metadata-to-jar link; this audit verifies the official metadata/client record, ready/artifact-manifest bindings, and matching cached client hash without claiming a stronger provenance relation.
