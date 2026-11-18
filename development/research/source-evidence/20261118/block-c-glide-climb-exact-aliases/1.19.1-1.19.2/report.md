# 1.19.1 → 1.19.2: already-active dry Elytra at climbable feet

## Scope and verdict

Exact-source comparison for one bounded state: an already-active ordinary Elytra player, dry, with ladder or vine in the feet block position, entering `LivingEntity.travel` on the logical client. **The adjacent releases are identical in the examined travel, climbable-predicate, and bound-move source files.** In both, the dry fall-flying branch performs a direct pre-move `fallDistance = 1.0F` overwrite only when the current vertical delta is strictly greater than `-0.5`; then, after Elytra velocity calculations and damping, it calls `move(MoverType.SELF, this.getDeltaMovement())`.

This result does not decide Elytra activation/selection or its upstream caller/input gates, does not set the exact tick at which the active state is reached, and does not establish a final tick limit. Keep those as separate claims and verify their own source paths. It is not a full-pair movement inventory, acceptance, or runtime-parity verdict.

## Exact source identity and integrity

Mapping: Mojang official mappings (`mojmap`) for both exact releases. Each side has `mojmap.ready.json` with `status: ready`, exact `versionId`, mapping, source-manifest digest, and client digest. There is no `.decompile-provenance.json` in these trees; the repository's release sidecars are `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`.

| Release | Client SHA-256 | Source-manifest SHA-256 (also declared by ready marker) | Provenance-sidecar SHA-256 | Ready-marker SHA-256 |
|---|---|---|---|---|
| 1.19.1 mojmap | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` |
| 1.19.2 mojmap | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` |

Source-file SHA-256 values below are raw file bytes. Each file also matches its corresponding lower-case digest entry in that release's `mojmap.sources.sha256` manifest. All three files are byte-identical between releases:

| Relative source path | SHA-256 (both releases) |
|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |

The release provenance records exact version IDs and Mojmap namespace; 1.19.1 was prepared as a one-release run and 1.19.2 in a batch containing 1.19.2–1.19.4. The ready markers report 4,480 source files for each side. This report used only the canonical decompiled source and its provenance/readiness sidecars; no wiki, implementation, or prior finding was consulted.

## Source facts

- `1.19.1`, `1.19.2`, `mojmap`, `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2042–2178: after the water branch (2052–2086) and lava branch (2087–2106), `else if (this.isFallFlying())` begins at 2107. Its first statements capture delta movement in `$$13`; line 2109 tests `if ($$13.y > -0.5)` (strict `>`; double literal `-0.5`); line 2110 assigns `this.fallDistance = 1.0F`. The branch then calculates lift/steering, calls `this.setDeltaMovement($$13.multiply(0.99F, 0.98F, 0.99F))` at 2135, and only then invokes `this.move(MoverType.SELF, this.getDeltaMovement())` at 2136. The direct assignment is therefore before the bound Elytra move. Its behavior is identical in the two byte-identical files.
- `LivingEntity.java`, `onClimbable()`, lines 1462–1477: spectators return false. Otherwise the method gets `blockPosition()` and `getFeetBlockState()`; a feet state in `BlockTags.CLIMBABLE` returns true and records that position as `lastClimbablePos`. The alternate positive witness is a trapdoor state accepted by `trapdoorUsableAsLadder`. A ladder or vine feet state is the tag witness when its vanilla state belongs to `BlockTags.CLIMBABLE`. The Elytra branch itself does not call `onClimbable()` to choose its travel path.
- `1.19.1` and `1.19.2`, `Entity.java`, `move(MoverType, Vec3)`, lines 547–579: before positional application, the only fall-distance write in this slice is a conditional reset at 570–575: when nonzero distance and squared displacement `>= 1.0`, clip against `FALLDAMAGE_RESETTING`, resetting to zero on a hit. Otherwise this part does not overwrite the travel assignment before applying the position at 578. Later in the move, lines 594–597 call `checkFallDamage`; lines 991–1002 show its landing reset or falling-distance accumulation. This is post-call movement behavior, not an earlier overwrite.
- The `onClimbable()` predicate does not appear in `Entity.move`'s pre-position fall-distance gate. The adjacent `Player.java` files are also byte-identical; their climbable and Elytra movement-stat branches do not change the `LivingEntity.travel` branch above.

## Limits

The conclusion is scoped to already-active, dry Elytra travel and the stated feet-block witness. The sources establish the pre-move direct write and later move-time collision/landing handling, but this inspection does not establish the upstream activation/selection sequence or a final tick bound. It makes no claim about a 1.19.1→1.19.2 behavioral change: the compared relevant files are byte-identical.