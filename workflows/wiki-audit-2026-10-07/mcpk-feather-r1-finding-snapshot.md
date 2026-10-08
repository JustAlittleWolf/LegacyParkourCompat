# MCPK Feather revision consumer finding snapshot — `feather-r1-2026-10-07`

Date: 2026-10-07. This is a revision-specific evidence snapshot for the MCPK wiki findings listed below. It records the consumer-side checks against the canonical shared source store after the independent operations audit passed. It does not freeze the source/wiki pair; independent reviewer acceptance of this exact snapshot and its dependencies is still pending.

## Consumer verification

Canonical root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\`.

For each version row, the immutable mapped-JAR path is `revisions\derived-artifact-snapshots\feather-r1-2026-10-07\<version>\ornithe-feather\client-ornithe-feather.jar`; its adjacent `artifact.sha256` and `revision.json` were read and checked. The ready source manifest is `ready\<version>\ornithe-feather.sources.sha256`; the original artifact/raw-input manifest is `ready\<version>\artifacts.sha256`. The revision JSON itself is identified by its SHA-256 below.

The consumer check verified that the JAR bytes match both the sidecar and `revision.json.snapshotSha256`; the source and artifact manifest bytes match the ready marker and the revision record; every source file hashes to its original source-manifest entry; and every artifact-manifest input except the unavailable original derived mapped JAR hashes to its original entry in `artifacts\`. No ready marker or manifest was changed. The unavailable derived-JAR entry was deliberately excluded from the unchanged-input check.

| Version | Immutable JAR SHA-256 | `revision.json` SHA-256 | Original source manifest SHA-256 | Original artifact/raw manifest SHA-256 | Original derived JAR SHA-256 |
|---|---|---|---|---|---|
| 1.8.9 | `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` | `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` |
| 1.9.4 | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` | `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` |
| 1.10.2 | `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b` | `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` | `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d` |
| 1.11.2 | `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f` | `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` | `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3` |
| 1.12.2 | `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87` | `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b` |
| 1.13.2 | `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c` | `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` |

The per-version `revision.json` records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and zero source-file differences. Its `originalDerivedArtifactSha256` corresponds to the unavailable mapped-JAR entry above. The revised immutable mapped JARs are not asserted equivalent to those original derived JARs; that equivalence remains **UNPROVEN**.

| Version | Source files checked, mismatches | Unchanged artifact inputs checked, mismatches |
|---|---:|---:|
| 1.8.9 | 1,612 / 0 | 36 / 0 |
| 1.9.4 | 1,819 / 0 | 36 / 0 |
| 1.10.2 | 1,845 / 0 | 36 / 0 |
| 1.11.2 | 1,921 / 0 | 36 / 0 |
| 1.12.2 | 2,050 / 0 | 37 / 0 |
| 1.13.2 | 2,711 / 0 | 41 / 0 |
| **Total** | **11,958 / 0** | **222 / 0** |

## Findings whose endpoint evidence depends on this revision

The source behavior evidence is in the unchanged ready trees named by the source manifests above. Detailed paths, line references, operation order and wiki adjudications remain in [the 1.8.9→1.9.4 source record](mcpk-1.8.9-1.9.4.md) and [the full source adjudication](mcpk-source-adjudication.md). This snapshot binds those claims to the checked revision without claiming equivalence to unavailable original mapped JARs.

- 1.8.9→1.9.4: movement cutoff and jump-apex change; sneak dimensions; ladder, pane/bar, lily-pad, piston-head, snow-layer, chest and anvil collision-shape claims.
- 1.10.2: auto-jump option, eligibility and obstacle threshold.
- 1.10.2→1.11.2: sneak-edge support probe and cocoa collision correction.
- 1.11.2→1.12.2: stair-side connections for fences, walls and panes; pane/bar barrier exclusion; bed bounce.
- 1.12.2→1.13.2: single-layer snow shape representation and Blue Ice slipperiness.
- 1.13.2→1.14.4: horizontal collision-axis ordering and ladder/vine jump-climb predicate. The 1.14.4 endpoint uses its unchanged Feather ready source tree; the affected 1.13.2 dependency uses the revised snapshot recorded here.

## Status

Consumer-side hash verification passed for all six revised bundles, and the independent operations audit was reported as passed. This exact finding snapshot is now ready for independent reviewer acceptance. Until that reviewer accepts it and its dependencies, the source/wiki pair remains partial. The unavailable original derived mapped JAR identity and equivalence remain unproven; the old mutable-cache mismatch is not waived.
