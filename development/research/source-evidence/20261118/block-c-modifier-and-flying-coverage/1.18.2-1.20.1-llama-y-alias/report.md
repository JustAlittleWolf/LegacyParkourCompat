# Llama rider Y placement: 1.18.2–1.20.1

TERMINAL: **COMPLETED**

## Bounded verdict

No source-visible change to ordinary adult Llama/TraderLlama passenger Y placement was found from 1.18.2 through 1.20.1, checking each available release: 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, and 1.20.1. The result is a bounded source comparison, not full-pair coverage, independent acceptance, or runtime parity.

For all checked releases through 1.20.1, the ordinary adult local-player writer computes the Y argument in this order:

```java
this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset()
```

The Llama override returns `this.getBbHeight() * 0.6`; the player override returns `-0.35`. The Llama registration uses `.sized(0.9F, 1.87F)` in every checked release. The unsuffixed `0.6` promotes the float bounding-box height to double before multiplication. At default adult dimensions and scale, the passenger Y is therefore `llamaY + (double)1.87F * 0.6 + (-0.35)`, approximately `llamaY + 0.772000002861023`.

The local-player inheritance path is `LocalPlayer extends AbstractClientPlayer extends Player`, so this ordinary player riding offset applies to the client player. Llama placement has a `hasPassenger(passenger)` gate. The direct Llama method retains the same Y expression through the range; in 1.20 the API changes from `positionRider(Entity)` to the protected `positionRider(Entity, MoveFunction)` callback, but the guarded Y expression and callback argument order are unchanged. TraderLlama inherits Llama placement. This verdict does not cover other carriers or riding behavior outside passenger Y placement.

## Exact source references

All paths are under `decompiled_minecraft/<version>/mojmap/` unless noted.

- 1.18.2 `net/minecraft/world/entity/EntityType.java:302–304` registers the default Llama dimensions; `animal/horse/Llama.java:156–168` contains the guard, Y writer, and `getBbHeight() * 0.6`; `player/Player.java:1011–1013` returns `-0.35`; `client/player/LocalPlayer.java` and `client/player/AbstractClientPlayer.java` establish the inheritance chain; `Entity.java:1674–1702` supplies the passenger tick dispatch and generic placement fallback.
- 1.20.1 `net/minecraft/world/entity/EntityType.java:362–364` retains the default dimensions; `animal/horse/Llama.java:161–173` contains the callback form of the same guard, Y writer, and offset; `player/Player.java:1022–1024` returns `-0.35`; `Entity.java:1817–1845` supplies passenger dispatch and generic placement.
- Across 1.19, 1.19.1, and 1.19.2, the Llama source file hash is identical; across 1.19.3 and 1.19.4 it is identical. Across 1.20 and 1.20.1 it is identical. I checked the Llama placement and offset, Player offset, Llama EntityType dimensions, and LocalPlayer inheritance for each listed release. The source files' actual SHA-256 values match their published source manifests; for legacy 1.20 they match its embedded per-file provenance map.

## Direct later boundary: 1.20.2

The old Llama `getPassengersRidingOffset()` hook is replaced in 1.20.2 by `Llama.getPassengerAttachmentPoint(Entity, EntityDimensions, float)` (`Llama.java:487–489`), which returns Y `dimensions.height - (isBaby() ? 0.8125F : 0.5F) * scale`. The Llama-specific `positionRider` override is absent. `Entity.positionRider(Entity)` now gates with `hasPassenger` and dispatches through `getPassengerRidingPosition`; `LivingEntity.getPassengerRidingPosition` uses the attachment point and `yBodyRot`, adds the vehicle position, and the generic position writer then adds the passenger riding offset. `Player.ridingOffset(Entity)` returns `-0.6F`, and `LivingEntity.getMyRidingOffset(Entity)` multiplies it by rider scale. For ordinary adult default dimensions/scales, the source expressions give approximately `llamaY + 1.37F - 0.6F` (about `llamaY + 0.769999980926514`), rather than the prior approximately `llamaY + 0.772000002861023`. This is the next-operation fence; it does not move the observed boundary inside the assigned 1.18.2–1.20.1 range.

## Source identities and provenance

Mapping family is Mojmap throughout. For ready-marked trees, each listed value is the ready marker's SHA-256 for `mojmap.sources.sha256`; the marker identifies the exact version and mapping. The selected-file hashes were checked against that manifest.

| Exact release | Source-manifest SHA-256 |
| --- | --- |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20 | Legacy nested `.decompile-provenance.json` SHA-256: `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. This older embedded per-file provenance format has no ready marker/source-manifest digest or explicit version ID; selected file hashes match its `sources` entries and match 1.20.1 byte-for-byte for the checked placement files. Treat the exact 1.20 release association as less strongly pinned than the other rows. |
| 1.20.1 | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` |
| 1.20.2 boundary | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |

The prior source-audit input was read only after confirming its supplied identity: `llama-rider-height-audit/verdict.md`, SHA-256 `3d91a3be598c66bccc724b5182dce96651f68cc40b0cb305f6b74846be49d9e3`. It bounds the earlier 1.17.1–1.18 Y-factor change and the later 1.20.2 hook fence; this report independently checks the assigned intervening releases.
