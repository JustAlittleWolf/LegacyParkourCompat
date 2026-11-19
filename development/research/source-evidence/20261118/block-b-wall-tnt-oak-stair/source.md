# Cobblestone wall mixed TNT and solid-facing stair tuple

This is a bounded exact-source prerequisite draft for 1.11.2 → 1.12. It does not establish independent acceptance or broaden a verdict to every stair state.

## Pinned evidence

- **1.11.2, Ornithe Feather build.2:** `decompiled_minecraft/1.11.2/ornithe-feather`; parent `ornithe-feather.ready.json` identifies version `1.11.2`, mapping artifact `feather-gen2-1.11.2+build.2-mergedv2.jar` and `.tiny`, and source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. Hashes for the cited source files match that manifest.
- **1.12, Ornithe Feather build.2:** `decompiled_minecraft/1.12/ornithe-feather`; `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` identifies the `1.12+build.2` merged mapping artifact and exact client input. Hashes for the cited source files match its `sources` map.

## Predicate composition and applicability

**1.11.2:** `WallBlock.resolveVirtualProperties` calls `shouldConnectTo` independently for north/east/south/west, then stores those booleans and derives `UP` from the opposite-pair pattern plus the above-air check (`net/minecraft/block/WallBlock.java`, 148–157, 188–195). TNT uses `Material.TNT` (`TntBlock.java`, 26–29); `Material.TNT` is translucent and `Material.isSolidBlocking()` returns false for translucent materials (`Material.java`, 26, 103–105), so this wall predicate rejects the TNT side. `StairsBlock.isCube` returns false (`StairsBlock.java`, 145–147), and the state implementation dispatches `isCube()` to its block (`StateDefinition.java`, 273–275), so the predicate rejects the stair side at that gate regardless of the stair's half or visible face. Air also fails the solid-blocking/cube predicate (`AirMaterial.java`, 20–21 together with `Material.java`, 103–105; `AirBlock.java`, 48–50). For the specified two non-air neighbors, the resulting wall mask is therefore `0` and `UP=true`.

**1.12:** `WallBlock.resolveVirtualProperties` still invokes `shouldConnectTo` once per direction and composes the results into the same directional properties and opposite-pair `UP` rule (`WallBlock.java`, 146–159, 190–196). TNT's `TntBlock` constructor still uses `Material.TNT` (`TntBlock.java`, 26–29), but the wall now reads the queried face shape. The base `Block.getFaceShape` returns `FaceShape.SOLID` (`Block.java`, 362–364); TNT is absent from both the explicit placement exceptions (`Block.java`, 219–234) and wall connection exceptions (`WallBlock.java`, 154–159). Thus the dry TNT side connects under this predicate. Air explicitly returns `FaceShape.UNDEFINED` (`AirBlock.java`, 54–56), so the two other sides do not connect. The stair connects only for an exact resolved state whose face toward the wall is `SOLID`: the 1.12 stair face method resolves virtual stair shape and, for `STRAIGHT`, returns `SOLID` exactly when `FACING == face` (`StairsBlock.java`, 140–162; 328–359). This is the required applicability condition; it is not a claim that every Oak stair orientation/shape connects.

Each directional result is consumed directly in the combined mask. `getShapeIndex` maps N/E/S/W to the corresponding `Direction.getIdHorizontal()` bit (`WallBlock.java`, 105–123); direction IDs are N=2, E=3, S=0, W=1 (`Direction.java`, 17–20, 47–49). The same shape and collision-shape arrays occur in both versions (`WallBlock.java`, 28–63 in 1.12; 30–65 in 1.11.2). Since the tuple does not fix the relative side arrangement, the exact 1.12 result is:

| TNT side + solid-facing stair side | Mask/index | Outline box `(minX,minY,minZ)–(maxX,maxY,maxZ)` | Collision box |
|---|---:|---|---|
| N + E | 12 | `(0.25,0,0)–(1,1,0.75)` | same x/z; maxY `1.5` |
| N + W | 6 | `(0,0,0)–(0.75,1,0.75)` | same x/z; maxY `1.5` |
| S + E | 9 | `(0.25,0,0.25)–(1,1,1)` | same x/z; maxY `1.5` |
| S + W | 3 | `(0,0,0.25)–(0.75,1,1)` | same x/z; maxY `1.5` |
| N + S | 5 | `(0.3125,0,0)–(0.6875,0.875,1)` | same x/z; maxY `1.5` |
| E + W | 10 | `(0,0,0.3125)–(1,0.875,0.6875)` | same x/z; maxY `1.5` |

For adjacent sides `UP=true`; for opposite sides with air above `UP=false`. `UP` is not included in `getShapeIndex`, so it does not further alter these listed boxes. The 1.11.2 mask-0 result is outline `(0.25,0,0.25)–(0.75,1,0.75)` and collision `(0.25,0,0.25)–(0.75,1.5,0.75)` for every arrangement.

## Movement consequence and dependency boundary

Both wall implementations expose these computed boxes through `getCollisionShape` and through `addCollisions`; the latter recomputes virtual properties when `forceShape` is false (`WallBlock.java`, 84–104 and 90–98 in 1.12; 86–105 and 92–98 in 1.11.2). `World.getCollisions` calls each visited block state's `addCollisions(..., false)` (1.12 `World.java`, 964–1015; 1.11.2 `World.java`, 960–1011), and ordinary `Entity.move` obtains that collision list before axis clipping (both `Entity.java`, 567 onward). Therefore a player movement query intersecting the added side-arm volume can be clipped differently; no particular trajectory/result is asserted without a concrete player box and requested displacement.

The owned technical operation remains the wall's neighbor predicate → directional mask → outline/collision box. Closed source dependencies for this tuple are the 1.12 TNT default face-shape/exception path, the 1.12 resolved stair face-shape gate, the 1.11.2 material/cube gates, the two-air gate, direction-bit mapping, and the generic world/player collision consumer. The combined tuple is source-supported under the conditions above; the independently accepted singleton evidence alone is not a verdict on all side arrangements or all Oak stair states.

