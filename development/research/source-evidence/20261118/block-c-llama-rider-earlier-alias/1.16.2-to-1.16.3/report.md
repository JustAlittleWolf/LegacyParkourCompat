# Llama passenger Y placement: 1.16.2 → 1.16.3

## Scope and result

Compared the default adult `Llama` and inherited `TraderLlama` operation that positions a player passenger, its caller/dynamic dispatch, player riding offset, and the registered adult mount dimensions. This is an exact-source, adjacent-patch comparison in Mojmap names. Llama yaw/XZ placement is outside this bounded question; other actors, vehicle movement, and unrelated movement operations were not reviewed.

**Result: no difference found for player passenger Y placement or its tick-time routing.** The compared `Llama.java`, `TraderLlama.java`, `Entity.java`, `Player.java`, and `EntityType.java` payloads have identical SHA-256 hashes across the two source folders. The checked expression, operand types, constants, left-to-right addition order, passenger gate, and dynamic override route therefore match byte-for-byte.

## Exact source evidence

All references are `decompiled_minecraft/<version>/mojmap/` and Mojmap symbols.

| Slice | 1.16.2 | 1.16.3 | SHA-256 in both versions |
|---|---|---|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | `positionRider(Entity)`, lines 152–159; `getPassengersRidingOffset()`, 162–164 | same lines | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | class declaration and constructor, 22–27; no local rider-position/offset override | same lines | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |
| `net/minecraft/world/entity/Entity.java` | `rideTick()`, 1577–1583; generic `positionRider`, 1585–1594; default offsets, 1599–1605 | same lines | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/player/Player.java` | `getMyRidingOffset()`, 989–991 | same lines | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/EntityType.java` | `LLAMA`, 277–279; `TRADER_LLAMA`, 425–427 | same lines | `8b4c9ea61b2417dafc8bdc52a18aacb556e3ec9ce2a94fe69b307b9db2c009cd` |

## Operation and movement meaning

In both releases, `Entity.rideTick()` calls `this.getVehicle().positionRider(this)` when the player is a passenger. Virtual dispatch reaches `Llama.positionRider(Entity)`: under `this.hasPassenger(entity)`, the llama calls `entity.setPos(...)` with Y `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. Addition is left-associative and all three Y terms are `double` at the addition sites. The generic `Entity.positionRider` callback helper is not used by this Llama override, and its source is also unchanged.

`TraderLlama extends Llama` and declares no rider-position or rider-offset override, so the same implementation applies. Both registered adult types use `.sized(0.9F, 1.87F)`. For an adult mount, Llama's rider offset is `this.getBbHeight() * 0.67`; `getBbHeight()` returns `float`, so `1.87F` is promoted to `double` for multiplication by the `double` literal `0.67`. `Player.getMyRidingOffset()` returns `-0.35` (a `double`). Thus the player passenger's Y expression is, in source order, `mountY + (1.87F-as-float promoted to double * 0.67D) + (-0.35D)`, a mount-relative delta of approximately `0.902900003194809`. No version-specific change was found that could alter this Y placement.

## Source identity and provenance limit

For 1.16.2, adjacent metadata is present: `decompiled_minecraft/1.16.2/mojmap.ready.json` (SHA-256 `e5f69bab646b6143b0660a97fae59595ff2e924d36567b9cd285c8babdb31214`), `mojmap.sources.sha256` (SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`), and `mojmap.provenance.json` (SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`). The ready marker names release `1.16.2`, mapping `mojmap`, Vineflower, and the source manifest; its pinned client SHA-256 is `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. The provenance sidecar records the exact 1.16.1/1.16.2 decompile run, Mojmap option, and Vineflower 1.12.0.

The 1.16.3 folder contains only `mojmap/`; no adjacent ready marker, provenance sidecar, artifact manifest, or source hash manifest was present. Its relevant source-file SHA-256 values above were computed from the files read in place. They prove equality of the compared payloads, but the local folder does not establish independent publication provenance for the 1.16.3 source set. Treat 1.16.3 source identity as unverified unless the preparation owner can supply the canonical sidecars or regenerate/publish that exact set.

## Coverage boundary

Examined the paired Llama and TraderLlama implementations, `Entity.rideTick`/generic rider placement and offsets, Player rider offset, registered Llama/TraderLlama dimensions, and the Llama superclass declaration. No additional dependency was needed to resolve the bounded Y expression. X/Z/yaw placement was intentionally not compared for behavioral conclusions; no whole-pair movement coverage or patch-boundary claim is made.
