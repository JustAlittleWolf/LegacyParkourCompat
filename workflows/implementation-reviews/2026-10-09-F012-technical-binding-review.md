# Independent technical binding review: F-012 sprint air control

**Review date:** 2026-10-09
**Disposition:** **ACCEPT**, bounded to the accepted source evidence and the implementation commit below.
**Implementation commit:** `e386e31f7b55d584461469f20752eefd5bfde955` (candidate code).
**Proof commit:** `cc3ee3c8f3239e894923a1f23810c3a9d3130d31`, path `workflows/fix-implementation/reconciliations/F012-sprint-air-control-2026-10-09.md`, blob `ed869980297255521661b65310ad88e73259a786`.
**Prior acceptance record:** `de6084c5b1b9b57a836ba862e113103e19767845` updates the author reconciliation to quote an ACCEPT, but the standalone technical review report was not present on current main. This report independently binds the verdict to the exact code and proof identities; the author’s quoted verdict alone was not treated as binding evidence.

## Immutable candidate identities

| Candidate path | Git blob | Raw file SHA-256 |
|---|---|---|
| `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/DoubleSprintAirSpeed.java` | `a42b6a0b71ea176c8fb8f93def5b2dd1d1de44d9` | `f9c4e8d9a68b0e17ff7d23a7ee5d32fccf6f3f7ffcaad91590dd877768e6819c` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` | `b26ec31d09a40c9f970659676904b6b198f02a6a` | `f1d23e38f75789b9b291888c6990058da5750565f09ab37683236823ca29478d` |
| `src/main/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java` | `c036a3499741474fbd517e457c583e1659abc938` | `548e7c60e2d6d281035a7076ab66ad539f1b9baac65c99c96b6c55b44e845162` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/AirSpeed.java` | `78bca57bab35fcfa71d5ed9487bc2d3bd047f9be` | `1276ca82b5db58a1b54a190c3959340192b42e6ae830f58c4de5a082d30dca7b` |
| `workflows/fix-implementation/reconciliations/F012-sprint-air-control-2026-10-09.md` | `ed869980297255521661b65310ad88e73259a786` | `ed869980297255521661b65310ad88e73259a786f31a8ab206d9d6634cd5d10b` |

The class blobs match those recorded by the author proof. Hashes above were calculated from the exact immutable commit contents, not the current working tree.

## Accepted source evidence

The source-only finding snapshot is commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, finding blob `947ca63e44ec3f8ecaa710f4168c95bf66e5402a`, raw SHA-256 `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff`. Its blind acceptance is commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, review blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`. The release-boundary memo is commit `1c4317af04f70a596642369a402ce38ccb5492da`, blob `ebd4cb218453a9b2ce7afbe15932d2668dc6b742`, raw SHA-256 `477812d4bd08dcb3841b902b311899ee51c2f72d9ad76e323cb4a206dee01a06`; its independent ACCEPT is commit `ee586d98cbd02df932f6f076daa56a9d2176557b`, review blob `7a8315f0e5f253ef8c02a7e373524b3f8859080b`, raw SHA-256 `7493c46aaf5e55125ff94c94b6ca6322bd150e41526a81ff78e3ef7187b37e9e`.

The accepted source publication binds exact Mojmap `Player.java` hashes for 1.17.1 (`724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`), 1.18 and 1.18.1 (both `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`), and 1.18.2 (`bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`). Their `LivingEntity.java` hashes are respectively `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`, `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` (same for 1.18.1), and `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`. I rechecked these direct class-file hashes against the read-only ready cache and compared the corresponding writer snippets.

The accepted exact claim is narrow: 1.17.1, 1.18 and 1.18.1 reset `flyingSpeed` to `0.02F` after `super.aiStep()` then sprint-assign `(float)(flyingSpeed + 0.005999999865889549)`; 1.18.2 uses `flyingSpeed += 0.006F`. On `0.02F`, the accepted evaluation yields `0x3cd4fdf3` and `0x3cd4fdf4`, respectively. The update follows that tick’s movement, so ordinary airborne non-fluid travel can consume it on the following eligible tick. The finding does not claim collision-resolved trajectory or source fidelity outside the sampled releases.

## Technical review

- **Expression and operation order:** `DoubleSprintAirSpeed.afterAiStep` resets to `0.02F`, checks sprinting, performs double-literal addition, then narrows to `float`, matching the three accepted older-source samples. The existing 1.18.2 implementation remains the float compound-add variant. No arithmetic simplification or changed guard is present.
- **Tick timing and consumer:** the existing `PlayerMixin` stores `afterAiStep` at the return from `Avatar.aiStep()` and overrides `getFlyingSpeed()` only when `AirSpeedBehavior` resolves. This preserves the delayed coefficient consumer and uses separate narrow update/consumer hooks. The hook bodies dispatch through `MovementRuntime`.
- **Flight/passenger gate:** both version implementations return the supplied vanilla speed when abilities-flight is active and the Player is not a passenger; otherwise they return the stored historical value. This matches the cited Player-side ability-flight substitution/restore path. The passive stored value is not substituted during standalone ability flight.
- **Registration and resolution:** `MovementChangeCatalog.registerV1_18` registers the same `DoubleSprintAirSpeed` instance under both `AirSpeedBehavior` and `AirSpeedUpdateBehavior`; `registerV1_18_2` keeps the existing float implementation under both hooks. `ChangeResolver` returns no changes for CURRENT and, for historical selections, selects the nearest registered emulation version at or after the selected version. This yields the accepted 1.17.1/1.18/1.18.1 double-expression and 1.18.2 float-expression routes.
- **Native inactive path:** the mixin changes no result when no behavior resolves; CURRENT resolves to no historical changes, so the native return value remains intact. Runtime is not tested or claimed.
- **Evidence boundary:** the `V1_18` registration may also resolve for selected profiles older than 1.17.1 where no closer air-speed change exists. Those profiles are not source-proven by this finding or its four-release boundary memo. The author proof identifies that wider mechanical reach and makes no claim of fidelity for those profiles. This ACCEPT is consequently limited to the exact accepted sample routes; it must not be reported as historical parity for earlier selections.

## Verdict

**ACCEPT** the implementation and proof as a faithful minimal implementation of the independently accepted F-012 behavior across the source-supported 1.17.1, 1.18, 1.18.1 and 1.18.2 profiles. The binding is limited to the exact commits and blobs above. Older profile applicability remains unproven; no full-pair discovery closure, build success or runtime parity is implied. No code changes, build, tests, game/client/server/TAS/Gym/Docker runs, or shared-cache writes were performed.
