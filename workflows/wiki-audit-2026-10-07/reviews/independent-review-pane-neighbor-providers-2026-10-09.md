# Independent review: pane neighbor providers (2026-10-09)

## Scope and verdict

Review of `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09.md`, introduced by `aa9e10f7792aef6215bab1f97ccdca61b64bbd86` and present unchanged at final author tip `9e1841d0874217366149afa80dd8b25cbe2f4313` (`fix/wiki-attribution-2026-10-09`). The final tip is a merge of the author commit and `main`; the target record has no diff between those commits.

**Verdict: ACCEPT, bounded to the three listed provider classes and the single east-neighbor witness.** The cited ready-tree sources support the registrations, predicate behavior, and pane-mask consequences. This does not close the provider census, all reachable masks, or the source/JAR provenance gap.

## Candidate snapshot binding

| Candidate file at `9e1841d0874217366149afa80dd8b25cbe2f4313` | Git blob | Bytes | Raw SHA-256 |
|---|---|---:|---|
| `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09.md` | `4af937333cfe189c1205c2bf4871874325e74a9a` | 7,515 | `da9196859a9456fa036ec9d446eaf4ff56314c03816da3c23cf6bc8de8ef361a` |

## Source identity verification

The candidate's `ornithe-feather.ready.json` files both say `ready`. I recomputed each ready marker, source manifest, and artifact manifest SHA-256. The marker-declared manifest hashes match the actual manifest bytes. Every source hash cited in the candidate was recomputed from the canonical ready tree and matched both the candidate and the corresponding source-manifest row.

| Version | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Source count |
|---|---|---|---|---:|
| 1.8.9 | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | 1,612 |
| 1.9.4 | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | 1,819 |

The candidate table's short `StateDefinition.java` label resolves to `net/minecraft/block/state/StateDefinition.java`; that exact file has SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` and matches the 1.9.4 source manifest.

The verification records report zero source-file differences and no raw-input artifact differences. They also report a regenerated derived `client-ornithe-feather.jar` difference in each version. The current regenerated hashes (`5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` and `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`) differ from their recorded original hashes (`e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` and `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`). The candidate correctly leaves equivalence to the unavailable original derived JARs unproven and does not label the difference metadata-only.

## Claim decisions

### Registration and predicate classification — ACCEPT, three classes

The exact `Block.java` registries instantiate `LeavesBlock` at ID 18, `Leaves2Block` at ID 161, and `SlimeBlock` at ID 165 in both versions. The source hashes and relevant ranges listed in the candidate were verified against the exact ready-tree manifests.

- In 1.8.9, `PaneBlock.shouldConnectTo` calls `block.isOpaque()`. `Block.isOpaque()` returns the constructor-cached `opaqueCube`, which the base constructor initializes by virtual dispatch to `isSolidRender()`. Both leaves classes inherit `AbstractLeavesBlock.isSolidRender()`, which returns `!culling`; the field is still its default `false` during the base constructor call, so the cached predicate result is `true`. Their later default state property assignments do not rewrite that cached result.
- In 1.8.9, slime inherits `TransparentBlock.isSolidRender() == false`; its base-constructor `opaqueCube` result is therefore `false`.
- In 1.9.4, `PaneBlock.shouldConnectTo` calls `block.defaultState().isCube()`. The default state's `isCube()` delegates to `block.isCube(state)`, and base `Block.isCube(state)` returns `true`. `LeavesBlock`, `Leaves2Block`, and `SlimeBlock` inherit that result. `TransparentBlock`'s false `isSolidRender(state)` result is a separate rendering predicate and does not change inherited `isCube(state)`.

The `culling`/`isSolidRender` path is only evidence for the old constructor-cached `isOpaque()` result. This review does not treat later culling or render lifecycle changes as collision geometry.

### East-only pane witness — ACCEPT, one arrangement

Both pane resolvers inspect the four cardinal neighbors. Put the registered slime block east of the pane, with north, south, and west air. The 1.8.9 general predicate rejects slime, so no east arm is selected. The 1.9.4 default-state cube predicate accepts slime, so the resolved mask contains only east. This is a reachable mask witness for the stated arrangement. It neither classifies every block registration nor establishes the full world-reachable mask set.

The leaves and leaves2 examples yield an east connection in both versions; they are unchanged controls, not changed-predicate witnesses. The report correctly keeps the previous Ice, End Portal Frame, and fixed-mask records separate instead of using them to broaden this result.

## Open dependencies and limitations

The report correctly leaves the rest of each shared-era block registry unclassified, all world-reachable pane masks open, and the next source slice (`cactus` 81, `barrier` 166, `iron_trapdoor` 167) unevaluated. Preserve the distinction between this provider predicate/mask slice and the pane shape/collision inventory. No full provider closure, exact original derived-JAR equivalence, or runtime validation is accepted here.

## Review execution

Compared the author commit and final tip, recomputed candidate and source identities, matched all cited source hashes to the exact ready manifests, checked registrations and predicate call paths, and read the prior independent Wiki-lane decision for the separate Ice witness. No regular source-lane, MCPK, mod implementation, or runtime evidence was used. No source files were changed, decompiled, built, tested, or launched; no clients, servers, TAS, or Docker were run; no push was made.
