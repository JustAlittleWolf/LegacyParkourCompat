# Independent source audit verdict: 1.14.1 → 1.14.2 llama passenger Y placement

## Verdict

**ACCEPTED for the bounded claim.** I independently retraced the default direct-player passenger callback and relevant interaction/boarding guards in the canonical Yarn sources. The 1.14.1 → 1.14.2 report supports no Y-placement delta for an adult vanilla Llama, or for a Trader Llama that has a direct player passenger. Trader Llama inherits the Llama placement override. Its own override affects boarding eligibility, not passenger placement. This verdict does not close the entire release-pair movement inventory and does not audit vehicle physics.

The reviewed immutable report is `c-llama-1141-1142-position-intermediate/report.md`, SHA-256 `2e25e0aec2d8f0796ce4a8d994d331e7483d091d7c4fefdfbe45cfa35559fbfc`.

## Exact sources and provenance

Canonical roots are `decompiled_minecraft/1.14.1/yarn/` and `decompiled_minecraft/1.14.2/yarn/`; both are Yarn mappings. Their manifests identify the exact client artifacts and mapping artifacts:

| Release | Yarn mapping artifact and SHA-256 | Client artifact SHA-256 | Manifest SHA-256 |
|---|---|---|---|
| 1.14.1 | `1.14.1+build.10-v2.jar` — `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| 1.14.2 | `1.14.2+build.7-v2.jar` — `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |

The report's six compared file hashes were independently recomputed and each matched its side's manifest `sources` entry:

| Relative source under each Yarn root | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/passive/LlamaEntity.java` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` | `b9d8391860e33f3c97d75eddaa6519f1c614fba81d77a3baa9f9392fd8ecb998` |
| `net/minecraft/entity/passive/TraderLlamaEntity.java` | `998fb0e745c3656115fbaa46f9febb59eb651ec6947d66b3bfb95872163308a5` | `778c024f4751bfe6770c8c12286a86c01e1c4b9b1b86bae6c4f94f8e2c0b805c` |
| `net/minecraft/entity/passive/AbstractDonkeyEntity.java` | `885ab12cd50193a5cb878c41559445c995027f5f9155b150f358aff4c2391b3c` | `71db7009e25e905e720904820e7a9873e352884335085912f52968571c65a2b2` |
| `net/minecraft/entity/passive/HorseBaseEntity.java` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` |
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |

## Audit basis

- `entity/Entity.java`: `tickRiding` does `setVelocity(Vec3d.ZERO)`, `tick()`, then invokes the vehicle's virtual `updatePassengerPosition(this)` if `hasVehicle()`; lines 1522–1528 in 1.14.1 and 1524–1530 in 1.14.2. `hasPassenger` checks equality against the direct passenger list; lines 2355–2363 and 2366–2374, respectively.
- `entity/player/PlayerEntity.java`: the sneaking dismount branch is gated by `!world.isClient`; the client follows `super.tickRiding()` at lines 437–450 in both versions. `getHeightOffset()` returns double `-0.35` at lines 952–954 in both.
- `entity/passive/LlamaEntity.java`: both classes have the same hash and the same direct-passenger-guarded `updatePassengerPosition` body at lines 147–154. Its Y argument is evaluated as `this.y + this.getMountedHeightOffset() + arg.getHeightOffset()` and passed to `setPosition`; `getMountedHeightOffset()` is the Llama override returning `this.getHeight() * 0.67` at lines 157–159. `Entity.getHeight()` returns `float` (1.14.1 lines 2571–2573; 1.14.2 lines 2582–2584), so multiplication by the double literal `0.67` promotes the height to double before addition. The exact Y path is double `y + ((float)height * double 0.67) + double(-0.35)` for a player.
- Inheritance is `TraderLlamaEntity extends LlamaEntity` (`TraderLlamaEntity.java:20`), `LlamaEntity extends AbstractDonkeyEntity` (`LlamaEntity.java:50`), `AbstractDonkeyEntity extends HorseBaseEntity` (`AbstractDonkeyEntity.java:20`), unchanged on both sides. Trader Llama has no placement override and therefore uses Llama's virtual override.
- The real ancestor placement bodies were checked. `HorseBaseEntity.updatePassengerPosition` calls `super.updatePassengerPosition`, then has a `lastAngryAnimationProgress > 0.0F` branch that repositions a passenger with an added float offset (`HorseBaseEntity.java:914–931`, identical in both releases). Llama's override does not call it, so neither the base passenger placement nor angry-animation repositioning contributes to Llama or Trader Llama Y. `AbstractDonkeyEntity.getMountedHeightOffset()` returns `super.getMountedHeightOffset() - 0.25` (`AbstractDonkeyEntity.java:56–58`), but that is likewise overridden by Llama's `getHeight() * 0.67`.
- `AbstractDonkeyEntity.interactMob` has the same relevant ordering in both versions: spawn egg delegation, adult/tamed/sneaking inventory handling, existing-passenger delegation, item handling, baby delegation, then virtual `putPlayerOnBack` (`:139–196`). `HorseBaseEntity.putPlayerOnBack` clears eating/anger and only on the server sets rider angles and calls `startRiding(this)` (`:475–482`). `TraderLlamaEntity.putPlayerOnBack` delegates only if `getHoldingEntity()` is not a `WanderingTraderEntity` (`1.14.1 :63–67; 1.14.2 :59–63`). That same conditional can prevent this interaction from attaching a player; it does not define or change the later client placement callback.

## Limits

This audit establishes only the reviewed claim's source basis and applicability. It does not claim complete 1.14.1–1.14.2 movement coverage, a boundary for intermediate patch releases, or runtime parity.
