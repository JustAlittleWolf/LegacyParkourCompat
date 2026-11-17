# Portal block registry and legacy-state alias: 1.12.2 → 1.13.2

## Bounded result

The old registered portal block key `portal` (the built-in `minecraft:portal` block name) resolves to the 1.13.2 block key `nether_portal` (`minecraft:nether_portal`). This is supported by both endpoint registrations and the 1.13.2 block-state data fixer. The legacy flattened state records for the two valid portal axes are transformed as:

```text
{Name:"minecraft:portal",Properties:{axis:"x"}} → {Name:"minecraft:nether_portal",Properties:{axis:"x"}}
{Name:"minecraft:portal",Properties:{axis:"z"}} → {Name:"minecraft:nether_portal",Properties:{axis:"z"}}
```

The evidence closes this old registry-key and the listed legacy palette-state mapping only. It is not a general statement about unrelated aliases, every malformed state, or other portal mechanics.

## Exact source evidence

- 1.12.2 `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/Block.java:960` registers numeric block ID 90 under key `portal` with `new PortalBlock()`. `Blocks.java:384` binds `NETHER_PORTAL` by looking up `portal`. `PortalBlock.java:80-84,217-223` encodes horizontal axes as metadata 1 (X) and 2 (Z), with the state decoder selecting Z only for metadata 2 and otherwise X.
- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java:1081-1084` registers `nether_portal`; `Blocks.java:840` resolves `NETHER_PORTAL` by that key. The new `PortalBlock.java:26-37` declares/defaults/selects the horizontal `AXIS` property (X/Z).
- The legacy state palette is in 1.13.2 `unmapped/C_80754635.java:3603-3604`: flattened state IDs 1441 and 1442 map to `Name:'minecraft:portal'` with `axis:'x'` and `axis:'z'`, respectively. Those IDs are `(90 << 4) | 1` and `(90 << 4) | 2`. `unmapped/C_29689164.java:320-335,340-348` converts legacy block ID/name plus the low four metadata bits to the flattened state ID and obtains the state from this table.
- `util/datafix/DataFixes.java:406-410` adds schema version 1510 and the named `Block renamening fix`, passing the `f_76383611` block-name map from `unmapped/C_00119465.java:22-36`. That map contains `minecraft:portal` → `minecraft:nether_portal`.
- `unmapped/C_81767953.java:24-37` defines this renaming operation for both `BLOCK_NAME` and `BLOCK_STATE`. For block states it reads `Name`, sets only the renamed `Name` field, and returns the dynamic with its remaining fields intact; the portal `axis` property therefore survives. The resulting X/Z states are accepted by the registered 1.13.2 `PortalBlock`.

## Item/NBT boundary

`util/datafix/fixes/ItemIdFix.java:95` separately maps legacy numeric **item** ID 90 to the item name `minecraft:portal`. `DataFixes.java:408` separately installs an item renaming fix using `f_00266730`. Those item-stack operations are not the evidence for the block-state alias; the block mapping above is independently explicit and updates `BLOCK_NAME` and `BLOCK_STATE`. This report makes no claim about arbitrary NBT fields that are not typed as block states.

## Pinned source identity

The exact pair is 1.12.2 / 1.13.2, both Ornithe Feather Gen2 build 2. Ready markers report those versions and `ornithe-feather`; their source-manifest digests match the recomputed manifests:

- 1.12.2 `ornithe-feather.sources.sha256`: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 `ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Per-file SHA-256 values below were checked against the source files and their respective source-manifest entries:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/block/PortalBlock.java` | `d8fb94ff43876692bb61fbb61780995560c9c162c24702d2e25c33062c02abde` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/PortalBlock.java` | `075976960fdb4518ec5ee70443d8bfd175981aeb01b71aec9bc8ad5d957ccee9` |
| 1.13.2 | `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |
| 1.13.2 | `net/minecraft/unmapped/C_00119465.java` | `8e995b8101bbd4a2ad0f51ac8de432361cb533d3d3d3e65ac2f16da9a7b96cfd` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |
| 1.13.2 | `net/minecraft/unmapped/C_81767953.java` | `dc453a507d63d7bd371dc2c6315b7a54d356bf6e1f7ecb40af7debaaa90dd474` |
| 1.13.2 | `net/minecraft/unmapped/C_29689164.java` | `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c` |
| 1.13.2 | `net/minecraft/util/datafix/fixes/ItemIdFix.java` | `46620d1b0d1713b86c04c171f030a947dbcde68917ada66db49fd872d9c00e84` |

The associated PortalBlock provider report SHA-256 is `b4415e4ed8998a12f224b2e2a63df9638b955f0e0e08fdacd542041ec3a53333`, matching the independent audit's corrected 64-character digest.
