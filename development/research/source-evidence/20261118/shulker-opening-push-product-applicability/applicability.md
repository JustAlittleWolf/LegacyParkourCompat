# Shulker opening push: product applicability

## Disposition

**COVERED for the accepted opening push-amount operation, conditional on the native entity query selecting the local player.** The existing product change calculates the 1.16.5 lid-slab overlap plus `0.01`, signs it along the shulker facing, and substitutes it at the `Entity.move(MoverType.SHULKER_BOX, …)` call. It does not restore historical query admission, so this disposition does not establish complete end-to-end candidate-selection parity for every player placement.

## Pinned inputs

- Primary product checkout: `D:/Javastuff/LegacyParkourCompat`, `HEAD` **aae9c86464638cb5ed7faa535c92569b89484906**. Its worktree has unrelated research/workflow changes; the three inspected product files are not among them.
- Read-only portal product snapshot: `portal-product`, `HEAD` **c702ee745d674693ac654ed7c5557171d6c4972d**, clean. The inspected implementation, mixin, and version enum have the same text as the primary checkout.
- Accepted source review: `development/research/source-evidence/20261117/checkpoint01/mcpk-20261116/vd-full-checklist/shulker-push-cutover/review-revised-shulker-box-progress-push.md`, SHA-256 **c9b4a39011efd065b318582a520644779b2e7ec1698e5a880348d550b75e6083**. It accepts the revised finding bytes at SHA-256 **461301D58D5FF9F34DD86EBBE17B64817FEC4AE2ECE24BD877C3DFE462171165**. The finding pins the 1.16.5 source manifest at `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` and 1.17 provenance at `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`.

## Product route and boundary

`ShulkerOpeningPushAmount` is annotated `@MovementChange(emulates = ParkourVersion.V1_16_2)`, implements the separately typed `ShulkerOpeningPushAmountHook`, and has a public no-argument constructor. Client `MovementChangeDiscovery` scans version packages, reads the annotation, and automatically registers each implemented `@MechanicType` interface. No manual catalog entry is needed.

`ParkourVersion.V1_16_2` is selectable and explicitly aliases `1.16.2`, `1.16.3`, `1.16.4`, and `1.16.5`; `V1_16` separately aliases `1.16` and `1.16.1`. The resolver chooses the nearest change whose emulated version is greater than or equal to the selected profile. Thus this operation resolves for `V1_16_2` and earlier targets; the mixin's `V1_11` guard limits its call path to shulker-era profiles. It does not resolve for `V1_17` or later. The accepted 1.16.5→1.17 comparison supports the 1.16.5/pre-1.17 endpoint; it does not by itself audit every earlier patch that the resolver may select.

The client mixin is listed in `legacyparkourcompat.mixins.json`. Its redirect is limited to `LocalPlayer`, requires `MovementRuntime.appliesTo` (which excludes logical-server entities), and calls the hook only when one resolves. Otherwise it forwards the native vector to the same `entity.move` call. Non-local entities and disabled/native profiles retain the vanilla amount and movement call.

## Amount correspondence

The hook reads the facing, obtains the physical shulker box AABB, contracts it toward the moving lid slab, translates it to the block position, and compares the slab's outer face with the player's near face on the facing axis. It adds `0.01` and constructs the signed one-axis vector. This matches the accepted 1.16.5 per-call formula (`getTopBoundingBox` directional overlap plus `0.01`); the native progress and collision-resolved `Entity.move` remain in use. The accepted scenario's per-call amount and repeated opening displacement therefore have an implementation path once the local player is admitted by the native sweep.

## Admission dependency and next gate

The redirect is downstream of entity selection. The accepted 1.16.5 source selects entities intersecting the current lid slab. The product's 26.2 native `moveCollidedEntities` builds a progress-delta AABB and calls `level.getEntities(null, aabb)` before the redirect; the hook cannot add a player omitted by that query. The accepted scenario explicitly follows a player selected by successive sweeps, so this does not invalidate the narrow amount finding. It does mean the amount hook alone should not be described as restoring the historical query/admission envelope.

**Next gate:** keep the accepted amount finding marked covered. If the intended product claim expands to all opening pushes where historical query membership can differ, obtain a separately bounded accepted query/admission finding (including its applicable profile boundary), then assess a distinct player-scoped query hook. Do not fold closing behavior, block geometry, or other entity responses into this amount operation.