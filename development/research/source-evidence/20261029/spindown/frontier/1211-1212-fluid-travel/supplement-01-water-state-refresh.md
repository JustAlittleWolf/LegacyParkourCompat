# Supplement 01 — water-state refresh gate

This supplement is bound to `report.md` SHA-256 `6a53084d8a29f53e2614b7068726347d95ef98270635458b05031c289c921d9b` and leaves that report unchanged.

The upstream `Entity.updateInWaterStateAndDoWaterCurrentPushing` gate differs: 1.21.1 checks whether `getVehicle()` is a `Boat`, while 1.21.2 checks `AbstractBoat`, before deciding to clear `wasTouchingWater` for an above-water boat or run the water-height scan. The `updateFluidHeightAndDoFluidPushing` scan itself is token-identical across these releases. An unmounted player reaches the same scan in both versions. Passenger and vehicle-specific water-state behavior remains outside this unmounted fluid-travel scope and is excluded by the project boundary on vehicle physics; this source difference does not establish an ordinary local-player movement delta.
