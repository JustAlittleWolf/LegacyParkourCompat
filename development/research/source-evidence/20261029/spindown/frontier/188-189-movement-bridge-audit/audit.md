# Independent source-only audit: 1.8.8 → 1.8.9 movement bridge

## Verdict

The bounded endpoint comparison is corroborated: the exact 1.8.8 and 1.8.9 mapped Java trees each contain 1,612 files with identical path sets; every source hash matches its version's source manifest; and a fresh pairwise hash comparison yields exactly the 26 paths and endpoint hashes in the supplied CSV. All 9 listed movement roots match byte-for-byte. Inspection of every changed hunk supports the report's classifications; none introduces or changes an ordinary client movement root, collision/shape provider, or movement dependency. The `EffectCommand` variable changes preserve their roles, values, guards, duration/amplifier construction, and argument ordering. The `GameOptions` additions concern Realms notifications; movement key declarations and input conversion are unchanged.

This is acceptance of the bounded 1.8.8→1.8.9 bridge claim only. It is not an independent full-pair tick-graph/dependency-closure audit and does not freeze 1.8.8→1.8.9. The report correctly leaves full-pair coverage/audit as a master-level gate and makes no intermediate patch cutover or runtime parity claim.

## Inputs and immutable identity

- Audited report: `.native-source/20261028/frontier-c/188-189-movement-bridge/boundary-audit-1.8.8--1.8.9.md`, SHA-256 `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a`.
- Changed-path CSV: `.native-source/20261028/frontier-c/188-189-movement-bridge/changed-source-hashes.csv`, SHA-256 `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2`.
- Canonical read-only sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.8/ornithe-feather/` and `.../1.8.9/ornithe-feather/`.
- 1.8.8 embedded `.decompile-provenance.json` hashes to `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`. Its inputs bind the client SHA-256 `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8`, 1.8.8 Mojang metadata payload, Feather metadata payload, and `feather-gen2-1.8.8+build.2-mergedv2.jar` mapping SHA-256 `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f`. No parent/inherited release field is present; the exact version is instead bound through the input URLs and hashes.
- 1.8.9's marker files are adjacent to the source directory under `decompiled_minecraft/1.8.9/` (not inside `ornithe-feather/`): provenance, ready, verification, `ornithe-feather.sources.sha256`, and `artifacts.sha256`. The provenance and ready markers both identify version `1.8.9` and namespace/mapping `ornithe-feather`; ready records 1,612 source files and client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de591205d298`. The source manifest hash is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, matching the ready marker. The artifact manifest hashes to `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`, matching ready; it includes `feather-gen2-1.8.9+build.2-mergedv2.jar` at mapping SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da`. Verification marker reports 1,612 source files verified, zero source-file differences, and zero missing published source files. It separately records a derived remapped-archive difference; this does not contradict the source-tree verification.

The report's wording about the 1.8.9 source marker is understandable but should be read as version-parent-relative. The canonical source paths and actual marker placement were checked directly.

## Independent inventory and changed paths

I enumerated and SHA-256 hashed all `*.java` files under each `net/` tree, parsed the 1.8.8 embedded provenance source map and 1.8.9 source manifest, and compared endpoint path sets and hashes. Results:

| Check | Result |
|---|---:|
| 1.8.8 Java files / source-manifest entries | 1,612 / 1,612; 0 mismatches |
| 1.8.9 Java files / source-manifest entries | 1,612 / 1,612; 0 mismatches |
| Added or removed Java paths | 0 |
| Identical endpoint source hashes | 1,586 |
| Different endpoint source hashes | 26 |
| CSV rows / endpoint hash mismatches | 26 / 0 |
| Actual hash deltas omitted from CSV / CSV rows not actually changed | 0 / 0 |

Changed-path classification checked against the source hunks:

| Classification | Paths | Audit |
|---|---|---|
| Release strings/metadata | `DebugOverlay`, `Minecraft`, `ServerListEntry`, `RealmsSharedConstants`, `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `Snooper`, `CrashReport` | Version labels, HTTP header, handshake/version text, server status/version reporting, logging, or crash/snooper metadata only. |
| Realms notifications UI/option | `OptionsScreen`, `TitleScreen`, `Screen`, `GameOptions`, `Realms`, `RealmsBridge`, `RealmsScreen`, `RealmsScreenProxy` | Adds/toggles/serializes the Realms notification option and creates/draws/resizes/routes a screen. No player movement calculation or state writer is changed. |
| Rendering/UI | `ChestRenderer`, `WrittenBookItem` | Chest renderer changes indentation/formatting; book changes are local variable renames with the same text and NBT operations. |
| Source-name/generic-only | `SoundManager`, `BanEntry`, `EnchantCommand`, `CommandResults` | Generic type/local/loop variable naming changes; inspected uses retain the same values and operations. |
| Command-side local renames | `EffectCommand`, `SummonCommand` | Role-preserving locals. In `EffectCommand`, default duration/amplifier, positive-duration gate, seconds-to-ticks multiplication, instant-effect duration, status-effect constructor arguments, and success-message arguments correspond. In `SummonCommand`, the riding entity and subsequent position/add/start-riding operations correspond. |
| HSV local renames | `MathHelper` | HSV-to-RGB local names change; float multiplications, casts, clamps, shifts, and result expression remain the same. |

## Movement roots, providers, and dependency boundary

I independently hashed the report's nine selected roots at both endpoints; all matched the report's shared hashes:

- `Entity`, `LivingEntity`, `PlayerEntity`, `ClientPlayerEntity`, `LocalClientPlayerEntity`
- `Input`, `KeyboardInput`
- `World`, `ClientWorld`

The complete 26-path delta has no `net/minecraft/block/`, `entity/`, `world/`, or `network/` changed path, and no path for a movement provider. With unchanged full Java path sets, there are no added collision/shape or movement-provider sources. The only movement-adjacent changed root in the CSV is `GameOptions`; its hunks are the Realms-notifications option, and key-binding declarations/input conversion remain unchanged. `EffectCommand` was inspected as a potential upstream effect producer; its movement-relevant status-effect arguments remain equivalent. These checks support the bounded report claim that no ordinary movement dependency changed; they do not substitute for tracing every reachable call/dependency in a full-pair inventory.

## Coverage boundary

No product implementation, Git history, wiki/backlog, tests, builds, launches, or source generation was used. This audit independently verifies endpoint identities, manifests, file inventories, the 26-path delta and listed hunk dispositions. It does not retrace the full tick graph, all dependency paths, or interior 1.8.8 patch releases. Pair freeze and runtime parity remain unestablished.
