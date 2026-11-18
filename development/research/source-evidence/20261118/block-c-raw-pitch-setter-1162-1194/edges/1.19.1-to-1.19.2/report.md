# 1.19.1–1.19.2 Entity.turn pitch response: checked, no difference

## Exact source identity

Both sides are ready Mojmap decompilations. The read-only markers identify versions `1.19.1` and `1.19.2`, mapping `mojmap`, and Vineflower output. Their `mojmap.sources.sha256` manifest digests match the ready markers and provenance inventory:

- 1.19.1 source-manifest SHA-256: `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`.
- 1.19.2 source-manifest SHA-256: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`.

The exact source-manifest entries and recomputed SHA-256 values agree for each compared file. In both releases, `net/minecraft/world/entity/Entity.java` hashes to `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; `LivingEntity.java` to `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; `Mth.java` to `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f`; and `Player.java` to `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`. Each pair of files is byte-identical.

## Bounded comparison

- 1.19.1 Mojmap, `net/minecraft/world/entity/Entity.java`, `Entity.turn(double,double)`, lines 389–400.
- 1.19.2 Mojmap, same file/member/lines.
- 1.19.1 and 1.19.2 Mojmap, `Entity.setXRot(float)` and `Entity.getXRot()`, lines 3055–3065; `Mth.clamp(float,float,float)`, `net/minecraft/util/Mth.java`, lines 121–127.
- 1.19.1 and 1.19.2 Mojmap, `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1464–1493; `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)` fall-flying branch, lines 2107–2138.

`Entity.turn` computes pitch delta as `float deltaPitch = (float) yDelta * 0.15F`, calls `setXRot(getXRot() + deltaPitch)` first, then changes yaw, then calls `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`. It next adds the same pitch delta to `xRotO` and clamps that previous pitch. This operation contains no modulo. The `setRot` method nearby has `% 360.0F`, but it is a separate operation and is not called by `turn` here. The setters reject non-finite values before storing; `Mth.clamp` uses the same `< lower` then `> upper` comparisons in both releases.

The order and expressions are identical. For a lawful large finite example, `yDelta = 1.0e38` is finite as a `double`; narrowing yields a finite `float` near `1.0e38`, and multiplication by `0.15F` yields a finite pitch delta near `1.5e37`, not infinity. Starting from ordinary pitch zero, the first setter accepts that finite intermediate value and the final setter clamps the stored pitch to `90.0F`. The previous pitch update is clamped to the same bound. This path is the same in both releases.

The live player movement consumer is present in both versions: `Player.travel` delegates to `super.travel` when the player is not in the abilities-flying branch; `LivingEntity.travel` then has an `isFallFlying()` branch which reads `getLookAngle()` and `getXRot()` to update glide velocity before calling `move`. Thus the pitch response can feed player Elytra movement, but its source behavior and this consumer are unchanged across this pair.

## Result and limit

No source difference was found in the assigned direct pitch response or its live Elytra movement consumer. This closes only that bounded source slice; it does not establish complete 1.19.1→1.19.2 movement coverage or a pair freeze. No permanent difference finding is warranted from this comparison.
