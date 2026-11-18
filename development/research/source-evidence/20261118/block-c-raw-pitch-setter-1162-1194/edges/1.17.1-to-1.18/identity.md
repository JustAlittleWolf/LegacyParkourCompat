# Source identity correction: `Entity.turn` comparison

## Bound report and scope

This record supplements the blind comparison report at the same lab path, `report.md`. Its original, unchanged raw-byte SHA-256 is **`1ad1213578fd086fa7b199e11596ce1773667242714f383e8005efd44066764b`**. It preserves that literal identity and corrects the report's earlier provenance limitation using the version-root ready/provenance sidecars.

The compared trees are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.18\mojmap`. The root sidecars identify Mojmap versions `1.17.1` and `1.18` respectively; the canonical `1.18` directory and marker use `1.18` as the release ID.

## Root marker and manifest checks

All hashes below are SHA-256 over exact raw file bytes. For each version, the ready marker reports `status: ready`, the expected Mojmap namespace/version, and source/artifact counts matching the line counts in the corresponding manifests. The SHA-256 values of both manifests match the hashes declared in the ready marker. Each cited Java source hash also matches its entry in `mojmap.sources.sha256` and a fresh hash of the file under the canonical `mojmap` tree.

| Version root | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 (entries) | Artifact-manifest SHA-256 (entries) |
|---|---|---|---|---|
| `1.17.1` | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` (4,142) | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` (41) |
| `1.18` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` (4,203) | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` (39) |

The sidecar presence, ready states, declared source/artifact manifest digests, manifest entry counts, and cited-source byte hashes were verified. The root ready/provenance sidecars are separate from `.decompile-provenance.json`; absence of the nested manifest does not mean these ready markers are absent.

## Rehashed cited source bytes

Each SHA-256 below was recomputed from the canonical Java file and matched its relative-path entry in that version's source manifest.

| Version | Relative source path | SHA-256 |
|---|---|---|
| `1.17.1` | `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `1.18` | `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `1.17.1` | `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| `1.18` | `net/minecraft/util/Mth.java` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| `1.17.1` | `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `1.18` | `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `1.17.1` | `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| `1.18` | `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `1.17.1` | `net/minecraft/client/player/AbstractClientPlayer.java` | `d0508c94f43e7e90683b8f46586665bd1e1f0dca2fe25274b852f5e6c0ce05e8` |
| `1.18` | `net/minecraft/client/player/AbstractClientPlayer.java` | `5b68729fb699b5c5c6a8b1b3107b25800f513614b88a42bf1d29cc5684c8e7c0` |
| `1.17.1` | `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `1.18` | `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |

## Raw-cache limitation

`mojmap.provenance.json` records the generation run and cache root `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`. The root `artifacts.sha256` files are present, their raw bytes match the ready-marker hashes, and their expected entries/counts are internally consistent. The referenced raw cache artifacts (including client jars and mapping inputs) are not present at that recorded cache path now, so their bytes could not be independently rehashed in this pass. This limitation is separate from the successful rehashes of the canonical cited Java sources and source-manifest files; artifact-manifest entries remain recorded expected hashes, not fresh raw-cache verification.

## Corrected conclusion

The earlier report's source comparison remains: no difference was found in `Entity.turn` pitch response, the finite-value `setXRot` setter, or final pitch clamp between these two ready Mojmap source trees. Its statement about absent nested provenance should be read with this correction: both roots have manifest-backed ready markers and provenance sidecars, whose hashes and source links were verified here. Raw input artifacts were not available for a fresh hash pass.