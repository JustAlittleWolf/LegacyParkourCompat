# Independent full-pair source audit: 1.17.1 → 1.18.2

- Verdict: **REQUEST CHANGES**
- Audit scope: exact Mojmap A/B artifacts; player tick/call graph, required inventories, dependencies, finding reachability, and immutable handoff bindings.
- Audit mode: source-only; no implementation, MCPK/wiki, tests, builds, decompilation, runtime, game, TAS, gym, server, or Docker material used.
- Pair status remains `partial`; this audit does not accept any individual finding snapshot or claim implementation/runtime coverage.

## Artifact and immutable identity checks

The ready markers resolve exact IDs `1.17.1` and `1.18.2`, both `mojmap`. Their source-manifest SHA-256 values match the markers: A `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`, B `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. All 4,142 A and 4,236 B source files match their respective manifests. All 41 artifact entries per side match their manifests. The mapped-jar hashes are A `2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c`, B `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`.

The frozen report binding is verified: `run.md` Git blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`, raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`. The frozen handoff binding is verified: `SOURCE-HANDOFF-2026-10-08.md` Git blob `62180420b773cbfafe099330a1bbf66de6755a05`, raw SHA-256 `f0b27580679b5b839323b4ee2d5c78745ef59c8fe0d8410722d00fe12bddc134`.

All 14 candidate finding files F-001–F-014 match both the handoff's Git-blob and raw-file SHA-256 bindings. They are 14 unique finding IDs, not 15 findings: `findings` in the handoff count refers to coverage-ledger rows. The frozen handoff reports zero accepted snapshots; these bindings establish identity, not snapshot acceptance.

Verified candidate bindings (Git blob / raw SHA-256):

| ID | Git blob | Raw SHA-256 |
|---|---|---|
| F-001 | `9bc53d27195a6f89ddb19711ef40558d3c7bdcc8` | `77957564feaa103b7817055c71c0a9d9e31d7af4bacae5432b4b18d82c99e2fd` |
| F-002 | `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5` | `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` |
| F-003 | `e801da616021d5cab6a1ec6d985210803824f618` | `221aa9b6e306ded17642e6cf59afcf5c03d12f676d0bc7ded30e80cb670f77f1` |
| F-004 | `57aa2270e44cee63f27c538c13681ef90efe04d4` | `6a9b9d60301b6670fc4b3e6279bf2f9399d1bb714de9137a79738004935063d2` |
| F-005 | `8a9ff41d1c685bda185180863fe5069b5ec90783` | `6a0803d36f7d7f0f51ebdf9753e5f7adf3863cf8fe7418415ad55b12e6128d73` |
| F-006 | `4bc7991b67eca816de3f16b44180a7ef708bfb90` | `3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95` |
| F-007 | `2d3b24b4b99c828dfbe8b287e1fc67a863f6e1d0` | `e311648628d45ab6680d0715ae85817399933157d243bfc6ed37b30b1f5aba09` |
| F-008 | `58ebc5b2dbff5f0e409fdceb38b0466e89322ce4` | `3a8a5258cb741497a52085f76688df7ca32161dcbd0e068ffe5ee70b3a271afd` |
| F-009 | `1db71cf25a7b31b2efbebbed12cd582af7aec423` | `c54aa0c02915c6ba7853a5dec58f0a8d360f27d65510501f0d746254703c1c21` |
| F-010 | `5fe78a7d848bc370bb9bc86d44a296a9efb90f37` | `65407bcfbc1758f4e267362ac7cfeb88ac67abdf6f646b62452ce5ccfff757e6` |
| F-011 | `0d72a74ab5f1b5e982a630e026c2c31cc2a79c0a` | `decbf5a6a4f8963efb8d19824c30d96ebeac25c22598e8622858e3fa6bf153e3` |
| F-012 | `947ca63e44ec3f8ecaa710f4168c95bf66e5402a` | `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff` |
| F-013 | `7f021ef954c2e90314bea14e0e9ed06bbc7c2ef1` | `fc39381d46aa3257077dd20fd8ce7f8242b201b6b7c74ca9fc7fd2b1dceb511d` |
| F-014 | `ca968ab66df2cb44ea8c0918a3b4ff870740fc93` | `65161ef4a77c6c369af2a74e8a1cc4673cf2f724c8b94fa826548c04a05ae25a` |

The Feather `r1` derived-artifact caveat does not apply: that revision covers 1.8.9–1.13.2, while this pair uses Mojmap. The source and 41-entry artifact manifests are present and verify; no equivalence claim depends on a revised Feather mapped jar.

## Independent source path and inventory audit

I re-walked both exact trees from `LocalPlayer#tick` through `LocalPlayer#aiStep`, `Player#aiStep`, `LivingEntity#aiStep/#travel`, `Entity#move`, collision resolution, callbacks, and post-move state writes. I traced keyboard sampling and LocalPlayer input assignment; passenger/on-foot dispatch; travel branches and coefficient consumers; `setPosRaw` and feet-block cache invalidation/readers; collision query and solver order; movement flags, fall distance and velocity writes; position packet emission through server move acceptance/correction; inbound passenger refresh and rider placement; player dismount/teleport; and direct player impulse callers. I also searched both trees for movement setter/push/teleport/riding call sites and compared added/removed source-path inventories.

The evidence in the frozen ledger covers all seven required inventory headings: `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`, `INV-MODIFIERS`, `INV-EXTERNAL`, and `INV-EXCLUSIONS`. For the bounded source sweeps recorded there, I checked the cited producer-to-consumer routes and the paired vanilla bodies, including collision shape providers/registrations, fluid inputs, movement modifiers, direct player responses, and exclusions. I found no additional reachable player movement source slice outside the declared ledger. This finding is about source coverage only and does not decide whether each delta is eligible for map emulation.

The targeted checks confirmed the listed reachability and exact operation distinctions, including: the `KeyboardInput#tick` slowdown's different literal type only multiplying discrete `0/±1` impulses; A/B `Entity#move` solver/flag and new fall-reset paths; A's block-first versus B's entity-first auto-jump candidates; the fall-flying cosine precision change; and the local-air coefficient cast/value difference. Candidate traces for F-006/F-007/F-010/F-013 connect their packet or passenger predicates to direct player yaw, server position, selected player position, or rider-position consumers. The F-014 ordinary-air travel path is reachable for a LocalPlayer under its stated no-Levitation, missing-XZ-chunk guards.

## Bounded findings requiring author correction

1. **The terminal-coverage claim is false in the frozen ledger.** Parsing the 28 `### Slice` blocks in `run.md` yields 13 `compared-no-difference`, 14 `findings`, and one `pending`, not the handoff's `13/15/0`. The pending row is `T-PLAYER-UNLOADED-CHUNK`, which cites F-014. Although the paired evidence and candidate file describe the delta, the coverage row itself has `Status: pending`. A pending slice forbids full-pair freeze. Route this exact row to the source author to close with paired evidence/disposition or leave the pair partial; do not infer closure from F-014's existence or from a structural validator.
2. **F-014 is environment-input conditional.** A's default Overworld minY is 0 and B's is -64, and the ordinary-air client fallback reads the active level's `getMinBuildHeight()`. The reported `-64 < Y <= 0` divergence exists for the respective built-in default Overworld inputs with a missing XZ chunk. If the active dimension sends the same minY to both sides, this delta disappears. Keep that precondition attached to F-014 and make any later emulation-eligibility decision separately; this does not invalidate the source-observed default-dimension delta.

Because of finding 1, the independent full-pair audit does **not** accept full-pair freeze. The source path and listed inventory scope otherwise have no independently routed missed slice in this audit. Once the source author resolves the exact pending row and corrects the `13/15/0` coverage count while preserving all immutable bindings, a new audit of that revision can decide freeze. Finding-snapshot acceptance and runtime validation remain separate gates.

## Erratum and author-response re-audit (2026-10-08)

This section supersedes the historical verdict and finding 1 above; those entries are retained as the original audit event.

- Corrected verdict: **ACCEPT — full-pair source coverage freeze** for the exact frozen report object below.
- Frozen target: `run.md` Git blob `53bef7aae76bef026d5f5d19bc111635ddc6265d`, raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`.
- Re-audited author response: commit `6334207dcbd1f06a97c99d489de12df3d4e474e2`, file blob `b96bd3fbea36491926e41acb528ef8662c1d3333`, raw SHA-256 `26917ce02ec68ea360503f571a451e8a671407d44033eb97c26c2fe16ba62c29`. Its final commit `351b75fdcd166ea8a1d05d4158996697fac59576` includes current primary local `main` `3a60fe735560e478bf0aa0d05f5e306c74800f6a` as an ancestor.

The author's count reproduces exactly when the parser is bounded to `## Coverage ledger` through the next `##` heading and reads the first `- Status:` within each `### Slice` section: 28 rows = 13 `compared-no-difference`, 15 `findings`, zero pending/in-progress/not-applicable/blocked. `T-PLAYER-UNLOADED-CHUNK` is `findings` at line 354 in the frozen object. My earlier parser continued past the last slice into the later independent-audit template and let its out-of-scope `- Status: pending` placeholder at line 476 overwrite the final slice status. The resulting `13/14/1` count and pending-row request were wrong.

The response's other source-coverage claims agree with the exact frozen ledger reviewed earlier: all seven required inventories are marked complete, the dependency queue lists no unresolved source dependency, and `D-OVERWORLD-MIN-Y` is explicitly resolved. The prior call-graph and producer/consumer audit found no additional reachable player-movement slice. F-014 remains source-confirmed only under its stated active-dimension inputs: the built-in default Overworld minY changes from 0 to -64; equal server-synchronized minY removes that delta. This scope does not decide map/implementation eligibility.

This ACCEPT is limited to **full-pair source coverage and freeze review** against the bound report and response. It does not accept any of the 14 individual finding snapshots, claim implementation disposition, or claim runtime validation. The pair's mutable run status may remain `partial` until its owner records the accepted freeze; the independent coverage gate is cleared by this review.
