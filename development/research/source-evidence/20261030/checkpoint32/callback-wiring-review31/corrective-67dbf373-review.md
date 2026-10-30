# Callback wiring review supplement: corrective revision

Reviewed exact revision: `67dbf3736a9cd464422a849a8d704ee37620c57f` in the isolated integration repository.

Verdict: **accepted for the scoped callback-integration wiring review**. The two blockers in the initial review are corrected.

## Confirmed corrections

- `EntitySweptBlockResponseMixin` is deleted and its `legacyparkourcompat.client.mixins.json` entry is removed. There is no longer a global wrapper on 26.2's `Entity.checkInsideBlocks` lambda, so the direct `SweptBlockResponseHook` is reached only from `EntityInsideBlocksHistoricalSweep.apply`, which itself is dispatched only for `LocalPlayer` on a client level. Unsupported batches call the original method with no response-hook contamination; logical servers and other entities retain native callbacks and fluids.
- The swept-fluid omission API and `usesHistoricalResponses` switch are removed along with the now-unused 1.21 native response fence. The response change itself remains automatically discovered via its unique `@MechanicType("entity.swept-block-response")` interface and is called from the historical sweep. The distinct scan and sweep mechanic keys also remain unique.
- `BaseFireBlockAccessor` and its client mixin registration are removed. `AbstractCauldronBlockAccessor` remains listed in the client mixin config and exposes `getContentHeight(BlockState): double` with a matching `@Invoker("getContentHeight")`; the exact 26.2 source declares the protected method on `AbstractCauldronBlock` and `LavaCauldronBlock` overrides it, so invocation on the cauldron instance has the intended virtual dispatch.
- `EntityInsideBlocksHistoricalSweepMixin` remains registered in the client mixin config and keeps the `LocalPlayer` plus `level().isClientSide()` gate around historical replacement. Its unsupported branch calls the original list method intact.

## Other wiring checks

- Resolver direction is the documented closest `emulates >= selected` choice. The `V1_21` native fence remains for the scan and sweep operations, preventing the 1.21.2 implementation from activating for profiles before the accepted boundary.
- The `applyEffectsFromBlocks(List)` target and `List<Entity.Movement>` parameter match the exact 26.2 unobfuscated Entity source declaration. The queried accessors/invokers used by the historical sweep match exact source member names already checked in the initial report.
- JSON parses successfully. No build, test, game launch, or decompilation was run; source and cache inspection was read-only.

This accepts wiring only; it does not certify callback-body historical math or runtime parity.
