# Independent source review: sticky piston edge support

**Input revision:** `.native-source/20261031/1194-1204-pool54/block-responses-residual/sticky-piston-edge-support.md`
**SHA-256:** `E53178BD29564A699C778C857214A6FD69F071D42049F720B3ACA3A4E30D32B3`
**Decision: CONDITIONAL — semantic finding accepted; exact provenance for 1.20 and 1.20.3 needs a manifest identity**

## Source comparison

The 1.19.4 `PistonMovingBlockEntity.moveStuckEntities` and `matchesStickyCritera` source matches the draft at lines 186–208. It only admits a sticky entity when `PushReaction.NORMAL`, on-ground, and its center X/Z is within the translated scan AABB. The scan maximum Y is `1.5000000999999998`.

In 1.20–1.20.4, the moving-honey scan maximum Y is `1.5000010000000001`. The matching predicate retains normal push reaction and on-ground gates, then admits either `Entity.isSupportedBy(movingBlockPos)` or the former center X/Z checks. `moveStuckEntities` still calls `moveEntityByPiston(direction, entity, newProgress - progress, direction)`, so accepted entities receive the same per-tick movement delta in the piston direction. The branch is reached only for honey and horizontal movement. PistonMovingBlockEntity source is byte-identical across 1.20, 1.20.1, 1.20.2, 1.20.3, and 1.20.4.

## Support state and player reachability

In 1.20+, `Entity.isSupportedBy(pos)` checks whether `mainSupportingBlockPos` is present and equals `pos`. `Entity.setOnGroundWithKnownMovement` sets `onGround` and calls `checkSupportingBlock`; when grounded, that method builds a thin AABB just below the entity's feet and calls `CollisionGetter.findSupportingBlock`. The latter enumerates block collisions in that AABB and selects the nearest supporting block. `MovingPistonBlock.getCollisionShape` delegates to the moving block entity, whose collision shape includes the translated moved honey shape during active movement. Thus an entity with a bounding-box edge overlapping that moved shape can have the moving piston position as its recorded support. These methods are inherited by Player/LocalPlayer; searches of the six release source trees found no `isSupportedBy` or `setOnGroundWithKnownMovement` overrides in player subclasses. In 1.19.4 neither support-position API exists.

A concrete reachable geometry is an isolated honey block moving east with its translated collision footprint beginning at x=0.1 and ending at x=1.1. Let a grounded normal-pushable player have center x=0.0 and bounding box x=[-0.3, 0.3], with feet at y=1 and center z=0.5. The player's box overlaps the honey block at x=0.1–0.3, so the under-feet support search can record that moving block's BlockPos; the player center x=0.0 lies outside the scan footprint's minX=0.1. With the honey moving away from this left-edge player, the ordinary collision-push path does not supply the same carry. The new support predicate admits the player, while the old center test rejects it. `moveStuckEntities`' entity query also sees the overlapping player box. This is a direct player movement response in the client world; the vanilla moving-piston ticker is not guarded to server-only, and the same local-player physics path executes in ClientLevel. Honey pistons predate both compared releases, so this is not a modern-only feature.

The support selector has a dependency: the player's movement must have populated `mainSupportingBlockPos` from a grounded support query for the current moving-piston position. Normal entity movement does this through `setOnGroundWithKnownMovement`; the condition is reachable for the isolated edge-contact setup above. No separate unclosed source dependency was found.

## Source hashes and metadata gate

Computed Mojmap file SHA-256s for `PistonMovingBlockEntity.java`, in order 1.19.4 / 1.20 / 1.20.1 / 1.20.2 / 1.20.3 / 1.20.4:

`0089969B2CA412EC2EDB3A2A58ADAEC245C392E51FC114E403B43AC7F301EC03` / `ECBE61885F9D5ECB3229427FD4082E8324B50894DA83D65E2D2A4B5F0DB2D4D0` / same / same / same / same.

The entity support implementation SHA-256s are `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B` (1.19.4), `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` (1.20 and 1.20.1), `D7EE49AAEA5E862B92508E767562CABC8F10515605E8FB67565C8C8D5C23B01B` (1.20.2), and `07383522BFF169938136638EF8C3244CA511B56CA4266913524F99F9821331B9` (1.20.3 and 1.20.4). CollisionGetter hashes: `ED62E6B800EC057C1269D3CC4E2FB7C0CC2037370223FAC99F41371E000C6B5C` (1.19.4), `8E4863AFE40705497B36D0A58D8EE11E5103F4E72CB642AA63CC57F57649A130` (1.20 and 1.20.1), and `F21DAA1ED0365CC16A2638373D702FBAEB4242AD12A80A41105CF43C711E6D95` (1.20.2–1.20.4). All raw source files were inspected under the stated release's Mojmap source root.

The root Mojmap source manifests are present and contain matching hashes for 1.19.4, 1.20.1, 1.20.2, and 1.20.4; their source-manifest SHA-256s are respectively `6286E325371E085DFEE1AB7987E66D4B6D1A984A49B6EC8EA730045CD24CECB3`, `858B56764113E591C60D09BC63E4D90F9C1D67A705645F0C793E8B28F2378465`, `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887`, and `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1`. The `decompiled_minecraft/1.20` and `1.20.3` roots have only a `mojmap` source directory and no ready/provenance/source-manifest sidecars. I therefore cannot independently bind those two raw trees to their embedded version/mapping input identities from the permitted source tree. Close that provenance check to promote this to ACCEPT; no semantic or reachability correction is otherwise needed.

## Final provenance closure

**Input revision:** `.native-source/20261031/1194-1204-pool54/block-responses-residual/sticky-piston-edge-support.md`
**SHA-256:** `E53178BD29564A699C778C857214A6FD69F071D42049F720B3ACA3A4E30D32B3`
**Final decision: ACCEPT**

The source-identity gate is closed by the method-owner audit: `PistonMovingBlockEntity.java`, `Entity.java`, and `Player.java` hashes match the exact per-release source identity manifests/maps for all six releases. The embedded manifest identities for 1.20 and 1.20.3 bind the exact version metadata and official Mojmap input; root manifests for 1.19.4, 1.20.1, 1.20.2, and 1.20.4 also match the checked files. The semantic and reachability findings above stand; no additional gate remains.
