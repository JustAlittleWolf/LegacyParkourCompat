# Evidence-binding addendum: wall double/stem plant projection

This immutable addendum supplements, without replacing or editing, `wall_double_stem_plant_projection/report.md` (SHA-256 `59c078e34b1d9df85ae7e913a94914d9c0bcd4b102599e712eaf389439ba7f8c`). It addresses the independent audit request in `audit_wall_double_stem_plant/verdict.md` only. It adds exact evidence bindings for the already-reported provider delegation and state-domain claims; it does not repeat the wall-bit comparison.

## Exact source bindings

Endpoints remain 1.13.2 Ornithe Feather Gen2 build 2 (ready/source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`) and 1.14.4 Ornithe Feather Gen2 build 2 (ready/source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`). Each listed Java hash below was recomputed from the read-only canonical source file and matched its endpoint `.sources.sha256` entry.

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 | Anchor bound |
|---|---|---|---|
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` | Old `getFaceShape` at lines 285–286 delegates to `this.getBlock().getFaceShape(world, this, pos, face)`. Target `m_87223014` at lines 326–327 returns the cached face-solid result or delegates to `Block.isFaceSolid(this, blockView, blockPos, direction)`. |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` | Old `shouldConnectTo` / `isExceptionForConnection`; target `m_71853799` and the calls passing `m_87223014` in placement/update. |
| `net/minecraft/block/state/property/Half.java` | `140be608026a924e0a60d5e7678623fada34a764de26b2a6e4ca64b288f52997` | `140be608026a924e0a60d5e7678623fada34a764de26b2a6e4ca64b288f52997` | Enum values at lines 5–7 are exactly `UPPER` and `LOWER`. |
| `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | `121f8b78eda07640eccbe369f40acc7ccbdcb275dcb6aca87c4495cd193b01c0` | `Plane.HORIZONTAL` is exactly `{NORTH,EAST,SOUTH,WEST}` at line 400 (old) and line 415 (target); `UP` and `DOWN` are in the separate vertical plane. |
| `net/minecraft/state/property/DirectionProperty.java` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` | `3d36632d97141b65d1c45fe11d632d7cf6c5ef0c7c08cf7b57635fb87e8ac188` | `DirectionProperty.of(name, Direction.Plane.HORIZONTAL)` constructs the facing domain from the supplied four-direction plane. |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` | `HORIZONTAL_FACING` is declared from `Direction.Plane.HORIZONTAL`; `AGE_7` is inclusive range 0–7. |
| `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` | `FACING` aliases `Properties.HORIZONTAL_FACING`. |

These exact anchors support the report's corrected state count: each attached-stem key has four horizontal `FACING` values; each double-plant key has two `HALF` values; each melon/pumpkin stem key has eight `AGE` values. The immutable original report remains bound to its original digest above. No source comparison, scope, result, or pair-completion status is changed by this addendum.
