# 1.8.5–1.8.9 resource and registration boundary supplement

## Scope and conclusion

This immutable supplement extends, but does not edit, `closure.md` (SHA-256 `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257`). It checks the bounded local cache locations available from the source provenance, then follows changed resource-pack and registration code to the player movement terminals.

**No cached client JAR or version JSON was present in the allowed locations, so no packaged-resource archive entry count or content comparison can be reported.** Consequently the source evidence here cannot prove that every changed packaged asset is presentation-only or assert that asset bytes are equal. It does establish that the reviewed Java resource-pack deltas are selection/download/reload/UI logic, the Java movement/provider registrations reviewed here do not change, and the one removed registration is a server-only command-block state producer. Under the frozen equal-effective-input and equal-vanilla-block-state premise, none changes the movement response to the same input and state. A conclusion about whole-game evolution or an uninspected packaged-resource difference remains open.

## Cache and archive checks

The only launcher cache checked was `%APPDATA%\.minecraft\versions`, and the only build cache checked was the exact `cacheDirectory` named by the 1.8.9 provenance marker. No other paths were searched and no network or download was used.

| Release | Provenance-pinned client SHA-256 | `%APPDATA%\.minecraft\versions\<version>` | `client.jar` | `<version>.json` |
|---|---|---|---|---|
| 1.8.5 | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | Absent | Absent; no hash | Absent; no hash |
| 1.8.6 | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | Absent | Absent; no hash | Absent; no hash |
| 1.8.7 | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | Absent | Absent; no hash | Absent; no hash |
| 1.8.8 | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | Absent | Absent; no hash | Absent; no hash |
| 1.8.9 | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | Absent | Absent; no hash | Absent; no hash |

The 1.8.9 provenance names `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/artifacts` as its artifact cache; that exact directory is absent. The 1.8.5–1.8.8 embedded provenance records pinned client inputs but does not provide a local cache path. The five version directories under the known launcher cache do not exist. There was therefore no candidate archive to hash against any pinned SHA and no `version.json` candidate to compare with the provenance. **Packaged-resource entry totals: unavailable, not zero.** The canonical mapping roots contain Java source and provenance/manifests, not extracted client resources.

## Java resource-pack routes

The source hashes for the inspected changed candidates were checked against each version's provenance/source manifest before reading their contents. In 1.8.5→1.8.6, `ResourcePacks.java` is unchanged. In 1.8.6→1.8.7, its changed hunk changes server-pack download filename selection and retention, and requests a client resource reload when a server pack is removed. In 1.8.7→1.8.8, it filters selected packs by metadata format and the saved `incompatibleResourcePacks` list, and reloads after an applicable server-pack removal. `GameOptions.java` in 1.8.7→1.8.8 only adds persistence for that pack-name list. The 1.8.8→1.8.9 `GameOptions.java` hunk adds the Realms-notifications flag and toggle/serialization; the movement bindings remain unchanged per the accepted ordinary-slice audit.

These Java changes can alter which client resources are selected, loaded, or displayed. They do not register blocks, write `BlockState`, or feed the movement input, travel, box, collision or block-contact methods. `KeyBinding.java` and the ordinary input/travel roots are byte-identical across the audited 1.8.8→1.8.9 boundary; the four-release selected movement/provider hashes are byte-identical across 1.8.5→1.8.8. This is a source-code route conclusion, **not** a resource-archive content inventory. Because no archive was available, no claim is made here about the exact packaged resource paths or per-release resource entry totals.

## Registration and server-side state producer trace

The targeted source files were rehashed against their embedded source manifests before inspection. Relevant full-file SHA-256 values are:

| Source | 1.8.5 | 1.8.6 |
|---|---|---|
| `net/minecraft/Bootstrap.java` | `ca472b6750729c52096750c93bb6449840d7ba3683971fc6834806187856df49` | `fb7cf74b1da59c01cc4c8429e7dc363beac0ca58781a3cc0cca0b40fd6ac3ea7` |
| `net/minecraft/block/DispenserBlock.java` | `794e6780d586a16a31264d953037d53e46cf44e7ef85ab69aae126f6a1444c31` | `794e6780d586a16a31264d953037d53e46cf44e7ef85ab69aae126f6a1444c31` |
| `net/minecraft/world/IBlockSource.java` | `9311a0fa8521aaa260cde5cf1b5a60336848de7f5371435c3b33ae4e32b0d41c` | `2d67bd94abd2062befb25b08dbb529b6b64dca8df4bbe94f767dd47b4ea2a566` |
| `net/minecraft/world/BlockSource.java` | `b859797339c0a76ac2b6f80a85141c2ff0b8715b8ad11a6124648e3fd2cfe46f` | `d6d4293be4b357b5126caa8044ff111d70373294165168f86156f850068ff8b8` |
| `net/minecraft/block/CommandBlock.java` | `205f3ace007f83f6e3f6fff6ec4064111b48ed231fbb33032ec57b446b1b26fa` | `205f3ace007f83f6e3f6fff6ec4064111b48ed231fbb33032ec57b446b1b26fa` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |

The changed 1.8.5 `Bootstrap.registerDispenseBehaviors` registers `Item.byBlock(Blocks.COMMAND_BLOCK)` with a custom behavior. Its `DispenserBlock.tick` caller is guarded by `if (!world.isClient)` before it calls `dispense`, so dispenser behavior runs on the logical server. Inside the custom behavior, the command-block placement, block-entity NBT copy and neighbor update are additionally inside `if (!world.isClient)`. The 1.8.6 `Bootstrap` removes that registration; its diff also removes the only `source.getBlock()` call found in the 1.8.5 Java tree. `IBlockSource.getBlock()` and `BlockSource.getBlock()` disappear in the same pair. The remaining `item.size--` is inside the server-only scheduled dispenser tick; it is inventory mutation, not a client movement write.

`DispenserBlock.java`, `CommandBlock.java` and `Block.java` are byte-identical across this pair. `Block.getCollisionShape` builds the box from the block's unchanged min/max extents, and `CommandBlock` does not replace that collision method. Therefore the removed registration cannot alter client movement by a client-side physics path. If a command-block item is present and the old handler places one while the newer server does not, a later player collision could differ only after the clients receive differing world/block states. With the same incoming vanilla state, the same player movement code responds identically. This is why the state producer remains a whole-game-evolution boundary but does not defeat the group’s equal-state movement conclusion.

The other registration-looking `Bootstrap` delta, 1.8.7→1.8.8, changes skull-owner parsing only: it declines to create a profile from an empty string. It does not add/remove a block, item, dispense behavior or movement provider. Across 1.8.8→1.8.9 the independent bridge audit found zero added/removed Java paths and no changed `block/`, `entity/`, `world/` or movement-provider path. The 1.8.5→1.8.8 sweep independently found all listed Java changes and no changed block collision/movement provider.

## Remaining evidence gate

Packaged assets remain uninspected because the allowed local client archives and version JSON files are absent. Thus this supplement cannot certify that all packaged-resource changes are presentation-only, cannot report archive entry totals, and cannot compare resource-entry paths/content across the five releases. It does establish the Java resource loader and registration behavior and the logical-side boundary of the known command-block producer. The narrow same-input/equal-block-state freeze recommendation stands; a packaged-resource inventory or a whole-game equal-initial-world claim requires the exact cached artifacts to be made available and hash-verified first.
