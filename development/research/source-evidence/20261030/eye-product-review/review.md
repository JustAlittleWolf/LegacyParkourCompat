# Independent product review — 1.14 player eye-water query

## Verdict

**Integration accepted for the bounded 1.14 contextual-height operation.** No code defect found in the reviewed product commit `66278c74dc36bdc88e596249c78749a612937365` (base `435f3e14`). This is not runtime parity evidence and does not establish movement coverage outside the inspected releases.

## Integrated behavior

- `change/v1_14_2/PlayerEyeWaterQuery` is a single independently resolved provider of the existing `PlayerEyeWaterQueryHook`. Its class annotation covers the 1.14.2 bucket (1.14.2–1.14.4); for selected 1.14/1.14.1 the resolver's closest-later rule also selects it. The accepted source comparison places contextual height at 1.14.0, so this preserves the inspected operation across both enum buckets.
- The provider preserves the historical boat veto where applicable and returns empty for current boat subclasses without a 1.13/1.14 counterpart, keeping the native result. It floors the double eye-point through `BlockPos.containing`, samples only that fluid state, requires WATER, and uses contextual `getHeight(level, pos)` with the shared strict comparison. `fluidHeight + 0.11111111F` and the subsequent block-Y addition remain float operations before comparison with the eye-Y double.
- The `checkLoaded` call-site is faithfully inert on an ordinary client. The 1.14 helper calls virtual `ClientWorld.isChunkLoaded(x,z)`, whose exact overrides return `true` across 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4. The product calls virtual `Level.hasChunkAt(eyePos)`; 26.2 `LevelReader.hasChunkAt` delegates to `hasChunk`, and `ClientLevel.hasChunk(int,int)` also returns `true`. The product therefore preserves client dispatch and does not substitute a real cache-residency check. The correction artifact records all source identities and hashes; its correction-2 alias verification passed independently.
- The existing `PlayerMixin` redirect is client-listed and scoped to `Player.updateIsUnderwater`; `MovementRuntime.find` requires a player on a client-side level and an enabled historical profile. WATER results use the hook, other tags and absent providers call the native method. The 1.12 `NativeBeforePlayerEyeWaterQuery` provider fences profiles through 1.12 from inheriting the later point query. Selected profiles after the 1.14.2 provider resolve no query provider and retain native behavior. The integration does not write `EntityFluidInteraction` eye-tracker flags or alter its body scan.

The existing mixin dispatch changes the value consumed by `Player.updateIsUnderwater` for historical profiles; it does not install or rewrite the later tracker producer. Consider describing this distinction more directly in the JavaDoc phrase “cached-water producer,” which may be read as changing `EntityFluidInteraction` state. This wording is not a behavioral defect.

## Evidence and limits

The source review and independent arithmetic audit accept the 1.13.2 → 1.14.0 contextual-height change, strict point-query math, and inspected patch aliases through 1.14.4. The source-gate corrections narrow the conclusion: the loaded-check branch exists and the refresh passes `true`, but ordinary client-world overrides make it always pass, so it is not a demonstrated client movement delta. The rejected neighboring-chunk witness remains rejected. Modern 26.2 tracker refresh stays on the native route when no historical hook applies.

Resolver and fence review is limited to the eye-query mechanic. The existing 1.13 provider is annotated `V1_13_1`; closest-later resolution means a `V1_13` selection can also select it. The accepted source comparison uses 1.13.2 as its lower endpoint, so this review makes no new claim about uninspected 1.13.0 behavior.

No product edits, build, tests, game/TAS/Gym/server launches, or decompilation were performed by this reviewer. The integration/build owner reported a successful no-tests build at the reviewed commit.

## Bound identities

Product commit: `66278c74dc36bdc88e596249c78749a612937365`; product worktree was clean at review. The newly integrated change SHA-256 is `639669e3f05bd6b0164069e460ff3ed3a199e17a3b793b221b3c0f781df17d48`; shared math helper SHA-256 is `75dcb365513884e880a0af7dd16eb30399d23deb4e48a4423345bceda9b62709`.

Independent candidate arithmetic review: `.native-source/20261030/eye-candidate-review/review.md`, SHA-256 `2d4f371a162852b35af8c55bd07c0466c82da3108130549f06e82655bd40adfd`.

Independent source report: `.native-source/20261030/eye-query-114-closure/source-review.md`, SHA-256 `7784336b9268bb4a156ebee41fd0394affc9c66711f5650d86d3f900cd2e6db4d`; audit: `.native-source/20261030/ordinary-pool/eye-query-114-review/audit.md`, SHA-256 `f81c0f9f04e9d6c669b28c7ed35632f237b56594fc1a7229f5888afbc50338df`.

Client-gate correction: `.native-source/20261030/ordinary-pool/eye-query-114-review/correction-1-client-world-gate.md`, SHA-256 `f9f028bec16e1a2d947726734d6aa328ab8d021fdb6f900dc5cb59a47ad70319`; helper-alias correction: `correction-2-query-aliases.md`, SHA-256 `77a05378a678d77adc33e940a02248271a45c861ee954c11f0fb12bdb796eec8`. The ordinary source reviewer confirmed correction 2's five helper names and line anchors against canonical source with no remaining alias gate.
