# 1.12.2 → 1.13.2 base-collision `hasCollision` key join

## Bound result

The exact-key join contains **115** legacy registered keys for which (a) the 1.12.2 effective `addCollisions` owner and effective `getCollisionShape` owner are both base `Block`, and (b) the 1.13.2 route starts at base `Block.getCollisionShape`. Every one of those 115 1.13.2 registered instances has `hasCollision=true`; none reaches the new false gate. The complete key/class/effective-dispatch/property join, including old numeric IDs and 1.13.2 registration source lines, is `key-join.csv`.

The join is exact key equality only. It does not infer aliases for old-only/new-only keys, compare shape values for specialized `getShape` methods, or close unrelated state/context dependencies.

## False-gate common-key exception

`minecraft:end_portal` is an exact common key whose new registered instance has `hasCollision=false` and uses base `Block.getCollisionShape` before `EndPortalBlock.getShape`. Its old effective dispatch is `EndPortalBlock.addCollisions` (not base `Block.addCollisions`); that override is empty. Although old `EndPortalBlock.getShape` supplies a 1×0.75×1 box for other shape consumers, the old movement collision callback appends no obstacle. Therefore this key does **not** establish an old nonempty default collision shape newly suppressed by `hasCollision`; the registered old player-collision route already emits none. No common key in the bounded base-dispatch join has a new false gate.

## Dispatch and property evidence

- The accepted registry crosswalk supplies 1.12.2 key, registered class, effective `addCollisions`/`getCollisionShape` owners, and 1.13.2 registered class/effective collision-to-shape route. Filtering its exact-key rows on both old owners being `Block` and the new route beginning `Block.getCollisionShape` yields the 115 rows in the CSV.
- In 1.13.2 `Block.java`, `Properties.hasCollision` defaults to `true` (line 1901); `noCollision()` sets it to `false` (1943–1945); the `Block(Properties)` constructor copies that value into the registered block instance (224–231). Each matched literal registration expression was inspected as a balanced Java call; all 115 lack `.noCollision()` and thus retain `true`.
- `BlockState.getCollisionShape` delegates to the block instance (`BlockState.java:209–210`). This flag is on the block instance, not a state-specific property. Base `Block.getCollisionShape` returns the shape only when that flag is true (`Block.java:393–394`).
- For the exception, 1.12.2 `EndPortalBlock.addCollisions` has an empty body (`EndPortalBlock.java:44`), while its `getShape` returns the static 1×0.75×1 box (`EndPortalBlock.java:21,35`). In 1.13.2 the key registration applies `.noCollision()` (`Block.java:1217–1219`); `EndPortalBlock.getShape` returns its 16×12×16 voxel shape (`EndPortalBlock.java:20,33`), but the inherited base collision method gates it to empty.

## Evidence identity

Both source manifests were verified against their ready markers and local manifest files: 1.12.2 Feather Gen2 build.2 SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 Feather Gen2 build.2 SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Accepted reusable registry inputs and hashes: `registry-collision-1122.md` `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`; `registry-collision-1132.md` `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`; exact-key crosswalk `registry-collision-crosswalk.md` `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`. The crosswalk hashes were checked locally before reuse.

Specific consulted Java source hashes, all matching the cited manifests:

| Release | File | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/EndPortalBlock.java` | `b091d0fbf32b3bf18090fac68c876657e518d986eac7236ef0eb19cdacebf8e6` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/EndPortalBlock.java` | `9541e703d04bf9148cb324741de73c4f3ce76021bc358f22308c946bccb72ad3` |

The companion `provider_override_roster/provider-override-roster.md` was reused for effective owner context; its owner census was not repeated.
