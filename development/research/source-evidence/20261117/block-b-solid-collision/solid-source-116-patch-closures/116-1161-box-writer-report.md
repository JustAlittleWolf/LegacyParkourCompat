# 1.16 → 1.16.1 source-only box-writer review

## Scope and verdict

Exact canonical Mojmap comparison only: Minecraft `1.16` against `1.16.1`. This report covers the local-player tick route into position/AABB writers and block collision queries; their collision provider inputs; step, edge-support, and post-move support consumers; and only the correction caller gate needed to close that route. **No operation-level difference or material collision-shape projection was found.** Endpoint no-diff does not establish an intermediate cutover. No product files, Git data, wiki material, or backlog were consulted. No build, test, runtime, or decompilation was performed.

## Version-root evidence and exact artifacts

The ready markers identify each exact version and `mojmap`. Each source sidecar has 3,572 entries; its actual SHA-256 matches the value recorded in the ready marker. These SHA-256 values also identify the exact inspected trees. Hashes below are SHA-256.

| Evidence | 1.16 | 1.16.1 |
|---|---|---|
| Raw client `client.jar` | `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` |
| Mojmap client `client-mojmap.jar` | `3213f0827fa9215f41e74ca1dedaf104738e9c70ea1635769fb90f3996e4bf6c` | `f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d` |
| Mojmap artifact `client_mappings.txt` | `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d` | `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b` |
| Version metadata | `32ea7a259475ba6f2dc03f979bb2429115ba4137727f776dfd22cc55b1483297` (`1.16.json`) | `9688ec094a807c45513733388f7c8426d80a58cee96ed86be5380cb6a45f4a73` (`1.16.1/version.json`) |
| Version catalog | `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04` | `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04` |
| `mojmap.provenance.json` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` |
| `mojmap.ready.json` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| `mojmap.sources.sha256` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| `artifacts.sha256` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| `movement-diagnostics.txt` | `950f97d4e7cc067f12f6504d95244c36df991c95aabba6c94b81cae605430e2f` | `3a7c0831f5c6d4b5d3bc8aef2b7ef88f0c1ee7e3981379ea0181d77733a4a827` |

`1.16` provenance records `requestedVersionId=resolvedVersionId=1.16`, Mojmap, raw client SHA-256 and metadata identity. `1.16.1` provenance records version `1.16.1`, Mojmap, and exact batch IDs `1.16.1,1.16.2`; its ready marker records the 1.16.1 client SHA-256. The 1.16.1 movement diagnostics enumerate the relevant player/entity movement methods. The 1.16 diagnostics explicitly say they are an anchor-presence inventory, not a method-body or pair review.

**Archived inputs unavailable:** the exact cache directories named by provenance (`build/movement-campaign-2026-10-07/artifacts/prep-1.16-mojmap-2026-10-08-r1` and `build/movement-campaign-2026-10-07/artifacts`) are absent. Therefore the archived raw/mapped JAR payloads are unavailable for independent re-hashing or decompiler replay. Artifact values above are the exact hashes recorded in the version-root `artifacts.sha256` sidecars and cross-referenced by provenance/ready markers; this review does not claim a fresh JAR verification.

The bounded no-diff claim is accepted from this review of the exact version-root identity sidecars and source bodies. Neither version root contains a separate reviewer-verdict sidecar, so this report records this reviewer's verdict; it is not evidence of an additional independent second reviewer verdict.

## Reachable path

`LocalPlayer.tick` checks chunk availability and calls `super.tick` (`client/player/LocalPlayer.java:183–186`). The inherited `LivingEntity.tick` invokes virtual `aiStep` (`world/entity/LivingEntity.java:2168`). `LocalPlayer.aiStep` delegates to `Player.aiStep` and `LivingEntity.aiStep` (`client/player/LocalPlayer.java:813`; `world/entity/player/Player.java:500`). `LivingEntity.aiStep` calls `travel` (`world/entity/LivingEntity.java:2452–2458`); `Player.travel` delegates to `LivingEntity.travel` (`world/entity/player/Player.java:1380–1407`). Movement reaches `LocalPlayer.move` and then `Entity.move` (`client/player/LocalPlayer.java:888–893`; `world/entity/Entity.java:469–502`). These sources and all cited method bodies are byte-identical across the compared releases.

## Per-operation comparison

All source references below are Mojmap paths and line ranges present in both exact releases. The two source-manifest inventories have identical paths and hashes except for three unrelated files: `com/mojang/realmsclient/gui/screens/RealmsUploadScreen.java`, `com/mojang/realmsclient/RealmsMainScreen.java`, and `net/minecraft/DetectedVersion.java`.

| Operation | Exact anchor | Verdict |
|---|---|---|
| Local-player movement dispatch | `client/player/LocalPlayer.java:183–186, 813, 888–893`; `world/entity/LivingEntity.java:2168, 2452–2458`; `world/entity/player/Player.java:500, 1380–1407` | No diff |
| Collision, accepted displacement and AABB update | `world/entity/Entity.java:469–502, 653–692, 698–779` | No diff |
| Position-to-box write and raw position sync | `world/entity/Entity.java:321–330, 786–789, 2778–2790` | No diff |
| Absolute reposition and dimensions/pose box updates | `world/entity/Entity.java:1104–1115, 2298–2325`; pose test `1620–1622` | No diff |
| Player X/Z world-limit reposition | `world/entity/player/Player.java:257–262` | No diff |
| Step-up/axis collision math | `world/entity/Entity.java:653–779` | No diff |
| Ground edge support/backoff | `world/entity/player/Player.java:1010–1042` | No diff |
| Post-move support and movement-factor block inputs | `world/entity/Entity.java:500–502, 579–614`; `world/entity/LivingEntity.java:2021–2025` | No diff |
| Block collision stream and state-shape dispatch | `world/level/CollisionGetter.java:36–64`; `CollisionSpliterator.java:50–99`; `block/state/BlockBehaviour.java:269–281, 523–529, 739–753` | No diff |
| Block registry and geometry operations | `world/level/block/Blocks.java`; `Block.java`; `block/state/BlockState.java`; `world/phys/shapes/Shapes.java`; `world/phys/AABB.java` | No diff; manifest hashes match |

Directly checked equal file SHA-256 values in both trees: `LocalPlayer.java` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; `CollisionGetter.java` `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98`; `CollisionSpliterator.java` `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; `BlockGetter.java` `0f81fc1750218ddefabb33a03120cf1ac3447640c1c97ff59850fd45fab38f44`; `BlockAndTintGetter.java` `bd6f5ec6e49de9b9a37da192168e905586fb8f9e2032e977bde559a50aae8de8`; `Level.java` `5f6be38c6b3e11e1ca00a9067f676151d1288f19d8cd817a3bbd1cc91b8eaa19`; `Block.java` `ca9a413bf8198402ec9f2febb65c2e89eef4386cb0f1f3d36650f4047d896655`; `Blocks.java` `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`; `BlockBehaviour.java` `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f`; `BlockState.java` `edd2d9dba0c10d035774f8b2dd7dcf00d112d7a2d67734fba1851a2d2f91bd36`; `Shapes.java` `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4`; `AABB.java` `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878`.

`CollisionGetter.noCollision` delegates through combined block/entity collision streams; its block stream uses `CollisionSpliterator`. The spliterator uses the queried AABB, collision chunk/state inputs, context, and block collision shapes, with large-shape and moving-piston gates. Block-state dispatch and the all-source manifest comparison found no provider, registry, or neighbor-dependent shape code delta. No changed provider was found to induce a resource/input projection.

## Correction caller and exclusions

The correction caller is supporting evidence only: `ClientPacketListener.handleMovePlayer` ensures execution on the client thread (`client/multiplayer/ClientPacketListener.java:683–685`), then calls `setPosRaw` and `absMoveTo` (`730–745`). Packet relative-coordinate, rotation, acknowledgement, and correction semantics belong to C and were not evaluated.

Adjacent independent calls noted but not treated as findings for this assignment: local-player auto-jump shape probing (`client/player/LocalPlayer.java:899–993`); fluid escape tests via `isFree` (`world/entity/LivingEntity.java:1954, 1974`; `world/entity/Entity.java:453–458`); suffocation-filtered collision query (`world/entity/Entity.java:1542–1546`); and spawn reposition search (`world/entity/Entity.java:283–287`). Their relevant source files are identical across this pair. Rendering, non-player movement, packet semantics, runtime parity, and intermediate-version boundary discovery were not assessed.

