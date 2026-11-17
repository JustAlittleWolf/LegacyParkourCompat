# Independent source audit — ground speed-factor/provider residual

## Verdict

**ACCEPT** the bounded source-only inventory in `ground-speed-factor-provider-residual.md` (SHA-256 `61FBEFF3B0032302B7682CFFA3D0CA5D70565E9750AB703834B2B04CD4164494`). This accepts only the compared player block-speed-factor override/consumer paths and the stated open resource dependency. It does not freeze the pair or establish resource membership, effective tag values, or runtime parity.

## Source identity and report binding

The report hash matches current bytes. It correctly reuses the prior bounded comparison `ground_friction_unobfuscated_revision_r2/report-r2.md` (SHA-256 `1b24ef978d38a2d5a89543f0ee8b208a288d371e8a29377ccc16fedac50b691f`) and its ACCEPT verdict `ground_friction_unobfuscated_r2_audit/verdict.md` (SHA-256 `d0b5b3c433936bdaae6a6c581c3d2ca9540ea08a6d923905c67d107e941300e1`); both references match their current bytes.

The exact endpoint identities match the ready markers and sidecars: 1.21.11 `mojmap` root `decompiled_minecraft/1.21.11/mojmap/`, ready SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`, source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; 26.1.2 `unobfuscated` root `decompiled_minecraft/26.1.2/unobfuscated/`, ready SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, provenance SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`, source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`. The source manifests and provenance sidecars are present at those paths and match the reported digests.

All six newly bound Java files were rehashed from disk and matched both endpoint manifest entries and the report: `Entity.java`, `LivingEntity.java`, `Player.java`, `Attributes.java`, `Enchantments.java`, and `BlockTags.java`.

## Bounded source findings verified

- A canonical-root search for `getBlockSpeedFactor()` declarations found the same relevant `Entity` → `LivingEntity` → `Player` chain at both endpoints. `Player` returns `1.0F` when flying or fall-flying and otherwise delegates. The only additional override in either source tree is `AbstractMinecart.getBlockSpeedFactor()`; other matches are call sites, not overrides.
- `LivingEntity.getBlockSpeedFactor()` uses `Mth.lerp((float)getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), super.getBlockSpeedFactor(), 1.0F)` at both endpoints. The attribute is registered with default `0.0`, minimum `0.0`, maximum `1.0` at both endpoints. The inspected Soul Speed effect uses the same `+1.0F` movement-efficiency modifier under the same movement condition and checks `BlockTags.SOUL_SPEED_BLOCKS`; that dependency is distinct from the support-position tags left open here.
- `Entity.move` calls `getBlockSpeedFactor()` after collision clipping and the movement-emission branch, then multiplies X/Z delta movement by the returned factor. The two implementations preserve the stated operation order; the client/server/passenger gates for emitting movement effects do not guard this multiplier.
- A canonical block-source search found `getSpeedFactor()` and `getFriction()` declarations only on `Block`, with no block subclass override in the searched endpoint source roots. This verifies the claimed getter-override closure, not every property assignment.
- `Entity.getOnPos(float)` uses the same FENCES/WALLS/fence-gate support-position rule in both versions. `BlockTags.create(String)` uses the default `minecraft` namespace. The corresponding modern resource identifiers are `data/minecraft/tags/block/fences.json` and `data/minecraft/tags/block/walls.json`.

## Open dependency and limits

The report appropriately leaves FENCES/WALLS packaged membership open. The inspected pair resource-input inventory under `build/source-resource-inputs-20261117/movement-gates/` and its `development/research/source-evidence/20261117/checkpoint04/source-resource-inputs-20261117/movement-gates/` checkpoint contains no pair-local fences or walls entry. Tag constants/provider declarations do not prove archive membership. The next gate is exact pinned-artifact verification for those four endpoint entries and payloads; effective replacements remain outside this report.

This verdict closes only the Java override and consumer inventory described above. No runtime, resource archive, effective tag, product, or full-pair claim is made.
