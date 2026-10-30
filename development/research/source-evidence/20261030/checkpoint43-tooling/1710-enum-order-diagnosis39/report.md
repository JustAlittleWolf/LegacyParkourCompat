# Minecraft 1.7.10 enum-helper order diagnosis

## Scope and limits

Static-only inspection of the failed exact-version `decompileMinecraft 1.7.10` run, cached inputs, ordinary mapped JAR, current `EnumSwitchHelperRepair`, and Vineflower 1.12.0 source JAR. No build, decompile, test, game launch, download, or cache/source write was performed. The candidate ordering below is a graph feasibility result, not a new Vineflower run or proof that reordering eliminates all nine errors.

## Failed Vineflower bindings

`.native-artifacts/decompile-1710-proof39.log` records nine `Unable to simplify switch on enum: const(1) not found, available: {}` errors:

| Failed class and method | Mapped helper field observed with `javap -c -p` |
| --- | --- |
| `GameOptions.getBoolean` | `GameOptions__67871314.f_94081458` |
| `TwitchChat.chatChannelMembershipCallback` | `TwitchChat__65676818.f_47910079` |
| `TwitchChat.update` | `TwitchChat__65676818.f_13860485` |
| `StreamUnavailableScreen.buttonClicked` | `StreamUnavailableScreen__15502813.f_89268933` |
| `StreamUnavailableScreen.m_90754652` (first switch) | `StreamUnavailableScreen__15502813.f_43279484` |
| `StreamUnavailableScreen.m_90754652` (second switch) | `StreamUnavailableScreen__15502813.f_67764942` |
| `Minecraft.doAttack` | `Minecraft__30089582.f_57361349` |
| `Minecraft.doUse` | `Minecraft__30089582.f_57361349` |
| `ClientPlayerEntity.textureAvailable` | `ClientPlayerEntity__60417933.f_23541581` |

Each failed method contains an actual `GETSTATIC` of the stated `[I` field. The two `Minecraft` methods share one table; the two `m_90754652` switches use separate arrays. All nine method bindings point to five helper classes.

## Exact cached pins and candidate mapped-content pin

| Input | SHA-256 / identity | Evidence |
| --- | --- | --- |
| Minecraft release metadata | `265c7e47aad4c74f6085a49667d3a828f48d63f484def2ae70b5e426e5b7cba6` | `version.json.pin.json`; release `1.7.10` |
| Raw client | `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce` | `client.jar.pin.json`; Mojang SHA-1 in version metadata also matches `e80d9b3bf5085002218d4be59e668bac718abbc6` |
| Feather mapping artifact | `1a22781aa3bac248e25c619842ec2ec5c126d015436d528d411a9e85a6f491a7` | pinned `feather-gen2-1.7.10+build.2-mergedv2.jar.pin.json` |
| Extracted Tiny mapping | `bee4c201a7f815efc947eafec2a300d59e5e869f9730f5e76319285d2bd59485` | cached `feather-gen2-1.7.10+build.2.tiny`; header is `tiny 2 0 official intermediary named` |
| Ordinary mapped client archive | `d373731588184ca24c89b09b8cf48ae159278e31d429d088a812067094dc90fd` (container SHA-256); canonical content SHA-256 `36a733c84bc094a08a3118e8bd7f3a88c9a6c94e41bbe806acb175794cd43243` | cached `client-ornithe-feather.jar`; observed candidate, not yet an accepted feature pin |

The canonical digest uses the existing `LPC-JAR-CONTENT-v1\0` framing: sort paths, then hash each UTF-8 path with a big-endian 32-bit length and each uncompressed entry body with a big-endian 64-bit length. This archive contains 1,962 unique paths, including 1,803 class files; no duplicate archive path was found. The current cached ordinary mapped archive is evidence from the failed attempt, not a preexisting content pin. A feature pin should be adopted only after verifying the ordinary remap under the exact input pins.

## Classfile shape and Vineflower roots

The exact ordinary mapped archive has 1,803 Java 6 class files (major version 50). The full classfile-attribute audit found only `Code`, `ConstantValue`, `LineNumberTable`, `LocalVariableTable`, `MethodParameters`, `SourceFile`, and `StackMapTable`; there are no `InnerClasses`, `EnclosingMethod`, `NestHost`, or `NestMembers` attributes and no unknown attributes. Thus this exact archive has no metadata-defined nested-root groups. Names such as `Entity__Size` and `StrongholdPieces__StrongholdPiece` still represent separate Vineflower roots here; do not infer grouping from mapped names or edit/strip attributes.

Vineflower 1.12.0 source confirms the ordering boundary: `ContextUnit.initEntries()` retains the input class-entry order (`ContextUnit.java:55-60`); `StructContext.getOwnClasses()` traverses those names in that order (`StructContext.java:146-151`); `ClassesProcessor` preserves that list while building class nodes (`ClassesProcessor.java:115-122`). With inner decompilation enabled, nested relationships are assembled from `InnerClasses` entries and related enclosing metadata (`ClassesProcessor.java:137-239, 251-300`); absent such relationships, every own class is initialized as a `ROOT` (`ClassesProcessor.java:243-247`). `ContextUnit.save()` then submits the same `classEntries` sequence for root processing (`ContextUnit.java:178-205`). The cached `vineflower-1.12.0-sources.jar` SHA-256 is `9ac4120c6afdf83bf0e798f3a28c56040d5e05fd32462681704539a244398da9`.

## Helper dependency graph

Every candidate has a synthetic class flag, exactly one `<clinit>`, and one or more `[I` fields with `static final synthetic` flags. Scanning all field references identified 14 candidates; `javap -c -p` confirmed one external consumer root per helper and 14 helper-to-consumer edges (21 external `GETSTATIC [I` instructions total). The remaining array reads inside each helper are its own initializer code. The table gives class-entry indexes in the ordinary mapped archive:

| Helper root (index) | Consumer root (index) | External `GETSTATIC` sites | Needs reorder? |
| --- | --- | ---: | --- |
| `Minecraft__30089582` (1648) | `Minecraft` (703) | 2 | Yes |
| `ClientPlayerEntity__60417933` (1655) | `ClientPlayerEntity` (805) | 1 | Yes |
| `StreamUnavailableScreen__15502813` (1617) | `StreamUnavailableScreen` (647) | 3 | Yes |
| `GameOptions__67871314` (208) | `GameOptions` (47) | 1 | Yes |
| `SoundManager__86087158` (793) | `SoundManager` (1487) | 1 | No |
| `BroadcastController__86014756` (201) | `BroadcastController` (298) | 1 | No |
| `IngestTester__35774615` (107) | `IngestTester` (1050) | 4 | No |
| `TwitchChat__65676818` (1102) | `TwitchChat` (596) | 2 | Yes |
| `Entity__48650159` (280) | `Entity__Size` (1425) | 1 | No |
| `LocalHandshakeNetworkHandler__40630631` (37) | `LocalHandshakeNetworkHandler` (1580) | 1 | No |
| `RemoteHandshakeNetworkHandler__25347352` (739) | `RemoteHandshakeNetworkHandler` (1320) | 1 | No |
| `ServerPlayNetworkHandler__69539816` (169) | `ServerPlayNetworkHandler` (797) | 1 | No |
| `AddEdgeLayer__41582934` (640) | `AddEdgeLayer` (1206) | 1 | No |
| `StrongholdPieces__21168123` (0) | `StrongholdPieces__StrongholdPiece` (1257) | 1 | No |

The graph has 1,803 class nodes, 14 edges, no cycle, and five edges whose helper currently follows its consumer. A stable Kahn ordering by original class-entry position satisfies all 14 helper-before-consumer constraints. Reordering class-entry slots can preserve every path and uncompressed entry body; this is feasible without bytecode changes, class renames, or metadata changes. It remains to run the authorized preparation separately and check whether this candidate resolves the strict Vineflower errors.

## Minimal pin-gated tooling design

1. Add a distinct `1.7.10` Feather pipeline identity and exact raw-client, mapping-artifact, Tiny, and ordinary-mapped canonical-content pins. Keep the `1.7.10+build.2` cache/output identity separate from the 1.8 and 1.8.1 pipelines.
2. In the decompile engine, verify the release pins before remapping. Preserve the ordinary direct `official -> named` remap as a baseline archive; verify its canonical content pin and all nine diagnostic bindings before ordering.
3. Reuse the byte-preserving archive order operation only after the pinned archive proves the expected helper shape, complete direct `GETSTATIC` bindings, unique paths, and acyclic graph. Keep a separate ordered output path and pipeline ID; run existing strict zero-error, failed-method, required-source, and provenance gates afterward.
4. Fail closed if any nested-class metadata appears in this exact flat-archive pin. Do not strip or rewrite it. If a future exact input with nested groups is separately audited, mirror Vineflower’s actual `InnerClasses`/enclosing grouping: resolve each helper and consumer to its Vineflower top-level root, add dependencies between those roots, and stable-topologically reorder root groups while preserving each group’s internal entry order and every class byte. This avoids splitting or reinterpreting existing nested groups.

## Reproducible static evidence

- `inventory_1710.py` — raw classfile, helper-shape, candidate-archive inventory.
- `verify_order_graph_1710.py` — verifies direct consumer bindings with read-only `javap`, then performs a stable topological feasibility check.
- `raw-inventory.json`, `feather-inventory.json` — machine-readable findings for raw and mapped JARs.
- `mapped-attributes.json` — full standard-attribute inventory of the ordinary mapped archive.
- Source archive: `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\1.7.10\client-ornithe-feather.jar`.
- Mapping artifact and Tiny paths are under `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\yarn\`.
