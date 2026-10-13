# F-011: Chorus-fruit random teleport rejects a candidate already overlapping the border shape in 1.18.2

- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/item/ChorusFruitItem.java`; lines 19-49.
- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/item/ChorusFruitItem.java`; lines 19-49.

## Difference

For a block-supported candidate whose player AABB is otherwise free of block/entity collisions and contains no liquid, choose a border and candidate box that overlaps the outside-of-floor/ceiling border collision shape by more than `1.0E-7`, while the entity center still passes 1.18.2's `isInsideCloseToBorder` proximity guard. 1.17.1's `worldBorderCheck` sees that the deflated AABB already intersects the outside shape (`isOutsideBorder`), so it does not yield that shape; block/entity checks can therefore accept the candidate. 1.18.2 supplies the border shape and `noCollision` rejects the same candidate.

For example, with the maximum border at X=10.5 (collision shape begins beyond ceil(X)=11), a width-0.6 player centered at X=10.9 overlaps the border shape while remaining within 1.18.2's reported two-block proximity margin.

Chorus fruit tries this method for a player on the server, so a differing first candidate can stop 1.17.1's loop while 1.18.2 continues to a later candidate, or make success/failure differ.

## Movement meaning

The direct player destination acceptance is in movement scope.

Food/hunger production and the implementation of the chorus-fruit random selection remain outside the movement mechanic; the unchanged caller supplies equivalent candidates to the paired movement collision gate.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-011-chorus-fruit-border-teleport.md).
