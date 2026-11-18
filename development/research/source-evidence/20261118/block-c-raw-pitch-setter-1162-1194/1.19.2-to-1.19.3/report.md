# 1.19.2 → 1.19.3 pitch setter and Elytra movement slice

## Scope and evidence identity

Windows PowerShell; exact local Mojmap decompilations only. Both version-root ready markers declare status `ready`, mapping `mojmap`, Vineflower, Java runtime 25, and matching version IDs. Their declared source-manifest and artifact-manifest SHA-256 values match the files read:

- 1.19.2: source manifest `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; artifact manifest `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`.
- 1.19.3: source manifest `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; artifact manifest `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`.

Pinned source file SHA-256 values (also present as matching entries in each source manifest):

| Version | Source | SHA-256 |
| --- | --- | --- |
| 1.19.2 | `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.3 | `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.2 | `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.3 | `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| Both | `net/minecraft/client/MouseHandler.java` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` |

## Direct `Entity.turn` comparison

`Entity.turn(double, double)` is text-identical in both mappings at `Entity.java:389–402`:

```java
float $$2 = (float)$$1 * 0.15F;
float $$3 = (float)$$0 * 0.15F;
this.setXRot(this.getXRot() + $$2);
this.setYRot(this.getYRot() + $$3);
this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F));
this.xRotO += $$2;
this.yRotO += $$3;
this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
```

The first pitch write is `setXRot(current + scaledDelta)`, before the explicit current-pitch clamp. The later old-pitch accumulation is independently clamped. `setXRot` accepts any finite float and writes it directly; only a non-finite argument is logged/discarded (1.19.2 line 3059, 1.19.3 line 3094).

A finite ordinary supplied delta reaches the out-of-range write: with starting pitch `0.0F` and vertical delta `1000.0` (finite double), the cast stays finite and `1000.0F * 0.15F` is `150.0F`; the first setter stores `150.0F`, then the explicit clamp stores `90.0F`. `xRotO` similarly becomes `90.0F` after its add and clamp. Thus this path does not depend on an infinite cast or a rejected setter call. The same result holds with the opposite sign at `-90.0F`.

As input context only, both `MouseHandler.java:244` bodies pass `player.turn($$7, $$8 * $$13)` and the files have the same SHA-256. No callback scheduling or device plumbing was examined.

## Live active-Elytra movement consumer

In both `LivingEntity.java` versions, `travel(Vec3)` enters movement when `isEffectiveAi() || isControlledByLocalInstance()`, then has an `else if (this.isFallFlying())` movement branch. For a local player, `Player.travel(Vec3)` delegates to `super.travel($$0)` in its ordinary non-abilities-flying path. Inside the Elytra branch the real movement math reads both:

- `this.getLookAngle()`, which `Entity.getLookAngle()` forms from `calculateViewVector(this.getXRot(), this.getYRot())`;
- `float $$15 = this.getXRot() * (float)(Math.PI / 180.0)`, used in the cosine, sine and pitch-sign gliding terms.

Therefore the finite-delta `Entity.turn` path feeds the pitch actually used by active gliding movement; after the normal turn call it is clamped to ±90° before these reads. The relevant pitch reads and gliding formulas match across the pair. The 1.19.3 branch adds `this.checkSlowFallDistance()` at the start of its Elytra branch; it does not replace or alter these pitch reads/formulas.

## Result

No 1.19.2→1.19.3 source difference found in the assigned `Entity.turn` pitch-write/clamp order or the active-Elytra pitch consumer. A legal finite delta can produce a finite out-of-range first setter value and then reach the ordinary ±90° clamp in both versions.
