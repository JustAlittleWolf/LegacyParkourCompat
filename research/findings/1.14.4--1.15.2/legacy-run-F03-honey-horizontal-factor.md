# F03: Honey Block applies a horizontal speed factor to movement calls

- **1.14.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F03-honey-horizontal-factor.md).
- **1.15.2**, mojmap: `Blocks.java`; lines 2118–2122.

## Difference

1.14.4 has no Honey Block block or speed-factor property.

In 1.15.2, a player whose current block lookup resolves to Honey Block receives the `0.4F` factor on X and Z at the end of each `Entity.move` call.

The vertical component is multiplied by `1.0` in this call.

## Movement meaning

Local player travel -> entity collision/movement -> post-move block speed-factor lookup -> delta movement.

For a player standing on the Honey Block's 15/16-high top, the entity's current block position resolves to the Honey Block cell.

The factor is a block property registered in `Blocks`, read through `Block.getSpeedFactor`.

Source proves horizontal delta movement is multiplied by `0.4F` for the stated block lookup.

This is a block added after 1.14.4 and does not imply 1.14.4 maps can contain it or that its behavior should be emulated for old maps.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F03-honey-horizontal-factor.md).
