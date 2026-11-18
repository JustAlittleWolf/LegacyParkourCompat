# Independent product review: raw-pitch extension

## Verdict

**PASS** for the scoped implementation in product commit `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce`, tree `e49ce4e534203a675c5001b1ce8fa670f054db0a`, branch `codex/c-raw-pitch-saturation-117-1204`. The checked product working tree was clean. This verdict covers product-code correctness and source-bound scope; it does not establish runtime parity.

## Reviewed inputs

- Incremental product ancestry is exact: `72adb0e` (reviewed; parent `bc469fe6d6ae9e6b62ba5c16dcb13a3160209248`) → `bc469fe6` (parent immutable ready baseline `c0704c7b8a06b44d680ee9f48858cedc1a3e50ab`). The reviewed delta from that baseline changes only the raw-pitch native fence, the existing clamp provider, and its package location.
- Accepted applicability report: `c-raw-pitch-earlier-range-acceptance/report.md`, SHA-256 `7fc31ac2995085e569be00658db0b1e68576233ca1f3e14fc345b85727b8cf07` (verified against the supplied file).
- Signed A audit archive: commit `ee30fad38ecac269c0c295df025cb97f227a446b`. Its five adjacent 1.19.4→1.20.4 records are in the archived evidence tree and review the direct turn-pitch setter/clamp and active Elytra use boundary.
- C accepted source archive bound by the applicability report: head `9ef25ce2047fdeb52ea9b93e07c2e3a407800948`, tree `4af21244582c38629a5171d2c3415840619d38be`, bundle SHA-256 `21af32782e3f8099d8301e438b8a0bbf71021a30b29cc42556043bfd87eb21bd`. The 1.16.5→1.17 report records the earlier arithmetic change; the direct setter range begins at 1.17. The accepted 1.20.4→1.20.5 source record concerns mouse callback batching and explicitly preserves `Entity.turn`/setter behavior, so it does not extend this hook's scope.

## Findings

- **Resolver range is correct and minimal.** The native identity provider is now `@MovementChange(V1_16_2)` in `change/v1_16_2`; the sole enabled clamp provider remains `@MovementChange(V1_21)`. `ChangeResolver` excludes providers older than the selected profile and chooses the earliest eligible provider per mechanic, while `CURRENT` returns no changes. Therefore selected 1.16.2/.3/.4/.5 profiles resolve to the native fence; selected 1.17 through 1.21.1 resolve to the V1_21 clamp; selected 1.21.2 and later resolve no pitch provider and retain native fallback. The V1_16_2 enum alias covers exactly 1.16.2–1.16.5. No additional V1_17 provider or exact-profile gate is needed, and there is no hidden extension past 1.21.1.
- **Injection target and gates are correct.** The client-source `EntityMixin` modifies argument 0 of `Entity.setXRot(F)V`, ordinal 0, inside `Entity.turn(DD)V`. This is the first pitch setter only; the subsequent yaw setter and final clamping setter are untouched. It dispatches through `MovementRuntime` only when the receiver is `LocalPlayer`, and falls back to the vanilla argument when no hook resolves. Because this mixin is client-only and logical-server entities cannot be `LocalPlayer`, logical servers—including integrated-server entities—remain native.
- **Transform preserves the accepted behavior.** The provider returns every non-finite value unchanged, clamps only finite values outside inclusive `[-90.0F, 90.0F]`, and returns in-range values unchanged. The latter preserves exact input bits, including positive and negative zero. Non-finite values continue into the unchanged vanilla setter's existing rejection/logging path. The transform does not recompute pitch arithmetic, alter `Entity.turn` operation order, or change native fallback.
- **Range citations and later evolution are bounded.** The updated Change comment cites the 1.17 direct setter path, accepted continuity through 1.20.4 and 1.21.1, and the 1.21.2 setter remainder/clamp transition plus 26.2 behavior. This supports the stated finite first-setter restoration and native cutoff without absorbing the separate 1.20.5 callback scheduling change.

## Build and parity status

The available integration receipt records a successful required Gradle build for earlier product code commit `9ec57a6256b39ea8a290b298322d827d8b3002de`, tree `d2d74eedc026224e8d01bdfc30e01c5ec7bae252`. That is not the reviewed code tree. I found no build receipt for `72adb0e`/`e49ce4e`; the designated product build owner must record the build against this exact tree before treating the integration build gate as closed. No tests or game/runtime launch were run for this review. Compilation, once confirmed, will not prove parity.

## Signature

Independent product-code review, signed PASS for commit `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce` and tree `e49ce4e534203a675c5001b1ce8fa670f054db0a`.

## Owner-attested build update

After this review, the designated product owner confirmed that exact reviewed commit `72adb0edccf5dc8f850ecfb727c0fe10afeb15ce` / tree `e49ce4e534203a675c5001b1ce8fa670f054db0a` was clean at a successful required build following the final Javadoc correction. Command: `.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`. Result: `BUILD SUCCESSFUL` in 9 seconds; 19 tasks, 3 executed and 16 up-to-date. The init script disabled every Gradle `Test` task and `-x test` excluded tests. No runtime was launched. This owner attestation closes the build gate for the reviewed tree; a duplicate build transcript is unnecessary in this review verdict because the designated owner will record the durable integration receipt.
