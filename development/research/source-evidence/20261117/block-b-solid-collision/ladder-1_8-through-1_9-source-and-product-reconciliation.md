# Ladder collision source and product reconciliation

Status: scoped source and product-code reconciliation; product checkout was read-only. The accepted scope supplied to this task is 1.8–1.8.9: historical ladder collision thickness 2/16 (1/8), followed by 1.9: 3/16. No decompilation, build, test, or launch was performed.

## Exact source identity and immutable class hashes

All sources are under `decompiled_minecraft/<release>/ornithe-feather/net/minecraft/block/LadderBlock.java`; all use the Ornithe Feather Gen2 namespace. SHA-256 is over the exact source file bytes. Source release, mapping family, and mapping build were checked against the per-release cached mapping metadata (`.minecraft-decompile-cache/mapping-metadata/ornithe-feather-<release>.json`) and mapping artifact directories. The 1.8.9 canonical root additionally carries its provenance, ready marker, artifact manifest, and full source manifest under `decompiled_minecraft/1.8.9/`.

| Exact release | Feather mapping | `LadderBlock.java` SHA-256 |
|---|---|---|
| 1.8 | `feather-gen2:1.8+build.2` | `e94f204ebda56ce831be472cd30e3138a96306be5e692111cbbc52aca1e13177` |
| 1.8.1 | `feather-gen2:1.8.1+build.2` | `e94f204ebda56ce831be472cd30e3138a96306be5e692111cbbc52aca1e13177` |
| 1.8.2 | `feather-gen2:1.8.2+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.3 | `feather-gen2:1.8.3+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.4 | `feather-gen2:1.8.4+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.5 | `feather-gen2:1.8.5+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.6 | `feather-gen2:1.8.6+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.7 | `feather-gen2:1.8.7+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.8 | `feather-gen2:1.8.8+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.8.9 | `feather-gen2:1.8.9+build.2` | `c13b123f8fd7427d7f646da3b789eb97d4e54aa2fe2860f66cf4fd1302865106` |
| 1.9 | `feather-gen2:1.9+build.2` | `42d3c814f8ba54b4fa09a979420f65e0ff73217fe7f36f0f06533207e97032ce` |

For 1.8–1.8.8, original source roots have no per-root provenance manifest. Their mapping identities are corroborated by the hidden cache's exact version metadata and pinned `feather-gen2` build-2 mapping artifacts; the table's per-source hashes were freshly computed from the canonical source files. For 1.8.9, `ornithe-feather.provenance.json` records exact version `1.8.9`, namespace, Feather mapping option, mapping artifacts `feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`, and the exact run; its source manifest is `ornithe-feather.sources.sha256` (SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`). For 1.9, metadata identifies `feather-gen2:1.9+build.2`; the exact class bytes are bound by the table hash.

## Source comparison

In 1.8 (`LadderBlock#getCollisionShape`, `#getOutlineShape`, `#updateShape`) and unchanged source bytes in 1.8.1, collision and outline queries refresh the facing box through `updateShape`. The four `FACING` arms set an extent of `0.125F` (2/16 = 1/8): NORTH occupies z=[0.875,1], SOUTH z=[0,0.125], WEST x=[0.875,1], EAST x=[0,0.125], with y=[0,1] and the other horizontal axis spanning [0,1]. The 1.8.2–1.8.9 class source hashes are also identical and retain the same `getCollisionShape` → `updateShape` call and four 0.125F arms. Exact source references: 1.8.9 `LadderBlock.java`, `getCollisionShape` and `updateShape` (lines 26–56); identical class bytes also prove the earlier 1.8.2–1.8.8 versions. 1.8 and 1.8.1 have identical class bytes and corresponding members (lines 28–55).

In 1.9, `LadderBlock#getShape` selects one of four static facing boxes (lines 17–37); each has thickness `0.1875` (3/16): NORTH z=[0.8125,1], SOUTH z=[0,0.1875], WEST x=[0.8125,1], EAST x=[0,0.1875]. The block base collision path consumes this selected state shape. The transition is therefore 1/8 on all four horizontal facings through 1.8.9, then 3/16 from 1.9.

## Product resolver reconciliation

Relevant code is in:

- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8/LadderCollisionShape.java`: `@MovementChange(V1_8)`, `minecraft:ladder` keyed hook, four 0.125D shapes.
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8/LadderCollisionNativeFence.java`: same hook key at `V1_8_5`, returns `Optional.empty()` and thus preserves the native 26.2 3/16 shape.
- `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8_9/LadderCollisionThickness.java`: same key at `V1_8_9`, four 0.125 shapes.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`: per-key selection filters to `emulates >= selected` and takes the lowest ordinal among them.

Consequently, the V1_8_5 native fence is selected for profiles V1_8_1 (releases 1.8.1–1.8.4) and V1_8_5 (1.8.5–1.8.8), ahead of the V1_8_9 historical implementation. It shadows the accepted 1/8 behavior across every profile from 1.8.1 through 1.8.8. V1_8 itself currently selects the correct 1/8 implementation; V1_8_9 selects its correct 1/8 implementation because V1_8_5 is older than the selection. From V1_9 onward the later-than-selected filter leaves this hook unresolved, so native 3/16 is correct.

Minimal integration action: remove `LadderCollisionNativeFence` from the product. The existing V1_8_9 historical hook then resolves for all older profiles up to 1.8.9 (while the V1_8 hook remains the closer match for V1_8); V1_9 and later remain native at 3/16. Keep the two 1/8 implementations or consolidate only if the integration owner chooses a separately justified simplification. No resolver change is needed.

The behavior is applicable in the current integration path: `MovementRuntime.appliesTo` rejects non-player entities, logical servers, and disabled/native profiles; `collectMovementBlock` requires `MovementCollisionContext`, resolves by the ladder block key, and uses native fallback when a hook returns empty. Both nonempty historical implementations read the world `BlockState`, verify the block is `Blocks.LADDER`, and switch on the vanilla `LadderBlock.FACING`, covering all four orientations. This remains player collision-shape emulation with vanilla state intact.

## Boundaries

This dossier reconciles source and the current product resolver only. It does not revise the prior source-acceptance decision, establish intermediate boundaries beyond the exact source rows above, or claim runtime parity.