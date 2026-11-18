# Blind source audit freeze: 1.15.1 → 1.15.2 fluid-tag / fall-flying gate

Status: COMPLETED (bounded operation audit; no source-confirmed difference in the assigned operation).

## Evidence identity

- Canonical exact sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.15.1/mojmap` and `.../1.15.2/mojmap`; both ready markers identify exact release and `mojmap` namespace. Ready-marker SHA256: 1.15.1 `AFBB16C3CB832FFEE8E4763BF9933D9635ECD811C9FBD7166554B78E59788504`; 1.15.2 `64A0E40B784D525CE50937C63E465FC4F564EFB1F7AF17F2BAC3C77A36907BE0`.
- Source-manifest SHA256: 1.15.1 `C8FDCE2F9432B72FC13C19A48F9AB3BE203CAC41A617139E81BAC3552245912D`; 1.15.2 `CB7FD93F8730D8B2813857744F8572060535C7DE44FCEE86222ACD1CEB6F43E7`. Each consumed source file below was independently SHA256-hashed and matched its corresponding manifest line.
- Provenance SHA256: 1.15.1 `6A7AF1F5D65D7AF165C2AB9A90C1FAE20169B38E70C8E92DBC5F59D94EFE9461`; 1.15.2 `D4802D35EE2927A44D753871F184C3255C060EB94457A5C65A8BC121A087954E`. Artifact-manifest SHA256: 1.15.1 `D2C40F0770820752FE879670B35BD718B87E4F14491B3EC782E2E74DF6B38BBB`; 1.15.2 `208AB867640097A0C188E452DE4876934DEB217D75AC726D358CFA6730260406`.
- Raw client SHA256: 1.15.1 `6E28ECD008D0CA5E1DCD346DBC2882430F02A77A78864D57ACC49FC8BC1B5358`; 1.15.2 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`. Both match the version ready marker; Mojang version JSON IDs are respectively `1.15.1` / `1.15.2`, and client SHA1 / sizes match the metadata (`8B11614BEA9293592A947EA8F4FD72981EA66677` / 15,518,397 and `E3F78CD16F9EB9A52307ED96EBEC64241CC5B32D` / 15,531,492).
- Raw version-metadata SHA256: 1.15.1 `DDA0859BCA7447FF5F071643006B35A51F54E2FBEF851A6665B424CF5743C598`; 1.15.2 `E974511243E845B2427AB635EBA5612481CB420A70C0630AA041EAB3EEB590C5`.

## Frozen independent operation and caller

Operation: update the entity's body-water latch from the fluid tag, then apply the player's direct `isInWater()` exclusion in `Player.tryToStartFallFlying()`.

Paired evidence (Mojmap source paths relative to each release's `mojmap/`):

- `net/minecraft/world/entity/Entity.java`: `tick` calls `updateWaterState` (line 363); `isInWater` returns `wasInWater` (912–914); `updateWaterState` orders `updateInWaterState`, `updateUnderWaterState`, `updateSwimming` (943–947); the water latch selects `FluidTags.WATER`, then sets/clears `wasInWater` (957–977); `checkAndHandleWater` scans the deflated bounding box, checks loaded chunks, tests each `FluidState` against its tag, applies the fluid-height gate, and returns the scan result (2593–2652). These excerpts are identical in both versions; the entire `Entity.java` SHA256 is identical: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- `net/minecraft/world/entity/player/Player.java`: `tryToStartFallFlying` gate is `!onGround && !isFallFlying() && !isInWater()`, before the Elytra check. 1.15.1 lines 1586–1596; 1.15.2 lines 1578–1588. The method text matches. Whole-file hashes (the rest of Player.java may differ): 1.15.1 `6985E11AD9BE553E9A2F25E5DECC20A9DA5C3B59B68F4E83A5B9F448CD2E48A0`; 1.15.2 `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`.
- Tag match path is unchanged: `FluidState.is(Tag<Fluid>)` forwards to its type; `Fluid.is(Tag<Fluid>)` calls `tag.contains(this)`; `FluidTags.WATER` is a wrapper for tag id `water` resolved through `FluidTags`' current `TagCollection`. Relevant source hashes are identical in both versions: `FluidTags.java` `7B75994500C5286295F17227CD89CE0C42FFE829AF57E6A5FF3415E41342953C`; `FluidState.java` `F88BFF7D54EA0220EE67E0FE36A78DF67871E52AE636E584EA26DD107F652C15`; `Fluid.java` `B20C97ED8FDAE1E3EF945340113A2629A8BC53B12FAD84BF1AC6C689669DE13B`; `TagCollection.java` `BAC1A865C19D55D3731646A41B2C4B8E831E422E8ADA3BDC619ACFD5BE841545`; `Tag.java` `8D14BEF6AD4C64D8A6A8B2A6F556DDB3A34AEAF2AC08010FD3AC4C59DDEE1D07`.

## Packed default tags

The four raw files were read from the two exact client ZIPs. Both releases carry byte-identical entries:

- `data/minecraft/tags/fluids/water.json`, 94 bytes, SHA256 `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18`. `replace:false`; explicit default members resolve to `{minecraft:water, minecraft:flowing_water}`; there are no `#tag` includes.
- `data/minecraft/tags/fluids/lava.json`, 92 bytes, SHA256 `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD`. `replace:false`; explicit default members resolve to `{minecraft:lava, minecraft:flowing_lava}`; there are no `#tag` includes.

The default packed tag payloads and memberships therefore do not change. These payloads establish vanilla defaults only: the live `FluidTags` wrapper resolves against the active tag collection, so custom or received datapack/tag membership remains conditional. Lava is irrelevant to this assigned predicate path: the latch is refreshed with `FluidTags.WATER`, and `tryToStartFallFlying` reads only `isInWater()`.

## Verdict and remaining boundaries

No source-confirmed change found in the assigned 1.15.1→1.15.2 operation. A player whose entity latch is water-tagged remains blocked by the same `!isInWater()` fall-flying gate; default tag membership, check order, scan result, latch writes and relevant movement side effects are unchanged. Elytra/equipment mechanics were treated as the requested read-only boundary and not traced. This does not establish runtime parity or a broader pair freeze. Any follow-up outside this bounded path needs its own assignment; actual custom/received tag membership is environment-dependent.