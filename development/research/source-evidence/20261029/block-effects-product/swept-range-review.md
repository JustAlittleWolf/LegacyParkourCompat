# Swept-shape boundary correction review

**Verdict: ACCEPT** for product commit `a2cd01048cfa7c35ceac89c1b7b6dd21787f99d3` (parent `4da3fd3a8b36f9960220708c59017bbdf3d1524c`) on `fix/segment-lifecycle-1214-1215-20261029`.

## Findings

- Exact Mojmap 1.21.4 `Entity.checkInsideBlocks` obtains `getEntityInsideCollisionShape`, preserves the `Shapes.block()` shortcut, and calls private `collidedWithShapeMovingFrom(from, to, blockPos, shape)` for the non-full-block shape. That helper builds the entity AABB at `from`, computes `to.subtract(from)`, translates the shape to world coordinates and calls `AABB.collidedAlongVector`. Exact 1.21.4 `AABB.collidedAlongVector` computes the center and endpoint, inflates each shape part by the moving AABB's full half-size on each axis, checks endpoint/start containment, then segment clipping. The new historical implementation reproduces those operations and their order.
- Current unobfuscated 26.2 uses `Entity.collidedWithShapeMovingFrom(from, to, aabbs)` to make the AABB at `from`, compute `to.subtract(from)`, and call `AABB.collidedAlongVector`. Current `AABB.collidedAlongVector` performs the same center/endpoint, containment, and clip sequence, but inflates by half-size minus `1.0E-7`. The mixin wraps exactly that call, and `SweptShapeIntersection` restores the full half-size. Empty hook result calls the original unchanged.
- The resolved hook is reached through client-only `EntitySweptShapeIntersectionMixin`; `MovementRuntime.find` first checks `appliesTo`, which requires a `Player`, client-side level and enabled profile. Thus non-player entities and logical servers keep native behavior.
- Literal source provider projection, using `.native-artifacts/project-resolver.py` against the product tree, returned: V1_21 and V1_21_2 select `NativeSweptShapeIntersection@V1_21_2` (empty result/native); V1_21_4, V1_21_5 and V1_21_6 select `SweptShapeIntersection@V1_21_6`; V1_21_9 and CURRENT have no eligible provider and use native fallback. Enum aliases confirm V1_21_2 covers 1.21.2/.3, V1_21_4 is 1.21.4, V1_21_5 is 1.21.5, V1_21_6 covers .6–.8, and V1_21_9 is 1.21.9. The older native fence is an empty-result no-op, so retagging it from V1_21_4 to V1_21_2 does not activate historical intersection for older selections; it prevents the .6 implementation from resolving forward for .0–.3.
- Commit scope is limited to the native fence's move from `change/v1_21_4` to `change/v1_21_2` and the historical implementation comment's boundary correction. The implementation's annotation and intersection logic are unchanged; no unrelated sweep or other G behavior changed in this commit.

## Projection

| Selected enum | Alias | Resolved provider | Effect |
|---|---|---|---|
| `V1_21` | 1.21 / 1.21.1 | `NativeSweptShapeIntersection@V1_21_2` | native (`Optional.empty`) |
| `V1_21_2` | 1.21.2 / 1.21.3 | `NativeSweptShapeIntersection@V1_21_2` | native (`Optional.empty`) |
| `V1_21_4` | 1.21.4 | `SweptShapeIntersection@V1_21_6` | full half-size swept operation |
| `V1_21_5` | 1.21.5 | `SweptShapeIntersection@V1_21_6` | full half-size swept operation |
| `V1_21_6` | 1.21.6 / .7 / .8 | `SweptShapeIntersection@V1_21_6` | full half-size swept operation |
| `V1_21_9` | 1.21.9 | no eligible provider | native inset operation |
| `CURRENT` | running version | no eligible provider | native inset operation |

## Hashes and scope

- Product commit: `a2cd01048cfa7c35ceac89c1b7b6dd21787f99d3`; parent: `4da3fd3a8b36f9960220708c59017bbdf3d1524c`.
- Changed files: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_4/NativeSweptShapeIntersection.java` (deleted), `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/NativeSweptShapeIntersection.java` (added), and `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_5/SweptShapeIntersection.java` (comment only).
- `ParkourVersion.java`: `372B4572FB232D3251421F09CCE59711470A419636DF9DA9D1D0EB9DA2F9AE44`; `ChangeResolver.java`: `57F3B806F65B48AD0ABB2612CDC67BF84C94D70C3A4A8EA230FD7D6F962AB763`.
- Historical implementation `SweptShapeIntersection.java`: `809FAE776FA59F48F8AC10E09597F712BF8EB7380F9C9088E820154CE24CFD48`; native fence `NativeSweptShapeIntersection.java`: `5EEE1B7FB8FBCF8260729F5CA2B826E79F55724586D0C5148EF82A411AC351D2`; mixin `EntitySweptShapeIntersectionMixin.java`: `37DC145F6F25702B942F66D4EFEBAFAB58BFA0EFBE9DADD8484AC31B6E90E52D`; hook interface: `49130E365EAB03B1A55C5AEDC915340CCC53D773D28E26CBBE397E6F3E683DFB`.
- Projection script `.native-artifacts/project-resolver.py`: `4AA120B96F7DCE260DCA4EC1C189420401D51BE7C5E418795E78090B972C6B5B`.
- Exact source hashes: 1.21.4 `Entity.java` `05F18EF2EC0413FC010230407C812A11553EB5123B68D21B5D7B2C0C175698AD`; 1.21.4 `AABB.java` `1990E0E1058E9AC5201C32E51C6A5F9B671E7ED00BA863AACF2C5B7B0ADFF217`; 26.2 `Entity.java` `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5`; 26.2 `AABB.java` `1FFDD0F6D26A4DD1E26407EA9A386EAB7903E43462E60C9AE5FDC50A0468F10B`.

No build, tests or runtime were run.
