# MCPK source adjudication — 2026-10-07

Scope: MCPK parkour wiki only, matched to exact campaign endpoint source where available. Wiki text is treated as a lead, not authority. No Minecraft Wiki page or other audit track was consulted. No project implementation, test, build, server, or Docker action was performed.

## Source identity

Ready trees were read from the canonical shared source root. Ready JSON metadata and source manifest identities were recorded for the endpoint comparisons below. The 1.16.1/1.16.2 Player.java rows were separately checked against their source manifests; source trees remain owned by the shared source owner and were not modified.

**Integrity status:** the source owner reported that a reproducibility rerun replaced derived mapped JARs in the shared cache for Feather versions 1.8.9–1.13.2 while preserving source-file and raw-input hashes. The 1.13.2 endpoint of the 1.13.2→1.14.4 comparison is also affected. Findings that depend on those Feather endpoints are provisional pending canonical artifact-integrity repair and fresh verification. No marker was rewritten, no mismatch waived, and no independent decompilation was run.

| Endpoint | Namespace | Source manifest SHA-256 |
|---|---|---|
| 1.10.2 | ornithe-feather | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11.2 | ornithe-feather | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12.2 | ornithe-feather | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | ornithe-feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 | ornithe-feather | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` |
| 1.15.2 | mojmap | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.1 | mojmap | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | mojmap | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | mojmap | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |
| 1.17.1 | mojmap | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18.2 | mojmap | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |

For 1.8.9 and 1.9.4 hashes and details, see [the focused source record](mcpk-1.8.9-1.9.4.md).

## Findings

### Provisional Feather findings and currently verified Mojmap findings

- **1.10 auto-jump option and conditions (endpoint 1.10.2).** `GameOptions.autoJump` initializes to `true` and is serialized as a user option. `LocalClientPlayerEntity.autoJump` only proceeds when enabled, its cooldown is clear, the player is on ground, not sneaking, and not riding (`LocalClientPlayerEntity.java:823–825`). The obstacle bounds use `u > 0.5F` and `u <= 1.2F`, with Jump Boost increasing the upper bound by `0.75F` per level (`:856–862`, `:911–915`). Thus the wiki's “at least 0.5” is slightly imprecise: the source uses a strict greater-than lower bound. This is an optional client input behavior, not an unconditional physics change. The endpoint proves the implementation exists in 1.10.2, not the exact first patch release.
- **Sneak edge safety change between 1.10.2 and 1.11.2.** In 1.10.2 `Entity.move` probes for support one full block below the player (`Entity.java:484`, plus the neighboring X/combined-axis probes). In 1.11.2 the same three probes use `-this.stepHeight` (`Entity.java:522`, `:534`, `:544`); `LivingEntity` sets `stepHeight` to `0.6F`. This supports the wiki's 1.11-era description “0.6 instead of 1.0” at the available endpoints. Exact first patch within the 1.11 release is unverified.
- **Bed bounce (endpoint 1.12.2).** `BedBlock.onEntityCollision` suppresses bounce while sneaking; otherwise, when `velocityY < 0`, it assigns `-velocityY * 0.66F` (`BedBlock.java:142–147`). This verifies the stated 66% velocity retention/bounce rule, subject to the method's collision/sneak gates. The 1.12.2 endpoint does not establish the earliest 1.12 patch.
- **Single snow layer (1.12.2 vs 1.13.2).** In 1.12.2 collision height is `(layers - 1) * 0.125F`; with one layer this is a degenerate zero-height box (`SnowLayerBlock.java:64–71`). In 1.13.2 collision uses `SHAPES[layers - 1]`, whose zero index is `VoxelShapes.empty()` (`SnowLayerBlock.java:31–40, 77–80`). The representation changes from a zero-height box to an empty shape, but both endpoints produce no positive-volume collision for one layer. The wiki's “no longer tangible” wording describes a representation change; source does not show a material player collision difference in this case.
- **1.14 X/Z collision order; one MCPK page is wrong.** In 1.13.2 `Entity.move` resolves Y, then X, then Z (`Entity.java:581–598`). In 1.14.4 both collision helpers compute `bl = Math.abs(d) < Math.abs(f)` (`Entity.java:719`, `:752`): when `|X| < |Z|`, Z resolves first; otherwise X resolves first. Equality goes X then Z because `<` is strict. This matches the Version Differences table (`|Vx| >= |Vz|` → Y-X-Z). It contradicts the prose on the MCPK Collisions page, which says `|Vz| > |Vx|` yields Y-X-Z. The compared value is the requested X/Z displacement passed into the helper, before collision resolution. These helpers are also used in the step-up candidates, so their axis ordering applies there too.
- **1.14 crouch height (endpoint 1.14.4).** `PlayerEntity` defines the `SNEAKING` dimensions as `0.6 × 1.5` (`PlayerEntity.java:116`). This verifies the 1.5-block pose height at the endpoint. The source excerpt inspected did not independently establish the earlier 1.65-to-1.5 boundary, sprint input semantics while crouched, or animation timing; those remain unresolved as version-delta claims.
- **1.15 slipperiness sampling (1.14.4 vs 1.15.2).** 1.14.4 reads slipperiness at `boundingBox.minY - 1.0` (`LivingEntity.java:1841–1844`); 1.15.2 reads block friction at `minY - 0.5000001` and applies it to horizontal movement (`Entity.java:600–602`; `LivingEntity.java:1886–1889`). This verifies the sample offset change. Specific geometry examples such as beds and slabs still need exact shape and sampled-position analysis.
- **1.17 swim-start predicate (endpoint 1.17.1).** `Entity.updateSwimming` keeps swimming only while sprinting, in water, and not a passenger; when entering, it additionally requires underwater status and the fluid at `blockPosition` to be water (`Entity.java:1056–1063`). This supports the MCPK claim that the player's own block coordinates must contain water to start swimming. It is a player movement-state change; the page's separate animal-riding observation is out of scope under this track.
- **1.17 powder snow collision predicate (endpoint 1.17.1).** `PowderSnowBlock.getCollisionShape` returns the falling shape when fall distance exceeds `2.5F`; otherwise collision depends on entity context and boots/walk-on eligibility, being empty in the default case (`PowderSnowBlock.java:87–104`). This source-checks the player-relevant collision distinction at the endpoint. The wiki's detailed timing, frozen-speed, and leather-boot climbing statements were not fully adjudicated here.

### Wiki-only / unresolved

- **1.11.1 wall-height bug.** 1.11.2 `WallBlock` contains 1.5-block collision shapes; no 1.11.1 tree was provided. The “1.11.1 only” interval and fix boundary are therefore not source-proved.
- **1.13 water, bubbles, and new block shapes.** 1.13.2 source contains the new water state and player swimming flow, bubble-column entity callbacks, and new block collision shapes. This verifies those mechanics exist at the endpoint, not the full 1.12→1.13 math, flow-height/area boundaries, or every shape listed in the wiki. Blue Ice is registered with slipperiness `0.989F` (`Block.java:1870`); several other shape-dimension entries were not individually checked.
- **1.14 sprint-sneak, ceiling pose changes, blips, and listed movement bugs.** Crouch height and jump-triggered climb are verified in the additional adjudications below; sprint input, ceiling-induced poses, blip behavior and listed movement bugs remain unresolved.
- **1.15 slipperiness examples.** The sample offset is source-verified below. Specific geometry examples (beds/slabs) still need exact shape and sampled-position analysis.
- **1.16. Y=256 water-exit fix.** The relevant exact pre-fix and fixed patch releases have not been compared; this bug remains open.

- **Player effects and enchantments.** Elytra, Levitation, Frost Walker, Slow Falling, Dolphin's Grace, Riptide, and Soul Speed are potentially movement-affecting, so they are in scope when the table lists them. This audit did not adjudicate their exact player movement math or release boundaries; they remain wiki-only leads. Damage-only details, fall-damage-only mechanics, boats and other non-player entity physics, and riding-animal changes are excluded.
- **1.18.** The wiki lists TODO in Movement, Blocks, and Other. There is no claim to verify from this page.
- **After 1.18.** The inspected MCPK Version Differences page has no later sections. This audit makes no coverage claim for later campaign endpoints (including 1.19+ and 26.x).

## Exact patch sources needed to close open claims

- 1.11.1 for the wall-height interval. Cocoa endpoint geometry is compared above; a 1.11.0 source is needed only to pin the earliest correction patch.
- Smaller 1.12 patch snapshots only if an exact first-patch boundary is needed for the verified pane/fence/wall/stair changes.
- 1.12.2 / 1.13.2 player travel, fluid current, Blue Ice and the listed block collision shapes.
- 1.13.2 / 1.14.4 player climb/sneak pose and stepping paths (plus the smallest patch versions if exact first release is required).
- 1.14.4 / 1.15.2 specific slipperiness examples (bed/slab geometry).
- The lava push is source-verified below; source for the exact Y=256 bug boundary remains needed. The sneak step-down boundary is source-verified above.
- 1.16.5 / 1.17.1 for dynamic player contact with dripleaf and full powder-snow tick timing.
- Exact release pairs for each listed player effect/enchantment if those claims need to be resolved.



### Added source comparison: exact 1.16.2 step-down boundary

The shared source owner published ready Mojmap trees for 1.16.1 and 1.16.2. Both Ready JSONs have status `ready`; each source-manifest digest matches its Ready JSON, and the relevant `Player.java` hash matches the source-manifest row.

- 1.16.1 manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; `Player.java` SHA-256: `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`.
- 1.16.2 manifest SHA-256: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; `Player.java` SHA-256: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.

In 1.16.1 `Player.maybeBackOffFromEdge` requires `onGround` and `isStayingOnGroundSurface()` (`Player.java:1010–1016`). In 1.16.2 it instead requires `!abilities.flying`, a sneaking/staying-on-surface state, and `isAboveGround()` (`:1010–1018`). `isAboveGround()` returns true when on ground, or when fall distance remains below `maxUpStep` and the candidate position does not collide (`:1062–1065`). The 0.05 movement-reduction loops and `-maxUpStep` support probes remain. This directly verifies the 1.16.2 step-down correction from the wiki table.


## Additional source adjudications

- **1.11 large cocoa correction (1.10.2 vs 1.11.2).** The 1.10.2 age-2 cocoa boxes duplicate the age-1 boxes for all four facings (`CocoaBlock.java:21–39`); in 1.11.2 each age-2 box is larger and extends lower (`:21–39`). This verifies the large-stage correction between endpoints; exact first 1.11 patch is not proven.
- **1.12 stair connections and pane/bar barrier correction (1.11.2 vs 1.12.2).** In 1.11.2 fences, walls and panes connect to full-cube blocks (`FenceBlock.java:124–143`, `WallBlock.java:148–157`, `PaneBlock.java:136–143`), without inspecting stair face shapes. In 1.12.2 their predicates use the neighbor face shape, allowing stair side faces classified `SOLID` (`FenceBlock.java:124–132`, `WallBlock.java:146–158`, `PaneBlock.java:136–156`; `StairsBlock.java:140–148`). Pane/bar code also explicitly excludes barrier blocks from its connectable exception list (`PaneBlock.java:142–156`), while 1.11.2 treated cube blocks as connectable. The endpoint comparison verifies stair-side connection and the pane/bar barrier fix; exact first 1.12 patch is not established.
- **1.14 ladder/vine jump climbing (1.13.2 vs 1.14.4).** In 1.13.2 the vertical climb assist triggers only when horizontally colliding and climbing (`LivingEntity.java:1576`). In 1.14.4 its condition is `(collidingHorizontally || jumping) && isClimbing()` (`:1848`), so jump input itself permits the climb impulse. This verifies the new jump-to-climb condition at the endpoint; whether it enables additional reachable jumps and the claim about unsupported vines are not established by this predicate alone.
- **1.15 slipperiness sampling (1.14.4 vs 1.15.2).** 1.14.4 reads slipperiness at `boundingBox.minY - 1.0` (`LivingEntity.java:1841–1844`); 1.15.2 reads block friction at `minY - 0.5000001` and applies it to horizontal movement (`Entity.java:600–602`; `LivingEntity.java:1886–1889`). This verifies the sample offset change. Specific block examples such as beds and slabs still need an exact shape and sampled-position walkthrough.
- **1.13 Blue Ice.** The registry sets Blue Ice slipperiness to `0.989F` (`Block.java:1870`), matching the wiki.


- **1.16 flowing lava push (1.15.2 vs 1.16.1).** 1.15.2 `Entity.java` has no generic fluid-current push path; in 1.16.1 `updateInWaterStateAndDoFluidPushing` samples lava and calls `updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, d)` with dimension-dependent strength (`Entity.java:947–955`). The helper samples fluid heights across the player’s bounding box, adds each fluid state’s flow vector, attenuates when the sampled depth is below `0.4`, averages vectors, then applies the fluid coefficient (`:2633–2689`). This verifies the player-push and level/area behavior at 1.16.1; it does not pin the earliest 1.16 patch.

- **1.17 big dripleaf tilt and collision (endpoint 1.17.1).** `getCollisionShape` selects a leaf shape by tilt state (`BigDripleafBlock.java:249–251`). A grounded entity above the leaf threshold triggers the initial tilt server-side (`:181–186`, `:218–220`); unstable and partial states each schedule the next tilt after 10 ticks, then full resets after 100 ticks (`:45–50`, `:191–203`, `:222–230`). This verifies the wiki's 20-tick transition to full tilt and changing collision shape at the endpoint. The exact vertical shape dimensions and projectile/neighbor-signal cases were not fully checked.


