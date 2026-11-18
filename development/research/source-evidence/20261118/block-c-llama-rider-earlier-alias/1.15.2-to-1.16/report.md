# Llama passenger Y placement: 1.15.2 → 1.16

## Evidence status

The requested source comparison is present in the checkout's `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/` trees, but neither tree has `.decompile-provenance.json`; neither exact version has an adjacent `.minecraft-decompile-cache/<version>/` directory. The checked roots contain only `com/` and `net/` source trees. Therefore the directory names and code can be compared, but exact input-release/mapping provenance and the decompiler/tool identity are not authenticated. Treat the result below as a source-tree comparison, not accepted exact-release evidence. No alias or download was used.

Full decompiler/tool SHA-256: **unavailable** (no provenance manifest or exact-version cache metadata in this checkout). The source-file SHA-256 values below are full 64-hex digests of the bytes inspected; they do not substitute for the missing tool/input provenance.

## Compared operation

The ordinary `Llama` implementation (inherited by `TraderLlama`) directly places its passenger when `positionRider(Entity)` is dynamically called. `Entity`'s ride tick calls `this.getVehicle().positionRider(this)` after ticking a passenger: `1.15.2` `Entity.java:1545-1549`; `1.16` `Entity.java:1558-1562`. The receiver is the vehicle, so a Llama/TraderLlama reaches the Llama override.

- 1.15.2 tree, Mojmap directory: `net/minecraft/world/entity/animal/horse/Llama.java:149-160`. Under `hasPassenger(entity)`, it calls `entity.setPos(...)`; Y is `this.getY() + this.getRideHeight() + entity.getRidingHeight()`. The Llama override returns `this.getBbHeight() * 0.67` for `getRideHeight()`.
- 1.16 tree, Mojmap directory: `net/minecraft/world/entity/animal/horse/Llama.java:150-162`. Under the same `hasPassenger(entity)` gate, it calls `entity.setPos(...)`; Y is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. The Llama override returns `this.getBbHeight() * 0.67` for `getPassengersRidingOffset()`.
- Player supplies the other dynamic offset in each tree: 1.15.2 `net/minecraft/world/entity/player/Player.java:985-988` returns `-0.35` from `getRidingHeight()`; 1.16 `Player.java:988-991` returns `-0.35` from `getMyRidingOffset()`.
- `TraderLlama.java:22` declares `TraderLlama extends Llama` in both trees and has no `positionRider` override. Thus it inherits the same direct placement.
- `AbstractHorse.positionRider` contains a superclass path that delegates to `super` and may apply a stand-animation passenger offset (`1.15.2` `AbstractHorse.java:900-916`; `1.16` `AbstractHorse.java:906-922`). Llama's own override replaces that method rather than calling `super`, so that superclass placement/stand-animation path is not part of this Llama result.

## Difference and movement meaning

For a Player passenger, both trees compute the Y coordinate in the same left-to-right `double` addition order: llama Y, `getBbHeight() * 0.67`, then `-0.35`. The names of the two offset accessors changed (`getRideHeight` → `getPassengersRidingOffset`, `getRidingHeight` → `getMyRidingOffset`); the Llama formula, passenger gate, direct `setPos` write, accessor return types (`double`), and Player's returned value are unchanged. The X/Z trig expressions are also textually equivalent (`float` intermediates and `0.3F`), though outside this Y-only assignment. No Y-placement difference is established by these source trees for an ordinary adult Llama or its TraderLlama subclass.

This is the vehicle's vanilla passenger-placement operation. The source call is not itself guarded by a local-player or client-side predicate; a product historical hook would need to preserve the project invariant that historical behavior runs only on the logical client. This comparison excludes other carriers, custom subclasses, non-player passengers and vehicle/non-player physics. The use of `getBbHeight()` means age/dimension-dependent height remains dynamically supplied; no separate baby behavior is claimed here.

## Source-byte identities

SHA-256 (full source-file byte hashes, current inspected checkout):

| Tree-relative path | SHA-256 |
| --- | --- |
| `1.15.2/mojmap/net/minecraft/world/entity/animal/horse/Llama.java` | `BBE98226C5AFADDCE8CA7458C721FBEFFF592CC75E02BC261D6B440CA6877F19` |
| `1.16/mojmap/net/minecraft/world/entity/animal/horse/Llama.java` | `942FCE37F13216906FE48D620076F6514FD93D4A3983EAC8A8909170A9385C5C` |
| `1.15.2/mojmap/net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495D65DA1C1903BA769793EE4DBCCD6248EF96137C1709AF6DAA00C563C03DD4` |
| `1.16/mojmap/net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495D65DA1C1903BA769793EE4DBCCD6248EF96137C1709AF6DAA00C563C03DD4` |
| `1.15.2/mojmap/net/minecraft/world/entity/Entity.java` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` |
| `1.16/mojmap/net/minecraft/world/entity/Entity.java` | `27E3BB01B5D03C09161891E717C16477899768333082E81EBFAAC4E525616576` |
| `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` |
| `1.16/mojmap/net/minecraft/world/entity/player/Player.java` | `A849960D47BF00A2CDDFB6636C78C518532A11967BA08829F19C5EBCED08681E` |
| `1.15.2/mojmap/net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `A967D0834D3C28080725A8B639D2C8C182EE6A7209A361D462FB0225A2836E6D` |
| `1.16/mojmap/net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `7F6C50EFA62362BF5BF86A085C52292DC8B5274E27989870BEB635BE0AAC51C2` |

No project files or source trees were changed.
