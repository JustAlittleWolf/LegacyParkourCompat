# Independent source audit — Llama rider Y placement

## Verdict

**Accepted, within the stated scope.** The frozen report's conclusion is supported: for one direct adult player passenger on a default-size adult Llama or Trader Llama, the client rider-position callback has the same Y calculation in 1.16.3 and 1.16.4. The exact callback reaches the Llama override by virtual dispatch. This is a bounded no-difference result, not a full-pair coverage or runtime-parity verdict.

## Frozen input identity

- Input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1163-1164-position-intermediate/report.md`
- SHA-256 recomputed directly from the input bytes: `1ca253969bd843101e165455f73bcf58f13e461906096a2f132422e04e82903a` (matches the assigned frozen identity).

## Exact-source provenance

The only source versions inspected were the canonical `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/` trees. Their `.decompile-provenance.json` files identify official Mojang client JAR and `client.txt` mapping inputs, Mojmap output, and the same decompiler pipeline. Recomputed provenance-manifest SHA-256 values match the frozen report:

- 1.16.3 manifest: `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; client JAR `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; `client.txt` `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4 manifest: `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`; client JAR `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

For both sides, the declared manifest hashes matched recomputed hashes for all six cited source files. Each file's hash was identical across the two versions. This binds the comparison to the stated exact source trees; it does not assert evidence for any other release alias.

## Callback and Y expression

All paths below are relative to the corresponding `decompiled_minecraft/<release>/mojmap/` directory; cited bodies were checked in both releases and are byte-identical for the six referenced files.

- `net/minecraft/client/player/LocalPlayer.java:837–845`: `rideTick()` calls `super.rideTick()`.
- `net/minecraft/world/entity/player/Player.java:452–465`: `rideTick()` stops riding when `wantsToStopRiding() && isPassenger()`; otherwise it calls `super.rideTick()`. Thus the placement callback is reached only through the non-dismount branch and subsequent passenger gate.
- `net/minecraft/world/entity/Entity.java:1577–1583`: `rideTick()` zeroes rider delta movement, ticks the rider, and, if still a passenger, invokes `this.getVehicle().positionRider(this)`. This is a virtual call on the runtime vehicle.
- `net/minecraft/world/entity/animal/horse/Llama.java:152–164`: `positionRider(Entity)` applies `hasPassenger(entity)` and calls `entity.setPos(...)`; its Y argument is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. Its offset override is `this.getBbHeight() * 0.67`.
- `net/minecraft/world/entity/player/Player.java:989–991`: player `getMyRidingOffset()` returns the unsuffixed double literal `-0.35`.
- `net/minecraft/world/entity/EntityType.java:277–279, 425–427`: both `LLAMA` and `TRADER_LLAMA` register `.sized(0.9F, 1.87F)`.
- `net/minecraft/world/entity/animal/horse/TraderLlama.java:22–27`: `TraderLlama extends Llama` and invokes the superclass constructor. Search of each full `TraderLlama.java` found no `positionRider` or `getPassengersRidingOffset` declaration, so the Llama implementations apply to Trader Llama as well.

For the default adult size, `getBbHeight()` supplies float `1.87F`. Multiplication by unsuffixed `0.67` promotes the float value to double; the player offset is also double. The Y operations therefore remain ordered double additions: `vehicleY + ((double)1.87F * 0.67D) + (-0.35D)`. At vehicle Y `0`, the computed result is `0.90290000319480901` (approximately `0.902900003194809`). The same source bodies, gates, callback dispatch, dimensions and numeric types occur on both sides.

## Applicability boundary

This audit covers only the normal client `rideTick()` position update for one direct adult player passenger on a commonly used default-size adult Llama or Trader Llama, while the player remains mounted through that update. It does not establish behavior for other carrier classes, other passenger counts or nested riding, babies or resized entities, other mount/update callbacks, or any older/later release or alias. It does not close the pair's other movement inventory or establish runtime parity.