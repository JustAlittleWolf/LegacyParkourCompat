# Bed landing callback: exact boundary and alias check

Scope: compare `Block.beforeCollision`, any `BedBlock.beforeCollision` override, the `Entity.move` dispatch and the local-player movement path in 1.11.2, 1.12, 1.12.1 and 1.12.2, using the canonical `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather` trees. No wiki, product implementation, or product history was consulted.

## Source and mapping identity

All four source trees use the Ornithe Feather / Feather Gen2 namespace. Endpoint provenance is in the release-parent manifests; 1.12 and 1.12.1 have their actual `.decompile-provenance.json` embedded at the source-tree root.

| Release | Mapping identity and recorded hash | Source identity |
| --- | --- | --- |
| 1.11.2 | `feather-gen2-1.11.2+build.2-mergedv2.jar` SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; `feather-gen2-1.11.2+build.2.tiny` SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`. `ornithe-feather.provenance.json` SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`. | `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, matching `ready.json`; manifest records the files below. Parent `artifacts.sha256` SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. |
| 1.12 | `feather-gen2-1.12+build.2-mergedv2.jar`, input SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; Feather version metadata input SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`. Embedded `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. | Embedded provenance records input client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`, version metadata SHA-256 `33cc40a05466b4e34e04e65def732094388e9149eb6bd37e5daa227c06cbde4b`, and source hashes below. |
| 1.12.1 | `feather-gen2-1.12.1+build.2-mergedv2.jar`, input SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; Feather version metadata input SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`. Embedded `.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. | Embedded provenance records input client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`, version metadata SHA-256 `a83e8ab7e3e78fd8dfa8fdbd0216b090ef7298fb44637b79093fe663bf2d6814`, and source hashes below. |
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar` SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; `feather-gen2-1.12.2+build.2.tiny` SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. `ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`. | `ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, matching `ready.json`; manifest records the files below. Parent `artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. |

The provenance tool identity for the embedded 1.12 and 1.12.1 records is the same pinned pipeline/runtime family as recorded in those files: LPC decompile v1, Vineflower 1.12.0, Mapping IO 0.9.1, TinyRemapper 0.14.1, ASM 9.10.1, Java 25.0.3+9-LTS. The 1.11.2 and 1.12.2 parent provenance records the same tool versions and states the requested exact releases and Feather mapping option.

## Compared source hashes

Hashes below are SHA-256 of the actual canonical source files and match the corresponding source manifest or embedded `sources` entry.

| Release | `Block.java` | `BedBlock.java` | `Entity.java` | `LocalClientPlayerEntity.java` |
| --- | --- | --- | --- | --- |
| 1.11.2 | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.12 | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| 1.12.1 | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| 1.12.2 | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The three callback/caller files (`Block`, `BedBlock`, `Entity`) are byte-identical across 1.12, 1.12.1 and 1.12.2. The local player class changed after 1.12.1, but each release still has `LocalClientPlayerEntity extends ClientPlayerEntity`, `ClientPlayerEntity extends PlayerEntity`, `PlayerEntity extends LivingEntity`, and `LivingEntity extends Entity`. Each local player overrides `move` and delegates to `super.move`; `LivingEntity`'s travel path invokes `this.move(...)`. Local player `tick()` delegates to `super.tick()` in each release. Thus a normal client player reaches `Entity.move` through the inherited player path.

## Exact behavior by concrete release

- **1.11.2:** `Block.beforeCollision(World, Entity)` at `Block.java:624–626` writes `entity.velocityY = 0.0`. `BedBlock.java` contains no `beforeCollision` override. `Entity.move` at `Entity.java:698–725` selects the post-move block state at `(floor(x), floor(y - 0.2F), floor(z))`; when that state is air, it may instead select the block below if it is a fence, wall or fence gate. It calls the selected block's `beforeCollision` only when the original requested vertical delta (`o`, saved from `y`) differs from the collision-resolved `y`. No logical-side check encloses this call.
- **1.12, 1.12.1, 1.12.2:** base `Block.beforeCollision` remains the same zeroing write at `Block.java:643–645`. `BedBlock.beforeCollision` at `BedBlock.java:141–150` first tests `entity.isSneaking()`: sneaking delegates to the base zeroing write. Otherwise, only `entity.velocityY < 0.0` enters the bounce branch and writes `entity.velocityY = -entity.velocityY * 0.66F`; for entities not `instanceof LivingEntity`, it then multiplies that result by `0.8`. The player is a `LivingEntity`, so the extra factor does not apply to a player. A non-sneaking player with negative vertical velocity is therefore assigned an upward velocity. The callback is not gated by logical side.
- **All four releases:** the `Entity.move` call gate and support-block selection are the same in the relevant behavior: resolved vertical delta differs from requested vertical delta; choose the block at `floor(y - 0.2F)` and allow the existing fence/wall/gate fallback. The call occurs after collision resolution and `setPositionFromShape`, but before the remainder of `Entity.move`. The eligible player path reaches this code in the logical client world.

## Boundary conclusion

The behavior is absent at 1.11.2 and present identically in the first concrete later release inspected, 1.12, with byte-identical callback and caller source through 1.12.1 and 1.12.2. The bounded earliest-stable endpoint is therefore **1.12** among the assigned concrete releases. The implementation/change interval is 1.11.2→1.12; this evidence does not justify a cutover between the inspected versions or claim any uninspected source state.

## Artifact-verification limits

The release-parent folders for 1.11.2 and 1.12.2 include provenance, ready, source-manifest, and artifact-manifest records; both ready records bind the source-manifest SHA-256 listed above. Their verification records say all published sources and all raw source inputs matched on a later exact re-decompile. Each later check reports one regenerated derived `client-ornithe-feather.jar` difference, explains it as TinyRemapper intermediate output, and records the original jar hash in the immutable artifact manifest. This was not independently repeated here because the referenced build/cache/staging directory no longer exists.

For 1.12 and 1.12.1, the `.decompile-provenance.json` is present inside each current source tree and includes exact release-specific client, Feather metadata and mapping artifact URLs/hashes, the pinned toolchain hashes, and per-source hashes. I recomputed the embedded source hashes against the current trees: zero mismatches for both releases. No release-parent manifests or raw artifact cache were present for these two releases, so the mapping/input hashes remain provenance claims rather than hashes re-read from raw artifacts in this workspace. This limits independent artifact-byte verification but does not affect direct inspection of the hashed current source files.

## Candidate identity

The scoped candidate supplied for comparison is `collision/bed-bounce-on-landing.md`, SHA-256 `065582cbbf16452d55a350c01f2a9b2ef822e8031266059d6539829ce200f621`. Its caller/callback summary is supported by the four-release source check above.
