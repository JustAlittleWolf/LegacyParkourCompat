# 1.14 patch alias source review (bounded)

Host: Windows 11 / PowerShell. Sources read-only. No implementation, source regeneration, Git, tests, or game launch.

## Identity and source hashes

- 1.14.1 Yarn build 10; source `decompiled_minecraft/1.14.1/yarn`; input client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`; intermediary `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d`; Yarn `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`.
- 1.14.2 Yarn build 7; source `decompiled_minecraft/1.14.2/yarn`; input client SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; intermediary `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e`; Yarn `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`.
- 1.14.3 Yarn build 13; source `decompiled_minecraft/1.14.3/yarn`; input client SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`; intermediary `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`; Yarn `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`.
- Anchors: 1.14 Feather `feather-gen2-1.14+build.2` (client `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`, mapping jar `57f0e3227099d010c4a0c7f2fea03dbd665bc7e612ad204db80fa5b5728e65a3`); 1.14.4 Feather `feather-gen2-1.14.4+build.2`. Both canonical source manifests checked.
- Rehashed every listed source below against its exact mapping/source manifest or `.decompile-provenance.json`; all passed.

| Release | WallBlock | Block | BlockState | BeaconBlock | StairsBlock |
|---|---|---|---|---|---|
| 1.14.1 Yarn | `f26af6b0b8efdcbd1b1ecdce52ca87bb0ded2f9268dec41f5d060883ae8f3245` | `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe` | `9106f1649a2ac0965f6b3b13f2ec8437d7b16d2ce72e217c4f105b4524732238` | `aaf8f5ad6c4488b121fc59b005291bfb387aa9381d76fda2e42eff5d720da456` | `370dbbe6502e888c952c0104ce9b4cd49ee8a0c9d0149ca4394c695da48cbe3d` |
| 1.14.2 Yarn | `6a383c7085498a257ce1652923f2b5f5ad06a6eba34fcd5ef3d98590047386f9` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` | `c35e64104be023c3f7940dec7b459fb4252d552b6770baa45f4fb4c345dd7035` | `d90072b548dad552513b9484af48cd7986610317835bd23afe60582114179e23` | `d69a9ebbda81ca8dcea9bfdf6a249343f5da60cc1e36003619c89a5e506d5838` |
| 1.14.3 Yarn | `e75639b10b71692d786b5f962b5e53fe1841b270898058f9825abb6142c53390` | `d8728c19555875aee8f47ea138bf836d02acf9076d69fce289baad93c60e715f` | `c93fd032a3f9bd83e852eb7ce95e7afe8acd895556ed942fabc596f04be06e04` | `e5364d91823c96144982b66be6bbdbd6c5c2c9a98db513ea7d2eb124d95200c9` | `00b8d1a9a13b4441e10f9ad1f71b0d0de848a3e4f70e6841dca2a76272bc284d` |
| 1.14 Feather | `9e120e49c648ffaa8c120b262d783bf9dd457cd7ad1a4d9b037ddb2679c6130b` | `0308b22d47062863ee36db68b907d42c7a95e060d5841f4d6e9cf67eda66fd6a` | `ba80efb083df4e0314463e876b59921bc65fa08d42a7b4bb2be2288d551db386` | `7416e10a733c49b608758f5937ab6690cb12e5cd6ba385106c44f28fadef7d36` | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` |
| 1.14.4 Feather | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` | `7416e10a733c49b608758f5937ab6690cb12e5cd6ba385106c44f28fadef7d36` | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` |

## Verdict

No patch-specific wall or stair movement delta found in the examined 1.14.1, 1.14.2, or 1.14.3 slices against the 1.14 and 1.14.4 anchors. The 1.14.1→.2→.3 WallBlock differences observed are mapping names (`Properties.UP_BOOL`→`UP`, `field_15504`→`WALLS`, obfuscated direction constants→named directions) and equivalent spellings of the DOWN gate. `BlockTags.java` resolves the old field to `register("walls")`. Direction enum entries resolve the symbols to down/north/south/west/east. Placement and update use the same 4 side order, collision face predicate, connection boolean and UP rule. Feather's parent name `PaneBlock` corresponds to Yarn `HorizontalConnectedBlock`; the wall base/arm shapes and shape-index table and UP-controlled outline/collision selection match.

Beacon adjacency: a cobblestone wall side adjacent to a Beacon in a dry state connects because WallBlock tests the neighbor's collision face through `Block.isSolidFullSquare` (Yarn) / `Block.isFaceSolid` (Feather). Beacon's `isSimpleFullBlock`/`isSolid` override returns false, but that is a different predicate; Beacon inherits the ordinary block collision shape and its registry settings specify glass material with collision enabled. Beacon is not in `Block.canConnect`'s exception list. This is stable across all inspected aliases and anchors, not a 1.14.1/.2/.3 patch alias.

Stairs secondary check: placement/update shape selection and full shape table are semantically identical across 1.14.1 Yarn and both Feather anchors. Same-half perpendicular stairs create outer/inner corners only when the third-side guard passes; shape-index lookup selects the same listed VoxelShapes. No candidate delta.

Coverage is deliberately limited to WallBlock plus its wall-shape/collision predicate dependencies and StairsBlock full shape selection; this does not freeze the entire 1.14 pair.

Additional exact resolution check: 1.14.1/.2 `Block.canConnect` exclusions resolve in Blocks.java to barrier, carved pumpkin, jack-o-lantern, melon and pumpkin, plus LeavesBlock; 1.14.3 spells those IDs directly. None is Beacon. `Block.getOutlineShape` defaults to full cube and collision delegates to outline shape when `collidable`; Beacon registration supplies no no-collision setting, and Beacon does not override collision. Thus its outward face is full for the wall predicate despite its separate `isSimpleFullBlock`/`isSolid` false override.
