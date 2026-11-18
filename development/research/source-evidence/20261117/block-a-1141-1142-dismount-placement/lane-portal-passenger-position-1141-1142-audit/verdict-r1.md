# Rebound audit verdict — corrected comparison r1

## Exact binding

The corrected input is `../lane-portal-passenger-position-1141-1142/comparison.corrected-r1.md`, SHA-256 `2debae772630d0a02ffa78d69fefc5743bfe1b081bcbd3c6587e5ab2e84a9805` (programmatically verified). Its cited original source roster SHA-256 `ac604983703e5ed08ca6b1c0777e780084e2e36c2e8de766cf2dacc89737ada7` matches. My original frozen roster remains unchanged at SHA-256 `3e9f86ba354b0ce37e8e7b3cc900a7253ab76f3636341c1471956fe4ca1d99bd`; its pre-verdict supplement remains unchanged at `d334db92700753fddd1f47d704f6a817c54bb306f4d8f3b57454a09d94e73674`; original verdict remains unchanged at `b251c099b69e0f38ce0a9741126ccb476c89352e0cf16af84d42175335017e87`.

Exact source roots are local Yarn 1.14.1+build.10 and 1.14.2+build.7. Provenance manifest SHA-256 values, verified from their exact local bytes: 1.14.1 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`; 1.14.2 `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`. Every source-file hash listed below was programmatically checked against the matching manifest `sources` entry.

## Scoped verdict

**The corrected comparison accurately resolves the prior omission. Accept the bounded no-difference / no-finding disposition for these operations in 1.14.1 → 1.14.2.** This rebind checks the corrected claims against the existing trace; it is not a broader pair audit.

`PlayerEntity.tickRiding()` has the server-side sneaking dismount gate. `LivingEntity.stopRiding()` calls `method_6038(oldVehicle)` after an actual detach only when `!world.isClient`. The full `method_6038(Entity)` body is byte-for-byte identical: extracted source-member SHA-256 `d24894e6a7edfee2a8333b036828107fc2acaaab4a8f72e4ed6242e144948332` on both releases. It chooses placement XYZ. In the ordinary non-boat/non-horse branch it checks direction-ordered candidate boxes and uses collision, solid-top and fluid predicates, then calls `requestTeleport` for the selected/fallback coordinates. In its boat/horse branch it computes a side position from yaw/width/hand, calls `setPosition`, and tries the raised and vehicle-top fallbacks. The method gate and operation order are unchanged.

For a server player, the ordinary branch's virtual `this.requestTeleport(x,y,z)` dispatches to `ServerPlayerEntity.requestTeleport`, which reaches the network handler. Once `super.stopRiding()` returns, the enclosing `ServerPlayerEntity.stopRiding()` sends another correction with current authoritative XYZ when the vehicle relation changed and a handler exists. Thus ordinary placement may already have selected a new XYZ before the outer correction. The boat/horse branch uses `setPosition`; the outer correction carries that resulting XYZ. Both dispatch and outer-correction bodies are identical in the pair.

The relevant XYZ prefix of client `onPlayerPositionLook()`—from packet X extraction through the applied-XYZ movement packet—has the same programmatic SHA-256 `5191c2669cabfc77ca21e444dcd5601035ec70558d909dfe08e31486c7025f4a` in each release. It applies relative offsets or absolute XYZ, zeros velocity on absolute axes, updates position/rotation, confirms the teleport, and sends applied XYZ. The complete enclosing method has a separate trailing screen-close call with different Yarn spellings (`method_1507(null)` / `openScreen(null)`); the XYZ slice itself is identical. Passenger-set reconciliation's complete method body is identical (SHA-256 `a2d6acb8571d122874a48a3aafee1788add09c764a1fb88578abb98e487067aa`).

`PortalBlock.onEntityCollision()` is identical (member SHA-256 `258fdfa04bcdc463b946dfd446c07e45083d8e1bd0d8283154191b08fd1f1397` both releases): it gates on no vehicle, no passengers and `canUsePortals()`. `Entity.setInPortal()` and `tickPortal()` also have equal member hashes listed below. A literal `PORTALS` search in the scoped `PortalBlock.java` and `Entity.java` files returned no matches; the collision path has no portal-tag special branch. Portal-position work remains server-gated, and transfer remains server-world/no-vehicle/time gated.

## Programmatic evidence hashes

Full-file SHA-256 values below were computed from the exact local source bytes. These are the source files in the frozen roster and its supplement that support this bounded conclusion.

| Source path | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| `net/minecraft/block/PortalBlock.java` | `4d7ea7eac1026673792084935dc2f2c12bb4e9c395aca57fa7a0a3d6ff6201c3` | `97ee27b8065a79e40eaaa25cd275746b87bcc4d975c02a458237cc0560f6b9a6` |
| `net/minecraft/server/network/ServerPlayerEntity.java` | `6e7b36cc3ef188fe1d144685bcd6620c363f2b95ac9e52b41df310babb325bae` | `5dd585383d9cb52bf1db7304fc755ae392d48a6d1513ff9143e3b49934340381` |
| `net/minecraft/server/network/ServerPlayNetworkHandler.java` | `916e7131a20a693fc06f74a2231801f309c5f67c89e61610388bc71eba0db071` | `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a` |
| `net/minecraft/client/network/ClientPlayNetworkHandler.java` | `13db0a2baa0a2c986c795a2990eab64f3d9a4dc121e360dbd04c2ea84310ddd7` | `5055088f2e944c50be032cce0a921054eba2c13a022f60306e5e9411c400697b` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| `net/minecraft/entity/vehicle/BoatEntity.java` | `9b639ab039d20b4f224be4f7b15dbee77e4e7bfba11c64e92060fe97c06e7725` | `9b639ab039d20b4f224be4f7b15dbee77e4e7bfba11c64e92060fe97c06e7725` |
| `net/minecraft/world/ViewableWorld.java` | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` |
| `net/minecraft/block/BlockState.java` | `9106f1649a2ac0965f6b3b13f2ec8437d7b16d2ce72e217c4f105b4524732238` | `c35e64104be023c3f7940dec7b459fb4252d552b6770baa45f4fb4c345dd7035` |
| `net/minecraft/block/Block.java` | `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` |

Equal extracted member hashes: `LivingEntity.method_6038` `d24894e6…4948332`; `LivingEntity.stopRiding` `ea91a9e2…8005ea6`; `ServerPlayerEntity.stopRiding` `8b42016c…37381d6`; `ServerPlayerEntity.requestTeleport` `30903f17…f289a2`; server handler `requestTeleport` `3579abb0…e8095e` and `teleportRequest` `82a86d56…bb9600`; portal `onEntityCollision` `258fdfa0…f1397`; entity `setInPortal` `764431d3…57ca274` and `tickPortal` `ffec223a…cb7890`; `PlayerEntity.tickRiding` `d7701b47…20381b`; `ViewableWorld.doesNotCollide` `55f99f9f…827c9d`; `BlockState.hasSolidTopSurface` `48de1e54…fac45c4`; `Block.isFaceFullSquare` `4d783daf…0e5b7d`; client XYZ prefix and passenger-set hashes are given above. Hashes were calculated over UTF-8 extracted member text; source full-file hashes are the provenance-bound evidence identities.

Bounded conclusion only: this validates the corrected claim for 1.14.1 → 1.14.2; it does not establish adjacent patch boundaries or runtime parity. Generic `ServerPlayNetworkHandler.onPlayerMove` collision recheck remains excluded.