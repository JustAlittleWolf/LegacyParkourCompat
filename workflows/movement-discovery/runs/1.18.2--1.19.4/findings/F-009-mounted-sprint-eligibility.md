# F-009: Historical mounts stop allowing the passenger to start sprinting

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 1 local sprint eligibility and passenger state
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior while riding a 1.18.2-era mount
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()`, sprint-start branches around lines 687–716; SHA-256 `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`. The manual sprint-key branch checks sprint state, water/underwater status, forward impulse, food/flight ability, item use and blindness; it does not check passenger status or vehicle capability before `setSprinting(true)`.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()` around lines 685–708 and `canStartSprinting()` / `vehicleCanSprint(Entity)` around lines 1022–1033; SHA-256 `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`. The sprint-key branch calls `canStartSprinting()`, which requires `!isPassenger() || vehicleCanSprint(getVehicle())`; the helper requires both `vehicle.canSprint()` and `vehicle.isControlledByLocalInstance()`.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `canSprint()` returns `false` by default; SHA-256 `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`. A has no corresponding `Entity.canSprint()` method; its paired `LocalPlayer.aiStep()` contains the complete eligibility path above.
- B full-tree override check: the only `canSprint()` overrides are `Player` and `Camel`. Camel is new in B and outside A's feature intersection; the Player override is not a historical mount implementation. The shared historical vehicles do not override the default. The 1.18.2 source tree has no `canSprint()` declaration or override.

## Source-level difference

For a local player riding a shared historical mount, holding sprint with sufficient forward input and food (or mayfly ability), while not using an item or blinded and outside water, A can call `setSprinting(true)`. B's `canStartSprinting()` reaches `vehicleCanSprint()` because the player is a passenger, then rejects the start because that historical vehicle inherits `Entity.canSprint() == false`. A's manual sprint-key branch has no mount eligibility check. This finding is limited to this passenger sprint-start path; B's modern Camel-specific opt-in is outside the historical map feature set.

## Reachability and dependencies

Local-player tick/input -> `LocalPlayer.aiStep()` sprint-key branch -> `setSprinting(true)` in A, or B `canStartSprinting()` -> vehicle capability/control checks -> sprint flag. A's food condition is `foodLevel > 6 || mayfly`; B's helper additionally treats a passenger as having enough food, but the common historical vehicle fails the earlier `canSprint()` check. The setter updates the player's sprint state and the shared living-entity sprint movement-speed modifier path documented in slice 3.11. Server authority and state synchronization are not established by this client-source slice.

## Consequence and uncertainty

Source proves that under the stated conditions the passenger sprint flag can become true in A and is refused in B for the shared historical mount set. The flag is a player movement input and the setter applies the sprint movement-speed modifier; this comparison does not establish a changed mounted vehicle trajectory or measure movement after dismount. No position/velocity trace was run. Exact introduction release remains unknown within `(1.18.2, 1.19.4]`.

## Handoff

Related to F-003 and F-004 through the sprint flag and its speed consumers, but independent from their numeric-value and tick-order deltas. Full passenger/sprint lifetime, server synchronization, and mount movement effects remain open. Runtime validation is deferred.
