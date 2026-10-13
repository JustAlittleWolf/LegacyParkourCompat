# F-010 independent blind review — 2026-10-10

- Reviewer: independent source-only worker `review_honey_contact`; not the discovery author.
- Verdict: **revision-required** for incremental implementation acceptance. The scan delta and conditional Honey response are verified; eligible entry-state producer closure and exact first-change boundary remain open.
- Immutable author snapshot: `74bb73221744d846c27cb03a1261f8f90c4da84d`.
- Finding: `findings/F-010-honey-contact-scan-boundary.md`; Git blob `a11ab0179fe9f0c01f3fb05124111623cc03c63b`; file SHA-256 `9a7c542dab631c09a90040f3c84721916d40045b563e3ff5433aa4089abd177e`.
- Pair: exact 1.19.2 → 1.19.3; partial; pair complete: no; full-pair audit not performed.
- Handoff: blocked. Implementation and runtime validation are separate, unperformed statuses.
- Isolation: only source-safe guidance, the pair run/finding and exact published vanilla sources/provenance were read. No mod implementation, wiki, mixed coordination output, whole-main merge/diff, cache writes, decompilation, build, tests, runtime or push.

## Verified behavior and bounds

Both `Entity.move` bodies at 547–679 call `tryCheckInsideBlocks` at 657 after collision resolution, position assignment, collision/ground updates and fall/step callbacks. The scan is bypassed by `noPhysics`, zero piston displacement, or removal before the callback; the witness must avoid these routes. The exception wrapper at 685–694 is identical and rethrows a reported exception.

A `Entity.checkInsideBlocks` 886–912 computes bounds with `min + 0.001`, `max - 0.001`; B 894–920 uses `1.0E-7` for all six bounds. `BlockPos(double,double,double)` 46–48 delegates to `Vec3i` 39–41, which floors via `Mth.floor(double)` (A 65–68; B 70–73). The inclusive loop order is X, Y, Z. Each cell invokes `BlockState.entityInside`, then `Entity.onInsideBlock`. There is no contact-shape intersection test in this scan. A small positive overlap across a max edge gives a sufficient changed band: more than `1.0E-7` and less than `0.001`. This open band is a sufficient witness, not an exhaustive description of equality cases; minimum-edge equality behaves differently because of flooring. Exact floating-point arithmetic must be retained.

Both scans require `LevelReader.hasChunksAt` (A 185–209; B 190–214): max scan Y at least min build height, min scan Y below max build height, and every X/Z section in the inclusive rectangle passing `hasChunk`. The rectangle itself can expand with the smaller inset. For the local client, `ClientLevel.hasChunk` (A 298–300; B 300–302) returns true. The concrete Honey state still must actually be available in the client world; this method is not proof of chunk publication. A bounded interior-height Honey witness avoids the height rejection.

`Blocks.HONEY_BLOCK` (A 3369–3378; B 3899–3908) registers `HoneyBlock` with matching speed factor `0.4F`, jump factor `0.5F`, no occlusion and Honey sound. Both `HoneyBlock.java` files are byte-identical. Their collision provider returns `Block.box(1,0,1,15,15,15)` (shape at 28, provider 39–41); `Block.box` divides coordinates by `16.0`. Thus solid X/Z occupies `[0.0625,0.9375]` within the cell and top Y is `0.9375`. The collision shape does not by itself forbid the much smaller cell-overlap band. It also does not establish a player trajectory into that band.

`BlockBehaviour.BlockStateBase.entityInside` (A 636–638; B 653–655) delegates to the registered block. Honey callback 56–64 tests `isSlidingDown` 66–83: not grounded, entity Y no greater than block Y + `0.9375 - 1.0E-7`, velocity Y strictly below `-0.08`, and X or Z center separation plus `1.0E-7` strictly greater than `0.4375 + getBbWidth()/2.0F`. The player registration width is `0.6F` (A `EntityType` 527; B 536). The width is a float; do not substitute an exact decimal double when constructing the witness. A horizontal narrow cell overlap can satisfy the side predicate; a vertical overlap alone does not prove it.

`doSlideMovement` 91–101 writes Y `-0.05`. If prior Y is below `-0.13`, X/Z are multiplied by `-0.05 / priorY`; otherwise X/Z are preserved. `resetFallDistance` follows. Advancement dispatch requires `ServerPlayer` and therefore does not run for `LocalPlayer`; slide sound/particles do not replace the velocity write. The inherited base `entityInside` is empty. No resource/tag lookup controls this Honey callback; its dependencies are Java registration/state and external world availability. Original client-jar identities were verified; no additional resource bytes are claimed as evidence for this path.

## Caller and consumer closure checked

Ordinary ground/air `LivingEntity.travel` calls `handleRelativeFrictionAndCalculateMovement` (A 2154; B 2161). That helper (A 2194–2204; B 2201–2211) updates relative/climbable movement, invokes `move(SELF,getDeltaMovement())`, then reads the resulting velocity. `LocalPlayer.move` (A 979–984; B 900–905) delegates to the inherited move and subsequently updates auto-jump. Thus the scan is a reachable local-player operation. Honey `setDeltaMovement` directly writes `Entity.deltaMovement` (A 2924–2926; B 2955–2957), and later travel reads the response before levitation/gravity/friction processing (A 2154–2173; B 2161–2180). The callback's immediate `-0.05` is not a claim that end-of-tick Y remains `-0.05`.

This establishes the operation/callback/writer/consumer route. It does not complete the production of the finding's narrow-overlap state. The snapshot expressly says ordinary collision trajectories and an external producer are unproven. A state stipulated after collision resolution is a conditional witness, not an independently closed ordinary-entry proof. No runtime demonstration is required to repair this source-evidence gap.

## Required revision and next action

1. Add a paired source witness producing the post-move box and velocity, including exact float width/AABB construction, move displacement and collision-shape resolution, ground/removal/noPhysics gates, interior height, available Honey world state, and relevant earlier cell callbacks. Use an isolated Honey/air arrangement if appropriate. Keep movement input or external position/velocity origins explicit. The inset shape makes such a witness plausible; this review does not reject its feasibility.
2. Bind the local caller, flooring, chunk/height guard, registered shape and later velocity consumer to exact paired ranges/hashes in the revised finding or its bounded dependency record. State the immediate callback response separately from later travel writes.
3. Preserve `unknown within (1.19.2, 1.19.3]` until exact release-boundary evidence is independently supplied. Endpoint evidence must not silently become a first-changed-release assertion.
4. Commit a new immutable finding snapshot, preserve this verdict, and request fresh blind review. Do not dispatch the current snapshot as accepted or implementation-ready. Continue other source work with pair status partial.

## Artifact and byte verification

Canonical read-only root: `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/{version}/mojmap`. Readiness version IDs, aligned official Mojmap namespace, preparation commands/options and tool versions were checked. Original publication; no revised-artifact equivalence claim.

Rehashed readiness/provenance/source-manifest/artifact-manifest files match the immutable run/finding. Raw client jars, mapped jars and official mapping files under the preparation artifact cache were also rehashed and match `artifacts.sha256`. Every source listed below was rehashed and matched its source-manifest entry. The finding's excerpt hashes reproduce with UTF-8, LF-joined inclusive lines, without a terminal newline: wrapper `1728dd40b3ab42d89f52536fba63d5337e6b1e6ebd1a89a8d119daf65b4af39f`; A scan `0650c89cc809e7c6eca8289b59a34468991b64df9d2262e43763cdfaa704f86c`; B scan `4a4f1232320785153d960c2badc8e65c753506351b0e04b7e4ba12127811e7d1`; Honey callback `a6ed70220d53b4410eb126287fa4cdbbdcd5bbc52c0b5a645895c0a73a8dd37e`, gate `b84f030732d9e818acf77c15e5c7f65c64b5bc63545146eb7ba699c655a2c94a`, writer `74a48094df8c61cbab8ef97fdb3443d61f20433a0df8535a76310991dccfda4e`.

### 1.19.2 verified SHA-256 identities

- mojmap.ready.json: `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`
- mojmap.provenance.json: `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`
- mojmap.sources.sha256: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- artifacts.sha256: `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`
- mojmap/net/minecraft/world/entity/Entity.java: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`
- mojmap/net/minecraft/world/level/block/HoneyBlock.java: `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5`
- mojmap/net/minecraft/world/level/block/Blocks.java: `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b`
- mojmap/net/minecraft/world/entity/EntityType.java: `4d2fb12f5458e43fcf94ec8f40058f0ef5ba545049fd5a73c0cf755cb4764836`
- mojmap/net/minecraft/client/player/LocalPlayer.java: `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`
- mojmap/net/minecraft/world/entity/LivingEntity.java: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`
- mojmap/net/minecraft/world/level/LevelReader.java: `15bbb266a0a2c7c6b81b6287ea3d672dd3163d1b7e41bd9ffece6cb98cd81e52`
- mojmap/net/minecraft/core/BlockPos.java: `f520f3056b16c033f28db64171471aa165537fc8a1504278a24a4760b3fcf0ec`
- mojmap/net/minecraft/core/Vec3i.java: `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32`
- mojmap/net/minecraft/util/Mth.java: `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f`
- mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java: `c0e62233fa21be352953edda6b411727762165fa2cc8d645c7c08406f92df08b`
- mojmap/net/minecraft/world/level/block/Block.java: `e95d5abe175a3648b697e542981ba01535157093d133b774aadef719393ccb8f`
- mojmap/net/minecraft/client/multiplayer/ClientLevel.java: `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab`
- raw client.jar: `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`
- raw client-mojmap.jar: `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- raw client_mappings.txt: `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`

### 1.19.3 verified SHA-256 identities

- mojmap.ready.json: `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6`
- mojmap.provenance.json: `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`
- mojmap.sources.sha256: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- artifacts.sha256: `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`
- mojmap/net/minecraft/world/entity/Entity.java: `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`
- mojmap/net/minecraft/world/level/block/HoneyBlock.java: `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5`
- mojmap/net/minecraft/world/level/block/Blocks.java: `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`
- mojmap/net/minecraft/world/entity/EntityType.java: `b63be8727a2fa406bef2ae45c1c9ebe06a56440e01b6dc85c443cbb738c32c06`
- mojmap/net/minecraft/client/player/LocalPlayer.java: `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`
- mojmap/net/minecraft/world/entity/LivingEntity.java: `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`
- mojmap/net/minecraft/world/level/LevelReader.java: `e37a0214ad3c92cf48ef39ffa172c9986a3837cf690c5b3e95c60fff258cfe0f`
- mojmap/net/minecraft/core/BlockPos.java: `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a`
- mojmap/net/minecraft/core/Vec3i.java: `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32`
- mojmap/net/minecraft/util/Mth.java: `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f`
- mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java: `183ea122421dc3c0649ff0c64affda3d3cc3090c9fc2e01d96da44410d4b39c1`
- mojmap/net/minecraft/world/level/block/Block.java: `c7cabe648cf2a153f892499560492204a0fb3b6b4cbcd3e209ea4a08a8acca60`
- mojmap/net/minecraft/client/multiplayer/ClientLevel.java: `8c8a6134a4409c899c098012e1a11e7eee736edac3403b89211f8ae8fa06df85`
- raw client.jar: `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`
- raw client-mojmap.jar: `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- raw client_mappings.txt: `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`

## Review checkout checkpoint

Owned checkout: D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-honey-contact-2026-10-10. Branch: fix/review-honey-contact-2026-10-10, based on the immutable author SHA above. Only this review path changed. No owned background processes. Static verification only; build/runtime validation not performed. Report commit is the submitted local HEAD. Next action belongs to the source owner: revise the witness and evidence closure, then obtain fresh review. No whole-main merge is permitted in this blind checkout; report-only integration belongs to the integration owner.
