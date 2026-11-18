# Independent source audit: ordinary Oak leaves dry-side provider

Status: **COMPLETED — accepted for the bounded source claim.**

## Frozen source roster and identity

Roster frozen before reading the author's evidence: **24 stable releases** — `1.8`, `1.8.1`–`1.8.9`, `1.9`–`1.9.4`, `1.10`–`1.10.2`, `1.11`–`1.11.2`, `1.12`–`1.12.2`. All are Ornithe Feather Gen2 `+build.2`. The 17 source-root `.decompile-provenance.json` manifests and seven version-root ready/provenance/source-hash records agree on exact version/mapping identity; for the seven sidecar releases, the ready marker's `sourceManifestSha256` matches the sidecar file. Recomputed source SHA-256 values for all ten checked source families (liquid flow, fluid propagation, block registration, material, leaves, placement item, player water-query entity and Oak variant source) match their published SHA-256 source records in every release.

| Release | `LiquidBlock.java` SHA-256 | `FlowingLiquidBlock.java` SHA-256 |
|---|---|---|
| 1.8 | `e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3` | `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c` |
| 1.8.9 | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` |
| 1.9.4 | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `12bc1bdbcb17a4fa321a14094144919d5a6c00058a3d28c5f8383a1465d47104` |
| 1.10.2 | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79` |
| 1.11.2 | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2` |
| 1.12.2 | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2` |

The six distinct `FlowingLiquidBlock` source bodies covering the roster were checked; each resolves `isLiquidBlocking` for Oak leaves through `Material.blocksMovement()` (plus unrelated later-version special cases), and `canSpreadTo` requires a non-blocking destination.

## Accepted bounded claim

The legacy ID/name is **`leaves`**, not `oak_leaves`. `Block.java` registers `leaves` as `LeavesBlock` (1.8 line 806; 1.12.2 line 782); `leaves2` is separately registered and out of scope. Oak is variant/meta 0 (`LeavesBlock.java`, 1.8 lines 23, 84; 1.12.2 lines 29, 59, 79).

The ordinary liquid-flow gate directly tests the dry horizontal neighbor's `getMaterial().blocksMovement()` before reading that neighbor's lower cell (`LiquidBlock.java`, 1.8 lines 133–141; 1.12.2 lines 146–156). Oak leaves inherit `Material.LEAVES` via `AbstractLeavesBlock`; `Material.LEAVES` is a plain `Material`, and the base `blocksMovement()` returns true (1.8 `AbstractLeavesBlock.java:26`, `Material.java:13,63–65`; 1.12.2 `AbstractLeavesBlock.java:25`, `Material.java:15,71–73`). The translucent flag does not change that method. The gate is not querying opacity, solid-block status, or collision shape. Therefore ordinary Oak leaves block the east-down water read in this operation.

Ordinary item placement is sufficient: `LeavesItem.getBlockMetadata(metadata)` returns `metadata | 4`, and `BlockItem` passes it to `getPlacementState` (1.8 and 1.12.2 `LeavesItem.java:17`, `BlockItem.java:50–52`). For item metadata 0, LeavesBlock decodes Oak variant 0 and `(metadata & 4) == 0` is false, setting `DECAYABLE=false` (1.8 `LeavesBlock.java:84`; 1.12.2 `LeavesBlock.java:59,79`). No custom state or support workaround is needed.

For the requested standing-player water query, the supplied fixture has its center source at `(0,64,0)` inside the already-loaded standing query volume, with only the north same-height level-1 water face as an open horizontal water neighbor. `Entity.checkWaterCollisions` samples liquid in its shrunken/vertically grown body shape in both endpoints (1.8 `Entity.java:745–762`; 1.12.2 `Entity.java:948–965`). In `getFlow`, north contributes raw `-Z` by `1 - 0`; the east leaf rejects the lower read, leaving east zero. The water states are ordinary levels 0, 1, and 0, so no falling-liquid branch is involved.

The enclosed snapshot is stable for the relevant liquid update: Oak blocks liquid replacement/flow by `isLiquidBlocking`/`canSpreadTo`; the center source's horizontal outlets are north (already water of the same material), the remaining solid walls, or Oak, and its floor blocks downward flow. The north level-1 cell's center source gives its level-1 update input; its other lower-depth alternatives and floor are blocked. A corresponding lower source beneath the east Oak is enclosed by its walls and floor. The persistent Oak item state does not decay. No runtime simulation claim is made.

Replacing the leaf with air is only a query-time comparator over the same immutable state snapshot: the dry-side formula at center `i=0`, lower source `j=0` yields `j-(i-8)=8`, so air adds raw `+8X` alongside the independent `-1Z`. Do not claim the air replacement is stable after a center-source liquid update.

## Binding and limits

The independently checked author candidate is immutable `42817118d79ffa88d1081ed6f5d44d32da232d09:evidence.md` (blob SHA-256 `b3d86d632c2d8144a0e31af0200437660aed5bf7f535b1c7b255bb7b1f47bd62`). It agrees with this bounded verdict. No product source, implementation, Git history, wiki inventory, unrelated material/provider, other leaf species, falling-wall gate, body-contact behavior, test/build/decompile/runtime execution, or gameplay was inspected.

