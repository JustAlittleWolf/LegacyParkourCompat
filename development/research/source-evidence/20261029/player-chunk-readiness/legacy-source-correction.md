# Supplement 02: legacy-manifest source closure for 1.21.6 and 1.21.9

This correction adds bounded source evidence for 1.21.6 and 1.21.9. It preserves the bytes of the original report and Supplement 01.

**Bound source records:** original `report.md` SHA-256 `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1`; Supplement 01 SHA-256 `283a413da6b69fa6b812d7ff2ed8b6711c8c9a5acc10d339cee8d3f2a5e21cd4`. Both were recomputed before this file was written and match their sidecars.

## Result

The absence of modern `mojmap.ready.json` and `mojmap.sources.sha256` files does **not** make these two mapping roots unusable for the bounded readiness question. Each mapping root has a legacy `.decompile-provenance.json` whose embedded `sources` map covers the complete source tree. I rehashed every listed source file: 1.21.6 has 6,103 entries with no missing or mismatched files; 1.21.9 has 6,386 entries with no missing or mismatched files. The sidecars also bind the exact-version metadata URL, client JAR URL, and client mapping-file URL to SHA-256 values.

**1.21.6 has the direct compiled-only player-position predicate.** Its `LevelLoadStatusManager` is byte-identical to the already verified 1.21.3, 1.21.4, 1.21.5, and 1.21.8 manager (`LevelLoadStatusManager.java` SHA-256 `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec`). In `WAITING_FOR_PLAYER_CHUNK`, it reads `player.blockPosition()` and asks `LevelRenderer.isSectionCompiled(pos)` (1.21.6 `LevelLoadStatusManager.java:19–29`). The exact renderer method looks up that block position's section and returns true when it exists and its mesh is not `UNCOMPILED` (`LevelRenderer.java:1245–1248`). The direct section predicate is unchanged from .3–.8; it is separate from the `clientLoaded` flag/local tick gate.

**1.21.9 also has the direct compiled-only player-position predicate.** Its `LevelLoadTracker.java` is byte-identical to 1.21.10's exact file (SHA-256 `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13`). `WaitingForPlayerChunk.isReady()` accepts after the tracker timeout; otherwise it reads `player.blockPosition()` and, for a player within build height who is alive and not a spectator, calls `isSectionCompiled(pos)` (`LevelLoadTracker.java:112–136`). The renderer query accepts a present section with a compiled mesh and has no visibility-threshold gate (`LevelRenderer.java:1283–1286`). `ClientPacketListener.tick()` advances this tracker and, once ready, notifies the local player then clears the tracker (`ClientPacketListener.java:2637–2643`); the notification sends `ServerboundPlayerLoadedPacket` before setting the player loaded (`:2646–2650`). The local simulation consumer remains `LocalPlayer.tick()`'s `hasClientLoaded()` guard (`LocalPlayer.java:195–219`).

These two verified endpoints extend the direct compiled-only/player-position result across the previously unverified gaps. They do not establish behavior for any other unavailable release, rendered output, runtime callback timing, or movement trajectory. No source-generation run or product/history/wiki operation was performed.

## Exact legacy manifest and input identities

Canonical roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.6\mojmap` and `...\1.21.9\mojmap`. The following digests are the recomputed SHA-256 values of each root's `.decompile-provenance.json`. The `sources` property in that exact sidecar is the per-file hash manifest; all entries were checked against their named files under that same root.

| Release | Mapping-root provenance SHA-256 | Embedded source entries checked | Missing / mismatched | Provenance pipeline / runtime |
|---|---|---:|---:|---|
| 1.21.6 | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` | 6,103 | 0 / 0 | `lpc-decompile-v1`; Eclipse Adoptium Java 25.0.3+9-LTS, Windows 11 x64 |
| 1.21.9 | `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` | 6,386 | 0 / 0 | `lpc-decompile-v1`; Eclipse Adoptium Java 25.0.3+9-LTS, Windows 11 x64 |

The sidecars bind the exact release metadata and client/mapping inputs as follows. Artifact URLs, object IDs, and SHA-256 values below are transcribed from those hashed provenance sidecars; the original downloaded payloads were not separately rehashed in this closure.

| Release | Version metadata URL / SHA-256 | Client JAR URL / SHA-256 | Client mapping input URL / SHA-256 |
|---|---|---|---|
| 1.21.6 | `https://piston-meta.mojang.com/v1/packages/8dd125560431a618094435343acccd45191bb006/1.21.6.json` / `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd` | `https://piston-data.mojang.com/v1/objects/740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar` / `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | `https://piston-data.mojang.com/v1/objects/848855615bc81e3db1c85e69b6afb150807a1261/client.txt` / `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` |
| 1.21.9 | `https://piston-meta.mojang.com/v1/packages/91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json` / `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5` | `https://piston-data.mojang.com/v1/objects/ce92fd8d1b2460c41ceda07ae7b3fe863a80d045/client.jar` / `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b` | `https://piston-data.mojang.com/v1/objects/3641ccb54eac2153c7e8274823c5a8e046beaba0/client.txt` / `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3` |

## Cited source hashes

Each hash below was both read from that release's embedded `sources` manifest and recomputed from the named file. This is the direct source-level binding for the methods cited above.

| Release | Source file | SHA-256 |
|---|---|---|
| 1.21.6 | `LevelLoadStatusManager.java` | `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec` |
| 1.21.6 | `ClientPacketListener.java` | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` |
| 1.21.6 | `LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| 1.21.6 | `LevelRenderer.java` | `581979a2ef91d93bd5d8cc28079ddeeeb76c76a419c3d5424eb94dbca00a5e63` |
| 1.21.6 | `Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| 1.21.9 | `LevelLoadTracker.java` | `876841eef72f74decfbb52ca7de27d95604d9474ac2e65ef07a0bcda0e4fec13` |
| 1.21.9 | `ClientPacketListener.java` | `1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348` |
| 1.21.9 | `LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| 1.21.9 | `LevelRenderer.java` | `15588458bdffd99d8c83d6eb6c066cf3f75a5f8cef73cf41bcfa23a63b13d786` |
| 1.21.9 | `Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |

The .6 readiness manager and its listener/player/render support members are exact hash-identical to the corresponding 1.21.8 sources already covered in Supplement 01; the .9 tracker, listener, player, and renderer members are exact hash-identical to the corresponding 1.21.10 sources covered by the bound original report. This hash-first pruning is why their previously read call-chain details apply to these now manifest-verified endpoints.

## Bounded status correction

Supplement 01 recorded 1.21.6 and 1.21.9 as unavailable because modern ready markers and separate `*.sources.sha256` files were absent. This correction supersedes that availability disposition for the bounded player-readiness source claim: both legacy embedded manifests are complete and all entries verify, and their relevant input identities are explicitly bound above. Exact predicate result is positive for both endpoints, with no evidence of the later visibility callback at either queried readiness operation.

This correction does not declare a modern build-preparation `ready` marker for either root, does not rehash original input JAR or `client.txt` payloads, and does not claim a first-ever introduction earlier than the evidence recorded in Supplement 01.
