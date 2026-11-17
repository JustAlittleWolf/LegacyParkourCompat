# Independent audit: End Portal collision provider

## Verdict

**ACCEPT** for the bounded `minecraft:end_portal` ordinary block-collision provider comparison in `end_portal_collision_provider/report.md` (SHA-256 `8bc581617c3f3327d42dd2d7a3ce22429f26e2608f41c2f41f03557b2530dba3`). The report's endpoint identities, cited file hashes, exact registration join, one-state domain, and old/new collision-route conclusion are supported. No corrections are required within this slice.

## Identity and source checks

The 1.12.2 and 1.13.2 ready markers identify the expected releases and Ornithe Feather Gen2 build 2 inputs. Their `sourceManifestSha256` values match the corresponding root `ornithe-feather.sources.sha256` sidecars: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Every one of the report's eight cited Java source hashes was recomputed and matches both local source bytes and its sidecar entry:

- 1.12.2: `EndPortalBlock.java` `b091d0fbf32b3bf18090fac68c876657e518d986eac7236ef0eb19cdacebf8e6`; `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `StateDefinition.java` `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37`; `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- 1.13.2: `EndPortalBlock.java` `9541e703d04bf9148cb324741de73c4f3ce76021bc358f22308c946bccb72ad3`; `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `BlockState.java` `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`; `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.

## Claim review

Both registration bodies bind exact key `end_portal` to `EndPortalBlock`: 1.12.2's numeric registration 119 and 1.13.2's named registration. The class and inherited base state definitions add no block-state properties, so the provider has one state in each release.

The old `EndPortalBlock.addCollisions` override is empty. `World.getCollisions` forwards candidate block states through `StateDefinition.State.addCollisions` to that registered block instance. Thus the old outline returned by `getShape` does not contribute a collision entry for this key.

In 1.13.2, the exact registration sets `.noCollision()`, which sets `hasCollision=false`; base `Block.getCollisionShape` returns `VoxelShapes.empty()` in that case. `BlockState.getCollisionShape` forwards to the block provider, and `WorldView.getBlockCollisions` rejects empty shapes before including them. The report correctly distinguishes collision from outline: both outline providers encode a 0.75-block-high footprint, but neither old nor target movement collision route consumes that outline for this key.

The bounded result is therefore no ordinary block-collision provider difference for the single registered `end_portal` state. The report appropriately excludes End Portal entry/transfer, other interactions, and pair completion.

## Scope limit

Acceptance applies only to this exact key's ordinary collision-provider route. It does not close other block keys, EndPortalFrame, teleport or dimension-transfer behavior, all source dependencies, or pair-wide coverage.
