# 1.19.3 → 1.19.4: `Entity.turn` pitch setter slice

## Result

**No source difference in the assigned operation.** An ordinary `LocalPlayer` inherits `Entity.turn(double, double)` through `AbstractClientPlayer` → `Player` → `LivingEntity` → `Entity`; the checked subclasses do not declare another `turn` method. The direct pitch write, float conversion, intermediate ordering, and final clamp are unchanged between the exact Mojmap sources. This report covers only that direct operation and the narrowly requested input/use witnesses; it does not claim pair-wide coverage.

## Compared operation

- **1.19.3 Mojmap** — `net/minecraft/world/entity/Entity.java:389–401`: `turn` casts the vertical `double` to `float`, multiplies by `0.15F` into a `float`, calls `setXRot(getXRot() + delta)` first, updates yaw, then clamps the stored pitch to `[-90.0F, 90.0F]`. It separately adds the same float delta to `xRotO`, clamps `xRotO`, and notifies a vehicle if present.
- **1.19.4 Mojmap** — `net/minecraft/world/entity/Entity.java:391–403`: same types, expressions, call order, clamp bounds, and vehicle gate. The method's source lines moved by two; no operation change was found.
- **Setter gate:** `Entity.java:3094–3100` (1.19.3) and `3155–3161` (1.19.4) both reject a non-finite `float` with the same log call and otherwise assign `xRot`. Finite values whose cast and multiplication remain finite (including the normal device argument below) are accepted by this setter before the caller's following clamp.
- **Local-player dispatch:** `LocalPlayer.java:86` extends `AbstractClientPlayer`; `AbstractClientPlayer.java:25` / `26` extends `Player`; `Player.java:115` / `117` extends `LivingEntity`; and `LivingEntity.java:122` / `120` extends `Entity`. A `turn` declaration search across those classes returned no override on either side.

## Reachable finite large argument and movement use

The permitted input-path witness is `MouseHandler`, only to establish that the inherited operation can receive a normal finite device delta; device scheduling is not analyzed. In both releases, `onMove` adds the mouse callback displacement to `accumulatedDY` (`MouseHandler.java:196–203` in 1.19.3; `200–207` in 1.19.4). `turnPlayer` derives a finite `double` pitch argument from the accumulated delta and sensitivity, resets the accumulators, then calls `minecraft.player.turn($$7, $$8 * $$13)` (`1.19.3:208–245`; `1.19.4:212–249`). In the ordinary unsmoothed, unscoped path, sensitivity `0.5` is the declared default in `Options.java:95–101` (1.19.3) / `96–102` (1.19.4); its multiplier is approximately one, so a finite 400-pixel vertical callback delta can pass an argument greater than 360 to `Entity.turn`. For example, from a 45-degree pitch, the float delta is about 60 degrees: the first setter accepts the finite 105-degree intermediate pitch and the following clamp stores 90 degrees. This is a reachability witness, not a claim that ordinary movement uses teleport-style angle normalization.

A live movement read exists in active fall-flying travel. `Player.travel` delegates to `super.travel` for the non-ability-flight branch (`Player.java:1473–1475` in 1.19.3; `1462–1464` in 1.19.4). In the fall-flying branch of `LivingEntity.travel`, `getLookAngle()` uses `calculateViewVector(getXRot(), getYRot())` (`Entity.java:1850–1852` / `1867–1869`); the branch then reads `getXRot()` directly for its pitch radians and uses that value in the glide velocity update (`LivingEntity.java:2117–2135` / `2048–2066`). Thus the bounded pitch produced by `turn` is live movement state. The relevant pitch path is present on both sides; this comparison found no changed movement meaning.

## Exact source identities

Read-only roots: `decompiled_minecraft/1.19.3/mojmap/` and `decompiled_minecraft/1.19.4/mojmap/`. Each adjacent ready marker says `status: ready`, names its exact version and `mojmap` mapping, and records its source and artifact manifest hashes. SHA-256 values below are over the exact files shown.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` |

Original SHA-256 entries in each `mojmap.sources.sha256` manifest for files cited above:

| Source file | 1.19.3 | 1.19.4 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `net/minecraft/world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `ddb93c39a10695c793eaad8d6a1bbf5b63e57fad57850c055896109079320878` | `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf` |
| `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| `net/minecraft/client/MouseHandler.java` | `fd2b9d6c1079df233db76139a0060d4449f7b725d66f6805cc9401c2c1ff64fb` | `45e708b9d74447b2f9fa1d44b5af8098ba2414eac4552be9195b55cd299d4a32` |
| `net/minecraft/client/Options.java` | `28050b7112293b104dd09660f820ceab7bcecbec24e4f613b1aff7b9cf83707e` | `215a11c9898cbab93c2c8ed734d0b182d41f163f293dd48e2cb706a812bec23e` |