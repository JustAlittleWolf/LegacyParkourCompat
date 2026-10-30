# Corrected client-archive resource comparison: 1.8.5–1.8.8

## Correction to prior supplement

This immutable correction supplements `closure.md` and `resource-registration-supplement.md`; neither earlier file is edited. The earlier supplement searched the standard launcher versions folder and the 1.8.9 provenance build-cache path, but missed the canonical client-cache path assigned for this follow-up: `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/<version>/`. That omission made its 1.8.5–1.8.8 archive-absence statement inaccurate. The archive checks below use only the assigned path, with no downloads and no search elsewhere.

## Verify archives and version metadata before inspection

I first hashed each `client.jar` and `version.json` in the assigned cache and matched each hash to the corresponding input recorded by that release's embedded `.decompile-provenance.json`. Only after all eight hashes matched did I open the four client archives and inspect entries.

| Release | Client object ID in provenance | `client.jar` SHA-256 (expected = actual) | `version.json` provenance URL | `version.json` SHA-256 (expected = actual) | Bytes: JAR / JSON |
|---|---|---|---|---|---:|
| 1.8.5 | `5d39957c61d19042f8bd669a6faa99989cf37083` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `https://piston-meta.mojang.com/v1/packages/16da5f5be7478f3602c25182a90f2057ba2f60d8/1.8.5.json` | `4fe7e25babf0c16244675812332c1403f0a30f8dac03b9e7c9cfd40ba39c7597` | 8,462,879 / 18,244 |
| 1.8.6 | `faa55e34ded35089d34fe921ea83d317fc152e93` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `https://piston-meta.mojang.com/v1/packages/acccbb056a3e8f3086c4614974fb3a894317853a/1.8.6.json` | `aecb637b9ce4e0212b02eb177f128e70bb809338e57ff3ba3128d2bef09df84b` | 8,461,536 / 18,244 |
| 1.8.7 | `d546a6a092060c85f1eb1d9213ff823c558b1255` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `https://piston-meta.mojang.com/v1/packages/7152f102903cd3ce7514d84c8ac98efecac30839/1.8.7.json` | `e45b14dec986c564a2daf1024d508f3691cb5c7e10c1ed4450dffeb0790fba47` | 8,461,925 / 18,244 |
| 1.8.8 | `0983f08be6a4e624f5d85689d1aca869ed99c738` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `https://piston-meta.mojang.com/v1/packages/690172f1227e1c1d2fa8fceadd0f578f7851a69e/1.8.8.json` | `7f002c1dca74cc51ce995e7fdb465ab4984c940e77a1b1c4f2b09bed8702e558` | 8,465,313 / 18,243 |

1.8.9 is absent separately: `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.9/` does not exist, so no 1.8.9 `client.jar` or `version.json` was hashed or inspected. Its source provenance pins client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`, but no local archive was available to compare against that pin. No archive/resource equality claim is made for 1.8.8→1.8.9.

## Exact archive entry totals and deltas

Counts below include non-directory ZIP entries. Every archive has exactly 3,085 `assets/` entries. Their top-level groups are 340 `blockstates`, 1 `font`, 1 `lang`, 1,595 `models`, 87 `shaders`, 3 `texts`, and 1,058 `textures` entries. Other non-class entries are the same five paths in all four archives: `META-INF/MANIFEST.MF`, `META-INF/MOJANGCS.SF`, `META-INF/MOJANGCS.RSA`, `pack.png`, and `log4j2.xml`.

| Release | All file entries | `assets/` | `.class` | Other |
|---|---:|---:|---:|---:|
| 1.8.5 | 5,597 | 3,085 | 2,507 | 5 |
| 1.8.6 | 5,596 | 3,085 | 2,506 | 5 |
| 1.8.7 | 5,596 | 3,085 | 2,506 | 5 |
| 1.8.8 | 5,597 | 3,085 | 2,507 | 5 |

| Pair | Added / removed entries | Common-path content changes | Asset added / removed / content-changed | Other changed paths |
|---|---:|---:|---:|---|
| 1.8.5→1.8.6 | 1 / 2 (all classes) | 52: 49 classes + 3 `META-INF` entries | 0 / 0 / 0 | `META-INF/MANIFEST.MF`, `.MOJANGCS.SF`, `.MOJANGCS.RSA` |
| 1.8.6→1.8.7 | 0 / 0 | 37: 34 classes + 3 `META-INF` entries | 0 / 0 / 0 | Same three manifest/signature entries |
| 1.8.7→1.8.8 | 2 / 1 (all classes) | 112: 108 classes + 3 `META-INF` entries + 1 asset | 0 / 0 / 1 | Same three manifest/signature entries |

The archive asset content was hashed entry by entry. A sorted `path<TAB>entry-SHA256<LF>` inventory of all assets hashes to:

| Release | Asset inventory SHA-256 |
|---|---|
| 1.8.5 | `a47eba476019cb27fc9f8b4990568fad3e6126a529494317fc4ae815b6a7a4aa` |
| 1.8.6 | `a47eba476019cb27fc9f8b4990568fad3e6126a529494317fc4ae815b6a7a4aa` |
| 1.8.7 | `a47eba476019cb27fc9f8b4990568fad3e6126a529494317fc4ae815b6a7a4aa` |
| 1.8.8 | `cd32b56b8deb945c4b885192671ce8e064a6a194b8df38ad6d8ce77d6a562bd7` |

The only changed resource entry is `assets/minecraft/lang/en_US.lang`. Its entry SHA-256 is `ff0d64150ebe70e332026ccdb9f3d4f14fc4365363630db85879aff98014f6ee` in 1.8.5, 1.8.6 and 1.8.7, then `5df7541d53fdd24ee3e55143a038e61e19f377bdc2568cf5a41d980626cd302e` in 1.8.8. The diff adds six translated warnings/confirmation strings for resource packs with older/newer formats and removes none:

```text
resourcePack.incompatible=Incompatible
resourcePack.incompatible.old=(Made for an older version of Minecraft)
resourcePack.incompatible.new=(Made for a newer version of Minecraft)
resourcePack.incompatible.confirm.title=Are you sure you want to load this resource pack?
resourcePack.incompatible.confirm.old=This resource pack was made for an older version of Minecraft and may no longer work correctly.
resourcePack.incompatible.confirm.new=This resource pack was made for a newer version of Minecraft and may no longer work correctly.
```

There are no added, removed or changed `assets/minecraft/blockstates/**`, `assets/minecraft/models/**`, or `assets/minecraft/textures/**` paths in 1.8.5→1.8.8. Their sorted per-entry inventories are identical across all four archives:

| Resource family | Entries per release | Aggregate inventory SHA-256 (identical for 1.8.5–1.8.8) |
|---|---:|---|
| `assets/minecraft/blockstates/` | 340 | `d5d3cd9b8d5133722b3a247faae152f0cad1cf32916feb11e30f1a8d947471b9` |
| `assets/minecraft/models/` | 1,595 | `4caa6d6405ef5c0f3418ad468ea6ae103fe2e1913694ab750aed78f33866b3e3` |
| `assets/minecraft/textures/blocks/` | 382 | `9e4d3daeb7a2e61c91e5c69b99d718e2c1cfe9fd6edf164aa1ee1a8218d508e5` |

The aggregate digests use the same sorted path/hash serialization described above. Every blockstate/model/texture path and every entry hash in these families matches, not merely family counts. The three `META-INF` path contents change on each release boundary as signed-archive metadata; they are not resource definitions. The other two non-class paths, `pack.png` and `log4j2.xml`, remain byte-identical.

## Movement/resource conclusion

For the inspected 1.8.5→1.8.8 vanilla client archives, the only packaged asset delta is the English resource-pack compatibility UI text. There are no changed blockstate, model, block texture, sound, shader, font, or text-resource payloads; no asset entries were added or removed. In particular, the packaged block rendering definitions and models are path-and-content identical. Combined with the audited Java source inventory—which has no changed block/entity/world collision provider in this span—there is no packaged resource or Java registration delta that changes block movement semantics in these three intervals.

The known 1.8.5→1.8.6 `Bootstrap` removal remains a Java registration/state-producer change, not a resource change. In 1.8.5, `DispenserBlock.tick` calls behavior only on `!world.isClient`; the custom command-block behavior separately gates `setBlockState`, NBT copying and neighbor updates on `!world.isClient`. Its item decrement is still only reached through the server-only dispenser tick. `DispenserBlock.java`, `CommandBlock.java`, and `Block.java` have equal full-file hashes on both sides. Accordingly the removal cannot change a client-only movement calculation. If an old server places a command block and a new one does not, subsequent client collision can differ because the server sent a different world/block state; with the same received state and movement inputs, the unchanged collision/travel code responds the same. `IBlockSource.getBlock()` disappears with the registration, and the only old call found was the removed behavior's `updateNeighbors(source.getPos(), source.getBlock())`.

The `.8→.9` Java/source movement boundary remains covered by the existing audited inventories and ordinary-slice report, but its packaged asset boundary remains open because the assigned cache contains no 1.8.9 archive or version JSON. Do not extend the resource-equality conclusion above through 1.8.9 without the exact artifact available and hash-matched.
