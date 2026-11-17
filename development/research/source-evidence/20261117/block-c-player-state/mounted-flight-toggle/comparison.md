# Passenger creative flight toggle source comparison

## Result

The local jump-double-tap flight toggle changes between 1.21.10 and 1.21.11. In 1.21.9 and 1.21.10, once `Abilities.mayfly` permits flight, the second rising jump edge toggles `Abilities.flying` whenever the player is not swimming; passenger status is not checked. In 1.21.11, 26.1.2 and 26.2, that same edge additionally requires either no vehicle or a non-null `jumpableVehicle()`. The current passenger is allowed only when the client recognizes a jumpable rideable.

The exact stable comparison boundary is therefore 1.21.10 -> 1.21.11. The check is still present in 26.1.2 and 26.2. This comparison establishes the endpoint boundary only; it does not identify the first intermediate release that introduced it.

## Input-edge and permission path

`LocalPlayer.aiStep` snapshots whether jump was already pressed, then gates the flight path on `abilities.mayfly` (named `Abilities.mayfly` in Mojmap and `abilities.mayfly` in the unobfuscated sources). Spectator mode forces flying on when it is off. Otherwise, a fresh jump press (`!wasJumping && jumpPressed && !wasAutoJump`) starts a seven-tick trigger on the first press. On the next qualifying press, the older sources check only `!isSwimming()`; the newer sources also require `(getVehicle() == null || jumpableVehicle() != null)`. On success, the code flips `flying`, conditionally invokes `jumpFromGround()` if newly flying and on ground, updates abilities, and clears the trigger. Thus the difference concerns players whose actual ability state has `mayfly == true`; it does not grant flight to ordinary passengers. Spectators use the separate always-flying branch.

A bounded vanilla witness is a player passenger in a boat while `mayfly == true` and not in spectator mode. The older predicate admits the second press because it has no vehicle check. In 1.21.11 and 26.x, `getVehicle() != null`, while `jumpableVehicle()` recognizes a `PlayerRideableJumping` vehicle and the vanilla boat is an `AbstractBoat`, not that interface, so this witness cannot toggle flight. A horse remains an eligible passenger when `jumpableVehicle()` returns its jumpable implementation. This is only the passenger eligibility predicate; it makes no claim about vehicle movement or jump physics.

## Immediately local flying movement

The later `LocalPlayer.aiStep` branch is retained in all five sources: when `flying && isControlledCamera()`, jump contributes +1 and shift contributes -1 to a local vertical input value; if nonzero, the player immediately adds `input * getFlyingSpeed() * 3.0F` to its own delta movement Y. `isControlledCamera()` is `minecraft.getCameraEntity() == this`. Thus, after an allowed passenger toggle, the held jump/shift input can directly alter the local player velocity in that tick when the player is the camera entity. The old/new difference is reachability of that local state through a passenger double-tap; the vertical-control operation itself has no change in these comparisons. It is not a claim that the vehicle's physics changes.

## Exact source evidence

All paths below are relative to `decompiled_minecraft/` in the primary checkout. Each listed SHA-256 is the exact local `LocalPlayer.java` source file hash; it matches the source manifest or, for 1.21.9, the `sources` entry in `.decompile-provenance.json`.

- `1.21.9/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, flight input around lines 751-775 and local flying vertical input around 791-803; source SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`. Mapping family: Mojmap. Its `.decompile-provenance.json` identifies version input `1.21.9`, with client JAR SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b` and Mojang mapping input `1.21.9.json` SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`.
- `1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 751-775 and 791-803; source SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`. Mapping family: Mojmap; exact run recorded in `mojmap.provenance.json` for release `1.21.10`.
- `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 792-817 for flight input and 832-844 for local vertical input; source SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. Mapping family: Mojmap; exact run recorded in `mojmap.provenance.json` for release `1.21.11`.
- `26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 831-856 for flight input and 871-883 for local vertical input; source SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. Mapping family: unobfuscated; exact run recorded in `unobfuscated.provenance.json` for release `26.1.2`.
- `26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 831-856 for flight input and 871-883 for local vertical input; source SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`. Mapping family: unobfuscated; exact run recorded in `unobfuscated.provenance.json` for release `26.2`.

The witness type check is visible in `1.21.9/mojmap/net/minecraft/world/entity/vehicle/AbstractBoat.java:50` (`AbstractBoat extends VehicleEntity`) and `1.21.9/mojmap/net/minecraft/world/entity/animal/horse/AbstractHorse.java:78` (`AbstractHorse ... implements PlayerRideableJumping`); the same boat inheritance appears in `1.21.11/mojmap/.../AbstractBoat.java:52` and `26.2/unobfuscated/.../AbstractBoat.java:52`. `LocalPlayer.jumpableVehicle()` in these Mojmap/unobfuscated sources returns a vehicle only when `getControlledVehicle()` implements `PlayerRideableJumping` and `canJump()` is true.

## Source identity note

The `1.21.10` and `1.21.11` canonical roots have `mojmap.provenance.json`, `.ready.json`, and `.sources.sha256`; the `26.1.2` and `26.2` roots have corresponding unobfuscated metadata. The 1.21.9 root has no sibling ready/provenance wrapper, but its LocalPlayer hash agrees with the embedded `.decompile-provenance.json` source record, whose inputs identify the exact 1.21.9 client JAR and Mojang mapping artifact. No source generation or runtime checks were performed.