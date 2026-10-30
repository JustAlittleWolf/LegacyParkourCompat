# Glide vertical-acceleration alias extension

## Scope and input binding

This source-only supplement extends the accepted `elytra-grouping-closure/reviewer-verdict.md` operation-level finding (bound there to finding SHA-256 `ed7ac76568adf100f48d92b27d42785ae703bfd0b7631e32d1850d4e50a1a44e`). It checks exact `moveRelative` glide branches in 1.9, 1.9.4, 1.10.2, 1.11.2, 1.12, 1.12.1, and 1.12.2; it does not use an endpoint as evidence for an uninspected release. Canonical read-only source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`; every source file below is `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`.

## Versioned evidence

| Release | Mapping artifact / provenance | `LivingEntity.java` SHA-256 | Glide branch |
|---|---|---|---|
| 1.9 | Feather Gen2 `1.9+build.2`; embedded `.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `moveRelative`, lines 1289–1319 |
| 1.9.4 | Feather Gen2 `1.9.4+build.2`; provenance and source manifest | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `moveRelative`, lines 1302–1332 |
| 1.10.2 | Feather Gen2 `1.10.2+build.2`; provenance and source manifest | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `moveRelative`, lines 1332–1362 |
| 1.11.2 | Feather Gen2 `1.11.2+build.2`; provenance and source manifest | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `moveRelative`, lines 1388–1418 |
| 1.12 | Feather Gen2 `1.12+build.2`; embedded `.decompile-provenance.json`, SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `moveRelative`, lines 1425–1455 |
| 1.12.1 | Feather Gen2 `1.12.1+build.2`; embedded `.decompile-provenance.json`, SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `moveRelative`, lines 1425–1455 |
| 1.12.2 | Feather Gen2 `1.12.2+build.2`; provenance and source manifest | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `moveRelative`, lines 1425–1455 |

For each row, the file hash was recomputed from the canonical source. Where a ready/source manifest exists, its `LivingEntity.java` row matches; for 1.9, 1.12, and 1.12.1 the relevant row in the embedded source provenance matches. Exact version inputs and Feather mapping artifact identities are recorded in the cited provenance. No source was regenerated.

## Shared historical operation and applicability

Every checked glide branch has the same gate sequence: local authority (`isLocallyControlled()` or `isLogicalSideForUpdatingMovement()`), not in water unless a flying player, not in lava unless a flying player, then `isFallFlying()`. The branch begins by setting fall distance to `1.0F` when vertical velocity exceeds `-0.5`, then reads the look vector and computes pitch, horizontal look length, horizontal velocity magnitude, and full look-vector length.

All seven versions compute the float lift factor as:

```java
float m = MathHelper.cos(i);
m = (float)(m * (m * Math.min(1.0, l / 0.4)));
```

Here `i` is the float pitch-in-radians, `l` is the double look-vector length, `Math.min` selects a double because `1.0` and `l / 0.4` are doubles, and the outer cast narrows the completed product to float. All seven then apply vertical acceleration in this exact order and type grouping:

```java
this.velocityY += -0.08 + m * 0.06;
```

Both numeric literals are double. The float `m` is promoted for multiplication by `0.06`; the product and addition to `-0.08` are double, then compound assignment updates the double vertical velocity. This confirms the 1.12.2 expression is already present at every inspected endpoint and patch alias from 1.9 through 1.12.1.

The checked sources show this branch as the glide-specific operation inside `LivingEntity.moveRelative`; after the acceleration, all seven retain the vertical drag and then subsequent dive and pull-up corrections in source order. The correction formulas are not part of this extension's changed operation. There is no default-gravity grouping cutover among the requested versions. The accepted 1.13 grouping transition remains separate and is not inferred to apply to earlier releases.

## Remaining limits

This closes only the exact glide vertical-acceleration expression, float lift pipeline, and enclosing gates for the seven named releases. It does not establish the first release between 1.9 and these samples, every 1.9–1.12 patch, glide-look guard behavior, player call graph for each version, 1.8 fallback wiring, or runtime parity. Keep the pre-1.9 native fallback and modern-only Slow Falling handling as integration gates. No product source, wiki, backlog, test, build, game, or source-generation operation was used.
