# Blind source sweep: Mojmap 1.21 → 1.21.1

## Result

No in-scope ordinary local-player movement delta was found in this bounded source sweep. The exact decompiled contents and manifest hashes for the seven movement-core classes listed below are identical between 1.21 and 1.21.1. The full source inventories contain 5,363 files each; only 17 source paths differ, and the changed hunks reviewed do not change the scoped movement routines or direct player movement responses.

This is a bounded examiner report, not a full-pair coverage freeze, independent pair audit, runtime parity result, or claim about an exact intermediate introduction boundary.

## Source identity

Canonical read-only tree: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

Both sides use the `mojmap` namespace. Release identities are anchored as follows:

- **1.21:** `1.21/mojmap/.decompile-provenance.json` embeds the 5,363-entry source map and records the 1.21 version metadata input (`.../c47b4c513b2883240565102b076054e347eac738/1.21.json`, SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`), client JAR SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, and Mojang mapping input `client.txt` SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. Decompile provenance file SHA-256: `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.
- **1.21.1:** `1.21.1/mojmap.ready.json` identifies `versionId` and `versionMetadataId` as `1.21.1`, mapping `mojmap`, 5,363 source files / 25,773,546 source bytes, and client JAR SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`. Its artifacts manifest identifies `client_mappings.txt` SHA-256 `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec` and mapped `client-mojmap.jar` SHA-256 `6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe`. The provenance records the exact batch containing 1.21.1 (1.21.1, 1.21.3, 1.21.4, 1.21.5), mapping option `mojmap`, and Vineflower 1.12.0. SHA-256 values of the release provenance, ready marker, source manifest, and artifacts manifest are recorded in the companion `hashes.sha256`.

Hidden release and source metadata were checked with hidden entries included. The 1.21 release has no separate root ready/source-manifest files; its embedded provenance source map supplies those per-source hashes. The 1.21.1 release has the root ready marker, source manifest, artifacts manifest, and provenance file. No source was generated or modified during this review.

## Paired movement coverage

The exact file SHA-256 is equal on both sides for each file below, so all listed method bodies and surrounding gates are byte-identical. Anchors refer to the shared line numbers in each release:

| Source | Reviewed movement paths (1.21 and 1.21.1) | SHA-256 (both) |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `tick` 15–16; movement vector and forward-impulse predicate 18–24 | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` |
| `net/minecraft/client/player/KeyboardInput.java` | impulse mapping 12–18; input sampling, jump/sneak flags and slowdown multiplier order 21–34 | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | input/sprint setup and cancellation 644–715; jump/fall-flight/water input and `super.aiStep` ordering 717–804; direct `move`/auto-jump response 881–988; collision-minor and auto-jump predicates 991–1044; underwater state predicate 1069–1092 | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | local-player superclass movement context | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| `net/minecraft/world/entity/player/Player.java` | fluid-affect predicate 1065–1067; ground-jump wrapper and sprint exhaustion gate 1445–1453; swimming/flying travel wrapper and call order 1456–1478; sprint and flying speed 2114–2124 | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | sprint flag 1994–1995; jump power and sprint impulse 2043–2068; liquid jump and stand-on-fluid predicate 2074–2084; water/lava/fall-flying/ground travel branches 2091–2215; relative-friction movement and fluid-falling adjustment 2252–2278; ordinary jump/travel dispatch 2586–2681; swimming predicate 3172–3173 | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | ground/support state writers and `onGround` read 558–596; collision, piston movement and collision-state responses 598–654; block jump/speed-factor predicates 822–835; relative input vector math 1350–1365; sprint flag read/write 2228–2234 | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

Reviewed reachable order: keyboard input sampling → `LocalPlayer.aiStep` input and sprint decisions → `LivingEntity.aiStep` jump decision and travel dispatch → `Player.travel` swimming/flying wrapper → `LivingEntity.travel` fluid, fall-flying and ordinary ground/air branches → relative friction / `Entity.move` position, collision and ground-state writes. The movement-affecting local predicates checked include forward impulse, sprint eligibility, ground/underwater gates, fluid susceptibility, jump strength, block jump/speed factors, collision state, and auto-jump eligibility. These are unchanged by exact per-file hashes.

## Whole-source delta inventory and dispositions

The two 5,363-entry source maps differ at 17 paths. Each path was checked for movement relevance. The non-movement changes are:

- `net/minecraft/client/multiplayer/chat/report/ReportEnvironment.java`, `net/minecraft/DetectedVersion.java`, and `net/minecraft/SharedConstants.java`: release/version metadata.
- `net/minecraft/commands/arguments/EntityArgument.java`, `GameProfileArgument.java`, `MessageArgument.java`, `ScoreHolderArgument.java`, `selector/EntitySelectorParser.java`, `net/minecraft/network/chat/contents/EntityDataSource.java`, `ScoreContents.java`, and `SelectorContents.java`: selector permission/parsing behavior.
- `net/minecraft/world/level/block/ChiseledBookShelfBlock.java`: changes in `onRemove` defer the neighbor-output update until after `super.onRemove`; this is block/redstone environment behavior, not a direct movement response or a change to the block's movement shape.
- `net/minecraft/world/level/block/entity/BlockEntity.java` and `net/minecraft/world/level/chunk/LevelChunk.java`: validate block-entity state compatibility and remove invalid block entities; these do not change vanilla block states, player movement methods, or collision-shape code.
- `net/minecraft/world/level/block/LecternBlock.java`, `SculkSensorBlock.java`, and `ShulkerBoxBlock.java`: source-manifest hash differs only by a trailing blank-line difference; no code difference.

No changed path is `LocalPlayer`, `Player`, `LivingEntity`, `Entity`, client input, a collision-shape provider, or a movement-factor provider. The changed environment/block-entity paths are noted as dispositions rather than movement findings.

## Negative-reviewed paths and limits

Packet receipt and remote-player correction, input-device plumbing beyond the keyboard state mapper, environment-side production of fluids/block states, rendering, logical-server behavior, and vehicle/non-player physics were excluded. `Entity.move`'s direct player movement branch, including the `MoverType.PISTON` gate, was reviewed because it is a player response; its implementation is unchanged. The examiner did not review other version pairs, intermediary patches, product implementations, historical findings, wiki material, or runtime behavior. This report therefore says only that the specified Mojmap source paths show no applicable movement delta for 1.21 → 1.21.1 within the assigned sweep.

