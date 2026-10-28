# Independent scoped audit — 1.21 → 1.21.1 (Mojmap)

## Disposition

**Accept the bounded negative claim:** within the examined local-player input, jump/sprint, travel, and direct movement-response paths, this pair shows no source delta. **Partial as a pair-level audit:** this verdict does not freeze the pair or establish full reachable movement-graph coverage or runtime parity.

## Source identity and integrity

Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- **1.21 / Mojmap:** the release parent contains the Mojmap directory; with `-Force`, its embedded `.decompile-provenance.json` is present. The provenance source map contains 5,363 entries. It binds the run to metadata input `1.21.json` SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`, client JAR SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, and Mojang `client.txt` SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. Provenance SHA-256: `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`.
- **1.21.1 / Mojmap:** `mojmap.ready.json` identifies version and metadata as 1.21.1, 5,363 files and 25,773,546 source bytes; `mojmap.sources.sha256` and `artifacts.sha256` hashes match the ready marker. Provenance identifies Mojmap and the 1.21.1/1.21.3/1.21.4/1.21.5 batch with Vineflower 1.12.0. Client JAR SHA-256: `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; client mappings SHA-256: `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`; mapped client JAR SHA-256: `6b36d2ccc99e7eeb98e942b6dc093388d66eb6ede06b64238f35a0e26b092abe`. Provenance, ready marker, source manifest, and artifacts manifest SHA-256 values match the examiner's companion hashes.
- I enumerated release parents with hidden entries included. 1.21 has no separate root ready/source-manifest files; its embedded provenance map is the source manifest. 1.21.1 has the expected root metadata and manifests; no additional hidden snapshots were present.
- Independently checked all mapped source entries against disk: both maps contain the same 5,363 paths, no missing paths, and zero source-hash mismatches. Source bytes: 25,771,219 (1.21) and 25,773,546 (1.21.1). The maps differ at exactly 17 paths.
- The assigned `report.md` SHA-256 recomputes to `e35be10bfe89dc1e51d5d60d686af05003be04b4fe7763ae3ab7dd4d680d7810`.

## Exact paired movement evidence reviewed

For every file below, the on-disk 1.21 and 1.21.1 SHA-256 values are equal and match each release's source map. Consequently all method bodies and enclosing gates in these files are byte-identical.

| Mojmap source | Reviewed members / ranges (shared line numbers) | SHA-256 on both releases |
|---|---|---|
| `net/minecraft/client/player/Input.java` | `tick` 15–16; `getMoveVector` and `hasForwardImpulse` 18–24 | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` |
| `net/minecraft/client/player/KeyboardInput.java` | `tick`, impulse mapping, jump/sneak sampling and slowdown 12–34 | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/LocalPlayer.java` | `aiStep` input/sprint and jump/fly ordering 644–804; `move`, auto-jump and collision/sprint predicates 881–1044; underwater predicate 1069–1092 | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | LocalPlayer superclass movement context | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| `net/minecraft/world/entity/player/Player.java` | fluid-affect predicate 1065–1067; `jumpFromGround` and exhaustion gate 1445–1453; `travel` wrapper/order 1456–1478; sprint/flying speed 2114–2124 | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/LivingEntity.java` | sprint modifier 1994–2001; jump power/impulse 2043–2068; liquid jumps 2074–2084; fluid/fall-flying/ground travel 2091–2215; relative movement/friction 2252–2278; jump and travel dispatch 2586–2681 | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | ground/support state 558–596; `move`, piston gate, collision and ground responses 598–654; block jump/speed factors 822–835; relative input math 1350–1365; sprint state 2228–2234 | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |

The verified call path is sampled keyboard input → `LocalPlayer.aiStep` input and sprint decisions → `LivingEntity.aiStep` jump/travel dispatch → player swimming/flying `travel` wrapper → living-entity fluid, fall-flying and ordinary travel → relative friction and `Entity.move` collision/position/ground responses. The direct `LocalPlayer.move` auto-jump response and its collision/sprint predicates are also in the unchanged file.

## Changed-path dispositions

The source-map delta consists only of these 17 paths: `ReportEnvironment.java`; `EntityArgument.java`, `GameProfileArgument.java`, `MessageArgument.java`, `ScoreHolderArgument.java`, `EntitySelectorParser.java`; `DetectedVersion.java`, `SharedConstants.java`; `EntityDataSource.java`, `ScoreContents.java`, `SelectorContents.java`; `ChiseledBookShelfBlock.java`, `BlockEntity.java`, `LevelChunk.java`, `LecternBlock.java`, `SculkSensorBlock.java`, and `ShulkerBoxBlock.java`.

The report's classification is supported. Version/environment files change metadata; argument/selector and chat-content files concern command selection, parsing or display; `LecternBlock`, `SculkSensorBlock` and `ShulkerBoxBlock` have only trailing-blank-line differences. The three nontrivial block-side diffs were inspected directly: `ChiseledBookShelfBlock.onRemove` defers neighbor-output notification until after `super.onRemove`; `BlockEntity` adds block-entity/block-state compatibility validation; `LevelChunk` removes an incompatible block entity before recreating it. These are block/redstone or block-entity lifecycle changes, not local-player movement responses or movement-shape changes. No changed path is one of the input/player/entity movement sources or a block collision-shape provider.

## Residual coverage

This audit accepts only the bounded negative claim above. It does not independently close every reachable movement dependency (including resource/tag and callback dependencies), inspect every release pair or intermediate patch, establish an exact introduction boundary, or validate runtime parity. The report itself also labels its sweep bounded and expressly declines pair freeze; retain that limit.
