# F-013: Llama lowers a Player passenger's vertical position in B

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-MOUNT-INPUT, INV-STATE, INV-EXTERNAL
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player is a passenger of a Llama during its rider-position update
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap`; `net/minecraft/world/entity/animal/horse/Llama.java`, `Llama#getPassengersRidingOffset`, lines 164-166, SHA-256 `0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3fce40d20bfb04d72`. A returns `this.getBbHeight() * 0.67`.
- B artifact: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap`; same member, lines 166-168, SHA-256 `48adc67e9e4746ae08ef77e813792c192a7a69ce76e499e87b4501bd658309de`. B returns `this.getBbHeight() * 0.6`.
- Passenger placement: A `Llama#positionRider`, lines 154-161, and B same member, lines 156-163, use `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()` as the player's Y coordinate under `this.hasPassenger(entity)`. The XZ placement formulas match.
- Player reachability: `Llama` inherits `AbstractChestedHorse#mobInteract`; for a non-baby tamed Llama and an otherwise eligible interaction, it reaches `doPlayerRide(Player)`, whose server-side body calls `player.startRiding(this)`. The paired `Entity#rideTick` then calls `this.getVehicle().positionRider(this)` for a passenger; `LocalPlayer#rideTick` reaches the same superclass path. `canBeControlledByRider() == false` prevents steering the Llama, not being positioned as its passenger. Source hashes: AbstractChestedHorse.java A/B `b302997039f3b9b9db5115f796cdf1ad160eb271019420355f29098a196d5e50` / `47d22d9cc4455587211f0aa5881f8639e49250206e600a1a04d90174b5fb76a4`; Entity.java A/B `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` / `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`.

## Source-level difference and consequence

For an equal Llama bounding-box height and the same passenger, A positions the player at the Llama's Y plus `height * 0.67` plus the passenger riding offset; B uses `height * 0.6` plus that same passenger offset. B therefore writes a player position lower by `height * 0.07` on each rider-position update. This is a direct player position result, not a claim about Llama acceleration, route or vehicle physics.

## Handoff

Source-proven player passenger-position delta with a vanilla interaction path. T-MOUNT-INPUT records the broader packet, input and rider-position boundary; other vehicle movement remains outside scope. No implementation or runtime disposition is assigned.
