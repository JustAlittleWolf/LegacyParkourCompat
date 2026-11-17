# Independent audit: workbench/music wall-face projection

Verdict: **ACCEPT**, bounded to the wall-neighbor predicate for `minecraft:bookshelf`, `minecraft:crafting_table`, `minecraft:jukebox`, and `minecraft:note_block`.

Audited author packet SHA-256: `1f9d0b341e232eba8c6cdc87fa84d8b18403f55523a3acdd3bab812297065df6`.

## Source identity and evidence

The 1.13.2 and 1.14.4 Ornithe Feather Gen2 build 2 source-manifest files hash to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, respectively, matching both ready markers. I independently recomputed all Java hashes listed in the author packet against the canonical source bytes and each `.sources.sha256` sidecar; all matched. The added `1.14.4 net/minecraft/tag/BlockTags.java` hash is `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a`; it defines `WALLS` and `LEAVES` tags. The exact `data/minecraft/tags/blocks/walls.json` payload used by the accepted resource extraction is SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its 14 values do not include these four keys. The resource extraction record SHA is `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`, and the independent raw-resource verdict-r2 SHA is `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

The source-only crosswalk input contains the four claimed keys; its `unexamined-wall-projection-keys.tsv` SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`.

## Predicate and state-domain checks

At 1.13.2, `BlockState.getFaceShape` delegates to the block provider, and `WallBlock.shouldConnectTo` accepts `FaceShape.SOLID` unless the block is an exception. The base `Block.getFaceShape` returns SOLID. No checked specialized class overrides the face-shape, shape, or collision-shape methods. The old exception branches do not include these keys.

At 1.14.4, `WallBlock` passes `BlockState.m_87223014` to its predicate; the state method calls `Block.isFaceSolid`, which tests the collision shape's full face. Base `Block.getShape` returns the full cube and `getCollisionShape` returns it when `hasCollision` is true. The target constructors do not call `noCollision`, and `hasCollision` defaults to true. The attachment-exception list and the exact WALLS resource exclude these keys; none is a fence gate. Therefore the generic wall predicate is true for each state and horizontal direction at both endpoints.

The registered domains were checked against the block definitions, state builders, properties, and exact `Instrument` enums:

- Bookshelf and crafting table: singleton state at each endpoint.
- Jukebox: `HAS_RECORD` Boolean, two states at each endpoint.
- Note block: `INSTRUMENT × POWERED × NOTE`; 1.13.2 has `10 × 2 × 25 = 500` states and 1.14.4 has `16 × 2 × 25 = 800` states. All 16 target enum values and all ten old enum values are present in their respective source.

Thus the four keys comprise 504 old and 804 target key-state combinations; every checked horizontal wall bit is true. The state-count increase for Note Block does not change the predicate result.

## Limits

**ACCEPT** the bounded no-difference result for these four keys only. The crosswalk's r1 TSV overwrite limitation remains attached to the broader inventory. This verdict makes no player-movement claim and does not freeze either pair.
