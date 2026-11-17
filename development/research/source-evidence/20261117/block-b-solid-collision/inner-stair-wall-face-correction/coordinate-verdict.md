# Independent 1.13.2 inner-stair face verdict

## Exact source identity

The canonical 1.13.2 Ornithe Feather source manifest is `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`, SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

- `net/minecraft/block/StairsBlock.java`, SHA-256 `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761`, lines 115–122.
- `net/minecraft/util/math/Direction.java`, SHA-256 `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082`, direction constants lines 16–22, clockwise rotation lines 126–139, counterclockwise rotation lines 174–187.

## Verbatim old branches and operands

`StairsBlock.getFaceShape`, with `direction = state.get(FACING)`:

```java
case INNER_LEFT:
    return direction != face && direction != face.clockwiseY() ? FaceShape.UNDEFINED : FaceShape.SOLID;
case INNER_RIGHT:
    return direction != face && direction != face.counterClockwiseY() ? FaceShape.UNDEFINED : FaceShape.SOLID;
```

`Direction.clockwiseY()` defines `NORTH -> EAST`, `EAST -> SOUTH`, `SOUTH -> WEST`, `WEST -> NORTH`. `Direction.counterClockwiseY()` defines `NORTH -> WEST`, `EAST -> NORTH`, `SOUTH -> EAST`, `WEST -> SOUTH`. Direction constants give `EAST=(+1,0,0)`, `WEST=(-1,0,0)`, `NORTH=(0,0,-1)`, and `SOUTH=(0,0,+1)`.

## Explicit north-facing evaluation

Here `face` is the side of the stair being queried, and `FACING=NORTH`.

| Shape | Queried face | `direction != face` | Rotation operand | `direction != operand` | Branch result |
|---|---|---:|---|---:|---|
| INNER_LEFT | EAST | true | `EAST.clockwiseY() = SOUTH` | true | UNDEFINED |
| INNER_LEFT | WEST | true | `WEST.clockwiseY() = NORTH` | false | SOLID |
| INNER_RIGHT | EAST | true | `EAST.counterClockwiseY() = NORTH` | false | SOLID |
| INNER_RIGHT | WEST | true | `WEST.counterClockwiseY() = SOUTH` | true | UNDEFINED |

The two comparisons are joined with `&&` and the true branch returns `UNDEFINED`; therefore the solid horizontal faces are `NORTH+WEST` for INNER_LEFT and `NORTH+EAST` for INNER_RIGHT. Equivalently: INNER_LEFT is `F` and `CCW(F)`; INNER_RIGHT is `F` and `CW(F)`.

Wall orientation check: a wall east of the stair queries the stair's EAST face and connects on INNER_RIGHT; a wall west queries WEST and connects on INNER_LEFT. This follows the 1.13.2 `WallBlock` placement/update calls passing the neighbor face `dir.getOpposite()` (placement mapping lines 75–84; update lines 100–115). Thus an inverse claim for INNER_LEFT versus INNER_RIGHT is contrary to these exact source expressions when “side” means the world direction from stair toward wall.

Signed coordinates use Minecraft convention (`+X=EAST`, `+Z=SOUTH`). A diagram with `+Z=NORTH` would reverse page-based rotational labels only; the source-defined result above remains unchanged.
