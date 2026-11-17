# Product readiness: fluid contact-height origin

**Status: FAILED — product gap/no-code disposition held pending source correction and re-audit.** Read-only correspondence completed; no product edits, Git writes, build, tests, or runtime work.

## Immutable inputs

- Accepted bounded report: `build/later-source-20261116/1.21.11--26.1.2/fluid_contact_vector_revision_r2/report-r2.md`, SHA-256 `43e3a870e35cd9aa9fbe5dcb13623802b3bdabfe1956b90f103adae65e6414f6`.
- Independent ACCEPT audit: `build/later-source-20261116/1.21.11--26.1.2/fluid_contact_vector_r2_audit/verdict.md`, SHA-256 `098F9E1A846AF87F6F2B425A6435D8F1C5B012F3FB24DFA90E127124F2000593`.
- Canonical finding: `development/research/findings/1.21.11--26.1.2/fluid-current-height-origin.md`, SHA-256 `f573ef62d3572b864da1cddb4d90aa9949434deb1d5c5c1f29c4886f107a32cf`.

## Product correspondence

- Literal profile aliases are in `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java:105-107`: exact `1.21.11` maps to `V1_21_11`; `26.1`, `26.1.1`, and `26.1.2` map to `V26_1`. `ParkourVersion.of` gives an exact running native release precedence and returns `CURRENT` for it (`:169-181`); when selecting 26.1.2 on the 26.2 product target, the alias is `V26_1`. Native/current maps to `CURRENT`.
- Provider: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_11/FluidCurrentDepthChange.java:32-42`, SHA-256 `7c5109575284dc51f48e3d25f44d5b17dc9254ca94040fd45c9c69d8ebeeeb36`. Its authoritative annotation is `@MovementChange(emulates = V1_21_11)`. It independently implements `FluidCurrentSurfaceHook` and `FluidCurrentDepthOriginHook`; for applicable water/lava it returns `(double)(blockY + fluidHeight)` and `rawBoundingBox.deflate(0.001).minY`.
- Per-hook resolution uses `change.emulates() >= selected`, choosing the closest qualifying version (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:28-35`, SHA-256 `f9dd060f232bb3d34a58a55b792283a3aa01875b44e80fc454b9298075af8587`). Thus selection `V1_21_11` can use this provider; selection `V26_1` cannot use an older `V1_21_11` provider. `CURRENT` resolves to no historical changes.
- Runtime: `MovementRuntime.appliesTo` accepts only client-side players (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java:129-134`); `find` returns empty for ineligible entities (`:383-385`). `FluidCurrentDepthMixin` captures the raw box and sampled height, dispatches the two hooks, and otherwise returns native `nativeFluidTop - rawBox.get().minY` (`src/client/java/me/wolfii/legacyparkourcompat/mixin/fluid/FluidCurrentDepthMixin.java:23-63`, SHA-256 `5614485af98031f761f48b84e138d861c7b0aaeb75c76d2ff2c43cebc1ab400f`). This is a depth-origin correction only: the mixin leaves cell admission, eye state, and contributor aggregation in their existing native/product paths. It is distinct from the `1.13` current contributor aggregation operation.
- Preliminary correspondence: the existing product provider expresses the exact older depth formula for selected historical profiles, and native fallback applies for `V26_1` / `CURRENT`. No product code gap is identified from the exact source expression alone; final covered/no-code disposition is withheld until the accepted claim is corrected and re-audited.

## Blocking source contradiction

The compared source identities match the immutable report: 1.21.11 `Entity.java` SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; 26.1.2 `EntityFluidInteraction.java` SHA-256 `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`; 26.1.2 `AABB.java` SHA-256 `22f23018fd588c2c4893d872f730381127e3d3ddd1a8c960901ecb8dae626392`.

Exact source arithmetic contradicts the accepted direction and contact-boundary claims:

1. 1.21.11 `Entity.updateFluidHeightAndDoFluidPushing` sets `D = B.deflate(0.001)` (line 3514), admits a cell only when `fluidTop >= D.minY` (lines 3528-29), and updates height with `max(fluidTop - D.minY, prior)` (line 3530).
2. 26.1.2 `EntityFluidInteraction.update` scans the interaction box (already deflated), gates with `fluidTop >= box.minY` (lines 34-44, 58), captures `entityY = B.minY` (line 43), and stores `max(fluidTop - entityY, prior)` (line 72).
3. Both exact AABB implementations define `deflate(amount)` as `inflate(-amount)` (1.21.11 lines 280-285; 26.1.2 lines 280-285); `inflate` computes `minY = this.minY - yAdd` (26.1.2 lines 183-190). Therefore `D.minY = B.minY + 0.001`, and on the same admitted cell old height is `fluidTop - D.minY`, which is **0.001 smaller** than new height `fluidTop - B.minY` (not larger).
4. A surface in `[B.minY - 0.001, B.minY)` is below both scanners' shared admitted lower bound `D.minY = B.minY + 0.001`; it cannot produce the claimed old-true/new-zero contact disagreement. The report r2, canonical finding, and audit all claim this interval can be accepted and/or reverse the sign. The audit acceptance therefore needs source correction/re-audit before product disposition relies on it.

## Remaining gates

Records owner has been notified with the exact conflicting claims, source identities, and hashes and is routing correction/re-audit. After that, disposition must remain bounded to an ordinary non-passenger player in the same accepted/tagged fluid cell; the accepted report's resource/tag membership, chunk/section preflight, passenger-route, pair-coverage, and runtime-parity gates remain outside this product correspondence check. No build or runtime parity is claimed.

