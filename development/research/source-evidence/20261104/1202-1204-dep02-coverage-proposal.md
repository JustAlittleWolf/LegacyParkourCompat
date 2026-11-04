# 1.20.2–1.20.4 DEP-02 coverage proposal

## Immutable evidence input

- Independently reviewed source-only input inventory: `build/source-drafts/1202-1204-dep02-player-inputs.md`, SHA-256 `ED94BD3092FBFD6278012EC5B52E7D19D9140E0AB7C840A0809E7D5DE6CA6689`. Reviewer `source_1202_1204_contact` returned PASS for this exact revision.
- Exact pair and Mojmap source identity: 1.20.2 manifest `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, client `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; 1.20.4 manifest `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`, client `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`.

## Proposed additive checked slice

Append one bounded source-reference census for movement-relevant tag, default, registry-key and synchronized-input references consumed along the direct local-player class path (`LocalPlayer` → `AbstractClientPlayer` → `Player` → `LivingEntity` → `Entity`). The reviewed inventory records paired input/consumer references for:

- climbable, soul-speed, fence and wall block tags, including support-position sampling;
- water and lava fluid tags, plus water, bubble-column, powder-snow, ladder and scaffolding block keys;
- Elytra item state, movement-speed attribute and player ability defaults, movement effects, Depth Strider, freeze eligibility tags, and the dimension-type `ultraWarm` input;
- server configuration registry/tag delivery and client application paths, plus player ability and attribute update handlers.

This is coverage of source references and direct consumer reachability only. It records no old/new behavioral difference and establishes no equivalence of keys, tag memberships, registry values, resources, or runtime state.

## Preservation limits

- Keep tag JSON, datapack/resource contents and hashes, synchronized tag memberships, dynamic-registry values, world-state payloads, equipment/effect/attribute state production, and runtime inputs open. The inventory intentionally did not inspect those contents.
- Keep block/fluid provider bodies and defaults, collector region/exclusion rules, collision-axis selection, and any resulting movement behavior open. The reference census does not show provider result or trajectory.
- Preserve existing pair coverage, evidence associations, dependencies, exclusions, unexamined slices and audit state. Add only this checked source-reference slice; do not mark the pair complete or claim implementation acceptance or runtime parity.
