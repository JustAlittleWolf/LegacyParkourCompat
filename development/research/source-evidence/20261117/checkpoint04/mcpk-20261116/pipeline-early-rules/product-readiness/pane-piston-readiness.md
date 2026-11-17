# Pane and piston provider product-readiness audit

Status: **COVERED_NO_CODE** for the bounded 1.8.9–1.9.4 provider operations. This is a static product wiring/resolution audit; it does not establish runtime parity.

## Inputs and identities

- Accepted pane finding: `development/research/findings/1.8.9--1.9.4/pane-collision-shapes.md`, SHA-256 `CC73493DEAFE45EEC5131F9BD25CE36B7C2C48AA5A4F28D04C62A474CC1FBF4F`.
- Accepted piston finding: `development/research/findings/1.8.9--1.9.4/piston-head-arm-collision.md`, SHA-256 `D0283EB0D4D69910FF695F59207F1CEEA71B09C4EF68EC4E9B834C2CCDB2468D`.
- Accepted first-checked-onset packet: `build/mcpk-20261116/pipeline-early-rules/pane-piston-source/pane-piston-onset.md`, SHA-256 `734ce421d5ac7c388ba21d7ee7e581e38dab5b5a12079aabb5f3337c86f2a6ac`.
- Its independent onset audit and identity addendum match the supplied SHA-256 values; addendum verdict is ACCEPT for the bounded 1.9 first-checked provider-form onset/source facts.

## Profile mapping and resolver

`ParkourVersion` declaration order supplies the resolver ordinals: `V1_8_9` = 3, `V1_9` = 4 (aliases 1.9–1.9.2), and `V1_9_3` = 5 (aliases 1.9.3–1.9.4). `ChangeResolver` groups by `MechanicKey`, excludes annotations older than the selected ordinal, and chooses the lowest eligible ordinal. Thus a later annotation is selected when there is no closer eligible implementation.

`MovementChangeDiscovery` scans annotated `change.v*` classes and registers every implemented `@MechanicType` interface; `BlockCollisionShapeHook` is variant-keyed by block ID. No manual catalog or second provider loop is involved. `BlockStateCollisionShapeMixin` dispatches a provider only for an applicable local client player and preserves the native shape when the hook returns empty. The normal block movement collector also takes provider parts when supplied and falls back to the native shape when the provider is empty.

## Pane: COVERED_NO_CODE

- At selected `V1_8_9`, `change/v1_8/PaneCollisionShape` (`@MovementChange(V1_8_9)`) resolves. It supplies the earlier strip geometry.
- At selected `V1_9` and `V1_9_3`, that annotation is older and ineligible. The closest pane `BlockCollisionShapeHook` registration is keyed `PaneCollisionNative @V1_11_1`; its empty shape result deliberately leaves native pane collision selection intact through the stated pre-1.11.2 boundary. The `@V1_11_2` and `@V1_12` custom providers are later than this native fence and do not resolve for these profiles.
- The native provider path supplies the post/arm mask geometry form accepted as first checked at 1.9, so no separate custom 1.9 geometry class is needed for the bounded provider operation. This disposition is limited to the geometry provider; it does not accept or assess other neighbor predicates or general pane behavior.

Relevant files: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8/PaneCollisionShape.java`, `.../change/v1_11_1/PaneCollisionNative.java`, `.../change/v1_11_2/PaneCollisionShape.java`, `.../mixin/BlockStateCollisionShapeMixin.java`, `.../mechanic/hook/block/BlockCollisionShapeHook.java`.

## Piston head arm: COVERED_NO_CODE

`PistonHeadCollisionShape @V1_8_9` supplies the bounded 1.8.9 in-cell arm geometry. `PistonHeadCollisionShape @V1_9_3` supplies the accepted 1.9 protruding arm geometry and states that 1.9.1–1.9.4 retain those bounds. Resolver selection is therefore:

| Selected profile | Resolved piston-head provider | Accepted era coverage |
|---|---|---|
| `V1_8_9` | `change/v1_8/PistonHeadCollisionShape @V1_8_9` | 1.8.9 old arm |
| `V1_9` | `change/v1_9/PistonHeadCollisionShape @V1_9_3` | 1.9–1.9.2 protruding arm |
| `V1_9_3` | `change/v1_9/PistonHeadCollisionShape @V1_9_3` | 1.9.3–1.9.4 protruding arm |

These providers both implement the keyed `BlockCollisionShapeHook` for `minecraft:piston_head`; the general player-context collision-shape dispatch invokes the resolved provider. This finding is only the static arm shape operation, not moving-piston entity response or clipping outcome.

Relevant files: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8/PistonHeadCollisionShape.java`, `.../change/v1_9/PistonHeadCollisionShape.java`, `.../mixin/BlockStateCollisionShapeMixin.java`, `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`, `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java`.

## Limits

No product files, canonical records, Git history, or branches were modified. No build, tests, game launch, or runtime check was run. The source acceptance identities bound the assigned onset findings; this report makes no claim about other pane predicates, later pane behavior, piston extension/base providers, moving-piston entity response, exact trajectory effects, or unexamined profile boundaries.