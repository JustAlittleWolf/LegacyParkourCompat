# Independent audit verdict r2: End Portal Frame clipping witness

**Verdict: corrected report accepted for the stated static geometric clipping gate.** The report is pinned to the requested revision. All nine listed whole-file source hashes match both the source bytes and their corresponding release manifest entries. Recomputing the coordinate witness confirms requested X motion resolves to `0.02` in 1.12.2 and `0` in 1.13.2 under the stated conditions.

## Identity checks

- Input report SHA-256: `4b244bf5072401bf25528703b43391f45df35b18ba3a416bc96e2366dac4997e` (matches the requested report identity).
- Full manifest SHA-256 values: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (both match the report).
- All nine source-file values in the report match the source bytes and the corresponding `ornithe-feather.sources.sha256` entries. This includes the provider, `Entity.move`, old `Box.intersectX`, and new voxel-shape/axis-clipping files.
- I additionally checked collision candidate enumeration: 1.12.2 `World.java` SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` and 1.13.2 `WorldView.java` SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; both match their release manifest entries.

## Geometry and path check

The chosen AABB has `minY=0.8125`, which exactly touches the frame base top. In 1.12.2, `Box.intersectX` requires strict overlap in Y and Z, so the base is ignored. The eye box overlaps in Y and Z, but its positive-X gap is `0.3125 - 0.25 = 0.0625`, greater than `dx=0.02`; if considered, it leaves the requested offset unchanged. The collision query's swept AABB ends at `x=0.27`, before the old eye box begins at `x=0.3125`, so that box is not needed as a candidate for this short move. Either way the resolved X offset is `0.02`.

In 1.13.2, `EYE=true` selects the combined base/eye shape. `Block.block` scales the eye bounds to X/Z `[0.25,0.75]` and Y `[0.8125,1]`. The swept collision query includes the eye shape, and the voxel axis scan's Y/Z index ranges intersect its occupied cells. With positive X motion, `calculateMaxDistance` scans the first eye X boundary at `0.25`; against `box.maxX=0.25`, the candidate is zero. The `f >= -1.0E-7` test accepts that boundary and clips the requested `0.02` to zero. Its Y scan begins at `box.minY + 1.0E-7`, so the base ending at `0.8125` does not cause the result.

This verifies a static clipping consequence for the specified frame state, AABB, and requested motion. It does not establish a reachable trajectory, practical map impact, pair coverage, an introduction boundary, or runtime parity.
