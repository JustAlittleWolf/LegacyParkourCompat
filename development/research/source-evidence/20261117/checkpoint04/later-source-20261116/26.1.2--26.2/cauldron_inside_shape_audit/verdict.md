# Independent audit verdict — cauldron inside shape

Bound report: `build/later-source-20261116/26.1.2--26.2/cauldron_inside_shape/report.md`  
Bound report SHA-256: `6D74970D411F31BA46A8B4822DD78F8320DBBCBC07006731631E8BF50560751D`

## Verdict

**Confirmed for the bounded source slice, with its dependency gate still open.** I independently verified the report's 26.1.2 → 26.2 no-difference conclusion for `LavaCauldronBlock`'s entity-inside shape and fire-related callback, plus the bounded dispatch and player reachability path. The report correctly leaves `S-POST-BLOCK-CAULDRON-INSIDE-SHAPE` partial/unclosed. This verdict does not accept the operation as a completed finding, close pair coverage, or freeze the pair.

## Evidence checked

- Rehashed both root-sidecar identity sets. `unobfuscated.ready.json` identifies the exact versions and unobfuscated namespace; the source and artifact manifest hashes match each ready marker. Source manifests identify 6,882 files for 26.1.2 and 7,055 for 26.2. Provenance reports exact-version Vineflower 1.12.0 runs.
- Rehashed cited files against both source manifests. `LavaCauldronBlock.java`, `AbstractCauldronBlock.java`, `InsideBlockEffectType.java`, and `InsideBlockEffectApplier.java` have matching manifest entries and are byte-identical across endpoints. `BlockBehaviour.java`, `Entity.java`, and `LivingEntity.java` rehash to each endpoint's manifest; their bounded members below match despite other file differences.
- `LavaCauldronBlock.java` 19–20, 42–58 is identical: it unions the cauldron rim with `Block.column(12.0, 4.0, 15.0)`, returns that filled shape from `getEntityInsideCollisionShape`, requests `CLEAR_FREEZE` and `LAVA_IGNITE`, and registers `Entity::lavaHurt` after lava ignition. No state-property branch appears in the bounded shape or callback.
- `AbstractCauldronBlock.java` 28–39, 70–76 is identical. Its ordinary collision shape (`getShape`) and interaction shape are separate from the lava cauldron's entity-inside collision shape.
- `BlockBehaviour.java` 331/337 and BlockState forwarding at 688–689 / 774–777 (26.1.2), corresponding to 331 and 694–695 / 780–783 (26.2), preserve the default entity-inside shape and forward both shape and callback to the block.
- `LivingEntity.java` calls `applyEffectsFromBlocks()` after travel under `!level.isClientSide() || isLocalInstanceAuthoritative()` at 3076–3077 / 3143–3144. `Player.tick()` calls `super.tick()` at 259 / 260, so the inherited player tick reaches this path when the client player is locally authoritative.
- `Entity.java` `applyEffectsFromBlocks` (839–897 / 903–961) and `checkInsideBlocks` (1225–1283 / 1308–1365) preserve the bounded flow: movement segments are checked, `state.getEntityInsideCollisionShape(...)` is intersected against the moving entity, then `state.entityInside(...)` is dispatched for an inside-block hit. This is the entity-inside shape query, not the ordinary solid collision-shape query. The cited effect-type bindings and collector are byte-identical; damage calculation remains outside this audit's claim.

## Limits retained

Effective resource/tag membership and registry loading, unrelated callbacks and movement paths, and pair-wide tick coverage remain unexamined. The report's stated partial dependency and exclusions are appropriate; no inference is made beyond the bounded path above. No intermediate-version onset, implementation, or runtime-parity claim is established.
