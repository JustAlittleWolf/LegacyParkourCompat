# Dispenser, dropper, spawner, and observer wall predicate

## Scope and source identity

This is a bounded comparison of the four registry rows assigned from `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (input SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`). The exact common registry keys are `minecraft:dispenser`, `minecraft:dropper`, `minecraft:spawner`, and `minecraft:observer`. The initial assignment called the third row `mob_spawner`; the pinned endpoint registrations and crosswalk both use `spawner`, so that is the key examined here.

Endpoint sources are Ornithe Feather Gen2 build 2. The 1.13.2 source manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, matching `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`; the 1.14.4 manifest SHA-256 is `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`, matching its ready marker. The consulted Java hashes below were checked against the canonical source bytes and each endpoint's `ornithe-feather.sources.sha256`.

## Old and target wall predicates

At 1.13.2, `BlockState.getFaceShape` delegates to the state's block. `Block.getFaceShape` returns `FaceShape.SOLID` by default. `WallBlock.shouldConnectTo` accepts that face when `!isExceptionForConnection(block)`; the separate alternate branch is for thick poles or a middle pole from a `FenceGateBlock` (`WallBlock.java:51–58`). None of these four registered classes overrides `getFaceShape`, appears in `isExceptionForConnection`'s explicit exclusions, or is a fence gate. Thus each queried horizontal face yields a connection bit of true.

At 1.14.4, `WallBlock.getPlacementState` passes `BlockState.m_87223014(world,pos,face)` to its wall predicate (`WallBlock.java:48–52, 55–77`). That state method delegates to `Block.isFaceSolid`. The latter tests the state's collision-shape face (`Block.java:371–378, 402–408`; `BlockState.java:197–202, 326–328`). The base collision shape is the state's shape when `hasCollision` is true; the base shape is a full unit cube. All four registrations use ordinary stone properties without `.noCollision()`, so the default `hasCollision=true` applies. `BlockWithBlockEntity` extends `Block` without overriding either shape method; `DropperBlock` inherits the dispenser shape route; `ObserverBlock` inherits `FacingBlock`/`Block` geometry. These blocks are not in the target attachment-exception list, are not members of the exact 14-value `WALLS` tag payload, and are not fence gates. Therefore `Block.isFaceSolid` is true and the target wall's generic arm returns true for all four horizontal faces.

## Registrations and state domains

The exact keys/classes and state domains match at both endpoints:

| Key | Registered class | Registered state domain | Count |
|---|---|---|---:|
| `minecraft:dispenser` | `DispenserBlock` | `FACING` = six `Direction` values × `TRIGGERED` = false/true | 12 |
| `minecraft:dropper` | `DropperBlock` (inherits `DispenserBlock` state definition) | `FACING` = six values × `TRIGGERED` = false/true | 12 |
| `minecraft:spawner` | `MobSpawnerBlock` | no declared properties | 1 |
| `minecraft:observer` | `ObserverBlock` | `FACING` = six values × `POWERED` = false/true | 12 |

Old registrations are in `Block.java` at lines 895, 992, 1307 and 1614; target registrations are in `Blocks.java` at lines 207, 407–409, 966 and 1457. The 1.13.2 and 1.14.4 `Properties.FACING` source both declares NORTH, EAST, SOUTH, WEST, UP and DOWN; boolean properties have the two boolean values. `DispenserBlock.buildStateDefinition` adds FACING and TRIGGERED, `ObserverBlock.buildStateDefinition` adds FACING and POWERED, and MobSpawnerBlock declares no state properties. The target and old total is 37 registered key/state pairs. Each pair gives four true wall bits, for 148 true→true direction cases. No difference is found in this bounded predicate slice.

This is a wall-neighbor predicate projection only. It does not test player trajectories, block callbacks, or full pair coverage and does not freeze the pair.

## Canonical source hashes

| Canonical relative source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` (target registrations only) | — | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/DispenserBlock.java` | `2841b2ad7aef0801d2dd2b242997e874c5581c726f694c9e8a4eb02971da0e6e` | `6cfeb3df6855c78945c9baa8f71982a56fc87a74d63bc4d7970144da5dea26ab` |
| `net/minecraft/block/DropperBlock.java` | `5067742bba5e16c79a5004fed1b97e374b6e312f626bd3daedebc81ea1cc9a3a` | `d8a03dc348c52eaee5e604b047aac98c3878e37cfb6cd3d2606f43a935b4bd07` |
| `net/minecraft/block/MobSpawnerBlock.java` | `6fba55735b24b6f4cd5fa2aad00b8d2847d387c2ec0d131d3f06c8502ff80208` | `67c43436bba8eba8e96055b506802550612134567d9a31fa15a7b756e870da00` |
| `net/minecraft/block/ObserverBlock.java` | `787d9c0ecbb16e0e5543299a214e038c64861a237d512aa0b509a7ed4d353e6a` | `596557a079b6829e11c68c811e28103dbe971564be8fc08cecfdb0cdf80316ea` |
| `net/minecraft/block/FacingBlock.java` | `2e90aa010630ae9786a631f56cdee4b454a5d01359f15ac29f52a15ad3c9eb1e` | `2e90aa010630ae9786a631f56cdee4b454a5d01359f15ac29f52a15ad3c9eb1e` |
| `net/minecraft/block/BlockWithBlockEntity.java` | `9f190eebfcff3f23ce76bceafd6295d4e84d93cc3a76d85f83972792c811a266` | `dc644d4ab779d5ee65f5a0828097d3732efdbd9cc8fc8ecd1660ae7f1b322a2a` |
| `net/minecraft/state/StateDefinition.java` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` | `d7702c839e8d8181a49a24d9d81df69412f78253e9e66633bb5c6c03ef349365` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` |
| `net/minecraft/state/property/BooleanProperty.java` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` | `7a414a5c7a8110a97b32fce9ad0bfa9df0212f286bfbe5b777962205cc9dcf11` |

The exact 1.14.4 `data/minecraft/tags/blocks/walls.json` resource SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its independently audited raw-client extraction is `wall_resource_membership/extraction-record.md` SHA `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`, with audit `wall_resource_membership_audit/verdict-r2.md` SHA `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`. The resource payload lists only wall blocks, none of the four keys.
