# Direct pitch response: Minecraft 1.16 → 1.16.1

## Verdict

No source difference was found in the assigned direct `Entity.turn` pitch response or its immediate player Elytra consumer. The exact Mojmap `Entity.java`, `LivingEntity.java`, and `Player.java` payloads are byte-identical across these releases. The examined release pair therefore supplies no change finding for this operation.

## Exact source evidence

- 1.16, Mojmap: `decompiled_minecraft/1.16/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double, double)` lines 332–344; `setRot(float, float)` lines 316–319; `absMoveTo(...)` lines 1104–1117; `moveTo(..., float, float)` lines 1129–1133.
- 1.16.1, Mojmap: same relative file and members/line ranges.
- Both `Entity.java` files: SHA-256 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`.
- Both `LivingEntity.java` files: SHA-256 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`.
- Both `Player.java` files: SHA-256 `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`.
- 1.16 `mojmap.ready.json` source-manifest SHA-256: `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; exact version ID `1.16`, Mojmap, ready.
- 1.16.1 `mojmap.ready.json` source-manifest SHA-256: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; exact version ID `1.16.1`, Mojmap, ready.

## Pitch response and numeric order

In `Entity.turn`, the vertical delta is the double `e`; `f = e * 0.15` is also double. The current pitch is promoted for `xRot + f`, then cast to float before `Mth.clamp(float, -90.0F, 90.0F)`. `xRotO` is separately updated by the same double delta, cast to float, then clamped. Thus the current pitch's final write is bounded to `[-90F, 90F]`; the method does not normalize it modulo 360. The vehicle callback follows both pitch updates and does not alter this comparison.

For a finite, large but representable direct-turn input, start with `xRot = 0F` and pass `e = 2401.25`: `f = 360.1875` (double), the float cast remains `360.1875F`, then the final clamp stores `90F`. This is the same in both releases. An immediately active Elytra move reaches `Player.travel` → `LivingEntity.travel` (`Player.java` lines 1380–1405; `LivingEntity.java` lines 1912–1989). With the fall-flying branch active, the consumer reads current `this.xRot` at `LivingEntity.java` line 1984 and computes `j = this.xRot * (float)(Math.PI / 180.0)`; it therefore reads `90F`, not the intermediate `360.1875F`. The following glide equations use `j` and the look vector derived from current rotation.

`Entity` has no `setXRot` method in either release. The adjacent raw rotation entry points are distinct: `absMoveTo` accepts a float pitch and evaluates `Mth.clamp(h, -90F, 90F) % 360F`; `moveTo(..., g, h)` stores `h` directly. Those assignments are byte-identical too. This report's bounded claim is specifically the ordinary `turn` response and its immediate active-Elytra read; input-device plumbing and the other position/rotation entry-point call contexts were not inventoried. No callback scheduling, rendering, teleport, or network path was used to establish the claim.

## Coverage and remaining limit

Compared the complete `turn`, `setRot`, `absMoveTo`, and explicit-angle `moveTo` method bodies in `Entity.java`, plus the player-to-living `travel` path through the fall-flying pitch read. All files and those relevant method bodies are identical byte-for-byte for 1.16 and 1.16.1. This closes the assigned operation-level comparison only; it does not establish full-pair movement coverage or intermediate patch boundaries.
