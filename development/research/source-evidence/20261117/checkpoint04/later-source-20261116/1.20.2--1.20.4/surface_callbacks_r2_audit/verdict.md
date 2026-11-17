# Independent blind audit verdict — 1.20.2–1.20.4 surface callbacks r2

Candidate: `build/later-source-20261116/1.20.2--1.20.4/surface_callbacks_revision_r2/report-r2.md`
Candidate SHA-256: `2660FB5C987D064345BC7F868BD1CCCE720BB1B404228145174E4F59E7CE12E0`

## Verdict

**PASS for the bounded source identity and callback/key-join claims in this revision.** I independently checked the two endpoint root-sidecar identities and retraced the HoneyBlock, SlimeBlock, and WebBlock player-contact callbacks and their direct helper/dispatch/registration joins. The exact source supports the report's finding that no reachable movement difference was found in this bounded slice. This verdict does not accept a pair-wide finding inventory, close dependencies outside the slice, or infer pair completion.

## Source identity checks

Both endpoints are `mojmap` and have ready markers with `status: ready`, matching `versionId` and `versionMetadataId` (1.20.2 and 1.20.4 respectively), and matching Mojmap provenance `versionId`/`namespace`. The recorded root-sidecar format is consistent with the version-root ready, provenance, source-manifest, artifact-manifest, and diagnostics files.

For 1.20.2, the source manifest SHA-256 is `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` (4,904 entries); the artifact manifest SHA-256 is `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` (69 entries). Both hashes and counts match the ready marker. Its diagnostics hash matches as well. For 1.20.4, the corresponding source manifest SHA-256 is `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` (5,048 entries), and artifact manifest SHA-256 is `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` (69 entries); both hashes/counts match the ready marker, as does the diagnostics hash. Provenance declares the stated exact batch command and tool/runtime versions.

I independently matched all seven cited Java inputs at both endpoints—HoneyBlock, SlimeBlock, WebBlock, Entity, Player, BlockBehaviour, and Blocks—to their source-manifest entries and verified the local SHA-256 values. All 14 file comparisons matched.

## Claim checks

- HoneyBlock: compared `fallOn`, `entityInside`, `isSlidingDown`, and `doSlideMovement` at both endpoints. The fall multiplier, ordered slide gates and threshold constants, edge-distance arithmetic, velocity branch and writes, fall-distance reset, and callback order are the same. The report's no-difference conclusion is supported.
- SlimeBlock: compared `fallOn`, `updateEntityAfterFallOn`, `bounceUp`, and `stepOn`. The suppress-bounce and superclass branches, negative-y condition, living-entity bounce factor, and horizontal damping condition/write are the same. The report's no-difference conclusion is supported.
- WebBlock: `entityInside` passes the same callback state and `(0.25, 0.05F, 0.25)` multiplier to `makeStuckInBlock` on both sides.
- Direct player/helper path: Entity's collision dispatch reaches the current block state callback at 1.20.2 line 985 / 1.20.4 line 986; BlockBehaviour's state callback delegates to its block at 693–694 / 740–741. Entity's `makeStuckInBlock` resets fall distance and stores the multiplier at both endpoints, while Player only suppresses that superclass operation when flying, unchanged between endpoints.
- Vanilla key/provider joins: Blocks registers `cobweb` with `WebBlock`, `slime_block` with `SlimeBlock` and friction `0.8F`, and `honey_block` with `HoneyBlock` and speed/jump factors `0.4F`/`0.5F` at both endpoints. The reported constructor/key joins are supported. These checks establish the direct slice's registration addressability, not a general audit of resource or tag behavior.

The report accurately limits the conclusion: wider callback caller tick/order context, resource/tag dependencies beyond the inspected direct vanilla registrations, and all other movement areas remain unexamined. Its statement that the pair remains partial is appropriate. No coverage or acceptance claim beyond this bounded slice is warranted.
