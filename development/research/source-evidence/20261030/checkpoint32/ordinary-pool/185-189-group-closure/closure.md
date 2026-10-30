# 1.8.5–1.8.9 same-input ordinary movement group closure

## Verdict and scope

**Freeze recommendation: accept the bounded ordinary local-player movement conclusion for equal effective movement inputs and equal vanilla world/block state at each tick boundary. Do not label this a full movement-pair freeze or runtime-parity result.** Across 1.8.5–1.8.8, the pooled adjacent-patch Java hash sweep found no changed player/entity movement implementation or collision/movement provider, and its independent audit verified the inventories, changed-path sets and selected core movement/provider hashes. For 1.8.8–1.8.9, an independently audited full Java-source endpoint comparison found no changed ordinary-movement root or provider; the independent ordinary-movement audit confirms the input, sprint/jump, and ground/air travel call chain is byte-identical. The retained movement terminals therefore receive the same Java behavior under equal movement inputs and equal movement-relevant world state throughout this adjacent chain.

The conclusion is deliberately conditional on equal effective movement inputs and block states. A 1.8.5→1.8.6 change removes a command-block dispenser behavior that can produce a different block state in a composed gameplay trace. That is a real source delta, not a movement-math delta; if the task instead requires equal initial worlds and all non-player world evolution to remain identical, that producer needs a separate scope decision. No other open issue in this report is evidence of a movement delta.

## Immutable evidence identities

All evidence below was read from the repository artifacts and read-only canonical source trees. The author/audit hashes were recomputed directly from the repository files.

| Evidence | SHA-256 | What it establishes |
|---|---|---|
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep/report.md` | `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb` | Adjacent Java path/hash inventory and dispositions for 1.8.5→1.8.6→1.8.7→1.8.8. |
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep-audit/audit.md` | `336ed48262297ad046847c8d5a84d7fd234083461786943c81a3da6b19da84aa` | Independent verification of those four source inventories, 65 adjacent changed-path counts, all changed-path sets, and 80 reported hashes for the selected movement/provider files. |
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep/movement-core-sha256.corrected.tsv` | `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6` | Corrected 20-class movement/provider hash table, each class byte-identical in 1.8.5, 1.8.6, 1.8.7 and 1.8.8. The original TSV has a malformed header and is not used for parsing. |
| `.native-source/20261028/frontier-c/188-189-movement-bridge/boundary-audit-1.8.8--1.8.9.md` | `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a` | Endpoint Java inventory, changed hunks and movement-root/provider boundary. |
| `.native-source/20261028/frontier-c/188-189-movement-bridge-audit/audit.md` | `19023d9ab5d805561b9e66640a48778c0c9a3a36661ebf23b8c127eb23363598` | Independent verification of the 1,612-path manifests, all 26 path/hash deltas and bounded bridge dispositions. |
| `.native-source/20261028/frontier-c/188-189-movement-bridge/changed-source-hashes.csv` | `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2` | Exact endpoint hashes for every 1.8.8→1.8.9 changed Java path. |
| `.native-source/20261028/frontier-c/188-189-ordinary-movement/report.md` | `f2357c93a2e327a62f14d20e1fa68e1891e8efdad15b25bbb1c46ccb4b1fe86d` | Direct ordinary local-player input/sprint/jump/travel slice for 1.8.8→1.8.9. |
| `.native-source/20261028/frontier-c/188-189-ordinary-movement-audit/audit.md` | `edba68edfeba36c16850957617a73bf9945b704df88f505b338f842697bd7de6` | Independent audit of that bounded ordinary slice; identifies the author's omitted but unrelated `CrashReport.java` path. |

### Exact source roots and provenance

The 1.8.5, 1.8.6, 1.8.7 and 1.8.8 canonical roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Their embedded provenance markers and Mojang client SHA-256 values, independently verified by the sweep audit, are:

| Release | Client object ID | Client JAR SHA-256 | Embedded provenance SHA-256 | Verified Java files |
|---|---|---|---|---:|
| 1.8.5 | `5d39957c61d19042f8bd669a6faa99989cf37083` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | 1,612 / 1,612 |
| 1.8.6 | `faa55e34ded35089d34fe921ea83d317fc152e93` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | 1,612 / 1,612 |
| 1.8.7 | `d546a6a092060c85f1eb1d9213ff823c558b1255` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | 1,612 / 1,612 |
| 1.8.8 | `0983f08be6a4e624f5d85689d1aca869ed99c738` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | 1,612 / 1,612 |

1.8.9 is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/`, bound by the release-parent ready/provenance markers and `ornithe-feather.sources.sha256`: client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`, ready marker SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`, provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, and source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. The existing independent audits verified all 1,612 Java files against the per-release inventory. The four 1.8.5–1.8.8 release-parent provenance markers are absent; each mapping-root marker is present and verified. No locally cached client JAR was used or opened.

## Composed movement terminals

The corrected 1.8.5–1.8.8 table contains 20 whole-file movement/provider anchors, each byte-identical at all four releases: `Entity`, `LivingEntity`, `PlayerEntity`, `ClientPlayerEntity`, `LocalClientPlayerEntity`, `Input`, `KeyboardInput`, `World`, `ClientWorld`, `Block`, `Blocks`, `LadderBlock`, `LiquidBlock`, `SlimeBlock`, `SoulSandBlock`, `PistonBaseBlock`, `PistonHeadBlock`, `IceBlock`, `StairsBlock`, and `FallingBlock`. This establishes equality for those full files, not a complete movement-callgraph inventory by itself.

For 1.8.8→1.8.9, the bridge report and audit find the full Java trees have identical 1,612-path sets, 1,586 equal hashes, 26 changed hashes, and no added/removed Java paths. The nine byte-identical roots independently named for the ordinary local-player path are `Entity`, `LivingEntity`, `PlayerEntity`, `ClientPlayerEntity`, `LocalClientPlayerEntity`, `Input`, `KeyboardInput`, `World`, and `ClientWorld`. The ordinary-slice audit also verifies `KeyBinding` and `ClientPlayerInteractionManager` equal. The complete 26-path delta has no `block/`, `entity/`, `world/`, `network/` or movement-provider path. Thus the selected input/travel roots, collision providers covered by the 20-class table, and Java block/entity/world provider path sets compose without a changed movement implementation across the group.

## Changed-path routing and boundary findings

The existing independent sweep audit checked every changed-path set and reviewed the changed hunks. The 1.8.8→1.8.9 bridge audit independently checked all 26 changed hunks. This section routes the changed classes to movement terminals and records the one state-producer boundary; it does not redo the per-file hash sweeps.

### 1.8.5→1.8.6 (21 changed Java paths)

`BlockSource` and `IBlockSource` remove the `getBlock()` accessor. In 1.8.5 the only call found is the command-block dispenser behavior in `Bootstrap`; that behavior is removed in 1.8.6. The removed handler used an item in a dispenser to place `Blocks.COMMAND_BLOCK` with `TRIGGERED=false` in an adjacent air cell, copy block-entity NBT, and update neighbors. This is a possible world-state producer delta. It does not change `Block` collision math, movement roots, or the response to the same block state, but it can cause a different future world state when the handler is reachable. Therefore equal effective block state is an explicit premise of the freeze recommendation.

`BlockEntity`, `CommandBlockBlockEntity`, `MobSpawnerBlockEntity`, `SignBlockEntity`, `BlockItem`, `PlaceableItem`, and `SignItem` route to placement authorization/block-entity NBT setup. The added operator gate is not player travel or collision code. `DebugOverlay`, `TitleScreen`, `Minecraft`, `ServerListEntry`, and `RealmsSharedConstants` route to UI/version display. `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `ServerPlayNetworkHandler`, `Snooper`, and `CrashReport` route to server/protocol/diagnostic handling. None changes the same-state movement terminals.

### 1.8.6→1.8.7 (16 changed Java paths)

`ResourcePacks` changes server-pack download naming/retention and reload triggering. It selects, downloads, removes and reloads client resources; it does not write movement input, player pose/velocity/box, world block state, or collision providers. `TextRenderer`, `DebugOverlay`, `TitleScreen`, `Minecraft`, `ServerListEntry`, and `RealmsSharedConstants` route to rendering/UI/version display. `SignUpdateC2SPacket` routes to sign text serialization; `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `ServerPlayNetworkHandler`, and `ServerWorld` route to server/network behavior; `Snooper`, `CrashReport`, and `HttpUtil` route to diagnostics/download support. No changed path is a movement terminal/provider.

### 1.8.7→1.8.8 (28 changed Java paths)

`GameOptions` adds and persists the incompatible-resource-pack names list; movement key-binding declarations and conversion are unchanged. `ResourcePacks` uses this list to accept or remove packs by format and conditionally reload a server pack. `ResourcePackEntry`, `CustomResourcePackEntry`, `DefaultResourcePackEntry`, and `ResourcePacksScreen` display compatibility and confirm pack selection; these route to resource selection/UI. The resource selection can change client presentation assets, but does not change vanilla block states or feed collision/travel calculations under equal effective input and world state.

`Bootstrap` changes skull-owner parsing only: an empty string no longer creates a profile. It does not alter block/item registrations or movement-provider registration. `Blocks.java`, `Block.java`, all class paths under `block/`, `entity/`, and `world/` other than storage are unchanged in this interval; selected movement/provider hashes remain equal. `AnvilChunkStorage` adds a save-progress flag/log and changes save error reporting/flush bookkeeping; its delta concerns persistence and scheduling, not loaded block-state collision responses under the stated premise.

The other changed paths—`DebugOverlay`, `PlayerTabOverlay`, `TitleScreen`, `ProgressScreen`, `Minecraft`, `ServerListEntry`, `WornSkullLayer`, `GLX`, `LayeredColorMaskTexture`, `SkinManager`, `Realms`, `RealmsConnect`, `RealmsSharedConstants`, `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `ServerQueryNetworkHandler`, `Snooper`, `CrashReport`, and `Utils`—route to display/rendering, version/Realms/server reporting, networking, or utility code. No path changes a player movement terminal or collision provider.

### 1.8.8→1.8.9 (26 changed Java paths)

`GameOptions` adds the Realms-notifications boolean and option serialization/UI toggle; the movement key declarations and `KeyBinding` code are unchanged. `Minecraft` changes version strings, and `MathHelper` only renames locals in HSV-to-RGB conversion while preserving casts and expressions. The ordinary-movement audit found the `EffectCommand` local renames preserve effect duration, amplifier, guards and argument order. The other paths route to UI, render/audio, Realms, book text, command-local renames, server/network, or diagnostics. These are `DebugOverlay`, `OptionsScreen`, `TitleScreen`, `Screen`, `ServerListEntry`, `ChestRenderer`, `SoundManager`, `WrittenBookItem`, `Realms`, `RealmsBridge`, `RealmsScreen`, `RealmsScreenProxy`, `RealmsSharedConstants`, `BanEntry`, `EffectCommand`, `EnchantCommand`, `CommandResults`, `SummonCommand`, `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `Snooper`, `CrashReport`, plus `GameOptions`, `Minecraft`, and `MathHelper`. The independent audit notes `CrashReport.java` was omitted from the author’s changed-file prose; it changes only the displayed release string. No path touches ordinary movement terminals or block/entity/world providers.

## Resources, options and registration boundary

The mapped source roots contain Java plus provenance, not packaged resource assets. No client JAR was inspected, so this report does not claim asset-byte equality across releases. The changed Java resource-pack code does have behavior: it can change which pack is accepted/selected and when client resources reload. In these versions that mechanism loads client resource assets and does not register blocks or substitute block states; texture, model, language, font and sound resources affect client presentation/audio. With equal effective input, vanilla block states, player effects and movement-relevant runtime state, those assets have no route into the Java input, travel, collision-box or block-contact response terminals. Any claim about a full game trace where altered resource-selection UI changes what a user presses is outside the equal-effective-input premise.

Registration routing was checked separately because `Bootstrap` changed twice. The 1.8.5→1.8.6 registration removal is specifically the command-block dispenser behavior described above. The 1.8.7→1.8.8 `Bootstrap` hunk only rejects an empty skull-owner string. The bridge audit found no 1.8.8→1.8.9 added/removed Java path, and the selected block registry/provider source hashes are unchanged. No changed hunk registers or alters a movement/collision provider. `GameOptions` changes do not alter movement key bindings; therefore its new persisted resource/Realms settings are not a changed input mapping under equal effective input.

## Genuine limits and freeze decision

- The 1.8.5–1.8.8 sweep is a verified adjacent Java hash-pruning pass plus focused changed-hunk inspection, not a separate blind full tick-graph audit. The selected 20-class hash roster is not by itself a complete inventory of every movement dependency.
- The 1.8.8→1.8.9 bridge and ordinary audit are endpoint and bounded-slice audits, not a full pair-wide dependency retrace. No 1.8.8 interior patch claim is inferred.
- The older four release-parent provenance markers are absent, although embedded mapping-root provenance was independently verified. This limits publication-marker evidence, not the verified source hashes.
- Packaged resource bytes were not inventoried; the movement conclusion depends on equal effective movement inputs and vanilla world/block state, not equal presentation assets.
- The command-block dispenser behavior is a concrete changed world-state producer and the sole identified scope-sensitive path. If the requested closure expands to equal initial worlds plus all later non-player world evolution, retain this as a bounded open question about reachable command-block items and resulting collision state. It does not defeat the stated same-effective-state movement conclusion.

**Recommendation:** freeze this reusable source conclusion for ordinary client player movement under equal effective movement inputs and equal movement-relevant vanilla world state. Keep full-pair tick/provider closure, whole-game state evolution and runtime parity explicitly unclaimed.
