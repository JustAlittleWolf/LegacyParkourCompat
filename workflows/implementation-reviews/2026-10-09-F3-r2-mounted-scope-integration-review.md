# Post-merge integration review: F-3 mounted-scope correction and F-005 fall reset

- **Decision:** ACCEPT the reviewed integration tree for the bounded F-3 and F-005 interactions below.
- **Review date:** 2026-10-09.
- **Scope:** exact merged tree `5c27fe49d651898f108b34de223c132ae1dd3fb2`; interaction of the accepted F-3 shallow-lava cutoff hook with the newly integrated F-005 `Entity.move` fall-distance reset redirect, shared `EntityMixin` and catalog, resolver direction, scoped fluid context, and native fallback.
- **Runtime validation:** not performed.

## Immutable review identities

- Merged HEAD: `5c27fe49d651898f108b34de223c132ae1dd3fb2`.
- Merge parents: F-3 correction branch `f89440c372c9f795e75d7c6e0e1bfef9253001a3`; current default branch `3467cc398a89bb86bfafa6eedb315e0a29e040de`.
- F-3 corrected implementation: `b2eac92a9adf1d773aafc2d7013ea41fb977319f`, parent `682c7903a00302bbd934bd4da3e41d79f1751d54`.
- F-3 request-changes review: `dafa594318874592f821da95393e0860a6ee4e0c`.
- F-3 corrected-code acceptance artifact already in the first parent: `f89440c372c9f795e75d7c6e0e1bfef9253001a3`.
- F-3 accepted source finding: commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`, blob `73a244f66a6501b1d48f11fc4bcbde95d25c8518`, raw SHA-256 `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`.
- F-3 independent source acceptance: commit `a76e28419f6b9767eab2e78147883c5459ed60c4`, blob `10d56f3e76eca339a95f6ea3f47ec099decb3467`; scope remains the standing, unmounted, non-flying Player witness only.

The F-3 corrected code and its original request-changes review remain preserved in history. This is an integration review of the merged tree; it does not broaden the accepted F-3 source scope.

## Merged code identities

Git blobs and raw SHA-256 hashes below were read from the merged worktree at the reviewed HEAD:

- `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java`: blob `5c52bfd6d66dd1df23d0a1ea3f50a035251f0729`, raw `0DC87D5B9E61AC143AA35638232BB2F03AA4EB9E3ED9BC348595BA6E70FFD6CA`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java`: blob `1a723091af9f26fcd9f6f0c92daf35087f1d0e19`, raw `4D37B8FE99B41386580F6F5C16B6B347C07F883751EB5A147D3A0BADFD3157D5`.
- `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityFluidCurrentMixin.java`: blob `b585502e41ceb229cf580297549f83303788f322`, raw `9A03E66C64183B46D3282FA06819F57C5E9625F79A8B67D0A6C7A522BECDE9BC`.
- `src/main/java/me/wolfii/legacyparkourcompat/mixin/FluidCurrentCutoffContext.java`: blob `49d454fac084db7df40f9c292b38bc326964df2b`, raw `161EF869F194D242F70F19254B6DBB86B06E6788A1EE7EA514E1D57F1EE628C0`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_11/ShallowLavaCurrentCutoff.java`: blob `7a83b7b1dfde2753b573049123435cd4918ce164`, raw `56E0A793CFB6A516077757DC26CF939F423DD797B50DE6FEF4CF5260D5CBD0AD`.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/ShallowLavaCurrentCutoffBehavior.java`: blob `6e3035f4e457beb210da272e61a99f14876d3fa2`, raw `9C43D01B4D1113416B5E8874C3BBF4D352F0BD4FAEDC079BD85BF1C077042B69`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/FallDistanceReset.java`: blob `3b5e47bc15608090aff24ca5e869953b5685eb45`, raw `53D602558B7043B45F40B745AE6AC4FE6D758B51CE0EAB4D1A65081E8794F2D8`.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/PlayerFallDistanceResetBehavior.java`: blob `ba5cf20368eeda10044347d51c7fa779801ef610`, raw `1AB1EA9769D079EA2D084B5DBD169AEDA65EB2BE62C726963FD90E0FE72B92FB`.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`: blob `9a6a57efd94aad45c2cd62ff452650528664577c`, raw `57F3B806F65B48AD0ABB2612CDC67BF84C94D70C3A4A8EA230FD7D6F962AB763`.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java`: blob `3911ac5967890a166f23198eb00e39da5213022f`, raw `C0806BA8CD8A49C5BF5C3A4D9F4DDA562FBE37B4B41370C645B69788DDCC9B9A`.

## Interaction review

**F-3 passenger guard and fluid dispatch.** `EntityMixin` retains the LAVA redirect in `updateFluidInteraction()Z` at `applyCurrentTo` ordinal 1 with `require = 1`. The historical branch requires LAVA, a Player, `!player.isPassenger()`, and the registered shallow-height predicate. Mounted Players, non-Players, unmatched heights, and inactive profiles reach the original `interaction.applyCurrentTo(...)` call. The accepted unmounted witness still satisfies the guard.

**F-005 movement reset.** The added redirect targets `Entity.resetFallDistance()V` only inside `Entity.move(MoverType, Vec3)` and uses `require = 1`. It resolves `PlayerFallDistanceResetBehavior` only for Players; when absent or false it calls `entity.resetFallDistance()` unchanged. This hook does not share the F-3 injection target: F-3 runs in `updateFluidInteraction`, while F-005 targets a reset invocation in `move`. The redirects do not collide or change each other's invocation ordering.

**Catalog and resolver.** The static catalog has one `ShallowLavaCurrentCutoffBehavior` registration at `V1_21_11` and one `PlayerFallDistanceResetBehavior` registration at `V1_18`. They are distinct mechanic keys and independently resolved operations. `ChangeResolver` selects the closest registration at or after the selected version and returns no historical changes for CURRENT. F-005's behavior range check is inside its own implementation and leaves this resolver rule intact. No profile gate or cross-release inheritance was added by the merge.

**Shared fluid context.** Both existing shallow-water and accepted shallow-lava paths still enter `FluidCurrentCutoffContext.run`. The helper saves the prior ThreadLocal entity and restores it in `finally`, including nested calls. `EntityFluidCurrentMixin` bypasses only the accumulated-current length-squared cutoff when the same entity is active; the separate fluid minimum-length behavior remains profile-resolved. The F-005 reset redirect neither reads nor writes this fluid context.

**Native and disabled fallback.** `MovementRuntime.find` returns empty when emulation is not active/current or the entity is not a Player. The F-3 redirect then delegates to `interaction.applyCurrentTo`; the F-005 redirect delegates to `entity.resetFallDistance`. Mounted Players also take the F-3 native branch. No semantic conflict with native fallback was found.

## Target publication binding

The canonical 26.2 target source publication previously checked for the same reviewed F-3 candidate is `ready/26.2/unobfuscated`, marker raw SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, and artifact-manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Direct `Entity.java`, `EntityFluidInteraction.java`, and `Player.java` hashes matched that source manifest in the prior independent F-3 review. The merge changes mod source only; it does not change the target publication.

## Review result and limits

1. **Requirement:** F-3 remains constrained to the accepted unmounted witness; the F-005 integration is confined to its separately accepted fall-distance operation.
2. **Correctness and edge cases:** the passenger exclusion retains native tracker behavior for mounted Players; the F-005 redirect suppresses only the movement reset for profiles where its hook returns true.
3. **Completeness:** both hooks have one mixin dispatch and one catalog registration; no unresolved caller conflict was found in the merged tree.
4. **Conventions and duplication:** shared `EntityMixin` contains separate, thin operation dispatches; no duplicate movement loop or provider was introduced.
5. **Permissions and visibility:** no permission, public API, or visibility change was introduced by the integration.
6. **Security:** no new external input, authorization, deserialization, or data exposure path was introduced.

No functional integration finding remains. This review does not claim the first changed F-3 release, runtime parity, or completion of the F-3 pair. No build, tests, client/server, TAS, Gym, or Docker activity was performed.
